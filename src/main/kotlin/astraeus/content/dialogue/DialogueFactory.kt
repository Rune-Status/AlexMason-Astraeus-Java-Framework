package main.astraeus.content.dialogue

import astraeus.game.model.World
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.npc.Npc
import astraeus.game.model.entity.mob.npc.NpcDefinition
import astraeus.net.packet.out.*
import astraeus.util.StringUtils
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Represents a factory class that contains important functions for building dialogues.
 *
 * @author Seven <https://github.com/Vult-R>
 */
class DialogueFactory(val player: Player) {

    companion object {

        /**
         * The single logger for this class.
         */
        private val LOGGER = Logger.getLogger(DialogueFactory::class.java.name)

        /**
         * The maximum length of a single line of dialogue.
         */
        private val MAXIMUM_LENGTH = 100
    }

    /**
     * The queue of dialogues in this factory.
     */
    val chain: Queue<Chainable> = ArrayDeque()

    /**
     * The flag that denotes dialogue is active.
     */
    var isActive: Boolean = false

    /**
     * The next action in the dialogue chain.
     */
    var nextAction = Optional.empty<Runnable>()

    /**
     * Sends a player a dialogue.
     *
     * @param dialogue
     * The dialogue to sent.
     */
    fun sendDialogue(dialogue: Dialogue): DialogueFactory {
        player.dialogue = Optional.of(dialogue)
        dialogue.sendDialogues(this)
        return this
    }

    /**
     * Sets an {@code action} so this action can be executed after dialogues are done.
     *
     * @param action
     * 		The action to set.
     *
     * @return The instance of this factory.
     */
    fun onAction(action: Runnable): DialogueFactory {
        nextAction = Optional.of(action)
        return this
    }

    /**
     * Accepts the next dialogue in the chain.
     *
     * @return The instance of this factory.
     */
    fun onNext(): DialogueFactory {
        if (chain.peek() != null) {
            val chain = chain.poll()
            chain.accept(this)
        } else {
            player.send(RemoveWidgetPacket())
        }
        return this
    }

    /**
     * Executes an {@code option} for a {@code player}.
     *
     * @param type
     * 		The type of option.
     *
     * 	@param option
     * 		The option to execute.
     */
    fun executeOption(type: Int, option: Optional<OptionDialogue>): DialogueFactory {
        option.ifPresent { it.actions[type].run() }
        execute()
        return this
    }

    /**
     * Clears the current dialogue {@code chain}.
     *
     * @return The instance of this factory.
     */
    fun clear() {
        chain.clear()
        nextAction = Optional.empty()
        isActive = false
    }

    /**
     * Appends a {@code chain} to this factory.
     *
     * @return The instance of this factory.
     */
    private fun append(chain: Chainable): DialogueFactory {
        this.chain.add(chain)
        return this
    }

    /**
     * Retrieves the next dialogue in the chain and executes it.
     *
     * @return The instance of this factory.
     */
    fun execute(): DialogueFactory {
        // check to see if there are anymore dialogues.
        if (chain.peek() != null) {
            // there is so, grab the next dialogue.
            val entry = chain.poll()
            // is this an option dialogue?
            if (entry is OptionDialogue) {
                val option = entry
                player.optionDialogue = Optional.of(option)
            }
            isActive = true
            // whatever dialogue it is, accept it.
            entry.accept(this)
        } else {
            // there are no dialogues in this chain.
            // is there an action?
            if (nextAction.isPresent) {
                // there is so, execute it.
                nextAction.ifPresent({ `$it` -> `$it`.run() })
                // we just used this action so empty it so it can't be used again.
                nextAction = Optional.empty()
                return this
            }
            isActive = false
            // there are no more dialogues, so clear the screen.
            player.send(RemoveWidgetPacket())
        }
        return this
    }

    /**
     * Appends keywords to an existing dialogue text.
     *
     * @param line
     * The line to check for a keyword.
     */
    private fun appendKeywords(line: String): String {

        var newLine: String = line

        if (line.contains("#username")) {
            newLine = line.replace(("#username").toRegex(), StringUtils.formatName((player.username)))
        }
        if (player.interactingEntity != null && player.interactingEntity is Npc) {
            val mob = World.getMobs()[player.interactingEntity.slot]
            if (line.contains("#name")) {
                newLine = line.replace(("#name").toRegex(), mob.name)
            }
        }
        return newLine
    }

    /**
     * Appends a {@link PlayerDialogue} to the current dialogue chain.
     *
     * @param lines
     * 		The dialogue of the player talking.
     *
     * @return The instance of this factory.
     */
    fun sendPlayerChat(vararg lines: String): DialogueFactory {
        return append(PlayerDialogue(*lines))
    }

    /**
     * Appends a {@link PlayerDialogue} to the current dialogue chain.
     *
     * @param lines
     * 		The dialogue of the player talking.
     *
     * @param expression
     * 		The expression of this dialogue.
     *
     * @return The instance of this factory.
     */
    fun sendPlayerChat(expression: Expression, vararg lines: String): DialogueFactory {
        return append(PlayerDialogue(expression, *lines))
    }

    /**
     * Sends a dialogue with a player talking.
     *
     * @param dialogue
     * 		The player dialogue.
     *
     * 	@return The instance of this factory.
     */
    internal fun sendPlayerChat(dialogue: PlayerDialogue): DialogueFactory {
        val expression = dialogue.expression
        val lines = dialogue.lines
        validateLength(*lines)
        when (lines.size) {
            1 -> {
                player.send(SetWidgetAnimationPacket(969, expression.id))
                player.send(SetWidgetStringPacket(StringUtils.formatName(player.username), 970))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 971))
                player.send(SetPlayerHeadModelOnWidgetPacket(969))
                player.send(DisplayChatBoxWidgetPacket(968))
            }
            2 -> {
                player.send(SetWidgetAnimationPacket(974, expression.id))
                player.send(SetWidgetStringPacket(StringUtils.formatName(player.username), 975))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 976))
                player.send(SetWidgetStringPacket(appendKeywords(lines[1]), 977))
                player.send(SetPlayerHeadModelOnWidgetPacket(974))
                player.send(DisplayChatBoxWidgetPacket(973))
            }
            3 -> {
                player.send(SetWidgetAnimationPacket(980, expression.id))
                player.send(SetWidgetStringPacket(StringUtils.formatName(player.username), 981))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 982))
                player.send(SetWidgetStringPacket(appendKeywords(lines[1]), 983))
                player.send(SetWidgetStringPacket(appendKeywords(lines[2]), 984))
                player.send(SetPlayerHeadModelOnWidgetPacket(980))
                player.send(DisplayChatBoxWidgetPacket(979))
            }
            4 -> {
                player.send(SetWidgetAnimationPacket(987, expression.id))
                player.send(SetWidgetStringPacket(StringUtils.formatName(player.username), 988))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 989))
                player.send(SetWidgetStringPacket(appendKeywords(lines[1]), 990))
                player.send(SetWidgetStringPacket(appendKeywords(lines[2]), 991))
                player.send(SetWidgetStringPacket(appendKeywords(lines[3]), 992))
                player.send(SetPlayerHeadModelOnWidgetPacket(987))
                player.send(DisplayChatBoxWidgetPacket(986))
            }
            else -> LOGGER.log(Level.SEVERE, String.format("Invalid player dialogue line length: %s", lines.size))
        }
        return this
    }

    /**
     * Appends an npc dialogue.
     *
     * @param lines
     * 		The text of this dialogue.
     *
     * @return The instance of this factory.
     */
    fun sendNpcChat(vararg lines: String): DialogueFactory {
        return append(NpcDialogue(*lines))
    }

    /**
     * Appends an {@link NpcDialogue} to the current dialogue chain.
     *
     * @param expression
     * 		The expression of this npc.
     *
     * @param lines
     * 		The text of this dialogue.
     *
     * @return The instance of this factory.
     */
    fun sendNpcChat(expression: Expression, vararg lines: String): DialogueFactory {
        if (player.interactingEntity != null) {
            val index = player.interactingEntity.id
            return append(NpcDialogue(index, expression, *lines))
        }
        return append(NpcDialogue(expression, *lines))
    }

    /**
     * Appends an {@link NpcDialogue} to the current dialogue chain.
     *
     * @param id
     * 		The id of this npc.
     *
     * @param lines
     * 		The text of this dialogue.
     *
     * @return The instance of this factory.
     */
    fun sendNpcChat(id: Int, vararg lines: String): DialogueFactory {
        return append(NpcDialogue(id, Expression.DEFAULT, *lines))
    }

    /**
     * Appends an {@link NpcDialogue} to the current dialogue chain.
     *
     * @param id
     * 		The id of this npc.
     *
     * @param expression
     * 		The expression of this npc.
     *
     * @param lines
     * 		The text of this dialogue.
     *
     * @return The instance of this factory.
     */
    fun sendNpcChat(id: Int, expression: Expression, vararg lines: String): DialogueFactory {
        return append(NpcDialogue(id, expression, *lines))
    }

    /**
     * Sends a dialogue with a npc talking.
     *
     * @param dialogue
     * 		The dialogue.
     *
     * @return The instance of this factory.
     */
    internal fun sendNpcChat(dialogue: NpcDialogue): DialogueFactory {
        val expression = dialogue.expression
        val lines = dialogue.lines
        validateLength(*lines)
        var npcId = 0
        if (dialogue.id === -1) {
            if (player.interactingEntity != null) {
                if (player.interactingEntity.isNpc) {
                    npcId = player.interactingEntity.npc.id
                }
            }
        } else {
            npcId = dialogue.id
        }
        val mob = NpcDefinition.get(npcId)

        if (mob == null) {
            return this
        }

        when (lines.size) {
            1 -> {
                player.send(SetWidgetAnimationPacket(4883, expression.id))
                player.send(SetWidgetStringPacket(mob.name, 4884))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 4885))
                player.send(DisplayNpcHeadModelOnWidgetPacket(mob.id, 4883))
                player.send(DisplayChatBoxWidgetPacket(4882))
            }
            2 -> {
                player.send(SetWidgetAnimationPacket(4888, expression.id))
                player.send(SetWidgetStringPacket(mob.name, 4889))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 4890))
                player.send(SetWidgetStringPacket(appendKeywords(lines[1]), 4891))
                player.send(DisplayNpcHeadModelOnWidgetPacket(mob.id, 4888))
                player.send(DisplayChatBoxWidgetPacket(4887))
            }
            3 -> {
                player.send(SetWidgetAnimationPacket(4894, expression.id))
                player.send(SetWidgetStringPacket(mob.name, 4895))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 4896))
                player.send(SetWidgetStringPacket(appendKeywords(lines[1]), 4897))
                player.send(SetWidgetStringPacket(appendKeywords(lines[2]), 4898))
                player.send(DisplayNpcHeadModelOnWidgetPacket(mob.id, 4894))
                player.send(DisplayChatBoxWidgetPacket(4893))
            }
            4 -> {
                player.send(SetWidgetAnimationPacket(4901, expression.id))
                player.send(SetWidgetStringPacket(mob.name, 4902))
                player.send(SetWidgetStringPacket(appendKeywords(lines[0]), 4903))
                player.send(SetWidgetStringPacket(appendKeywords(lines[1]), 4904))
                player.send(SetWidgetStringPacket(appendKeywords(lines[2]), 4905))
                player.send(SetWidgetStringPacket(appendKeywords(lines[3]), 4906))
                player.send(DisplayNpcHeadModelOnWidgetPacket(mob.id, 4901))
                player.send(DisplayChatBoxWidgetPacket(4900))
            }
            else -> LOGGER.log(Level.SEVERE, String.format("Invalid npc dialogue line length: %s", lines.size))
        }
        return this
    }

    /**
     * Appends the {@link OptionDialogue} onto the current dialogue chain.
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
    fun sendOption(option1: String, action1: Runnable, option2: String, action2: Runnable): DialogueFactory {
        return append(OptionDialogue(option1, action1, option2, action2))
    }

    /**
     * Appends the {@link OptionDialogue} onto the current dialogue chain.
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
    fun sendOption(option1: String, action1: Runnable, option2: String, action2: Runnable, option3: String, action3: Runnable): DialogueFactory {
        return append(OptionDialogue(option1, action1, option2, action2, option3, action3))
    }

    /**
     * Appends the {@link OptionDialogue} onto the current dialogue chain.
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
    fun sendOption(option1: String, action1: Runnable, option2: String, action2: Runnable, option3: String, action3: Runnable, option4: String, action4: Runnable): DialogueFactory {
        return append(OptionDialogue(option1, action1, option2, action2, option3, action3, option4, action4))
    }

    /**
     * Appends the {@link OptionDialogue} onto the current dialogue chain.
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
    fun sendOption(option1: String, action1: Runnable, option2: String, action2: Runnable, option3: String, action3: Runnable, option4: String, action4: Runnable, option5: String, action5: Runnable): DialogueFactory {
        return append(OptionDialogue(option1, action1, option2, action2, option3, action3, option4, action4, option5, action5))
    }

    /**
     * Sends a dialogue with options.
     *
     * @param dialogue
     * 		The dialogue.
     *
     * @return The instance of this factory.
     */
    internal fun sendOption(dialogue: OptionDialogue): DialogueFactory {
        val options = dialogue.lines
        validateLength(*options)
        when (options.size) {
            2 -> {
                player.send(SetWidgetStringPacket("Select an Option", 2460))
                player.send(SetWidgetStringPacket(options[0], 2461))
                player.send(SetWidgetStringPacket(options[1], 2462))
                player.send(DisplayChatBoxWidgetPacket(2459))
                return this
            }
            3 -> {
                player.send(SetWidgetStringPacket("Select an Option", 2470))
                player.send(SetWidgetStringPacket(options[0], 2471))
                player.send(SetWidgetStringPacket(options[1], 2472))
                player.send(SetWidgetStringPacket(options[2], 2473))
                player.send(DisplayChatBoxWidgetPacket(2469))
                return this
            }
            4 -> {
                player.send(SetWidgetStringPacket("Select an Option", 2481))
                player.send(SetWidgetStringPacket(options[0], 2482))
                player.send(SetWidgetStringPacket(options[1], 2483))
                player.send(SetWidgetStringPacket(options[2], 2484))
                player.send(SetWidgetStringPacket(options[3], 2485))
                player.send(DisplayChatBoxWidgetPacket(2480))
                return this
            }
            5 -> {
                player.send(SetWidgetStringPacket("Select an Option", 2493))
                player.send(SetWidgetStringPacket(options[0], 2494))
                player.send(SetWidgetStringPacket(options[1], 2495))
                player.send(SetWidgetStringPacket(options[2], 2496))
                player.send(SetWidgetStringPacket(options[3], 2497))
                player.send(SetWidgetStringPacket(options[4], 2498))
                player.send(DisplayChatBoxWidgetPacket(2492))
                return this
            }
        }
        return this
    }

    /**
     * Appends a {@link StatementDialogue} to the current dialogue chain.
     *
     * @param lines
     * 		The text for this statement.
     *
     * @return The instance of this factory.
     */
    fun sendStatement(vararg lines: String): DialogueFactory {
        validateLength(*lines)
        append(StatementDialogue(*lines))
        return this
    }

    /**
     * Sends a player a statement dialogue.
     *
     * @param dialogue
     * The statement dialogue.
     */
    internal fun sendStatement(dialogue: StatementDialogue): DialogueFactory {
        validateLength(*dialogue.lines)
        when (dialogue.lines.size) {
            1 -> {
                player.send(SetWidgetStringPacket(dialogue.lines[0], 357))
                player.send(SetWidgetStringPacket("Click here to continue.", 358))
                player.send(DisplayChatBoxWidgetPacket(356))
            }
            else -> LOGGER.log(Level.SEVERE, String.format("Invalid statement dialogue line length: %s", dialogue.lines.size))
        }
        return this
    }

    /**
     * The method that validates the length of {@code text}.
     *
     * @param text
     * the text that will be validated.
     *
     * @throws IllegalStateException
     * if any lines of the text exceed a certain length.
     */
    private fun validateLength(vararg text: String) {
        if (text.asSequence().filter { !it.isNullOrBlank() }.any { it.length > MAXIMUM_LENGTH }) {
            throw IllegalStateException("Dialogue length too long, maximum length is: $MAXIMUM_LENGTH");
        }
    }

}
