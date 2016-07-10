package astraeus.game.io;

import com.google.gson.JsonObject;
import astraeus.game.model.entity.mob.npc.NpcDefinition;
import astraeus.util.GsonParser;

import java.util.Objects;

public final class NpcDefinitionParser extends GsonParser {

	public NpcDefinitionParser() {
		super("./Data/npc/npc_definitions");
	}

	@Override
	public void parse(JsonObject data) {
		final int id = data.get("id").getAsInt();
		final String name = Objects.requireNonNull(data.get("name")).getAsString();
		final int combatLevel = data.get("combatLevel").getAsInt();
		final int size = data.get("size").getAsInt();
		final int standAnimation = data.get("standAnimation").getAsInt();
		final int walkAnimation = data.get("walkAnimation").getAsInt();
		final int turn180Animation = data.get("turn180Animation").getAsInt();
		final int turn90CWAnimation = data.get("turn90CWAnimation").getAsInt();
		final int turn90CCWAnimation = data.get("turn90CCWAnimation").getAsInt();
		final int attackAnimation = data.get("attackAnimation").getAsInt();
		final int blockAnimation = data.get("blockAnimation").getAsInt();
		final int deathAnimation = data.get("deathAnimation").getAsInt();

		NpcDefinition.getDefinitions()[id] = new NpcDefinition(id, name, combatLevel, size, standAnimation, walkAnimation, turn180Animation, turn90CWAnimation, turn90CCWAnimation, attackAnimation, blockAnimation, deathAnimation);
	}

}