package plugin.click.npc

import astraeus.game.event.EventContext
import astraeus.game.event.EventSubscriber
import astraeus.game.event.SubscribesTo
import astraeus.game.event.impl.NpcFirstClickEvent

import astraeus.net.packet.out.ServerMessagePacket

import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute

@SubscribesTo(NpcFirstClickEvent::class)
class NpcFirstClick : EventSubscriber<NpcFirstClickEvent> {
	
	override fun subscribe(context: EventContext, player: Player, event: NpcFirstClickEvent) {
		
		if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[click= npc], [type = first], [id= ${event.npc.id}], [slot= ${event.npc.slot}]"));
        }
		
		when(event.npc.id) {
		
		}
		
	}
	
}