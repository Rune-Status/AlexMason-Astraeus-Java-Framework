package astraeus.game.io;

import com.google.gson.JsonObject;
import astraeus.game.model.Direction;
import astraeus.game.model.Location;
import astraeus.game.model.entity.object.GameObjectType;
import astraeus.game.model.entity.object.GameObjects;
import astraeus.game.model.entity.object.impl.Door;
import astraeus.util.GsonParser;

public final class DoorParser extends GsonParser {

    public DoorParser() {
		super("/object/doors");
	}

	@Override
	public void parse(JsonObject data) {
		int id = data.get("id").getAsInt();
		GameObjectType type = GameObjectType.valueOf(data.get("type").getAsString());
		Location location = builder.fromJson(data.get("location"), Location.class);
		boolean open = data.get("open").getAsBoolean();
		Direction orientation = Direction.valueOf(data.get("orientation").getAsString());

		GameObjects.getDoors().add(new Door(id, type, location, open, orientation));
	}

}
