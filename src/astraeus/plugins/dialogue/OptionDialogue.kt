package astraeus.plugins.dialogue

import java.util.*

/**
 * The {@link Chainable} implementation that represents a dialogue in which options are given to the player.
 *
 * @author Seven
 */
class OptionDialogue : Chainable {

    /**
     * The text for this dialogue.
     */
    val lines: Array<String>

    /**
     * The list of actions for this dialogue.
     */
    val actions:MutableList<Runnable> = ArrayList()

    /**
     * Creates a new {@link OptionDialogue}.
     *
     * @param option1
     * 		The text for the first option.
     *
     * @param action1
     * 		The action for the first action.
     *
     * @param option2
     * 		The text for the second option.
     *
     * @param action2
     * 		The action for the second action.
     */
    constructor(option1: String, action1: Runnable, option2: String, action2: Runnable) {
        lines = listOf(option1, option2).toTypedArray()
        actions.add(action1)
        actions.add(action2)
    }

    /**
     * Creates a new {@link OptionDialogue}.
     *
     * @param option1
     * 		The text for the first option.
     *
     * @param action1
     * 		The action for the first action.
     *
     * @param option2
     * 		The text for the second option.
     *
     * @param action2
     * 		The action for the second action.
     *
     * @param option3
     * 		The text for the third option.
     *
     * @param action3
     * 		The action for the third action.
     */
    constructor(option1: String, action1: Runnable, option2: String, action2: Runnable, option3: String, action3: Runnable) {
        lines = listOf(option1, option2, option3).toTypedArray()
        actions.add(action1)
        actions.add(action2)
        actions.add(action3)
    }

    /**
     * Creates a new {@link OptionDialogue}.
     *
     * @param option1
     * 		The text for the first option.
     *
     * @param action1
     * 		The action for the first action.
     *
     * @param option2
     * 		The text for the second option.
     *
     * @param action2
     * 		The action for the second action.
     *
     * @param option3
     * 		The text for the third option.
     *
     * @param action3
     * 		The action for the third action.
     *
     * @param option4
     * 		The text for the four option.
     *
     * @param action4
     * 		The action for the four action.
     */
    constructor(option1: String, action1: Runnable, option2: String, action2: Runnable, option3: String, action3: Runnable, option4: String, action4: Runnable) {
        lines = listOf(option1, option2, option3, option4).toTypedArray()
        actions.add(action1)
        actions.add(action2)
        actions.add(action3)
        actions.add(action4)
    }

    /**
     * Creates a new {@link OptionDialogue}.
     *
     * @param option1
     * 		The text for the first option.
     *
     * @param action1
     * 		The action for the first action.
     *
     * @param option2
     * 		The text for the second option.
     *
     * @param action2
     * 		The action for the second action.
     *
     * @param option3
     * 		The text for the third option.
     *
     * @param action3
     * 		The action for the third action.
     *
     * @param option4
     * 		The text for the four option.
     *
     * @param action4
     * 		The action for the four action.
     *
     * @param option5
     * 		The text for the fifth option.
     *
     * @param action5
     * 		The action for the fifth action.
     */
    constructor(option1: String, action1: Runnable, option2: String, action2: Runnable, option3: String, action3: Runnable, option4: String, action4: Runnable, option5: String, action5: Runnable) {
        lines = listOf(option1, option2, option3, option4, option5).toTypedArray()
        actions.add(action1)
        actions.add(action2)
        actions.add(action3)
        actions.add(action4)
        actions.add(action5)
    }

    override fun accept(factory: DialogueFactory) {
        factory.sendOption(this)
    }

}