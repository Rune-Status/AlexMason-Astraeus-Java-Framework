package astraeus.net.packet.out;

import java.util.Optional;

import astraeus.game.model.entity.mob.combat.dmg.Poison.PoisonType;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

public final class SetPoisonPacket implements Sendable {
	
	/**
	 * The type of poison.
	 */
	private final PoisonType type;	
	
	/**
	 * The type of poison.
	 */
	public SetPoisonPacket(PoisonType type) {
		this.type = type;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder();
		builder.write(type.getType(), ByteModification.NEGATION);
		return builder.toOutgoingPacket();
	}

}
