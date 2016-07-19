package plugin.commands.impl

import astraeus.game.event.impl.CommandEvent
import astraeus.game.event.SubscribesTo
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.net.packet.out.ServerMessagePacket
import astraeus.game.model.entity.item.Item

import plugin.commands.Command
import plugin.commands.CommandParser

@SubscribesTo(CommandEvent::class)
class ItemCommand : Command() {

	override fun execute(player: Player, parser: CommandParser) {
		if (parser.hasNext(2)) {
			val id = parser.nextInt()
			
			val amount = parser.nextInt()
			
			println("{${id} ${amount}}")
			
			player.inventory.add(Item(id, amount))
		} else if (parser.hasNext(1)) {
			val id = parser.nextInt()
			
			player.inventory.add(Item(id, 1))
		}
	}

	override fun test(event: CommandEvent): Boolean {
		when (event.name) {
			"item", "pickup" -> return true

			else -> return false
		}
	}

	override val rights: PlayerRights
		get() {
			return PlayerRights.PLAYER
		}

}