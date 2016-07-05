package astraeus.content.clicking.npcs

import astraeus.game.model.entity.mob.npc.Npc
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket
import main.astraeus.content.dialogue.impl.AppearanceDialogue
import main.astraeus.content.dialogue.impl.BankerDialogue
import main.astraeus.content.dialogue.impl.RandomDialogue

/**
 * Handles the first click option of an npc.
 *
 * @author Seven
 */
open class NpcFirstClick(val player: Player, val mob: Npc) : ClickEvent {

    /**
     * Handles clicking on a mob.
     */
    override fun handleAction() {

        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[click= npc], [type = first], [id= ${mob.id}], [slot= ${mob.slot}]"));
        }

        when(mob.id) {
            494, 495 -> player.dialogueFactory.sendDialogue(BankerDialogue())
            599 -> player.dialogueFactory.sendDialogue(AppearanceDialogue())

            else -> player.dialogueFactory.sendDialogue(RandomDialogue(mob.id))
        }

    }

}
