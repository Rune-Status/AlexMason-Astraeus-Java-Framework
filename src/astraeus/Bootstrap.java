package astraeus;

import astraeus.game.io.*;
import astraeus.game.model.World;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import astraeus.game.service.GameEngine;
import astraeus.net.ChannelPiplineInitializer;
import astraeus.net.packet.in.IncomingPacketHandlerRegistration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * The bootstrap that will prepare the game and network.
 * 
 * @author Seven
 */
public final class Bootstrap {

      /**
       * The single logger for this class.
       */
      public static final Logger LOGGER = Logger.getLogger(Bootstrap.class.getName());

      /**
       * The {@link ExecutorService} that will run the startup services.
       */
      private final ExecutorService serviceLoader = Executors.newSingleThreadExecutor(
                  new ThreadFactoryBuilder().setNameFormat("ServiceLoaderThread").build());

      /**
       * The engine that manages the games logic.
       */
      private final GameEngine engine = new GameEngine();

      /**
       * Builds the game by executing any startup services, and starting the game loop.
       * 
       * @return The instance of this bootstrap.
       */
      public Bootstrap build() throws Exception {
            LOGGER.info("Preparing game engine...");
            // load and cache data
            executeServiceLoad();

            // run the game loop
            engine.start();

            serviceLoader.shutdown();
            LOGGER.info("Game Engine has been built");
            if (!serviceLoader.awaitTermination(15, TimeUnit.MINUTES)) {
                  throw new IllegalStateException("The background service load took too long!");
            }
            return this;
      }

      /**
       * Builds the network by creating the netty server bootstrap and binding to a specified port.
       * 
       * @return The instance of this bootstrap.
       */
      public Bootstrap bind() throws InterruptedException {
            LOGGER.info("Building network");
            ResourceLeakDetector.setLevel(Level.DISABLED);
            EventLoopGroup loopGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(loopGroup).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelPiplineInitializer()).bind(43594)
            .syncUninterruptibly();
            Server.SERVER_STARTED = true;
            LOGGER.info("Network has been bound");
            return this;
      }

      /**
       * Executes external files to be used in game.
       */
      private void executeServiceLoad() {

            LOGGER.info("Initializing packets...");
            serviceLoader.execute(() -> {
                  new PacketSizeParser().run();
                  new IncomingPacketHandlerRegistration();
            });

            LOGGER.info("Loading startup files..");
            serviceLoader.execute(() -> {
                  new GlobalObjectParser().run();
                  new ItemDefinitionParser().run();
                  new NpcDefinitionParser().run();
                  new SongParser().run();
                  new NpcSpawnParser().run();
                  new IPBanParser().run();
                  new UUIDBanParser().run();
            });
            
            LOGGER.info("Loading plugins");
            serviceLoader.execute(() -> {
            	new PluginMetaDataParser().run();
            	World.WORLD.getPluginService().load();
            });

            serviceLoader.execute(() -> new DoorParser().run());
      }

}
