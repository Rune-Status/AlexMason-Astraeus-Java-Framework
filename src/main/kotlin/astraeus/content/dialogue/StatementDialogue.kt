package main.astraeus.content.dialogue

/**
 * The {@link Chainable} implementation that represents a dialogue with a single statement; which has no models on the dialogue.
 *
 * @author Seven
 */
class StatementDialogue(vararg val lines: String): Chainable {

    override fun accept(factory: DialogueFactory) {
        factory.sendStatement(this)
    }

}
