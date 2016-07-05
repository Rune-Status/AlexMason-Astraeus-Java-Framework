package main.astraeus.content.clicking.items

import astraeus.game.model.entity.item.Item
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket

/**
 * Handles using an item with another item.
 *
 * @author Seven
 */
open class ItemOnItem(val player: Player, val used: Item, val usedWith: Item) : ClickEvent {

    /**
     * Handles the action of using an item with another item.
     */
    override fun handleAction() {
        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[ItemOnItem] - itemUsed: ${used.id} usedWith: ${usedWith.id}"));
        }

    }

}
