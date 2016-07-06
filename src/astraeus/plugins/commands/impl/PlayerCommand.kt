package astraeus.plugins.commands.impl

import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.net.packet.out.ServerMessagePacket
import astraeus.plugins.commands.Command
import astraeus.plugins.commands.CommandParser

/**
 * Represents commands that only [Player]s with [PlayerRights] of `PLAYER` or higher can access.

 * @author Seven
 */
class PlayerCommand : Command {

    @Throws(Exception::class)
    override fun execute(player: Player, parser: CommandParser): Boolean {
        when (parser.command) {

            "test" -> {
                player.send(ServerMessagePacket("I have access to player commands"))
                return true
            }

            "test3" -> {
                player.send(ServerMessagePacket("I have access to player commands"))
                return true
            }
        }
        return false
    }

    override val rights: PlayerRights
        get() = PlayerRights.PLAYER

}
