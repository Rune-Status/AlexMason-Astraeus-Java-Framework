package astraeus.content.clicking.npcs

import astraeus.game.model.entity.mob.npc.Npc
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket

/**
 * Handles the second click option of an npc.
 *
 * @author Seven
 */
open class NpcSecondClick(val player: Player, val mob: Npc) : ClickEvent {

    /**
     * Handles clicking on a mob.
     */
    override fun handleAction() {

        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[click= npc], [type = second], [id= ${mob.id}], [slot= ${mob.slot}]"));
        }

        when(mob.id) {

        }

    }

}
