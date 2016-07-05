package astraeus.game.model.entity.item.container.impl;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.game.model.entity.item.container.ItemContainer;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.out.SetWidgetConfigPacket;
import astraeus.net.packet.out.DisplayInventoryWidgetPacket;
import astraeus.net.packet.out.UpdateItemsOnWidgetPacket;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * Resembles the bank of a {@link Player}.
 *
 * @author Seven
 */
public final class Bank extends ItemContainer {

    /**
     * The flag that denotes a player is banking.
     */
    private boolean banking;

    /**
     * The flag that denotes to withdraw this item as a noted item.
     */
    private boolean note;

    /**
     * The flag that denotes the bank can swap items.
     */
    private boolean swap;

    /**
     * Creates a new {@link Bank}.
     *
     * @param player
     *      The player that owns this container.
     */
    public Bank(Player player) {
        super(352, player);
    }

    /**
     * The method that wil open up the bank.
     */
    public void open() {
        setBanking(true);
        player.send(new DisplayInventoryWidgetPacket(5292, 5063));
        player.send(new UpdateItemsOnWidgetPacket(5064, player.getInventory().getItems()));
        player.send(new UpdateItemsOnWidgetPacket(5382, items));
    }

    /**
     * The method that will withdraw an item from this bank.
     *
     * @param amount
     *      The amount to remove
     *
     * @param slot
     *      The index of the item to remove.
     */
    public void withdrawItem(final int amount, final int slot) {

        if (items[slot].getAmount() < 0) {
            return;
        }

        if (player.getInventory().getFreeSlots() == 0) {
            player.send(new ServerMessagePacket("There is not enough room in your inventory to withdraw this item."));
            return;
        }

        if (!player.getInventory().canHold(items[slot].getId())) {
            player.send(new ServerMessagePacket("There is not enough room in your inventory to withdraw this item."));
            return;
        }

        int calculatedAmount = amount;

        if (calculatedAmount > player.getInventory().getFreeSlots() && !isNote()) {
            calculatedAmount = player.getInventory().getFreeSlots();
        } else if (isNote()) {
            calculatedAmount = amount;
        }

        if (isNote()) {
            if (!ItemDefinition.getDefinitions()[items[slot].getId() + 1].isNoted()) {
                player.getInventory().add(new Item(items[slot].getId(), calculatedAmount));
                player.send(new ServerMessagePacket("This item cannot be withdraw from your bank account as a note."));
            } else {
                player.getInventory().add(new Item(items[slot].getId() + 1, calculatedAmount));
            }
        } else {
            player.getInventory().add(new Item(items[slot].getId(), calculatedAmount));
        }

        for (int i = 0; i < getCapacity(); i ++) {
            if (i == slot && items[slot] != null) {

                if (calculatedAmount >= items[slot].getAmount()) {
                    items[slot] = new Item(-1, 0);
                }

                if (calculatedAmount < items[slot].getAmount()) {
                    items[slot].setAmount(items[slot].getAmount() - calculatedAmount);
                }

                if (items[slot].getId() == -1) {
                    items[slot] = null;
                }

            }
        }

        shiftItems();
        refresh();
    }

    /**
     * The method that will add an item to this bank.
     *
     * @param item
     *      The item to add.
     *
     * @param slot
     *      The slot to add this item.
     */
    public void depositItem(final Item item, final int slot) {

        if (item.getAmount() < 0) {
            return;
        }

        if (getFreeSlots() == 0) {
            player.send(new ServerMessagePacket("There is not enough room in your bank account to deposit this item."));
            return;
        }

        int amount = item.getAmount();
        int index = item.getId();

        if (amount > player.getInventory().getItemAmount(item.getId())) {
            amount = player.getInventory().getItemAmount(item.getId());
        }

        if (ItemDefinition.getDefinitions()[index].isNoted()) {
            index -= 1;
        }

        for (int i = 0; i < getCapacity(); i ++) {
            if (items[i] == null) {
                set(i, new Item(index, amount));
                break;
            }

            if (items[i].getId() == index && items[i] != null) {
                set(i, new Item(index, amount + items[i].getAmount()));
                break;
            }
        }

        if (amount > 1) {
            player.getInventory().remove(item.getId(), amount);
        } else {
            player.getInventory().removeFromSlot(slot, 1);
        }

        refresh();
    }

    /**
     * The method that will initialize bank configs.
     */
    public void initBank() {
        setSwap(true);
        player.send(new SetWidgetConfigPacket(304, 0));
        player.send(new SetWidgetConfigPacket(115, 0));
    }

    @Override
    public void refresh() {
        getPlayer().send(new UpdateItemsOnWidgetPacket(5382, getPlayer().getBank().items));
        getPlayer().send(new UpdateItemsOnWidgetPacket(5064, getPlayer().getInventory().getItems()));
    }

    @Override
    public void add(Item item) {
        refresh();
    }

    @Override
    public void remove(int index, int amount) {
        refresh();
    }

    public boolean isBanking() {
        return banking;
    }

    public void setBanking(boolean banking) {
        this.banking = banking;
    }

    public boolean isNote() {
        return note;
    }

    public void setNote(boolean note) {
        this.note = note;
    }

    public boolean isSwap() {
        return swap;
    }

    public void setSwap(boolean swap) {
        this.swap = swap;
    }

}

