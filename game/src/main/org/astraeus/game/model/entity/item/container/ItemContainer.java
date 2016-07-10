package astraeus.game.model.entity.item.container;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.game.model.entity.mob.player.Player;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a container that holds {@link Item}s for a {@link Player}.
 *
 * @author Seven
 */
public abstract class ItemContainer {

      /**
       * The items that are being held.
       */
      protected Item[] items;

      /**
       * The player that owns this container.
       */
      protected final Player player;

      /**
       * The maximum amount of items that can be placed into this container.
       */
      private final int capacity;

      /**
       * The method that will update a users interfaces to display the items.
       */
      public abstract void refresh();

      /**
       * The method that will add a {@code item} into this container.
       */
      public abstract void add(Item item);

      /**
       * The method that will remove a {@code item} from this container.
       */
      public abstract void remove(int index, int amount);

      /**
       * Creates a new {@link ItemContainer}.
       *
       * @param capacity
       *    The capacity of this container.
       *
       * @param player
       *    The player that owns this container.
       */
      public ItemContainer(int capacity, Player player) {
            this.player = player;
            this.capacity = capacity;
            this.items = new Item[capacity];
      }

      /**
       * Gets the items that are held in this container.
       *
       * @return The items.
       */
      public final Item[] getItems() {
            return items;
      }

      /**
       * Sets the items of this container to a new list of items.
       *
       * @param items
       *          The items to replace with.
       */
      public final void setItems(Item[] items) {
            this.items = items;
      }

      /**
       * Gets the owner of this container.
       *
       * @return The player.
       */
      public final Player getPlayer() {
            return player;
      }

      /**
       * Gets the capacity of this container.
       *
       * @return The capacity.
       */
      public final int getCapacity() {
            return capacity;
      }

      /**
       * Gets the number of open spaces in this container.
       *
       * @return The amount of free spaces.
       */
      public final int getFreeSlots() {
            return Math.toIntExact(Arrays.stream(items).filter(item -> Objects.isNull(item)).count());
      }

      /**
       * Gets an {@link Item} in this container by a specified index.
       *
       * @param slot
       *          The index.
       *
       * @return The item.
       */
      public final Item getItem(int slot) {
            return items[slot];
      }

      /**
       * Determines if an {@link Item} can be held within this container.
       *
       * @param index
       *          The index to check.
       *
       * @return {@code true} If this index can hold this item. {@code false} otherwise.
       */
      public final boolean canHold(int index) {

            if (getFreeSlots() > 0) {
                  return true;
            }

            if (player.getInventory().contains(index) && ItemDefinition.getDefinitions()[index].isStackable()) {
                  return true;
            }

            return false;
      }

      /**
       * Determines if an {@link Item} is in this container.
       *
       * @param itemId
       *          The itemId to check.
       */
      public final boolean contains(int itemId) {
            return Arrays.stream(items).filter(Objects::nonNull).anyMatch($it -> $it.getId() == itemId);
      }

      /**
       * Places an {@link Item} into a specified slot.
       *
       * @param slot
       *    The index to place the item to.
       */
      public final void set(int slot, Item item) {
            getItems()[slot] = item;
            refresh();
      }

      /**
       * Swaps two items.
       *
       * @param first
       *    The index of the first item to swap.
       *
       * @param second
       *    The index of the second item to swap.
       */
      public final void swapItems(int first, int second) {
            Item temporary = getItems()[first];
            set(first, getItems()[second]);
            set(second, temporary);
            refresh();
      }

      /**
       * Gets the total number of items of a specified {@code itemId}.
       *
       * @return The number of items.
       */
      public final int getItemAmount(int itemId) {
            int accumulator = 0;

            for (int index = 0; index < getCapacity(); index ++) {
                  if (getItems()[index] != null && getItems()[index].getId() == itemId) {
                        if (ItemDefinition.getDefinitions()[getItems()[index].getId()].isStackable()) {
                              return getItems()[index].getAmount();
                        } else {
                              accumulator += getItems()[index].getAmount();
                        }
                  }
            }
            return accumulator;
      }

      /**
       * Removes a {@link Item} from a specified index.
       *
       * @param slot
       *          The index to remove the item from.
       *
       * @param amount
       *          The amount to remove.
       */
      public final void removeFromSlot(int slot, int amount) {
            if (getItems()[slot] != null) {
                  if (ItemDefinition.getDefinitions()[getItems()[slot].getId()].isStackable()) {
                        if (getItems()[slot].getAmount() > amount) {
                              getItems()[slot].setAmount(getItems()[slot].getAmount() - amount);
                        } else {
                              getItems()[slot] = new Item(-1, 0);
                        }
                  } else {
                        getItems()[slot] = new Item(-1, 0);
                  }

                  if (getItems()[slot].getId() == -1) {
                        getItems()[slot] = null;
                  }
                  refresh();
            }
      }

      /**
       * Sorts all non null elements by placing all null elements in the back of the array.
       *
       * @return The sorted items.
       */
      public final Item[] sortNonNull() {
            Item[] collection = new Item[capacity];

            for (int i = 0; i < collection.length; i ++) {
                  if (getItems()[i] != null) {
                        collection[i] = getItems()[i];
                  }
            }
            return collection;
      }

      /**
       * Shifts all items in this container by filling all empty spaces first.
       */
      public void shiftItems() {

            final Item[] priorItems = getItems();
            items = new Item[capacity];

            int shiftedIndex = 0;

            for (int i = 0; i < getItems().length; i ++) {

                  if (priorItems[i] != null) {
                        getItems()[shiftedIndex] = priorItems[i];
                        shiftedIndex ++;
                  }
            }

      }
}