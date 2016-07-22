package astraeus.net.packet.in;

import astraeus.game.event.impl.ObjectSecondClickEvent;
import astraeus.game.model.Position;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.object.GameObject;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

@IncomingPacket.IncomingPacketOpcode(IncomingPacket.OBJECT_OPTION_2)
public final class ObjectSecondOptionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		int id = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		int y = reader.readShort(ByteOrder.LITTLE);
		int x = reader.readShort(false, ByteModification.ADDITION);

		GameObject object = new GameObject(id, new Position(x, y, player.getPosition().getHeight()));

		if (player == null || object == null || object.getId() != id) {
			return;
		}

		player.getMovementListener().append(() -> {			
			if (player.getPosition().isWithinDistance(object.getPosition(), 1)) {
				player.faceLocation(object.getPosition());
				player.post(new ObjectSecondClickEvent(object));
			}
		});
	}

}
