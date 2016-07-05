package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.packet.in.IncomingPacketListener;
import astraeus.net.packet.out.ServerMessagePacket;
import astraeus.content.commands.Command;
import astraeus.content.commands.CommandParser;
import astraeus.content.commands.impl.AdministratorCommand;
import astraeus.content.commands.impl.ModeratorCommand;
import main.astraeus.content.commands.impl.PlayerCommand;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * The {@link IncomingPacket} responsible for handling user commands send from the client.
 * 
 * @author Seven
 */
@IncomingPacketOpcode(IncomingPacket.PLAYER_COMMAND)
public class CommandPacket implements IncomingPacketListener {

      private static final Command[] COMMANDS = new Command[] {new PlayerCommand(), new ModeratorCommand(), new AdministratorCommand()};

      @Override
      public void handlePacket(Player player, IncomingPacket packet) {

            final String input = packet.getReader().getRS2String().trim().toLowerCase();

            final CommandParser parser = new CommandParser(input);

            Iterator<Command> itr = Arrays.stream(COMMANDS).filter($it -> player.getRights().greaterOrEqual($it.getRights())).collect(Collectors.toCollection(ArrayDeque::new)).descendingIterator();

            while(itr.hasNext()) {

                  Command command = itr.next();

                  try {
                        if (command.execute(player, parser)) {
                             break;
                        }
                  } catch(Exception ex) {
                        player.send(new ServerMessagePacket(String.format("The command ::%s is invalid.", parser.getCommand())));
                  }

            }

      }

}
