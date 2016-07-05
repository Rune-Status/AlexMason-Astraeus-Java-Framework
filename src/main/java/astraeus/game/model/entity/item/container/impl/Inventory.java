package astraeus.game.model.entity.item.container.impl;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.game.model.entity.item.container.ItemContainer;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.out.UpdateItemsOnWidgetPacket;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * Resembles the inventory of a {@link Player}.
 *
 * @author Seven
 */
public final class Inventory extends ItemContainer {

    /**
     * Creates a new {@link Inventory}.
     *
     * @param player
     *      The player who owns this container.
     */
    public Inventory(Player player) {
        super(28, player);
    }

    @Override
    public void add(Item item) {

        if (!canHold(item.getId())) {
            player.send(new ServerMessagePacket("You don't have the required inventory space to hold this item."));
            return;
        }

        for (int slot = 0; slot < getCapacity(); slot ++) {

            if (items[slot] == null) {
                continue;
            }

            if (items[slot].getId() == item.getId()) {
                if (ItemDefinition.getDefinitions()[item.getId()].isStackable()) {
                    items[slot].setAmount(items[slot].getAmount() + item.getAmount());
                    refresh();
                    return;
                }
            }

        }

        if (!ItemDefinition.getDefinitions()[item.getId()].isStackable() && item.getAmount() > 1) {

            int itemAmount = item.getAmount();

            for (int amount = 0; amount < itemAmount; amount ++) {
                for (int slot = 0; slot < getCapacity(); slot ++) {
                    if (items[slot] == null) {
                        items[slot] = item;
                        item.setAmount(1);
                        break;
                    }
                }
            }

            refresh();
            return;
        }

        for (int slot = 0; slot < getCapacity(); slot ++) {

            if (items[slot] == null) {
                items[slot] = item;
                refresh();
                break;
            }
        }
    }

    @Override
    public void remove(int index, int amount) {

        int deleteCount = 0;

        for (int slot = 0; slot < getCapacity(); slot ++) {

            if (items[slot] == null) {
                continue;
            }

            if (items[slot].getId() == index) {

                if (deleteCount == amount) {
                    break;
                }

                if (items[slot].getAmount() > amount && ItemDefinition.getDefinitions()[index].isStackable()) {
                    items[slot].setAmount(items[slot].getAmount() - amount);
                    refresh();
                    break;
                }

                items[slot] = new Item(-1, 0);
                items[slot] = null;
                refresh();
                deleteCount ++;
            }
        }
    }

    @Override
    public void refresh() {
        player.send(new UpdateItemsOnWidgetPacket(3214, items));
    }

}
