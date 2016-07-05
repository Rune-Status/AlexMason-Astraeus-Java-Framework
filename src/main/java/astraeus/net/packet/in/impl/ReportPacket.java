package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.in.IncomingPacketListener;

/**
 * The {@link IncomingPacket} responsible reporting another player.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(218)
public class ReportPacket implements IncomingPacketListener {

    @Override
    public void handlePacket(Player player, IncomingPacket packet) {

    }
}
