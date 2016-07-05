package main.astraeus.content.clicking.objects

import astraeus.game.model.entity.`object`.GameObject
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket

open class ObjectThirdClick(val player: Player, val gameObject: GameObject): ClickEvent {

    override fun handleAction() {
        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[click= object], [type= third], [id= ${gameObject.id}], [location= ${gameObject.location.toString()}]"));
        }

        when(gameObject.id) {

        }

    }

}
