package astraeus.game.model.entity.item.container.impl;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.game.model.entity.item.container.ItemContainer;
import astraeus.game.model.entity.mob.MobAnimation;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.out.SetItemModelOnWidgetPacket;
import astraeus.net.packet.out.UpdateItemsOnWidgetPacket;
import astraeus.net.packet.out.SetSideBarWidgetPacket;
import astraeus.net.packet.out.SetWidgetStringPacket;

import java.util.Arrays;

/**
 * Represents a {@link Player}s equipment.
 *
 * @author Seven
 */
public final class Equipment extends ItemContainer {

    public static final Object[][] WEAPON_DATA = { { "whip", 12290 }, { "bow", 1764 }, { "stand", 328 }, { "wand", 328 }, { "dart", 4446 }, { "knife", 4446 }, { "javelin", 4446 }, { "dagger", 2276 }, { "sword", 2276 }, { "scimitar", 2276 }, { "pickaxe", 5570 }, { "axe", 1698 }, { "battleaxe", 1698 }, { "halberd", 8460 }, { "halberd", 8460 }, { "spear", 4679 }, { "mace", 3796 }, { "warhammer", 425 }, { "maul", 425 } };

    /**
     * Items which have complete head coverage.
     */
    public static final int[] FULL_HEAD_GEAR = { 6109, 1153, 1155, 1157, 1159, 1161, 1163, 1165, 2587, 2595, 2605, 2613, 2619, 2627, 2657, 2673, 3486, 4745, 1053, 1055, 1057 };

    /**
     * Items which have complete chest coverage.
     */
    public static final int[] PLATE_BODY = { 6107, 3140, 1115, 1117, 1119, 1121, 1123, 1125, 1127, 2583, 2591, 2599, 2607, 2615, 2623, 2653, 2669, 3481, 4720, 4728, 4749, 4712 };

    public static final int HEAD = 0;
    public static final int CAPE = 1;
    public static final int AMULET = 2;
    public static final int WEAPON = 3;
    public static final int CHEST = 4;
    public static final int SHIELD = 5;
    public static final int LEGS = 7;
    public static final int HANDS = 9;
    public static final int FEET = 10;
    public static final int RING = 12;
    public static final int ARROWS = 13;

    /**
     * Creates a new {@link Equipment} container.
     *
     * @param player
     *      The player that owns this container.
     */
    public Equipment(Player player) {
        super(14, player);
    }

    @Override
    public void add(Item item) {
        refresh();
    }

    @Override
    public void remove(int index, int amount) {
        refresh();
    }

    @Override
    public void refresh() {
        getPlayer().send(new UpdateItemsOnWidgetPacket(1688, items));
    }

    /**
     * Updates the weapon for a {@link Player}.
     */
    public void updateWeapon() {
        displayWeaponsInterface();
        if (items[Equipment.WEAPON] != null) {
            if (ItemDefinition.getDefinitions()[items[Equipment.WEAPON].getId()].getName().contains("2h")) {
                player.getMobAnimation().setStand(2561);
                player.getMobAnimation().setWalk(2562);
                player.getMobAnimation().setRun(2563);
                return;
            }
            switch (items[Equipment.WEAPON].getId()) {

                case 4153:
                    player.getMobAnimation().setStand(1662);
                    player.getMobAnimation().setWalk(1663);
                    player.getMobAnimation().setRun(1664);
                    break;

                case 4151:
                    player.getMobAnimation().setStand(0x328);
                    player.getMobAnimation().setWalk(1660);
                    player.getMobAnimation().setRun(1661);
                    break;

                default:
                    player.getMobAnimation().setStand(MobAnimation.PLAYER_STAND);
                    player.getMobAnimation().setWalk(MobAnimation.PLAYER_WALK);
                    player.getMobAnimation().setRun(MobAnimation.PLAYER_RUN);
                    break;
            }
        } else {
            player.getMobAnimation().setStand(MobAnimation.PLAYER_STAND);
            player.getMobAnimation().setWalk(MobAnimation.PLAYER_WALK);
            player.getMobAnimation().setRun(MobAnimation.PLAYER_RUN);
        }
    }

    private void displayWeaponsInterface() {
        if (items[Equipment.WEAPON] == null) {
            player.send(new SetSideBarWidgetPacket(0, 5855));
            player.send(new SetWidgetStringPacket("Unarmed", 5857));
        } else {
            player.send(new SetSideBarWidgetPacket(0, getWeaponInterface()));
            player.send(new SetWidgetStringPacket(ItemDefinition.getDefinitions()[items[Equipment.WEAPON].getId()].getName(), getWeaponInterface() + 3));
            player.send(new SetItemModelOnWidgetPacket(getWeaponInterface() + 1, 200, items[Equipment.WEAPON].getId()));
        }
    }

    private int getWeaponInterface() {
        if (items[Equipment.WEAPON] == null) {
            return 5855;
        }
        for (int i = 0; i < Equipment.WEAPON_DATA.length; i ++) {
            if (ItemDefinition.getDefinitions()[items[Equipment.WEAPON].getId()].getName().toLowerCase().contains((String) Equipment.WEAPON_DATA[i][0])) {
                return (Integer) Equipment.WEAPON_DATA[i][1];
            }
        }
        return 5855;
    }

    /**
     * If an item is considered to have complete head coverage.
     *
     * @param itemId The itemId of the item.
     *
     * @return The result of the operation.
     */
    public static boolean isFullHeadGear(int itemId) {
        return Arrays.binarySearch(Equipment.FULL_HEAD_GEAR, itemId) > 0;
    }

    /**
     * If an item is considered to have complete chest coverage.
     *
     * @param itemId The itemId of the item.
     *
     * @return The result of the operation.
     */
    public static boolean isFullChestGear(int itemId) {
        return Arrays.binarySearch(Equipment.PLATE_BODY, itemId) > 0;
    }

}
