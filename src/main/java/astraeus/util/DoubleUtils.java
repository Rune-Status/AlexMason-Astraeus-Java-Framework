package astraeus.util;

import astraeus.game.model.Location;

public class DoubleUtils {
	
	public static double distance(Location first, Location second) {
		final int dx = second.getX() - first.getX();
		final int dy = second.getY() - first.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

}
