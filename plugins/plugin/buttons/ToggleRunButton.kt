package plugin.buttons

import astraeus.game.event.SubscribesTo
import astraeus.game.event.impl.ButtonActionEvent
import astraeus.game.model.entity.mob.player.Player
import astraeus.net.packet.out.SetWidgetConfigPacket

@SubscribesTo(ButtonActionEvent::class)
class ToggleRunButton : ButtonClick() {

    override fun execute(player: Player, event: ButtonActionEvent) {
        when (event.button) {
            1050, 19158 -> {
                if (player.movement.isRunning == true) player.movement.isRunning = false else player.movement.isRunning = true
                // run orb toggle
                player.send(SetWidgetConfigPacket(152, if (player.movement.isRunning) 1 else 0))
                // run button in the wrench tab
                player.send(SetWidgetConfigPacket(429, if (player.movement.isRunning) 1 else 0))
            }
        }
    }

    override fun test(event: ButtonActionEvent): Boolean {
        return event.button == 1050 || event.button == 19158;
    }

}