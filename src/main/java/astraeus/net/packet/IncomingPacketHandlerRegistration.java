package astraeus.net.packet;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.in.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Handles registering the handlers responsible for intervening
 * {@link IncomingPacket}s.
 * 
 * @author Seven
 */
public final class IncomingPacketHandlerRegistration {

	/**
	 * The single logger for this class.
	 */
	private static final Logger LOGGER = Logger.getLogger(IncomingPacketHandlerRegistration.class.getName());

	/**
	 * The map of {@IncomingPacket} opcodes mapped to their listener.
	 */
	private final static Map<Integer, Receivable> INCOMING_PACKETS = new HashMap<>();

	/**
	 * Intercepts an {@link IncomingPacket} for a {@code player} and dispatches
	 * the packet to the correct listener to be handled.
	 * 
	 * @param player
	 *            The player that is receiving the packet.
	 * 
	 * @param packet
	 *            The incoming packet to intervene.
	 */
	public static final void dispatchToHandler(Player player, IncomingPacket packet) {
		Optional<Receivable> listener = Optional.ofNullable(INCOMING_PACKETS.get(packet.getOpcode()));

		if (player.attr().get(Player.DEBUG_NETWORK_KEY)) {
			LOGGER.info(String.format(packet.toString()));
		}

		listener.ifPresent(msg -> msg.handlePacket(player, packet));
	}

	/**
	 * Registers a handler for the {@link IncomingPacket}s.
	 * 
	 * @param listener
	 *            The handler to register.
	 */
	private static final void registerHandler(Receivable listener) {
		final IncomingPacket.IncomingPacketOpcode annotation = listener.getClass()
				.getAnnotation(IncomingPacket.IncomingPacketOpcode.class);
		if (annotation != null) {
			for (final int opcode : annotation.value()) {
				INCOMING_PACKETS.put(opcode, listener);
			}
		}
	}

	/**
	 * Initializes all the {@link IncomingPacket} handlers when this object is
	 * created.
	 */
	public IncomingPacketHandlerRegistration() {
		registerHandler(new PlayerSecondOptionPacket());
		registerHandler(new MagicOnPlayerPacket());
		registerHandler(new WidgetContainerFirstOptionPacket());
		registerHandler(new WidgetContainerSecondOptionPacket());
		registerHandler(new WidgetContainerThirdOptionPacket());
		registerHandler(new WidgetContainerFourthOptionPacket());
		registerHandler(new WidgetContainerFifthOptionPacket());
		registerHandler(new WidgetContainerSixthOptionPacket());
		registerHandler(new TypeOnWidgetPacket());
		registerHandler(new MagicOnNpcPacket());
		registerHandler(new AttackNpcPacket());
		registerHandler(new PlayerFirstOptionPacket());
		registerHandler(new PlayerSecondOptionPacket());
		registerHandler(new PlayerThirdOptionPacket());
		registerHandler(new PlayerFourthOptionPacket());
		registerHandler(new PlayerFifthOptionPacket());
		registerHandler(new TradeAnswerPacket());
		registerHandler(new AppearanceChangePacket());
		registerHandler(new ButtonClickPacket());
		registerHandler(new ChatMessagePacket());
		registerHandler(new DefaultPacket());
		registerHandler(new ItemFirstOptionPacket());
		registerHandler(new ItemSecondOptionPacket());
		registerHandler(new ItemThirdOptionPacket());
		registerHandler(new ObjectFirstOptionPacket());
		registerHandler(new ObjectSecondOptionPacket());
		registerHandler(new ObjectThirdOptionPacket());
		registerHandler(new CloseInterfacePacket());
		registerHandler(new CommandPacket());
		registerHandler(new DialoguePacket());
		registerHandler(new DropItemPacket());
		registerHandler(new IdleLogoutPacket());
		registerHandler(new MagicOnFloorItemPacket());
		registerHandler(new MagicOnItemPacket());
		registerHandler(new MoveItemPacket());
		registerHandler(new PickupGroundItemPacket());
		registerHandler(new PlayerRelationPacket());
		registerHandler(new EnterRegionPacket());
		registerHandler(new LoadRegionPacket());
		registerHandler(new ReportPlayerPacket());
		registerHandler(new ItemOnItemPacket());
		registerHandler(new ItemOnObjectPacket());
		registerHandler(new ItemOnNpcPacket());
		registerHandler(new ItemOnGroundItemPacket());
		registerHandler(new ItemOnPlayerPacket());
		registerHandler(new MovementPacket());
		registerHandler(new EquipItemPacket());
		registerHandler(new NpcFirstClickPacket());
		registerHandler(new NpcSecondClickPacket());
		registerHandler(new NpcThirdClickPacket());
	}
}
