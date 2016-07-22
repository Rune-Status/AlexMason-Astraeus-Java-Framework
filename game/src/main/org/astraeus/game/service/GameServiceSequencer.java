package astraeus.game.service;

import astraeus.game.model.World;

/**
 * The {@link GameService} sequencer implementation.
 * 
 * @author Vult-R
 */
public final class GameServiceSequencer extends GameService {

	@Override
	public void runGameLoop() {
		World.WORLD.dequeueLogin();
		
		synchronizer.synchronize();
		
		World.WORLD.dequeueLogout();

	}

}
