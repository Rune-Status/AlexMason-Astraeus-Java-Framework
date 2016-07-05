package astraeus.content.commands.impl

import astraeus.game.model.Location
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.net.packet.out.ServerMessagePacket
import astraeus.content.commands.CommandParser
import astraeus.content.commands.Command

/**
 * Represents commands that only [Player]s with [PlayerRights] of `ADMINISTRATOR` or higher can access.

 * @author Seven
 */
class AdministratorCommand : Command {

    @Throws(Exception::class)
    override fun execute(player: Player, parser: CommandParser): Boolean {
        when (parser.command) {

            "debug" -> {
                player.attr().toggle(Attribute.DEBUG)
                player.send(ServerMessagePacket(String.format("[debug=%s]", player.attr().get<Any>(Attribute.DEBUG).toString())))
                return true
            }

            "test" -> {
                player.send(ServerMessagePacket("I have access to admin commands"))
                return true
            }

            "test1" -> {
                player.send(ServerMessagePacket("I have access to admin commands"))
                return true
            }

            "tele" -> {
                if (parser.hasNext(2)) {
                    val x = parser.nextInt()
                    val y = parser.nextInt()

                    val location = Location(x, y)

                    player.move(location)
                    player.send(ServerMessagePacket(String.format("You have teleported to: %s", location.toString())))
                }
                return true
            }

            "bank" -> {
                player.bank.open()
                return true
            }

            "coords", "pos", "mypos" -> {
                player.send(ServerMessagePacket(String.format("Your location is %s", player.location.toString())))
                return true
            }
        }
        return false
    }

    override val rights: PlayerRights
        get() = PlayerRights.ADMINISTRATOR

}
