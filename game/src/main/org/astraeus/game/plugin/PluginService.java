package astraeus.game.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import astraeus.game.event.EventSubscriber;
import astraeus.game.model.World;

public final class PluginService {

	private static final Map<String, PluginMetaData> META_DATA = new HashMap<>();
	
	private static final List<EventSubscriber<?>> REGISTERED_SUBSCRIBERS = new ArrayList<>();

	public void load() {		
		try {
			for (PluginMetaData meta : META_DATA.values()) {
				String base = meta.getBase();
				
				Class<?> clazz = Class.forName(base);
				
				if (EventSubscriber.class.isAssignableFrom(clazz)) {
					
					final EventSubscriber<?> subscriber = (EventSubscriber<?>)clazz.newInstance();
					
					World.WORLD.provideSubscriber(subscriber);
					
					REGISTERED_SUBSCRIBERS.add(subscriber);
				}				
			}
			
			System.out.println("Loaded: " + REGISTERED_SUBSCRIBERS.size() + " plugins.");			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reload() {

		load();
		
	}
	
	public List<EventSubscriber<?>> getSubscribers() {
		return REGISTERED_SUBSCRIBERS;
	}
	
	public Map<String, PluginMetaData> getPluginMetaData() {
		return META_DATA;
	}

}
