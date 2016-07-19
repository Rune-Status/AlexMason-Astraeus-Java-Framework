package astraeus.game.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import astraeus.game.event.EventSubscriber;
import astraeus.game.model.World;
import astraeus.util.LoggerUtils;

public final class PluginService {
	
	private static final Logger LOGGER = LoggerUtils.getLogger(PluginService.class);

	private static final Map<String, PluginMetaData> META_DATA = new HashMap<>();

	private static final List<EventSubscriber<?>> SUBSCRIBERS = new ArrayList<>();	

	public void load() {
		try {
			for (PluginMetaData meta : META_DATA.values()) {
				String base = meta.getBase();

				Class<?> clazz;

				try {

					clazz = Class.forName(base);

				} catch (Exception ex) {
					continue;
				}

				if (EventSubscriber.class.isAssignableFrom(clazz)) {

					final EventSubscriber<?> subscriber = (EventSubscriber<?>) clazz.newInstance();

					World.WORLD.provideSubscriber(subscriber);

					SUBSCRIBERS.add(subscriber);
				}
			}

			LOGGER.info("Loaded: " + SUBSCRIBERS.size() + " plugins.");			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reload() {

		load();

	}

	public List<EventSubscriber<?>> getSubscribers() {
		return SUBSCRIBERS;
	}

	public Map<String, PluginMetaData> getPluginMetaData() {
		return META_DATA;
	}

}
