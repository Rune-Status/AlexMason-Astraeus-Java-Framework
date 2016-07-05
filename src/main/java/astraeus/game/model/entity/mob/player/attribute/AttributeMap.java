package astraeus.game.model.entity.mob.player.attribute;

import java.util.EnumMap;

/**
 * A wrapper class for an {@link EnumMap} that provide type safety for its values.
 *
 * @author Seven
 */
public final class AttributeMap {

      /**
       * The underlying map of attributes.
       *
       * EnumMap disallows null keys, but allows null values.
       */
      private final EnumMap<Attribute, AttributeValue<?>> attributes = new EnumMap<>(Attribute.class);

      /**
       * Serves as a helper-function that places an {@link Attribute} into the map with a specified value type.
       *
       * @param key
       *    The attribute to place.
       *
       * @param value
       *    The type safe value.
       *
       * @throws AttributeException
       *    The exception thrown the {@code key}s initial type is different than the specified {@code value}s initial type.
       */
      private <T extends Object> void put(Attribute key, AttributeValue<T> value) throws AttributeException {
            if (key.getDefaultValue().getClass() != value.getType()) {
                  throw new AttributeException(key, value);
            }

            attributes.put(key, value);
      }

      /**
       * Places an {@link Attribute} into the map with a type-safe value.
       *
       * @param key
       *    The attribute to place.
       *
       * @param value
       *    The type safe value.
       */
      public <T extends Object> void put(Attribute key, T value) {
            try {
                  put(key, new AttributeValue<T>(value));
            } catch (AttributeException ex) {
                  ex.printStackTrace();
            }
      }

      /**
       * Toggles a {@link Boolean} type by switching false to true and true to false.
       *
       * @param key
       *    The key who's value to toggle.
       */
      public void toggle(Attribute key) {
            Boolean value = (Boolean) attributes.get(key).getValue();
            try {
                  toggle(key, new AttributeValue<Boolean>(value));
            } catch (AttributeException ex) {
                  ex.printStackTrace();
            }
      }

      /**
       * The helper function which validated the type and toggles the {@link Boolean} value if it is possible to do so.
       *
       * @param key
       *    The key who's value to toggle.
       *
       * @param key
       *    The key to toggle.
       */
      private void toggle(Attribute key, AttributeValue<Boolean> value) throws AttributeException {
            if (key.getDefaultValue().getClass() != value.getType()) {
                  throw new AttributeException(key, value);
            }
            put(key, !value.getValue());
      }

      /**
       * Servers as a helper-function which determines if a {@code key} contains a specified {@code value}.
       *
       * @param key
       *    The attribute to place.
       *
       * @param value
       *    The type safe value.
       *
       * @return {@code true} If this map contains the specified value. {@code false} otherwise.
       * @throws AttributeException
       *    The exception thrown the {@code key}s initial type is different than the specified {@code value}s initial type.
       */
      private  <T extends Object> boolean contains(Attribute key, AttributeValue<T> value) throws AttributeException {
            if (key.getDefaultValue().getClass() != value.getType()) {
                  throw new AttributeException(key, value);
            }
            return attributes.get(key).getValue() == value.getValue();
      }

      /**
       * Determines if a {@code key} contains a specified {@code value}.
       *
       * @param key
       *    The attribute to place.
       *
       * @param value
       *    The type safe value.
       *
       * @return {@code true} If this map contains the specified value. {@code false} otherwise.
       */
      public <T extends Object> boolean contains(Attribute key, T value) {
            try {
                  return contains(key, new AttributeValue<T>(value));
            } catch (AttributeException ex) {
                  ex.printStackTrace();
            }
            return attributes.get(key).getValue() == value;
      }

      /**
       * Gets the value from a specified {@code key}.
       *
       * This will grab the actual value, so you don't have to cast every time to attemp to get
       * the value from a key.
       *
       *  @return The actual value.
       */
      @SuppressWarnings("unchecked")
      public <T extends Object> T get(Attribute key) {
            return (T) attributes.get(key).getValue();
      }

      /**
       * Gets the underlying map of attributes.
       *
       * @return The underlying map {@link EnumMap}.
       */
      public EnumMap<Attribute, AttributeValue<?>> map() {
            return this.attributes;
      }

      /**
       * Gets a specified value as a {@link String} type.
       *
       * @param key
       *    The key who's value to get as a string.
       *
       * @return The value as a string.
       */
      public String getValueAsString(Attribute key) {
            return attributes.get(key).toString();
      }

      /**
       * Determines if this map is empty.
       *
       * @return {@code true} If this map is empty. {@code false} otherwise.
       */
      public boolean isEmpty() {
            return attributes.isEmpty();
      }

}