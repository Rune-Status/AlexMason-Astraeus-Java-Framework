package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

/**
 * The {@link IncomingPacket} responsible for various clicking in-game.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({241, 3, 0, 77})
public class DefaultPacket implements Receivable {

    @Override
    public void handlePacket(Player player, IncomingPacket packet) {

    }

}
