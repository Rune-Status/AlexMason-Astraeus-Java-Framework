package astraeus.plugins.commands

import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights

/**
 * Represents a command.

 * @author Seven
 */
interface Command {

    @Throws(Exception::class)
    fun execute(player: Player, parser: CommandParser): Boolean

    val rights: PlayerRights

}
