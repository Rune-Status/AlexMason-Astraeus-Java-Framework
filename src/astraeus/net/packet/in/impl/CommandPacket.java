package astraeus.net.packet.in.impl;

import astraeus.game.event.impl.CommandEvent;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.packet.in.IncomingPacketListener;
import plugin.commands.CommandParser;

/**
 * The {@link IncomingPacket} responsible for handling user commands send from the client.
 * 
 * @author Vult-R
 */
@IncomingPacketOpcode(IncomingPacket.PLAYER_COMMAND)
public final class CommandPacket implements IncomingPacketListener {

      @Override
      public void handlePacket(Player player, IncomingPacket packet) {

			final String input = packet.getReader().getRS2String().trim().toLowerCase();
			
			player.post(new CommandEvent(new CommandParser(input)));
			
      }

}
