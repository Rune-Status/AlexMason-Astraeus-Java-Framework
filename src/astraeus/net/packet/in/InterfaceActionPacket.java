package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;

@IncomingPacketOpcode({ IncomingPacket.TYPING_ONTO_INTERFACE, 127, 213 })
public class InterfaceActionPacket implements Receivable {

	private void handleInterfaceAction(Player player, IncomingPacket packet) {
		
		final int id = packet.getReader().readShort();
		//final int action = packet.getShort();
		switch (id) {

		}
	}

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {

		switch (packet.getOpcode()) {
			case 127:
				handleReceiveString(player, packet);
			break;

			case 213:
				handleInterfaceAction(player, packet);
			break;
		}
	}

	private void handleReceiveString(Player player, IncomingPacket packet) {
		final String text = packet.getReader().getRS2String();
		System.out.println(text);
		final int index = text.indexOf(",");
		final int id = Integer.parseInt(text.substring(0, index));
		String string = text.substring(index + 1);
		switch (id) {
			
			default:
				System.out.println("Received string: identifier=" + id + ", string=" + string);
			break;
		}
	}

}
