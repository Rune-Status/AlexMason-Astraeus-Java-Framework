package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that shows an interface inside another interface.
 * 
 * @author SeVen
 */
public final class SetWidgetVisibilityPacket extends OutgoingPacket {

    /**
     * The id of the interface to show.
     */
    private final int id;

    /**
     * The toggle to display the interface to the user.
     */
    private final boolean hide;

    /**
     * Creates a new {@link SendInterfaceLayer).
     * 
     * @param id
     *            The id of the interface.
     * 
     * @param hide
     *            The toggle to display the interface.
     */
    public SetWidgetVisibilityPacket(int id, boolean hide) {
	super(171);
	this.id = id;
	this.hide = hide;
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	builder.write(hide ? 1 : 0)
	.writeShort(id);
	return builder;
    }

}
