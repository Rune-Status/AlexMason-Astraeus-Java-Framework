package astraeus.net.packet.in;

import astraeus.game.event.impl.ObjectFirstClickEvent;
import astraeus.game.model.Location;
import astraeus.game.model.World;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.object.GameObject;
import astraeus.game.task.Task;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;

/**
 * The {@link IncomingPacket} responsible for clicking various options of an
 * in-game object.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({ IncomingPacket.FIRST_CLICK_OBJECT, IncomingPacket.SECOND_CLICK_OBJECT, IncomingPacket.THIRD_CLICK_OBJECT })
public class ObjectInteractionPacket implements Receivable {

	@Override
	public void handlePacket(final Player player, IncomingPacket packet) {
		switch (packet.getOpcode()) {

		case IncomingPacket.FIRST_CLICK_OBJECT:
			handleFirstClickObject(player, packet);
			break;

		case IncomingPacket.SECOND_CLICK_OBJECT:
			handleSecondClickObject(player, packet);
			break;

		case IncomingPacket.THIRD_CLICK_OBJECT:
			handleThirdClickObject(player, packet);
			break;

		}

	}

	/**
	 * Handles the event of a player first clicking on an object.
	 * 
	 * @param player
	 *            The player clicking on the object.
	 * 
	 * @param packet
	 *            The packet that denotes which type of click.
	 */
	private void handleFirstClickObject(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int x = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		int id = reader.readShort(false);
		int y = reader.readShort(false, ByteModification.ADDITION);

		GameObject object = new GameObject(id, new Location(x, y));
		
		if (player == null || object == null) {
			return;
		}
		
		World.WORLD.submit(new Task(0, false) {

			@Override
			public void execute() {
				if (player.getLocation().isWithinInteractionDistance(object.getLocation())) {
					player.faceLocation(object.getLocation());
					cancel();
				}
			}
			
			@Override
			public void onCancel() {
				player.post(new ObjectFirstClickEvent(object));
			}
			
		});
	}

	/**
	 * Handles the event of a player second clicking an object.
	 * 
	 * @param player
	 *            The player clicking on the object.
	 * 
	 * @param packet
	 *            The packet that denotes which type of click.
	 */
	private void handleSecondClickObject(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int id = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		int y = reader.readShort(ByteOrder.LITTLE);
		int x = reader.readShort(false, ByteModification.ADDITION);

		GameObject object = new GameObject(id, new Location(x, y, player.getLocation().getHeight()));

		if (player == null || object == null || object.getId() != id) {
			return;
		}
		
		World.WORLD.submit(new Task(0, false) {

			@Override
			public void execute() {
				if (player.getLocation().isWithinInteractionDistance(object.getLocation())) {
					player.faceLocation(object.getLocation());
					cancel();
				}
			}
			
			@Override
			public void onCancel() {
				player.post(new ObjectFirstClickEvent(object));
			}
			
		});

	}

	/**
	 * Handles the event of a player third clicking an object.
	 * 
	 * @param player
	 *            The player clicking on the object.
	 * 
	 * @param packet
	 *            The packet that denotes which type of click.
	 */
	private void handleThirdClickObject(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int x = reader.readShort(ByteOrder.LITTLE);
		int y = reader.readShort(false);
		int id = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);

		GameObject object = new GameObject(id, new Location(x, y, player.getLocation().getHeight()));

		if (player == null || object == null || object.getId() != id) {
			return;
		}
		
		World.WORLD.submit(new Task(0, false) {

			@Override
			public void execute() {
				if (player.getLocation().isWithinInteractionDistance(object.getLocation())) {
					player.faceLocation(object.getLocation());
					cancel();
				}
			}
			
			@Override
			public void onCancel() {
				player.post(new ObjectFirstClickEvent(object));
			}
			
		});	

	}

}
