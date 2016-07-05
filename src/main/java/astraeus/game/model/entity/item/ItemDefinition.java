package astraeus.game.model.entity.item;

/**
 * Represents all of an in-game Item's attributes.
 * 
 * @author Seven
 */
public class ItemDefinition {

	public static final int ITEM_LIMIT = 7956;

	public static final ItemDefinition[] DEFINITIONS = new ItemDefinition[ITEM_LIMIT];

	private final int id;

	private final int notedId;

	private final int storePrice;

	private final int lowAlchValue;

	private final int highAlchValue;

	private final int equipmentSlot;

	private final String name;

	private final String description;

	private final boolean stackable;

	private final boolean noted;

	private final boolean twoHanded;

	private final int[] bonus;

	public ItemDefinition(int id, int notedId, int storePrice, int lowAlchValue, int highAlchValue, int equipmentSlot, String name, String description, boolean stackable, boolean noted, boolean twoHanded, int[] bonus) {
		this.id = id;
		this.notedId = notedId;
		this.storePrice = storePrice;
		this.lowAlchValue = lowAlchValue;
		this.highAlchValue = highAlchValue;
		this.equipmentSlot = equipmentSlot;
		this.name = name;
		this.description = description;
		this.stackable = stackable;
		this.noted = noted;
		this.twoHanded = twoHanded;
		this.bonus = bonus;
	}

	public static ItemDefinition[] getDefinitions() {
		return DEFINITIONS;
	}

	public static ItemDefinition lookup(int id) {
		return DEFINITIONS[id];
	}

	public int getId() {
		return id;
	}

	public int getNotedId() {
		return notedId;
	}

	public int getStorePrice() {
		return storePrice;
	}

	public int getLowAlchValue() {
		return lowAlchValue;
	}

	public int getHighAlchValue() {
		return highAlchValue;
	}

	public int getEquipmentSlot() {
		return equipmentSlot;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isStackable() {
		return stackable;
	}

	public boolean isNoted() {
		return noted;
	}

	public boolean isTwoHanded() {
		return twoHanded;
	}

	public int[] getBonus() {
		return bonus;
	}

	@Override
	public String toString() {
		return "ITEM[" + id + "," + name + "]";
	}

}
