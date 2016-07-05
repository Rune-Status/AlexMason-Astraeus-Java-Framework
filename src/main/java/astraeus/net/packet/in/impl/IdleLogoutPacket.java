package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.packet.in.IncomingPacketListener;

/**
 * The {@link IncomingPacket} responsible logging out a player after a certain
 * amount of time.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode(202)
public class IdleLogoutPacket implements IncomingPacketListener {

    @Override
    public void handlePacket(Player player, IncomingPacket packet) {

    }
}