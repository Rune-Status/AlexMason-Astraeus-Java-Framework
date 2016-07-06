package astraeus.plugins.clicking.items

import astraeus.game.model.entity.item.Item
import astraeus.game.model.entity.mob.npc.Npc
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket

/**
 * Handles the action of using an item on an npc.
 *
 * @author Seven
 */
open class ItemOnNpc(val player: Player, val item: Item, val mob: Npc) : ClickEvent {

    /**
     * Handles the action of using an {@link Item} on a {@link Npc}.
     */
    override fun handleAction() {
        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[ItemOnNpc] - itemId: ${item.id} npcId: ${mob.id} slot: ${mob.slot}"));
        }

    }

}