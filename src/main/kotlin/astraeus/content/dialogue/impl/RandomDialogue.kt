package main.astraeus.content.dialogue.impl

import main.astraeus.content.dialogue.Dialogue
import main.astraeus.content.dialogue.DialogueFactory
import main.astraeus.content.dialogue.Expression

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