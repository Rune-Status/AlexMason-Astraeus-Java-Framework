package astraeus.net.packet.in.impl;
import astraeus.game.model.World;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket}s responsible interacting with other players.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({ IncomingPacket.TRADE_REQUEST, IncomingPacket.TRADE_ANSWER, IncomingPacket.CHALLENGE_PLAYER, IncomingPacket.FOLLOW_PLAYER, IncomingPacket.MAGIC_ON_PLAYER, IncomingPacket.ATTACK_PLAYER })
public class PlayerOptionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("[PlayerOptionPacket] Opcode: " + packet.getOpcode()));
		}

		switch (packet.getOpcode()) {

		case IncomingPacket.ATTACK_PLAYER:
			handleAttackPlayer(player, packet);
			break;

		case IncomingPacket.MAGIC_ON_PLAYER:
			handleMagicOnPlayer(player, packet);
			break;

		case IncomingPacket.CHALLENGE_PLAYER:
			handleDuelRequest(player, packet);
			break;

		case IncomingPacket.FOLLOW_PLAYER:
			handleFollowPlayer(player, packet);
			break;

		case IncomingPacket.TRADE_REQUEST:
			handleTradeRequest(player, packet);
			break;

		case IncomingPacket.TRADE_ANSWER:
			System.out.println("trade answer packet received: " + packet.getOpcode());
			break;

		}

	}
	
	private void handleDuelRequest(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		final int otherPlayerIndex = reader.readShort(ByteOrder.LITTLE);
		
		if (World.WORLD.getPlayers().get(otherPlayerIndex) == null) {
			return;
		}

	}

	private void handleFollowPlayer(Player follower, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		final int otherPlayerIndex = reader.readShort(ByteOrder.LITTLE);

		if (World.WORLD.getPlayers().get(otherPlayerIndex) == null) {
			return;
		}

		@SuppressWarnings("unused")
            final Player leader = (Player) World.WORLD.getPlayers().get(otherPlayerIndex);
		
	}

	/**
	 * Handles the event of a {@link Player} attacking another {@link Player}.
	 * 
	 * @param player
	 *            The attacking player.
	 * 
	 * @param packet
	 *            The packet for this action.
	 */
	private void handleAttackPlayer(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int otherPlayerIndex = reader.readShort(ByteOrder.LITTLE);
		Player other = (Player) World.WORLD.getPlayers().get(otherPlayerIndex);

		if (other == null) {
			player.send(new ServerMessagePacket("You tried to attack a player that doesn't exist."));
			return;
		}

	}

	/**
	 * Handles the event of a {@link Player} using magic on another
	 * {@link Player}.
	 * 
	 * @param player
	 *            The player using magic on another player.
	 * 
	 * @param packet
	 *            The packet for this action.
	 */
	private void handleMagicOnPlayer(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int otherPlayerIndex = reader.readShort(ByteModification.ADDITION);
		Player other = (Player) World.WORLD.getPlayers().get(otherPlayerIndex);
		@SuppressWarnings("unused")
		final int spell = reader.readShort(ByteOrder.LITTLE);

		if (other == null) {
			player.send(new ServerMessagePacket("You tried to attack a player that doesn't exist."));
			return;
		}

	}

	private void handleTradeRequest(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		final int otherPlayerTradeIndex = reader.readShort(ByteOrder.LITTLE);

		if (otherPlayerTradeIndex == player.getSlot()) {
			return;
		}

		if (player.getRights().equal(PlayerRights.ADMINISTRATOR)) {
			player.send(new ServerMessagePacket("Trading as an admin has been disabled."));
			return;
		}

		if (otherPlayerTradeIndex < 1) {
			return;
		}

		if (World.WORLD.getPlayers().get(otherPlayerTradeIndex) == null) {
			return;
		}

		Player other = (Player) World.WORLD.getPlayers().get(otherPlayerTradeIndex);

		if (other == null || !other.isRegistered() || other.isTeleporting() || other.isDead()) {
			return;
		}

	}

}
