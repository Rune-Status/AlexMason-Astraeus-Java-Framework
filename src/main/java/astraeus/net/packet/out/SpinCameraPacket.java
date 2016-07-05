package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SpinCameraPacket implements Sendable {

	private final int x;

	private final int y;

	private final int z;

	private final int speed;

	private final int angle;

	public SpinCameraPacket(int x, int y, int z, int speed, int angle) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.speed = speed;
		this.angle = angle;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(177);
		builder.write(x)
		.write(y)
		.writeShort(z)
		.write(speed)
		.write(angle);
		return builder.toOutgoingPacket();
	}

}
