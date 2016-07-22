package astraeus.net.packet.in;

import astraeus.game.model.World;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.out.ServerMessagePacket;

@IncomingPacket.IncomingPacketOpcode(IncomingPacket.PLAYER_OPTION_2)
public final class PlayerSecondOptionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int otherPlayerIndex = reader.readShort(ByteOrder.LITTLE);
		Player other = (Player) World.WORLD.getPlayers().get(otherPlayerIndex);

		if (other == null) {
			player.send(new ServerMessagePacket("You tried to attack a player that doesn't exist."));
			return;
		}		
	}

}
