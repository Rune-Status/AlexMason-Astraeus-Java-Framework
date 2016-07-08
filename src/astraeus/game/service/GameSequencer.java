package astraeus.game.service;

import astraeus.game.model.World;
import astraeus.game.model.entity.mob.npc.Npc;
import astraeus.game.task.TaskManager;
import astraeus.util.LoggerUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The implementation that sequentially executes game-related processes.
 * 
 * @author Seven
 */
public final class GameSequencer implements Runnable {

      /**
       * The logger that will print important information.
       */
      private static final Logger LOGGER = LoggerUtils.getLogger(GameSequencer.class);

      @Override
      public void run() {
            try {
                  TaskManager.sequence();
                  World.WORLD.process();
                  Npc.process();
            } catch (final Throwable t) {
                  LOGGER.log(Level.SEVERE, "An error has occured during the main game sequence!", t);
            }
      }
}
