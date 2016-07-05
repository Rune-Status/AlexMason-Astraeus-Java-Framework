package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayWelcomeScreenPacket extends OutgoingPacket {

    private final int days;

    private final int unreadMessages;

    private final int member;

    private final int ip;

    private final int daysSince;

    public DisplayWelcomeScreenPacket(int days, int unreadMessages, int member, int ip, int daysSince) {
	super(176);
	this.days = days;
	this.unreadMessages = unreadMessages;
	this.member = member;
	this.ip = ip;
	this.daysSince = daysSince;
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	builder.write(days, ByteModification.NEGATION)
	.writeShort(unreadMessages, ByteModification.ADDITION)
	.write(member)
	.writeInt(ip, ByteOrder.MIDDLE)
	.writeShort(daysSince);
	
	return builder;
    }

}
