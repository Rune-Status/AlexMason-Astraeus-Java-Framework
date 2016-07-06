package astraeus.plugins.clicking.items

import astraeus.game.model.entity.item.Item
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket

/**
 * Handles the first option click of an item for a player.
 *
 * @author Seven
 */
open class ItemFirstClick(val player: Player, val item: Item) : ClickEvent {

    /**
     * Handles the first option click of an {@link Item}.
     */
    override fun handleAction() {

        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[ItemClick#1] - ItemId: ${item.id}"))
        }

        when(item.id){

        }

    }

}
