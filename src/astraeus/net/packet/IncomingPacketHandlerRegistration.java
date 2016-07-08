package astraeus.net.packet;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.packet.in.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Handles registering the handlers responsible for intervening {@link IncomingPacket}s.
 * 
 * @author Seven
 */
public final class IncomingPacketHandlerRegistration {

      /**
       * The single logger for this class.
       */
      private static final Logger LOGGER =
                  Logger.getLogger(IncomingPacketHandlerRegistration.class.getName());

      /**
       * The map of {@IncomingPacket} opcodes mapped to their listener.
       */
      private final static Map<Integer, Receivable> INCOMING_PACKETS = new HashMap<>();

      /**
       * Intercepts an {@link IncomingPacket} for a {@code player} and dispatches the packet to the
       * correct listener to be handled.
       * 
       * @param player The player that is receiving the packet.
       * 
       * @param packet The incoming packet to intervene.
       */
      public static final void dispatchToHandler(Player player, IncomingPacket packet) {
            Optional<Receivable> listener =
                        Optional.ofNullable(INCOMING_PACKETS.get(packet.getOpcode()));

            if ((boolean) player.attr().get(Attribute.DEBUG_NETWORK)) {
                  LOGGER.info(String.format(packet.toString()));
            }

            listener.ifPresent(msg -> msg.handlePacket(player, packet));
      }

      /**
       * Registers a handler for the {@link IncomingPacket}s.
       * 
       * @param listener The handler to register.
       */
      private static final void registerHandler(Receivable listener) {
            final IncomingPacket.IncomingPacketOpcode annotation =
                        listener.getClass().getAnnotation(IncomingPacket.IncomingPacketOpcode.class);
            if (annotation != null) {
                  for (final int opcode : annotation.value()) {
                        INCOMING_PACKETS.put(opcode, listener);
                  }
            }
      }

      /**
       * Initializes all the {@link IncomingPacket} handlers when this object is created.
       */
      public IncomingPacketHandlerRegistration() {
            registerHandler(new PlayerOptionPacket());
            registerHandler(new AppearanceChangePacket());
            registerHandler(new ButtonClickPacket());
            registerHandler(new ChatMessagePacket());
            registerHandler(new DefaultPacket());
            registerHandler(new ItemInteractionPacket());
            registerHandler(new NpcInteractionPacket());
            registerHandler(new ObjectInteractionPacket());
            registerHandler(new CloseInterfacePacket());
            registerHandler(new CommandPacket());
            registerHandler(new DialoguePacket());
            registerHandler(new DropItemPacket());
            registerHandler(new IdleLogoutPacket());
            registerHandler(new InterfaceActionPacket());
            registerHandler(new ItemContainerActionPacket());
            registerHandler(new MagicOnFloorItemPacket());
            registerHandler(new MagicOnItemPacket());
            registerHandler(new MoveItemPacket());
            registerHandler(new PickupItemPacket());
            registerHandler(new PlayerRelationPacket());
            registerHandler(new RegionChangePacket());
            registerHandler(new ReportPacket());
            registerHandler(new SilentPacket());
            registerHandler(new UseItemPacket());
            registerHandler(new MovementPacket());
            registerHandler(new WieldItemPacket());
      }
}
