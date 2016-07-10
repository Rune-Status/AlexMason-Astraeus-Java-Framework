package astraeus.net.codec.login;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import astraeus.net.codec.ProtocolConstants;
import astraeus.util.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class LoginRequestDecoder extends ByteToMessageDecoder {
	
	/**
	 * The single logger for this class.
	 */
	private static final Logger LOGGER = LoggerUtils.getLogger(LoginRequestDecoder.class);
	
	/**
	 * Generates random numbers via secure cryptography. Generates the session
	 * key for packet encryption.
	 */
	private static final Random RANDOM = new SecureRandom();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() >= 2) {
			int request = in.readUnsignedByte();

			@SuppressWarnings("unused")
			int nameHash = in.readUnsignedByte();

			if (request != ProtocolConstants.GAME_SEVER_OPCODE && request != ProtocolConstants.FILE_SERVER_OPCODE) {
				LOGGER.info(String.format("[host= %s] was rejected for having an invalid connection request.", ctx.channel().remoteAddress()));
				LoginUtils.sendResponseCode(ctx, LoginResponse.INVALID_LOGIN_SERVER);
				return;
			}

			ByteBuf buf = Unpooled.buffer(19);
			buf.writeLong(0);
			buf.writeByte(0);
			buf.writeLong(RANDOM.nextLong());
			ctx.writeAndFlush(buf);
			
			ctx.pipeline().replace("login-request-decoder", "login-decoder", new LoginDecoder());
		}
	}

}
