package astraeus.game.io;

import com.google.gson.JsonObject;
import astraeus.game.model.Direction;
import astraeus.game.model.Location;
import astraeus.game.model.entity.mob.npc.NpcSpawn;
import astraeus.game.model.entity.mob.npc.Npcs;
import astraeus.util.GsonParser;

/**
 * Parses through the mob spawn file and creates {@link NpcSpawn}s on startup.
 * 
 * @author Seven
 */
public final class NpcSpawnParser extends GsonParser {

	public NpcSpawnParser() {
		super("./Data/npc/npc_spawns");
	}

	@Override
	public void parse(JsonObject data) {
		final int id = data.get("id").getAsInt();

		final Location location = builder.fromJson(data.get("location"), Location.class);

		final boolean randomWalk = data.get("randomWalk").getAsBoolean();

		final String facing = data.get("facing").getAsString();

		final NpcSpawn spawn = new NpcSpawn(id, location, randomWalk, Direction.valueOf(facing));

		Npcs.createSpawn(spawn);
	}

}
