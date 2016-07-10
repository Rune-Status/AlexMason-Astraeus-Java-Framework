package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

/**
 * The {@link IncomingPacket}s that are known as silent packets.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({ IncomingPacket.FOCUS_CHANGE, 77, IncomingPacket.CAMERA_MOVEMENT,
        IncomingPacket.IDLE_LOGOUT, 78, 36, 226, 246, 148, 183, 230, 136, 189, 152, 200, 85, 165, 238, 150 })
public class SilentPacket implements Receivable {

    @Override
    public void handlePacket(Player player, IncomingPacket packet) {

    }
}
