package astraeus.game.io;

import com.google.gson.JsonObject;
import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.util.GsonParser;

/**
 * Parses through the item definitions file and creates {@link ItemDefinition}s
 * on startup.
 * 
 * @author SeVen
 */
public final class ItemDefinitionParser extends GsonParser {

    public ItemDefinitionParser() {
		super("item/item_definitions");
    }

	@Override
	public void parse(JsonObject data) {
		final int id = data.get("id").getAsInt();
		final int notedId = data.get("notedId").getAsInt();
		final int storePrice = data.get("storePrice").getAsInt();
		final int lowAlchValue = data.get("lowAlchValue").getAsInt();
		final int highAlchValue = data.get("highAlchValue").getAsInt();
		final int equipmentSlot = data.get("equipmentSlot").getAsInt();
		final String name = data.get("name").getAsString();
		final String description = data.get("description").getAsString();
		final boolean stackable = data.get("stackable").getAsBoolean();
		final boolean noted = data.get("noted").getAsBoolean();
		final boolean twoHanded = data.get("twoHanded").getAsBoolean();
		final int[] bonus = builder.fromJson(data.get("bonus"), int[].class);

		ItemDefinition.getDefinitions()[id] = new ItemDefinition(id, notedId, storePrice, lowAlchValue, highAlchValue, equipmentSlot,
				name, description, stackable, noted, twoHanded, bonus);
	}

}
