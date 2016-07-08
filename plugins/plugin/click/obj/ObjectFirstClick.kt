package plugin.click.obj

import astraeus.game.event.impl.ObjectFirstClickEvent
import astraeus.game.event.EventSubscriber
import astraeus.game.event.EventContext
import astraeus.game.event.SubscribesTo

import astraeus.net.packet.out.ServerMessagePacket

import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute

@SubscribesTo(ObjectFirstClickEvent::class)
class ObjectFirstClick : EventSubscriber<ObjectFirstClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: ObjectFirstClickEvent) {
		
		if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[click= object], [type= first], [id= ${event.gameObject.id}], [location= ${event.gameObject.location.toString()}]"));
        }
		
		when (event.gameObject.id) {
			
		}
		
	}
	
}