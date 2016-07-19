package astraeus.game.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import astraeus.game.model.World;
import astraeus.game.plugin.PluginMetaData;
import astraeus.util.GsonParser;

public final class PluginMetaDataParser extends GsonParser<PluginMetaData> {

	public PluginMetaDataParser() {
		super("./plugins/plugins", false);
	}

	@Override
	public PluginMetaData[] deserialize(FileReader reader) throws IOException {
		return gson.fromJson(reader, PluginMetaData[].class);		
	}

	@Override
	public void onRead(PluginMetaData[] array) throws IOException {
		Arrays.stream(array).forEach($it -> World.WORLD.getPluginService().getPluginMetaData().put($it.getBase(), $it));
	}

}
