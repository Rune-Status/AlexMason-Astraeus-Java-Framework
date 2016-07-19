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
		super("./Data/item/item_definitions");
    }

	@Override
	public void parse(JsonObject data) {
		final int id = data.get("id").getAsInt();		
		final String name = data.get("name").getAsString();		
		final String examine = data.get("examine").getAsString();
		final int lowAlch = data.get("lowAlch").getAsInt();
		final int highAlch = data.get("highAlch").getAsInt();
		final int shopValue = data.get("shopValue").getAsInt();
		final boolean noteable = data.get("noteable").getAsBoolean();
		final boolean noted = data.get("noted").getAsBoolean();
		final int unnotedId = data.get("unnotedId").getAsInt();
		final int notedId = data.get("notedId").getAsInt();
		final boolean stackable = data.get("stackable").getAsBoolean();
		final double weight = data.get("weight").getAsDouble();

		ItemDefinition.getDefinitions()[id] = new ItemDefinition(id, name, examine, lowAlch, highAlch,
				shopValue, noteable, noted, unnotedId, notedId, stackable, weight);
	}

}
