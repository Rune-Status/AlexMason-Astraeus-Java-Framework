package astraeus.game.io;

import com.google.gson.JsonObject;
import astraeus.game.model.Direction;
import astraeus.game.model.Location;
import astraeus.game.model.entity.object.GameObject;
import astraeus.game.model.entity.object.GameObjectType;
import astraeus.game.model.entity.object.GameObjects;
import astraeus.util.GsonParser;

/**
 * The class that loads all global objects on startup.
 * 
 * @author SeVen
 */
public final class GlobalObjectParser extends GsonParser {

    public GlobalObjectParser() {
		super("./Data/object/global_objects");
    }

	@Override
	public void parse(JsonObject data) {
		int id = data.get("id").getAsInt();
		int type = Integer.parseInt(data.get("type").getAsString());
		Location location = builder.fromJson(data.get("location"), Location.class);
		Direction orientation = Direction.valueOf(data.get("orientation").getAsString());
		GameObjects.getGlobalObjects().add(new GameObject(id, GameObjectType.lookup(type).get(), location, orientation));
	}

}
