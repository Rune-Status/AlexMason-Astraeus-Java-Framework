package astraeus.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import astraeus.game.model.World;
import astraeus.game.model.entity.mob.npc.Npc;
import astraeus.game.task.TaskManager;
import astraeus.util.LoggerUtils;

/**
 * Represents a simple engine that sequentially processes game logic.
 * 
 * @author Vult-R
 */
public final class GameEngine {

	/**
	 * The single logger for this class.
	 */
	private static final Logger LOGGER = LoggerUtils.getLogger(GameEngine.class);

	/**
	 * The {@link ScheduledExecutorService} that will run the game logic.
	 */
	private final ScheduledExecutorService sequencer = Executors
			.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameThread").build());

	/**
	 * Schedules the game engine to start processing logic.
	 */
	public void start() {
		LOGGER.info("Starting game engine...");
		sequencer.scheduleAtFixedRate(() -> {

			try {
				TaskManager.sequence();
				World.WORLD.process();
				Npc.process();
			} catch (final Throwable t) {
				LOGGER.log(Level.SEVERE, "An error has occured during the main game sequence!", t);
			}

		}, 600, GameConstants.CYLCE_RATE, TimeUnit.MILLISECONDS);
	}

}
