package astraeus.net.packet.out;

import astraeus.game.model.Location;
import astraeus.game.model.Projectile;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayProjectilePacket extends OutgoingPacket {

	private final Projectile projectile;
	private final int lock;
	private final byte offsetX;
	private final byte offsetY;
	//private final SendAltCoordinates sendAlt;

	public DisplayProjectilePacket(Player player, Projectile projectile, Location location, int lock, byte offsetX, byte offsetY) {
		super(117);
		this.projectile = projectile;
		this.lock = lock;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		if (projectile.getSize() > 1) {
			//sendAlt = new SendAltCoordinates(new Location(location.getX() + (projectile.getSize() / 2), location.getY() + (projectile.getSize() / 2)));
		} else {
			//sendAlt = new SendAltCoordinates(location);
		}
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		//player.send(sendAlt);
		builder.write(50)
		.write(offsetX)
		.write(offsetY)
		.writeShort(lock)
		.writeShort(projectile.getId())
		.write(projectile.getStartHeight())
		.write(projectile.getEndHeight())
		.writeShort(projectile.getDelay())
		.writeShort(projectile.getDuration())
		.write(projectile.getCurve())
		.write(64);
		return builder;
	}

}