package astraeus.game.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.google.gson.Gson;

import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.util.GsonParser;

/**
 * The {@link GsonParser} implementation that will parse item definitions.
 * 
 * @author Vult-R
 */
public final class ItemDefinitionParser extends GsonParser<ItemDefinition> {

	/**
	 * Creates a new {@link ItemDefinitionParser}.
	 * 
	 */
	public ItemDefinitionParser() {
		super("./data/item/item_definitions");
	}

	@Override
	public ItemDefinition[] deserialize(Gson gson, FileReader reader) throws IOException {
		return gson.fromJson(reader, ItemDefinition[].class);		
	}

	@Override
	public void onRead(ItemDefinition[] array) throws IOException {
		Arrays.stream(array).forEach($it -> ItemDefinition.getDefinitions()[$it.getId()] = $it);		
	}

}
