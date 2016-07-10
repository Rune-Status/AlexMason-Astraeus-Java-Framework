package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class PlaySoundPacket implements Sendable {

    private final int id;

    private final int volume;

    private final int delay;

    public PlaySoundPacket(int id) {
        this(id, 50, 0);
    }

    public PlaySoundPacket(int id, int volume) {
        this(id, volume, 0);
    }

    public PlaySoundPacket(int id, int volume, int delay) {
        this.id = id;
        this.volume = volume;
        this.delay = delay;
    }

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(174);

        if (!(Boolean) player.attr().get(Attribute.SOUND) || id <= 0) {
            return Optional.empty();
        }

            builder.writeShort(id)
                    .write(volume)
                    .writeShort(delay);

            return builder.toOutgoingPacket();
        }

    }
