package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetPrivacyOptionPacket extends OutgoingPacket {

    private final int publicChat;

    private final int privateChat;

    private final int tradeChat;

    public SetPrivacyOptionPacket(int publicChat, int privateChat, int tradeChat) {
	super(206);
	this.publicChat = publicChat;
	this.privateChat = privateChat;
	this.tradeChat = tradeChat;
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	builder.write(publicChat)
	.write(privateChat)
	.write(tradeChat);

	return builder;
    }

}
