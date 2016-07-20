package astraeus.net.packet.in;

import astraeus.game.event.impl.NpcThirdClickEvent;
import astraeus.game.model.World;
import astraeus.game.model.entity.mob.npc.Npc;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

@IncomingPacket.IncomingPacketOpcode({ IncomingPacket.NPC_OPTION_3 })
public final class NpcThirdClickPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		final Npc npc = World.WORLD.getMobs()[packet.getReader().readShort()];

		if (npc == null) {
			return;
		}

		player.getMovementListener().append(() -> {
			if (player.getPosition().isWithinDistance(npc.getPosition().copy(), 1)) {
				player.setInteractingEntity(npc);
				npc.setInteractingEntity(player);

				player.post(new NpcThirdClickEvent(npc));
			}
		});
	}

}
