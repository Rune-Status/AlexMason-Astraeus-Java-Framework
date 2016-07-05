package astraeus.game.model.entity.mob.player.attribute;

import java.util.EnumMap;

/**
 * A wrapper class for a {@link EnumMap}s value.
 *
 * This class can provide additional functions for a maps key values and to help force type safety.
 *
 * @author Seven
 */
public final class AttributeValue<T extends Object> {

    /**
     * The actual value of this {@link AttributeValue}.
     */
    private final T value;

    /**
     * Creates a new {@link AttributeValue}.
     *
     * @param value
     *    The value to add.
     */
    public AttributeValue(T value) {
        this.value = value;
    }

    /**
     * Gets the actual value.
     *
     * @return The actual value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Gets the type of this value.
     *
     * @return The type.
     */
    public Class<?> getType() {
        return value.getClass();
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
