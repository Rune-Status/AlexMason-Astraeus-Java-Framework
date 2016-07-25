package plugin.event;

import java.util.logging.Logger;

import astraeus.game.event.EventContext;
import astraeus.game.event.EventSubscriber;
import astraeus.game.event.SubscribesTo;
import astraeus.game.model.World;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.event.LogoutEvent;
import astraeus.game.model.entity.mob.player.io.PlayerSerializer;
import astraeus.net.packet.out.LogoutPlayerPacket;
import astraeus.util.LoggerUtils;

@SubscribesTo(LogoutEvent.class)
public final class LogoutEventConsumer implements EventSubscriber<LogoutEvent> {
	
	private final Logger logger = LoggerUtils.getLogger(LogoutEventConsumer.class);

	@Override
	public void subscribe(EventContext context, Player player, LogoutEvent event) {
		event.getPlayer().attr().put(Player.ACTIVE_KEY, false);
		event.getPlayer().attr().put(Player.LOGOUT_KEY, true);
		event.getPlayer().attr().put(Player.DISCONNECTED_KEY, true);
		
		event.getPlayer().resetEntityInteraction();
		
		PlayerSerializer.encode(event.getPlayer());
		
		event.getPlayer().getSession().getChannel().close();
		World.world.deregister(event.getPlayer());
		
		event.getPlayer().queuePacket(new LogoutPlayerPacket());

		logger.info(String.format("[DEREGISTERED]: [host= %s]", event.getPlayer().getSession().getHostAddress()));
	}

}
