package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SpinCameraPacket extends OutgoingPacket {

	private final int x;

	private final int y;

	private final int z;

	private final int speed;

	private final int angle;

	public SpinCameraPacket(int x, int y, int z, int speed, int angle) {
		super(177);
		this.x = x;
		this.y = y;
		this.z = z;
		this.speed = speed;
		this.angle = angle;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.write(x)
		.write(y)
		.writeShort(z)
		.write(speed)
		.write(angle);
		return builder;
	}

}
