package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayTabWidgetPacket extends OutgoingPacket {

	private final int id;

	public DisplayTabWidgetPacket(int id) {
		super(106);
		this.id = id;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.write(id, ByteModification.NEGATION);
		player.getUpdateFlags().add(UpdateFlag.APPEARANCE);
		return builder;
	}

}
