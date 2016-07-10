package astraeus.net.codec.login;

import astraeus.net.codec.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import astraeus.net.NetworkConstants;
import astraeus.net.codec.IsaacCipher;
import astraeus.net.codec.ProtocolConstants;
import astraeus.util.LoggerUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

/**
 * The class that handles a connection through the login protocol.
 * 
 * @author Seven
 */
public final class LoginDecoder extends ByteToMessageDecoder {

	/**
	 * The single logger for this class.
	 */
	private static final Logger LOGGER = LoggerUtils.getLogger(LoginDecoder.class);

	/**
	 * The size of the encrypted data.
	 */
	private int encryptedLoginBlockSize;

	/**
	 * The current state of this connection through the login protocol.
	 */
	private LoginDecoderState state = LoginDecoderState.CONNECTION_TYPE;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		switch (state) {

		case CONNECTION_TYPE:
			decodeConnectionType(ctx, in);
			break;

		case PRECRYPTED:
			decodePreCrypted(ctx, in);
			break;

		case CRYPTED:
			decodeCrypted(ctx, in, out);
			break;

		}
	}

	/**
	 * The stage that decodes the type of connection.
	 * 
	 * {@code 16} denotes a new connection
	 * 
	 * {@code 18} denotes a reconnection.
	 * 
	 * @param in
	 *            The incoming data.
	 */
	private void decodeConnectionType(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if (in.readableBytes() >= 2) {
			int connectionType = in.readUnsignedByte();

			if (connectionType != ProtocolConstants.NEW_CONNECTION_OPCODE && connectionType != ProtocolConstants.RECONNECTION_OPCODE) {
				LOGGER.info(String.format("[host= %s] was rejected for having the wrong connection type.", ctx.channel().remoteAddress()));
				LoginUtils.sendResponseCode(ctx, LoginResponse.LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			state = LoginDecoderState.PRECRYPTED;
		}
	}

	/**
	 * The stage that decodes the login block size.
	 * 
	 * @param in
	 *            The incoming data.
	 */
	private void decodePreCrypted(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		// 78 is the amount of readable bytes as of now, after the size of the encrypted data is read then the readable bytes
		// become 77, when the value is read to indicate the size of the encrypted login block it should read the same.
		// so if the readable bytes is 77 at this point, then it will read 77.

		if (in.readableBytes() >= 78) {
			// reads 77, if you place extra data into the login block then this will change.
			encryptedLoginBlockSize = in.readUnsignedByte();

			if (encryptedLoginBlockSize != in.readableBytes()) {
				LOGGER.info(String.format("[host= %s] encryptedLoginBlockSize != readable bytes", ctx.channel().remoteAddress()));
				LoginUtils.sendResponseCode(ctx, LoginResponse.LOGIN_SERVER_REJECTED_SESSION);
			}

			state = LoginDecoderState.CRYPTED;
		}
	}

	/**
	 * The stage that decodes the encrypted data.
	 * 
	 * @param in
	 *            The incoming data.
	 */
	private void decodeCrypted(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.isReadable(encryptedLoginBlockSize)) {
			int magicValue = in.readUnsignedByte();

			if (magicValue != 255) {
				LOGGER.info(String.format("[host= %s] [magic= %d] was rejected for the wrong magic value.", ctx.channel().remoteAddress(), magicValue));
				LoginUtils.sendResponseCode(ctx, LoginResponse.LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			int clientVersion = in.readUnsignedShort();

			if (clientVersion != 317) {
				LOGGER.info(String.format("[host= %s] [version= %d] was rejected for the wrong client version.", ctx.channel().remoteAddress(), clientVersion));
				LoginUtils.sendResponseCode(ctx, LoginResponse.LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			int memoryVersion = in.readUnsignedByte();

			if (memoryVersion != 0 && memoryVersion != 1) {
				LOGGER.info(String.format("[host= %s] was rejected for having the memory setting.", ctx.channel().remoteAddress()));
				LoginUtils.sendResponseCode(ctx, LoginResponse.LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			int[] crcs = new int[9];

			for (int index = 0; index < crcs.length; index++) {
				crcs[index] = in.readInt();
			}

			int expectedSize = in.readUnsignedByte();

			if (expectedSize != encryptedLoginBlockSize - 41) {
				LOGGER.info(String.format("[host= %s] was rejected for having the wrong client settings.", ctx.channel().remoteAddress()));
				LoginUtils.sendResponseCode(ctx, LoginResponse.LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

				byte[] rsaBytes = new byte[encryptedLoginBlockSize - 41];
				in.readBytes(rsaBytes);

				ByteBuf rsaBuffer = Unpooled.wrappedBuffer(new BigInteger(rsaBytes).modPow(NetworkConstants.RSA_EXPONENT, NetworkConstants.RSA_MODULUS).toByteArray());

				int rsa = rsaBuffer.readUnsignedByte();

				if (rsa != 10) {
					LOGGER.info(String.format("[host= %s] was rejected for having the wrong rsa opcode.", ctx.channel().remoteAddress()));
					LoginUtils.sendResponseCode(ctx, LoginResponse.LOGIN_SERVER_REJECTED_SESSION);
					return;
				}

				long clientHalf = rsaBuffer.readLong();
				long serverHalf = rsaBuffer.readLong();

				int[] isaacSeed = {
						(int) (clientHalf >> 32),
						(int) clientHalf,
						(int) (serverHalf >> 32),
						(int) serverHalf
				};

				IsaacCipher decryptor = new IsaacCipher(isaacSeed);

				for (int index = 0; index < isaacSeed.length; index++) {
					isaacSeed[index] += 50;
				}

				IsaacCipher encryptor = new IsaacCipher(isaacSeed);

				@SuppressWarnings("unused")
				int uid = rsaBuffer.readInt();

				ByteBuf buf = Unpooled.wrappedBuffer(rsaBuffer);

				// universal unique identifier, rate of collision is so low its said to be unique.
				String uuid = ByteBufUtils.readString(buf);
				String username = ByteBufUtils.readString(buf);
				String password = ByteBufUtils.readString(buf);

				if (password.length() < 6 || password.length() > 20 || username.isEmpty() || username.length() > 12) {
					LoginUtils.sendResponseCode(ctx, LoginResponse.INVALID_CREDENTIALS);
					return;
				}

			out.add(new LoginDetailsPacket(ctx, username, password, uuid, encryptor, decryptor));
		}
	}

}