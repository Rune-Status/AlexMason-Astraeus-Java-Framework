package astraeus.plugins.dialogue

/**
 * The {@link Chainable} implementation that represents dialogue in which an NPC is talking.
 *
 * @author Seven
 */
class NpcDialogue(var id: Int, val expression: Expression, vararg val lines: String): Chainable {

    /**
     * Creates a new {@link NpcDialogue}
     *
     * @param lines
     * 		The text for this dialogue.
     */
    constructor(vararg lines: String): this(-1, Expression.DEFAULT, *lines)

    /**
     * Creates a new {@link NpcDialogue}
     *
     * @param expression
     * 		The expression of this npc.
     *
     * 	@param lines
     * 		The text for this dialogue.
     */
    constructor(expression: Expression, vararg lines: String): this(-1, expression, *lines)

    /**
     * Creates a new {@link NpcDialogue}
     *
     * @param id
     * 		The id of this npc.
     *
     * @param lines
     * 		The text for this dialogue.
     */
    constructor(id: Int, vararg lines: String): this(id, Expression.DEFAULT, *lines)

    override fun accept(factory: DialogueFactory) {
        factory.sendNpcChat(this)
    }

}
