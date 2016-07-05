package astraeus.net.packet.out;

import astraeus.Configuration;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class AddFriendPacket extends OutgoingPacket {

    private final long username;

    private int world;

    public AddFriendPacket(long username, int world) {
	super(50);
	this.username = username;
	this.world = world;
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	    if (world != 0) {
		world += 9;
	    } else if (!Configuration.WORLD_LIST_FIX) {
		world += 1;
	    }
	    builder.writeLong(username);
	    builder.write(world);
	    return builder;
    }
    
}
