package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class RemoveWidgetPacket extends OutgoingPacket {

    public RemoveWidgetPacket() {
	    super(219);
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	return builder;
    }

}
