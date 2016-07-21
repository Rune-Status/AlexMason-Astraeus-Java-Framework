package plugin.click

import astraeus.game.event.EventContext
import astraeus.game.event.EventSubscriber
import astraeus.game.event.SubscribesTo
import astraeus.game.event.impl.ButtonActionEvent
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights

import astraeus.net.packet.out.SetWidgetConfigPacket
import astraeus.net.packet.out.ServerMessagePacket

@SubscribesTo(ButtonActionEvent::class)
class ButtonClick : EventSubscriber<ButtonActionEvent> {	

	override fun subscribe(context: EventContext, player: Player, event: ButtonActionEvent) {
		if (!player.canClickButton()) {
            return;
        }
		
		if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Player.DEBUG_KEY)) {
			player.send(ServerMessagePacket("[button= ${event.button}]"))
		}
		
		when(event.button) {
			
			2458 -> {
                if (player.canLogout()) {
                    player.onLogout()
                }
            }
			
			1050, 19158 ->  {
                if (player.movement.isRunning == true) player.movement.isRunning = false else player.movement.isRunning = true
                // run orb toggle
                player.send(SetWidgetConfigPacket(152, if (player.movement.isRunning) 1 else 0))
                // run button in the wrench tab
                player.send(SetWidgetConfigPacket(429, if (player.movement.isRunning) 1 else 0))
            }
			
		}
		
		
	}
	
}