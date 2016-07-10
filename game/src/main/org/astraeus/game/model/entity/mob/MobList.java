package astraeus.game.model.entity.mob;

import java.util.*;
import java.util.stream.IntStream;

/**
 * The container that represents a list of entities.
 * 
 * @author SeVen
 */
public final class MobList<E extends Mob> implements Collection<E>, Iterable<E> {

      /**
       * The array of entities.
       */
      private final Mob[] entities;

      /**
       * The cached slots to prevent expensive lookups.
       */
      private final Queue<Integer> slotQueue = new ArrayDeque<>();

      /**
       * The capacity of this container.
       */
      private final int capacity;

      /**
       * The size of this container.
       */
      private int size = 0;

      /**
       * Creates a new {@link MobList ).
       * 
       * @param capacity The amount of entities that can be in this container.
       */
      public MobList(int capacity) {
            this.capacity = capacity;
            this.entities = new Mob[capacity + 1];
            IntStream.rangeClosed(1, capacity).forEach(slotQueue::add);
      }

      @Override
      public boolean add(E e) {
            Objects.requireNonNull(e);

            if (!e.isRegistered()) {
                  int slot = slotQueue.remove();
                  e.setSlot(slot);
                  e.setRegistered(true);
                  entities[slot] = e;
                  size++;
                  return true;
            }
            return false;
      }

      @Override
      public boolean remove(Object o) {
            Mob e = (Mob) o;

            Objects.requireNonNull(e);

            if (e.isRegistered() && entities[e.getSlot()] != null) {
                  e.setRegistered(false);
                  entities[e.getSlot()] = null;
                  slotQueue.add(e.getSlot());
                  size--;
                  return true;
            }
            return false;
      }

      /**
       * Gets an entity.
       * 
       * @param index The index of the entity in this list.
       * 
       * @throws IndexOutOfBoundsException If the index is out of bounds.
       */
      public Mob get(int index) {
            if (index <= 0 || index >= entities.length) {
                  throw new IndexOutOfBoundsException();
            }
            return entities[index];
      }

      /**
       * Gets the index of a specified {@code entity}.
       * 
       * @param entity The entity to get the index of.
       * 
       * @return The index of this entity.
       */
      public int indexOf(Mob entity) {
            return entity.getSlot();
      }

      /**
       * Gets the capacity of this container.
       */
      public final int getCapacity() {
            return capacity;
      }

      /**
       * Gets the array of entities.
       */
      public final Mob[] getEntities() {
            return entities;
      }

      /**
       * Gets the size of this container.
       */
      public int getSize() {
            return size;
      }

      /**
       * Gets the next available index from this collection.
       * 
       * @return {@code -1} If this collection is full. Otherwise the available index.
       */
      private int getNextIndex() {
            for (int index = 1; index < entities.length; index++) {
                  if (entities[index] == null) {
                        return index;
                  }
            }
            return -1;
      }

      public boolean isFull() {
            return getNextIndex() == -1;
      }

      /**
       * Removes an entity from the array, by setting the index to null.
       * 
       * @param index The index to remove.
       */
      public final void removeIndex(int index) {
            getEntities()[index] = null;
            size--;
      }

      @Override
      public int size() {
            return size;
      }

      @Override
      public boolean isEmpty() {
            return size() == 0;
      }

      @Override
      public boolean contains(Object o) {
            for (Mob entity : entities) {
                  if (entity == null) {
                        continue;
                  }
                  if (entity.equals(o)) {
                        return true;
                  }
            }
            return false;
      }

      @Override
      public Iterator<E> iterator() {
            return new MobileEntityListIterator<E>(this);
      }

      @Override
      public Mob[] toArray() {
            int size = size();
            Mob[] array = new Mob[size];
            int ptr = 0;
            for (int i = 1; i < entities.length; i++) {
                  if (entities[i] != null) {
                        array[ptr++] = entities[i];
                  }
            }
            return array;
      }

      @SuppressWarnings("unchecked")
      @Override
      public <T> T[] toArray(T[] a) {
            Mob[] arr = toArray();
            return (T[]) Arrays.copyOf(arr, arr.length, a.getClass());
      }

      @Override
      public boolean containsAll(Collection<?> c) {
            boolean failed = false;
            for (Object o : c) {
                  if (!contains(o)) {
                        failed = true;
                  }
            }
            return !failed;
      }

      @Override
      public boolean addAll(Collection<? extends E> c) {
            boolean changed = false;
            for (E entity : c) {
                  if (add(entity)) {
                        changed = true;
                  }
            }
            return changed;
      }

      @Override
      public boolean removeAll(Collection<?> c) {
            boolean changed = false;
            for (Object o : c) {
                  if (remove(o)) {
                        changed = true;
                  }
            }
            return changed;
      }

      @Override
      public boolean retainAll(Collection<?> c) {
            boolean changed = false;
            for (int i = 1; i < entities.length; i++) {
                  if (entities[i] != null) {
                        if (!c.contains(entities[i])) {
                              entities[i] = null;
                              size--;
                              changed = true;
                        }
                  }
            }
            return changed;
      }

      @Override
      public void clear() {
            for (int index = 1; index < entities.length; index++) {
                  entities[index] = null;
            }
            size = 0;
      }

      /**
       * An implementation of an iterator for an entity list.
       * 
       * @author Graham Edgecombe
       *
       * @param <E> The type of entity.
       */
      public static final class MobileEntityListIterator<E extends Mob>
                  implements Iterator<E> {

            /**
             * The entities.
             */
            private Mob[] entities;

            /**
             * The entity list.
             */
            private MobList<E> entityList;

            /**
             * The previous index.
             */
            private int lastIndex = -1;

            /**
             * The current index.
             */
            private int cursor = 0;

            /**
             * The size of the list.
             */
            private int size;

            /**
             * Creates an entity list iterator.
             * 
             * @param entityList The entity list.
             */
            public MobileEntityListIterator(MobList<E> entityList) {
                  this.entityList = entityList;
                  entities = entityList.toArray(new Mob[0]);
                  size = entities.length;
            }

            @Override
            public boolean hasNext() {
                  return cursor < size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public E next() {
                  if (!hasNext()) {
                        throw new NoSuchElementException();
                  }
                  lastIndex = cursor++;
                  return (E) entities[lastIndex];
            }

            @Override
            public void remove() {
                  if (lastIndex == -1) {
                        throw new IllegalStateException();
                  }
                  entityList.remove(entities[lastIndex]);
            }

      }

}
