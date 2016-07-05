package astraeus.game.io;

import com.google.gson.JsonObject;
import astraeus.net.NetworkConstants;
import astraeus.util.GsonParser;

/**
 * Parses through the packet sizes file and associates their opcode with a size.
 *
 * @author SeVen
 */
public final class PacketSizeParser extends GsonParser {

	/**
	 * Creates a new {@link PacketSizeParser}.
	 */
	public PacketSizeParser() {
		super("io/packet_sizes");
	}

	@Override
	public void parse(JsonObject data) {
		final int opcode = data.get("opcode").getAsInt();
		final int size = data.get("size").getAsInt();
		NetworkConstants.PACKET_SIZES[opcode] = size;
	}

}
