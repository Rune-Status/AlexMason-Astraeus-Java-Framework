package astraeus.net;

import com.google.common.collect.ImmutableList;
import com.moandjiezana.toml.Toml;

import astraeus.net.channel.PlayerChannel;
import astraeus.net.channel.UpstreamChannelHandler;
import io.netty.util.AttributeKey;

import java.io.File;
import java.math.BigInteger;

/**
 * A class which holds network-related constants.
 * 
 * @author Seven
 */
public final class NetworkConstants {

	static {

		Toml parser = new Toml().read(new File("./settings.toml")).getTable("network");

		try {
			CONNECTION_LIMIT = Math.toIntExact(parser.getLong("connection_limit"));
			JAGGRAB_PORT = Math.toIntExact(parser.getLong("jaggrab_port"));
			INPUT_TIMEOUT = Math.toIntExact(parser.getLong("connection_timeout"));
			PACKET_LIMIT = Math.toIntExact(parser.getLong("packet_limit"));
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}

	}

	public static final int CONNECTION_LIMIT;

	/**
	 * The JAGGRAB port.
	 */
	public static final int JAGGRAB_PORT;

	/**
	 * The number of seconds until a connection becomes idle.
	 */
    public static final int INPUT_TIMEOUT;

	/**
	 * The amount of packets that can be decoded in one sequence.
	 */
	public static final int PACKET_LIMIT;
	
    public static final AttributeKey<PlayerChannel> SESSION_KEY = AttributeKey.valueOf("session.key");

	public static final BigInteger RSA_MODULUS = new BigInteger("94904992129904410061849432720048295856082621425118273522925386720620318960919649616773860564226013741030211135158797393273808089000770687087538386210551037271884505217469135237269866084874090369313013016228010726263597258760029391951907049483204438424117908438852851618778702170822555894057960542749301583313");

	public static final BigInteger RSA_EXPONENT = new BigInteger("72640252303588278644467876834506654511692882736878142674473705672822320822095174696379303197013981434572187481298130748148385818094460521624198552406940508805602215708418094058951352076283100448576575511642453669107583920561043364042814766866691981132717812444681081534760715694225059124574441435942822149161");

  /**
   * The list of exceptions that are ignored and discarded by the
   * {@link UpstreamChannelHandler}.
   */
  public static final ImmutableList<String> IGNORED_EXCEPTIONS = ImmutableList.of(
      "An existing connection was forcibly closed by the remote host",
      "An established connection was aborted by the software in your host machine");

	/**
	 * An array of message opcodes mapped to their respective sizes.
	 */
	public static final int PACKET_SIZES[] = new int[257];

}
