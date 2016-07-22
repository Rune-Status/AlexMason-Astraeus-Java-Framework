package astraeus;

import astraeus.game.model.World;
import astraeus.game.service.GameService;
import astraeus.game.service.GameServiceSequencer;
import astraeus.io.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import astraeus.net.channel.ChannelPiplineInitializer;
import astraeus.net.packet.IncomingPacketHandlerRegistration;

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
	public static final Logger logger = Logger.getLogger(Bootstrap.class.getName());

	/**
	 * The {@link ExecutorService} that will run the startup services.
	 */
	private final ExecutorService serviceLoader = Executors
			.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("ServiceLoaderThread").build());

	/**
	 * The engine that manages the games logic.
	 */
	private final GameService service = new GameServiceSequencer();

	/**
	 * Builds the game by executing any startup services, and starting the game
	 * loop.
	 * 
	 * @return The instance of this bootstrap.
	 */
	public Bootstrap build() throws Exception {
		logger.info("Preparing game resources...");
		// load and cache data
		executeServiceLoad();

		serviceLoader.shutdown();

		if (!serviceLoader.awaitTermination(15, TimeUnit.MINUTES)) {
			throw new IllegalStateException("The background service load took too long!");
		}

		logger.info("Preparing game engine...");
		// run the game loop
		service.start();
		logger.info("Game Engine has been built");
		return this;
	}

	/**
	 * Builds the network by creating the netty server bootstrap and binding to
	 * a specified port.
	 * 
	 * @return The instance of this bootstrap.
	 */
	public Bootstrap bind() throws InterruptedException {
		logger.info("Building network");
		ResourceLeakDetector.setLevel(Level.DISABLED);
		EventLoopGroup loopGroup = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap();

		bootstrap.group(loopGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelPiplineInitializer())
				.bind(43594).syncUninterruptibly();
		Server.SERVER_STARTED = true;
		logger.info("Network has been bound");
		return this;
	}

	/**
	 * Executes external files to be used in game.
	 */
	private void executeServiceLoad() {

		logger.info("Initializing packets...");
		serviceLoader.execute(() -> {
			new PacketSizeParser().run();
			new IncomingPacketHandlerRegistration();
		});

		logger.info("Loading startup files..");
		serviceLoader.execute(() -> {
			new GlobalObjectParser().run();
			new ItemDefinitionParser().run();
			new NpcDefinitionParser().run();
			new NpcSpawnParser().run();
			new IPBanParser().run();
			new UUIDBanParser().run();
			new EquipmentDefinitionParser().run();
		});

		logger.info("Loading plugins");
		serviceLoader.execute(() -> {
			World.WORLD.getPluginService().load();
		});
	}

}
