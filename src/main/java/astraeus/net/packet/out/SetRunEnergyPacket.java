package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public class SetRunEnergyPacket extends OutgoingPacket {

	public SetRunEnergyPacket() {
		super(110);
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
            builder.write(player.getRunEnergy());
            return builder;
	}

}
