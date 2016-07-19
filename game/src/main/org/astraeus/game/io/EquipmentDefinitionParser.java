package astraeus.game.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import astraeus.game.model.entity.item.container.impl.Equipment.EquipmentDefinition;
import astraeus.util.GsonParser;

public final class EquipmentDefinitionParser extends GsonParser<EquipmentDefinition> {

	public EquipmentDefinitionParser() {
		super("./data/equipment/equipment_definitions");
	}

	@Override
	public EquipmentDefinition[] deserialize(FileReader reader) throws IOException {
		return gson.fromJson(reader, EquipmentDefinition[].class);
	}

	@Override
	public void onRead(EquipmentDefinition[] array) throws IOException {
		Arrays.stream(array).forEach($it -> EquipmentDefinition.EQUIPMENT_DEFINITIONS.put($it.getId(), $it));		
	}

}
