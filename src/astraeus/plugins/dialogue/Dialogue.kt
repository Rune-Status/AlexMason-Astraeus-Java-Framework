package astraeus.plugins.dialogue

import com.google.common.collect.ImmutableList

/**
 * Represents an abstract dialogue, in which extending classes will be able to construct and send dialogues
 * to a player.
 *
 * @author Seven
 */
abstract class Dialogue {

    companion object {

        /**
         * The action buttons responsible for dialogues.
         */
        val DIALOGUE_BUTTONS = ImmutableList.of(2461, 2471, 2482, 2462, 2472, 2483, 2473, 2484, 2485, 2494, 2495, 2496, 2497, 2498)

        /**
         * Checks if the button triggered is an optional dialogue button.
         *
         * @param button
         * The index of the button being checked.
         *
         * @return The result of the operation.
         */
        fun isDialogueButton(button: Int): Boolean {
            return DIALOGUE_BUTTONS.asSequence().any{it == button}
        }
    }

    /**
     * Sends a player a dialogue.
     *
     * @param factory
     * The factory for this dialogue.
     */
    abstract fun sendDialogues(factory: DialogueFactory)

}