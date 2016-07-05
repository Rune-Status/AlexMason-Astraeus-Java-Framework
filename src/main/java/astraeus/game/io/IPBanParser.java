package astraeus.game.io;

import astraeus.game.model.World;
import astraeus.util.TextFileParser;

import java.io.IOException;
import java.util.Scanner;

/**
 * Parses the ip bans
 *
 * @author Seven
 */
public final class IPBanParser extends TextFileParser {

	/**
	 * Creates a new {@link IPBanParser}.
	 */
	public IPBanParser() {
		super("punishment/ip_bans");
	}

	@Override
	public void parse(Scanner reader) throws IOException {
		String ip_bans = reader.nextLine();
		World.getIpBans().add(ip_bans);
	}

}
