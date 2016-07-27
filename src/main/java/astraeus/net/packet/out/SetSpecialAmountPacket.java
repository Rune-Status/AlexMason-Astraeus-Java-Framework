package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetSpecialAmountPacket implements Sendable {

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(137);

		if (player.getSpecialAmount() > 100) {
			player.setSpecialAmount(100);
		}
		
		if (player.getSpecialAmount() < 0) {
			player.setSpecialAmount(0);
		}
        
        builder.write(player.getSpecialAmount());
        return builder.toOutgoingPacket();
    }
}
