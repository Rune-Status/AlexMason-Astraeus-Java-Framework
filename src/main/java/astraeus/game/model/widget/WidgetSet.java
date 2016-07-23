package astraeus.game.model.widget;

import java.util.HashMap;
import java.util.Map;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.out.DisplayChatBoxWidgetPacket;
import astraeus.net.packet.out.DisplayTabWidgetPacket;
import astraeus.net.packet.out.DisplayWidgetPacket;
import astraeus.net.packet.out.RemoveWidgetPacket;

/**
 * A specialized set for widgets.
 */
public final class WidgetSet {
	
	/**
	 * A map of widgets and their types.
	 */
	private final Map<WidgetType, Integer> widgets = new HashMap<>();
	
	/**
	 * That player that this set belongs to.
	 */
	private final Player player;
	
	/**
	 * Creates a new {@link WidgetSet}.
	 * 
	 * @param player
	 * 		The player that this set belongs to.
	 */
	public WidgetSet(Player player) {
		this.player = player;
	}
	
	/**
	 * Opens a window type widget for a player.
	 * 
	 * @param id
	 * 		The id of the widget.
	 */
	public void open(int id) {
		open(WidgetType.WINDOW, id);
	}
	
	/**
	 * Opens a widget for a player.
	 * 
	 * @param type
	 * 		The type of widget to open.
	 * 
	 * @param id
	 * 		The id of the widget.
	 */
	public void open(WidgetType type, int id) {
		widgets.clear();
		switch(type) {
		case CHAT_BOX:	
			player.queuePacket(new DisplayChatBoxWidgetPacket(id));
			break;
			
		case TAB:
			player.queuePacket(new DisplayTabWidgetPacket(id));
			break;
			
		case WINDOW:
			player.queuePacket(new DisplayWidgetPacket(id));
			break;		
		}
		widgets.put(type, id);
	}
	
	/**
	 * Closes any widgets that are open.
	 */
	public void close() {
		widgets.clear();
		player.queuePacket(new RemoveWidgetPacket());
	}
	
	/**
	 * Determines a widget is already present.
	 */
	public boolean contains(int id) {
		return widgets.containsValue(id);
	}
	
	/**
	 * Determines if a type of widget is already present.
	 */
	public boolean contains(WidgetType type) {
		return widgets.containsKey(type);
	}	
	
	/**
	 * Determines if the map is empty.
	 */
	public boolean isEmpty() {
		return widgets.isEmpty();
	}

}
