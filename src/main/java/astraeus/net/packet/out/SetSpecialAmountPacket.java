package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetSpecialAmountPacket extends OutgoingPacket {

    public SetSpecialAmountPacket() {
        super(137);
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
        if (player.getSpecialAmount() > 100) {
            player.setSpecialAmount(100);
        }
        if (player.getSpecialAmount() < 0) {
            player.setSpecialAmount(0);
        }
        builder.write(player.getSpecialAmount());
        return builder;
    }
}
