package astraeus.game.model.entity.mob.player.attribute;

/**
 * The {@link RuntimeException} implementation specifically for {@link Attribute}s.
 *
 * @author Seven
 */
public final class AttributeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link AttributeException}.
     *
     * @param key
     *    The key or this attribute.
     *
     * @param value
     *    The value for this attribute.
     */
    public AttributeException(Attribute key, AttributeValue<?> value) {
        super(String.format("Invalid value type: %s for [key=%s], only accepts type of %s", value.getType().getSimpleName(), key.name().toLowerCase(), key.getDefaultValue().getClass().getSimpleName()));
    }

}
