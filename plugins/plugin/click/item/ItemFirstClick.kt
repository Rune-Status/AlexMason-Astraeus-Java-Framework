package plugin.click.item

import astraeus.game.event.EventContext
import astraeus.game.event.EventSubscriber
import astraeus.game.event.SubscribesTo
import astraeus.game.event.impl.ItemFirstClickEvent
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute

import astraeus.net.packet.out.SetWidgetConfigPacket
import astraeus.net.packet.out.ServerMessagePacket

@SubscribesTo(ItemFirstClickEvent::class)
class ItemFirstClick : EventSubscriber<ItemFirstClickEvent> {	

	override fun subscribe(context: EventContext, player: Player, event: ItemFirstClickEvent) {
		
		if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[ItemClick#1] - ItemId: ${event.id} Slot: ${event.slot} WidgetId: ${event.widgetId}"))
        }
		
		when(event.id) {

		}
		
		
	}
	
}