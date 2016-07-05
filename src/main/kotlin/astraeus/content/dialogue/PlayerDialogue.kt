package main.astraeus.content.dialogue

/**
 * A {@link Chainable} implementation that represents a player talking.
 *
 * @author Seven
 */
class PlayerDialogue(val expression : Expression, vararg val lines: String) : Chainable {

    /**
     * Creates a new {@link PlayerDialogue} with a default expression of {@code DEFAULT}.
     *
     * @param lines
     * 		The text for this dialogue.
     */
    constructor(vararg lines : String) : this(Expression.DEFAULT, *lines)

    override fun accept(factory: DialogueFactory) {
        factory.sendPlayerChat(this)
    }
}
