package astraeus.game.io;

import com.google.gson.JsonObject;
import astraeus.util.GsonParser;

/**
 * Loads all the songs on startup.
 * 
 * @author SeVen
 */
public final class SongParser extends GsonParser {

	/**
	 * Creates a new {@link SongParser}.
	 */
	public SongParser() {
		super("./Data/music/songs");
	}

	@SuppressWarnings("unused")
	@Override
	public void parse(JsonObject data) {
		final int id = data.get("id").getAsInt();
		final String name = data.get("name").getAsString();
		final int[] region = builder.fromJson(data.get("region"), int[].class);
	}

}
