package astraeus.plugins.dialogue.impl

import astraeus.plugins.dialogue.Dialogue
import astraeus.plugins.dialogue.DialogueFactory
import astraeus.plugins.dialogue.Expression

/**
 * An implementation of dialogues, to show its syntax.
 *
 * @author Seven
 */
open class RandomDialogue(val npcId: Int): Dialogue() {

    override fun sendDialogues(factory: DialogueFactory) {
        factory.sendNpcChat(npcId, "Hello lad!", "Welcome to Astraeus!")
        .sendPlayerChat(Expression.HAPPY, "Well hello there stranger!")
        .sendNpcChat(Expression.HAPPY, "Wanna play a game?")
        .sendOption("Sure", Runnable {
            factory.sendPlayerChat(Expression.HAPPY, "Sure")
        }, "Hell no", Runnable {
            factory.sendPlayerChat(Expression.HAPPY, "Hell no")
        }).execute();
    }

}