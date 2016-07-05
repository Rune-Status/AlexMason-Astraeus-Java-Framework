package astraeus.content.commands.impl

import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.net.packet.out.ServerMessagePacket
import astraeus.content.commands.Command
import astraeus.content.commands.CommandParser

/**
 * Represents commands that only [Player]s with [PlayerRights] of `MODERATOR` or higher can access.

 * @author Seven
 */
class ModeratorCommand : Command {

    @Throws(Exception::class)
    override fun execute(player: Player, parser: CommandParser): Boolean {
        when (parser.command) {

            "test" -> {
                player.send(ServerMessagePacket("I have access to moderator commands!"))
                return true
            }

            "test2" -> {
                player.send(ServerMessagePacket("I have access to moderator commands"))
                return true
            }
        }
        return false
    }

    override val rights: PlayerRights
        get() = PlayerRights.MODERATOR

}
