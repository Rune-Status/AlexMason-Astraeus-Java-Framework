package astraeus.game.event.impl;

import astraeus.game.event.Event;

public final class ItemFirstClickEvent implements Event {
	
	private final int id;
	
	private final int slot;
	
	private final int widgetId;
	
	public ItemFirstClickEvent(int id, int slot, int widgetId) {
		this.id = id;
		this.slot = slot;
		this.widgetId = widgetId;
	}

	public int getId() {
		return id;
	}

	public int getSlot() {
		return slot;
	}

	public int getWidgetId() {
		return widgetId;
	}	

}
