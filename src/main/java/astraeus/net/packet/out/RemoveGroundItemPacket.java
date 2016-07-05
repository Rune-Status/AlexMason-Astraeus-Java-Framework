package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

/**
 * The {@link OutgoingPacket} that removes an item from the ground.
 *
 * @author SeVen
 */
public final class RemoveGroundItemPacket implements Sendable {

    public RemoveGroundItemPacket(Object n) {
        ///super(156);
    }

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
//        synchronized (player) {
//            player.send(new SendCoordinate(item.getLocation()));
//            builder.write(0, ByteModification.SUBTRACTION)
//                    .writeShort(item.getItem().getId());
            return Optional.empty();
       // }
    }

}
