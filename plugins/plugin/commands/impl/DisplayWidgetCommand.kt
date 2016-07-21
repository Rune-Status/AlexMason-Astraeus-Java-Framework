package plugin.commands.impl

import astraeus.game.event.impl.CommandEvent
import astraeus.game.event.SubscribesTo
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights

import astraeus.net.packet.out.DisplayWidgetPacket;
import astraeus.game.model.widget.WidgetType

import plugin.commands.Command
import plugin.commands.CommandParser

@SubscribesTo(CommandEvent::class)
class DisplayWidgetCommand : Command() {
	
	override fun execute(player: Player, parser: CommandParser) : Boolean {
		if (parser.hasNext(2)) {
			val type = parser.nextString();
			
			val id = parser.nextInt()
			
			when (type) {
				"window", "w" -> player.widgets.open(WidgetType.WINDOW, id)
				"tab", "t" -> player.widgets.open(WidgetType.TAB, id)
				"chat", "c" -> player.widgets.open(WidgetType.CHAT_BOX, id)
			}
			return true
		} else if (parser.hasNext(1)) {
			val id = parser.nextInt()
			
			player.widgets.open(WidgetType.WINDOW, id)
			return true
		}
		return false
	}
	
		override fun test(event: CommandEvent): Boolean {
		when (event.name) {
			"widget" -> return true

			else -> return false
		}
	}

	override val rights: PlayerRights
		get() {
			return PlayerRights.DEVELOPER
		}
	
}