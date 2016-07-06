package astraeus.game.model;

import astraeus.game.GameConstants;
import astraeus.game.model.entity.mob.Mob;
import astraeus.game.model.entity.mob.MobList;
import astraeus.game.model.entity.mob.npc.Npc;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.task.Task;
import astraeus.game.task.TaskManager;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * Represents the game world.
 * 
 * @author SeVen
 */
public final class World {

      /**
       * The single logger for this class.
       */
      public static final Logger logger = Logger.getLogger(World.class.getName());

      /**
       * The collection of players in the game world.
       */
      private static final MobList<Player> PLAYERS = new MobList<Player>(GameConstants.MAX_PLAYERS);

      /**
       * The mobs registered in this world.
       */
      private static Npc mobs[] = new Npc[GameConstants.MAXIMUM_MOB_SPAWNS];

      /**
       * The {@link Set} of banned hosts.
       */
      private static final Set<String> IP_BANS = new HashSet<>();

      /**
       * The {@link Set} of banned mac addresses.
       */
      private static final Set<String> BANNED_UUIDS = new HashSet<>();
      
      /**
       * The {@link Player}s waiting to login.
       */
      private final static Queue<Player> LOGINS = new ConcurrentLinkedQueue<>();

      /**
       * The {@link Player}s waiting to logout.
       */
      private final static Queue<Player> LOGOUTS = new ConcurrentLinkedQueue<>();

      /**
       * An intrinsic lock used to keep the server in sync.
       */
      public static final Object LOCK = new Object();

      /**
       * Registers and adds a {@code entity) into the game world.
       * 
       * @param entity
       *    The entity to add.
       */
      public static void register(Mob entity) {
            // check to make this entity is not registered already, and is present.
            if (entity == null || entity.isRegistered()) {
                  return;
            }

            if (entity.isPlayer()) {
                  Player player = (Player) entity;
                  player.setId(-1);
                  PLAYERS.add(player);
            }
      }

      /**
       * Deregisters a {@link Mob} from the game world.
       * 
       * @param entity The entity to remove.
       */
      public static void deregister(Mob entity) {
            
            if (entity == null || !entity.isRegistered()) {
                  return;
            }

            if (entity.isPlayer()) {
                  PLAYERS.remove(entity);
            } else {
                  World.getMobs()[entity.getSlot()].setRegistered(false);
                  World.getMobs()[entity.getSlot()] = null;
            }
      }

      public static void process() {
            synchronized (LOCK) {
                  
                  //handle players logging in
                  for(int index = 0; index < GameConstants.LOGIN_LIMIT; index++) {
                        Player player = LOGINS.poll();
                        
                        if (player == null) {
                              break;
                        }
                        
                        player.onRegister();
                  }
                  
                  int amount = 0;
                  
                  // handle players logging out
                  Iterator<Player> $it = LOGOUTS.iterator();
                  while($it.hasNext()) {
                        Player player = $it.next();
                        if (player == null || amount >= GameConstants.LOGOUT_LIMIT) {
                              break;
                        }                        
                        player.onDeregister();
                        $it.remove();
                        amount++;
                  }

                  // process incoming packets, handle movement, and any player process.
                  for (final Player player : World.getPlayers()) {
                        if (player == null || !player.isRegistered()
                                    || player.getSession() == null) {
                              continue;
                        }
                        try {
                              player.getSession().handleQueuedPackets();
                              player.prepare();
                              player.tick();
                        } catch (final Exception e) {
                              e.printStackTrace();
                        }

                  }

                  // mob movement, mob other various mob processes
                  for (final Npc mob : World.getMobs()) {

                        if (mob == null || !mob.isRegistered()) {
                              continue;
                        }

                        mob.prepare();
                  }

                  // player and mob updating in parallel
                  for (final Player player : World.getPlayers()) {
                        if (player == null || !player.isRegistered()) {
                              continue;
                        }
                        try {
                              player.update();
                        } catch (final Exception e) {
                              e.printStackTrace();
                        }

                  }

                  // reset player flags
                  for (final Player player : World.getPlayers()) {
                        if (player == null || !player.isRegistered()) {
                              continue;
                        }
                        try {
                              player.clearUpdateFlags();
                        } catch (final Exception e) {
                              e.printStackTrace();
                        }
                  }

                  // reset mob flags
                  for (final Npc mob : World.getMobs()) {
                        if (mob == null || !mob.isRegistered()) {
                              continue;
                        }

                        try {
                              mob.clearUpdateFlags();
                        } catch (final Exception ex) {
                              ex.printStackTrace();
                        }
                  }

            }
      }
      
      /**
       * Adds a {@code player} to the login queue.
       * 
       * @param player
       *    The player that is logging in.
       */
      public static void queueLogin(Player player) {
            if (player.getSession() != null && !LOGINS.contains(player)) {
                  LOGINS.add(player);
            }            
      }
      
      /**
       * Adds a {@link Player} to the logout queue.
       * 
       * @param player
       *    The player logging out.
       */
      public static void queueLogout(Player player) {
            if (player != null && !LOGOUTS.contains(player)) {
                  LOGOUTS.add(player);
            }
      }

      /**
       * Gets a player by their username
       *
       * @param name The name of the player.
       */
      public static Optional<Player> getPlayerByName(String name) {
            for (final Player player : World.getPlayers()) {
                  if (player != null) {
                        if (player.getUsername().equalsIgnoreCase(name)) {
                              return Optional.of(player);
                        }
                  }
            }
            return Optional.empty();
      }

      /**
       * Submits a new {@link Task}.
       * 
       * @param task
       *    The task to execute.
       */
      public static void submit(Task task) {
            TaskManager.submit(task);
      }

      /**
       * Gets the collection the players in this world.
       * 
       * @return The collection of players.
       */
      public static MobList<Player> getPlayers() {
            return PLAYERS;
      }

      /**
       * Gets the collection of mobs in this world.
       * 
       * @return The collection of mobs.
       */
      public static Npc[] getMobs() {
            return mobs;
      }

      /**
       * Gets the players logging in.
       * 
       * @return The queue containing players logging in.
       */
      public static Queue<Player> getLogins() {
            return LOGINS;
      }

      /**
       * Gets the players logging out.
       * 
       * @return The queue containing players logging out.
       */
      public static Queue<Player> getLogouts() {
            return LOGOUTS;
      }

      /**
       * Gets the set of banned hosts.
       *
       * @return The set of banned hosts.
       */
      public static Set<String> getIpBans() {
            return IP_BANS;
      }

      /**
       * Gets the set of banned mac addresses.
       *
       * @return The set of banned mac addresses.
       */
      public static Set<String> getBannedUUIDs() {
            return BANNED_UUIDS;
      }

}
