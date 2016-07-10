package astraeus.game.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import astraeus.game.GameConstants;

/**
 * Represents the games core class that sequentially processes game logic.
 * 
 * @author Seven
 */
public final class GameEngine {

      /**
       * The single logger for this class.
       */
      private static final Logger LOGGER = Logger.getLogger(GameEngine.class.getName());

      /**
       * The {@link ScheduledExecutorService} that will run the game logic.
       */
      private final ScheduledExecutorService sequencer = Executors.newSingleThreadScheduledExecutor(
                  new ThreadFactoryBuilder().setNameFormat("GameThread").build());

      public void start() {
            LOGGER.info("Starting game engine...");
            sequencer.scheduleAtFixedRate(new GameSequencer(), 600, GameConstants.CYLCE_RATE, TimeUnit.MILLISECONDS);
      }

}
