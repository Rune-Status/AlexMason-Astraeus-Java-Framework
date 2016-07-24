package plugin.shops;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import astraeus.game.event.EventContext;
import astraeus.game.event.EventSubscriber;
import astraeus.game.event.SubscribesTo;
import astraeus.game.event.impl.ShopEvent;
import astraeus.game.model.entity.mob.player.Player;

@SubscribesTo(ShopEvent.class)
public final class Shops implements EventSubscriber<ShopEvent> {
	
	public static final transient Map<String, Shop> shops = new HashMap<>();
	
	static {
		new ShopParser().run();
	}

	@Override
	public void subscribe(EventContext context, Player player, ShopEvent event) {
		shops.get(event.getName()).open(player);
	}
	
	public boolean test(ShopEvent event) {
		return shops.get(event.getName()) != null;
	}
	
	public static Optional<Shop> search(Player player) {
		for(Shop shop : shops.values()) {
			if (shop.getPlayers().contains(player)) {
				return Optional.of(shop);
			}
		}
		return Optional.empty();
	}

}
