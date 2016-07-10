package astraeus.game.io;

import com.google.gson.JsonObject;

import astraeus.game.model.World;
import astraeus.game.plugin.PluginMetaData;
import astraeus.util.GsonParser;

public final class PluginMetaDataParser extends GsonParser {

	public PluginMetaDataParser() {
		super("./plugins/plugins", false);
	}

	@Override
	protected void parse(JsonObject data) {
		String name = data.get("name").getAsString();
		String description = data.get("description").getAsString();
		String base = data.get("base").getAsString();
		String[] authors = builder.fromJson(data.get("authors"), String[].class);
		double version = data.get("version").getAsDouble();
		
		World.WORLD.getPluginService().getPluginMetaData().put(base, new PluginMetaData(name, description, base, authors, version));
	}

}
