package astraeus.game.event.impl;

import astraeus.game.event.Event;
import astraeus.game.model.entity.item.Item;

public final class ItemOnItemEvent implements Event {
	
	private final Item used;
	
	private final Item usedWith;
	
	public ItemOnItemEvent(Item used, Item usedWith) {
		this.used = used;
		this.usedWith = usedWith;
	}

	public Item getUsed() {
		return used;
	}

	public Item getUsedWith() {
		return usedWith;
	}	

}
