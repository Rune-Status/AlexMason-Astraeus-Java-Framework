package astraeus.game.model.entity.mob.player.collect;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemContainer;
import astraeus.game.model.entity.item.ItemContainerPolicy;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.skill.Skill;
import astraeus.game.model.entity.mob.player.skill.SkillRequirement;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.packet.out.ServerMessagePacket;
import astraeus.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a {@link Player}s equipment.
 *
 * @author Vult-R
 */
public final class Equipment extends ItemContainer {

	public static enum EquipmentType {
		NONE(-1), HAT(0), CAPE(1), SHIELD(5), GLOVES(9), BOOTS(10), AMULET(2), RING(12), ARROWS(13), BODY(4), LEGS(
				7), WEAPON(3);

		private final int slot;

		private EquipmentType(final int slot) {
			this.slot = slot;
		}

		public int getSlot() {
			return this.slot;
		}

	}

	/**
	 * Represents an in-game equipped item.
	 * 
	 * @author Vult-R
	 */
	public static final class EquipmentDefinition {

		// TODO make this immutable
		public static final Map<Integer, EquipmentDefinition> equipment_definitions = new HashMap<>();

		public static EquipmentDefinition get(int id) {
			return equipment_definitions.get(id);
		}

		private final int id;

		private final String name;

		private final EquipmentType type;

		private final SkillRequirement[] requirements;

		private final boolean twoHanded;

		private final boolean fullBody;

		private final boolean fullHat;

		private final boolean fullMask;

		private final int[] bonuses;

		public EquipmentDefinition(int id, String name, EquipmentType type, SkillRequirement[] requirements,
				boolean twoHanded, boolean fullBody, boolean fullHat, boolean fullMask, int[] bonuses) {
			this.id = id;
			this.name = name;
			this.type = type;
			this.requirements = requirements;
			this.twoHanded = twoHanded;
			this.fullBody = fullBody;
			this.fullHat = fullHat;
			this.fullMask = fullMask;
			this.bonuses = bonuses;
		}

		public int[] getBonuses() {
			return bonuses;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public SkillRequirement[] getRequirements() {
			return requirements;
		}

		public boolean isTwoHanded() {
			return twoHanded;
		}

		public boolean isFullBody() {
			return fullBody;
		}

		public boolean isFullHat() {
			return fullHat;
		}

		public boolean isFullMask() {
			return fullMask;
		}

		public EquipmentType getType() {
			return type;
		}

	}

	public static final int HEAD_SLOT = 0;
	public static final int CAPE_SLOT = 1;
	public static final int NECKLACE_SLOT = 2;
	public static final int WEAPON_SLOT = 3;
	public static final int CHEST_SLOT = 4;
	public static final int SHIELD_SLOT = 5;
	public static final int LEGS_SLOT = 7;
	public static final int HANDS_SLOT = 9;
	public static final int FEET_SLOT = 10;
	public static final int RING_SLOT = 12;
	public static final int AMMO_SLOT = 13;

	public static final int STAB = 0;
	public static final int SLASH = 1;
	public static final int CRUSH = 2;
	public static final int MAGIC = 3;
	public static final int RANGED = 4;
	public static final int STAB_DEFENSE = 5;
	public static final int SLASH_DEFENSE = 6;
	public static final int CRUSH_DEFENSE = 7;
	public static final int MAGIC_DEFENSE = 8;
	public static final int RANGED_DEFENSE = 9;
	public static final int STRENGTH = 10;
	public static final int RANGED_STRENGTH = 11;
	public static final int MAGIC_STRENGTH = 12;
	public static final int PRAYER = 13;

	/** Item bonus names. */
	public static final String[] BONUS_NAMES = {
		/* 0 */  "Stab",
		/* 1 */  "Slash",
		/* 2 */  "Crush",
		/* 3 */  "Magic",
		/* 4 */  "Range",
		/* - */
		/* 5 */  "Stab",
		/* 6 */  "Slash",
		/* 7 */  "Crush",
		/* 8 */  "Magic",
		/* 9 */  "Range",
		/* - */
		/* 10 */ "Strength",
		/* 11 */ "Ranged Strength",
		/* 12 */ "Magic Strength",
		/* 13 */ "Prayer"
	};

	/**
	 * The player that this container belongs to.
	 */
	private final Player player;

	/**
	 * Creates a new {@link Equipment} container.
	 *
	 * @param player
	 *            The player that owns this container.
	 */
	public Equipment(Player player) {
		super(14, ItemContainerPolicy.NORMAL);
		this.player = player;
	}

    /**
     * Equips the item in {@code inventorySlot} to the equipment container.
     *
     * @param inventorySlot
     *            the slot to equip the item on.
     * @return {@code true} if the item was equipped, {@code false} otherwise.
     */
    public boolean equip(int inventorySlot) {
        Item item = player.getInventory().get(inventorySlot);
        
        if (!Item.valid(item)) {
            return false;
        }
        
        EquipmentDefinition equipDef = EquipmentDefinition.get(item.getId());
        
        if (equipDef == null) {
        	return false;
        }
        
        if (!canEquip(item.getId())) {
        	return false;
        }
        
        if (item.definition().isStackable()) {
            int designatedSlot = equipDef.getType().getSlot();
            Item equipItem = get(designatedSlot);
            if (used(designatedSlot)) {
                if (item.getId() == equipItem.getId()) {
                    set(designatedSlot, new Item(item.getId(), item.getAmount() + equipItem.getAmount()));
                } else {
                    player.getInventory().set(inventorySlot, equipItem);
                    player.getInventory().refresh();
                    set(designatedSlot, item);
                }
            } else {
                set(designatedSlot, item);
            }
            player.getInventory().remove(item, inventorySlot);
        } else {
            int designatedSlot = equipDef.getType().getSlot();
            if (designatedSlot == Equipment.WEAPON_SLOT && equipDef.isTwoHanded() && used(Equipment.SHIELD_SLOT)) {
                if (!unequip(Equipment.SHIELD_SLOT, true))
                    return false;
            }
            if (designatedSlot == Equipment.SHIELD_SLOT && used(Equipment.WEAPON_SLOT)) {
                if (EquipmentDefinition.get(get(Equipment.WEAPON_SLOT).getId()).isTwoHanded()) {
                    if (!unequip(Equipment.WEAPON_SLOT, true))
                        return false;
                }
            }
            if (used(designatedSlot)) {
                Item equipItem = get(designatedSlot);
                if (!equipItem.definition().isStackable()) {
                    player.getInventory().set(inventorySlot, equipItem);
                } else {
                    player.getInventory().set(inventorySlot, null);
                    player.getInventory().add(equipItem, inventorySlot);
                }
                player.getInventory().refresh();
            } else {
                player.getInventory().remove(item, inventorySlot);
            }
            set(designatedSlot, new Item(item.getId(), item.getAmount()));
        }
        refresh();
        return true;
    }

    /**
     * Unequips the item in {@code equipmentSlot} from the equipment container.
     *
     * @param equipmentSlot
     *            the slot to unequip the item on.
     * @param addItem
     *            if the unequipped item should be added to the inventory.
     * @return {@code true} if the item was unequipped, {@code false} otherwise.
     */
    public boolean unequip(int equipmentSlot, boolean addItem) {
        if (free(equipmentSlot))
            return false;
        
        Item item = get(equipmentSlot);
        
        if (!player.getInventory().spaceFor(item)) {
            player.queuePacket(new ServerMessagePacket("You do not have enough space in " + "your inventory!"));
            return false;
        }
        
        remove(item, equipmentSlot);
        
        if (addItem) {
            player.getInventory().add(new Item(item.getId(), item.getAmount()));
        }
        
        refresh();
        player.getInventory().refresh();
        return true;
    }
    
    public boolean canEquip(int id) {
		final EquipmentDefinition req = EquipmentDefinition.equipment_definitions.get(id);
		
		if (req != null) {
			for (final SkillRequirement r : req.getRequirements()) {

				if (r == null) {
					continue;
				}
				
				if (r.getSkill().getId() == Skill.PRAYER || r.getSkill().getId() == Skill.HITPOINTS) {
					if (player.getSkills().getMaxLevel(r.getSkill().getId()) < r.getLevel()) {
						player.queuePacket(new ServerMessagePacket("You need " + StringUtils.getAOrAn(r.getSkill().toString()) + " " + r.getSkill().toString() + " level of " + r.getLevel() + " to equip this item."));
						return false;
					}
				} else {
					if (player.getSkills().getLevel(r.getSkill().getId()) < r.getLevel()) {
						player.queuePacket(new ServerMessagePacket("You need " + StringUtils.getAOrAn(r.getSkill().toString()) + " " + r.getSkill().toString() + " level of " + r.getLevel() + " to equip this item."));
						return false;
					}
				}
			}
		}
		return true;
    }

	/**
	 * Refreshes the contents of this equipment container to the interface.
	 */
	public void refresh() {
		refresh(player, 1688);
		player.getUpdateFlags().add(UpdateFlag.APPEARANCE);
	}

	public static boolean isFullHat(int itemId) {
		return EquipmentDefinition.get(itemId) != null ? EquipmentDefinition.get(itemId).isFullHat() : false;
	}

	public static boolean isFullMask(int itemId) {
		return EquipmentDefinition.get(itemId) != null ? EquipmentDefinition.get(itemId).isFullMask() : false;
	}

	public static boolean isFullBody(int itemId) {
		return EquipmentDefinition.get(itemId) != null ? EquipmentDefinition.get(itemId).isFullBody() : false;
	}

}
