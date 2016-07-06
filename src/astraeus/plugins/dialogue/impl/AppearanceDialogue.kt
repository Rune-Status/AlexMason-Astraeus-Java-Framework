package astraeus.plugins.dialogue.impl

import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.net.packet.out.DisplayWidgetPacket
import astraeus.plugins.dialogue.Dialogue
import astraeus.plugins.dialogue.DialogueFactory
import astraeus.plugins.dialogue.Expression

open class AppearanceDialogue(): Dialogue() {

    override fun sendDialogues(factory: DialogueFactory) {
        factory.sendNpcChat(Expression.HAPPY, "Would you care to change your appearance?")
                .sendOption("Change your appearance", Runnable {
                    factory.sendPlayerChat("I would like to change my appearance.")
                    .onAction(Runnable {
                        factory.player.attr().put(Attribute.CHANGING_APPEARANCE, true)
                        factory.player.send(DisplayWidgetPacket(3559))
                    })
                }, "Keep your current appearance.", Runnable {
                    factory.sendPlayerChat("I would like to keep my current appearance.")

                }).execute()
    }

}