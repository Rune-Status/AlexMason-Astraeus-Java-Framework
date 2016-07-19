package astraeus.game.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.google.gson.Gson;

import astraeus.game.model.entity.object.GameObject;
import astraeus.game.model.entity.object.GameObjectType;
import astraeus.game.model.entity.object.GameObjects;
import astraeus.game.model.entity.object.impl.GlobalObject;
import astraeus.util.GsonParser;

public final class GlobalObjectParser extends GsonParser<GlobalObject> {

	public GlobalObjectParser() {
		super("./data/object/global_objects");
	}

	@Override
	public GlobalObject[] deserialize(Gson gson, FileReader reader) throws IOException {
		return gson.fromJson(reader, GlobalObject[].class);
	}

	@Override
	public void onRead(GlobalObject[] array) throws IOException {		
		Arrays.stream(array).forEach($it -> GameObjects.getGlobalObjects().add(new GameObject($it.getId(), GameObjectType.lookup($it.getType()).get(), $it.getLocation(), $it.getOrientation())));
	}

}
