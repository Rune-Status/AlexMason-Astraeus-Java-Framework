package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

/**
 * The {@link IncomingPacket} responsible reporting another player.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(218)
public class ReportPacket implements Receivable {

    @Override
    public void handlePacket(Player player, IncomingPacket packet) {

    }
}
