package astraeus.plugins.clicking.items

import astraeus.game.model.entity.`object`.GameObject
import astraeus.game.model.entity.item.Item
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket

/**
 * Handles the action of using an {@link Item} with a {@link GameObject}.
 *
 * @author Seven
 */
open class ItemOnObject(val player: Player, val item: Item, val gameObject: GameObject) : ClickEvent {

    /**
     * Handles an {@link Item} on a {@link GameObject} event for a player.
     */
    override fun handleAction() {
        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[ItemOnObject] - itemId:  ${item.id} objectId: ${gameObject.id} objectLocation: ${gameObject.location}"))
        }

    }

}