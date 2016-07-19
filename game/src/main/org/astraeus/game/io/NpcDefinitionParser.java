package astraeus.game.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import astraeus.game.model.entity.mob.npc.NpcDefinition;
import astraeus.util.GsonParser;

public final class NpcDefinitionParser extends GsonParser<NpcDefinition> {

	public NpcDefinitionParser() {
		super("./Data/npc/npc_definitions");
	}

	@Override
	public NpcDefinition[] deserialize(FileReader reader) throws IOException {
		return gson.fromJson(reader, NpcDefinition[].class);		
	}

	@Override
	public void onRead(NpcDefinition[] array) throws IOException {
		Arrays.stream(array).forEach($it -> NpcDefinition.getDefinitions()[$it.getId()] = $it);		
	}

}
