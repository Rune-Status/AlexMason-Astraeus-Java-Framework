package astraeus.net.packet.in;

import astraeus.game.event.impl.NpcFirstClickEvent;
import astraeus.game.event.impl.NpcSecondClickEvent;
import astraeus.game.event.impl.NpcThirdClickEvent;
import astraeus.game.model.World;
import astraeus.game.model.entity.mob.npc.Npc;
import astraeus.game.model.entity.mob.npc.NpcDefinition;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.out.ServerMessagePacket;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;

/**
 * The {@link IncomingPacket} responsible for the different options while clicking an npc.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({IncomingPacket.ATTACK_NPC, IncomingPacket.MAGIC_ON_NPC,
        IncomingPacket.NPC_ACTION_1, IncomingPacket.NPC_ACTION_2,
        IncomingPacket.NPC_ACTION_3})
public class NpcInteractionPacket implements Receivable {

      @Override
      public void handlePacket(final Player player, IncomingPacket packet) {
            GamePacketReader reader = packet.getReader();
            switch (packet.getOpcode()) {

                  case IncomingPacket.ATTACK_NPC:
                        handleAttackMob(player, packet, reader);
                        break;

                  case IncomingPacket.MAGIC_ON_NPC:
                        handleMagicOnMob(player, packet, reader);
                        break;

                  case IncomingPacket.NPC_ACTION_1:
                        handleFirstClickNpc(player, packet, reader);
                        break;

                  case IncomingPacket.NPC_ACTION_2:
                        handleSecondClickNpc(player, packet, reader);
                        break;

                  case IncomingPacket.NPC_ACTION_3:
                        handleThirdClickNpc(player, packet, reader);
                        break;
            }
      }

      /**
       * Handles the action of attacking a mob.
       * 
       * @param player The player attacking the mob.
       * 
       * @param packet The packet for this action.
       */
      private void handleAttackMob(Player player, IncomingPacket packet, GamePacketReader reader) {

            final int npcIndex = reader.readShort(false, ByteModification.ADDITION);

            if (npcIndex < 0 || npcIndex > NpcDefinition.MOB_LIMIT) {
                  return;
            }

            final Npc npc = World.WORLD.getMobs()[npcIndex];

            if (npc == null) {
                  return;
            }

            if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
                  player.send(new ServerMessagePacket(String.format("[attack= npc], [id= %d], [slot= %d]", npc.getId(), npc.getSlot())));
            }

            if (npc.getCurrentHealth() <= 0) {
                  player.send(new ServerMessagePacket("This npc is already dead..."));
                  return;
            }

      }

      /**
       * Handles the action of using magic on a mob
       * 
       * @param player The player using magic on this mob.
       * 
       * @param packet The packet for this action.
       */
      private void handleMagicOnMob(Player player, IncomingPacket packet, GamePacketReader reader) {
            final int slot = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
            final Npc mobMagic = World.WORLD.getMobs()[slot];
            @SuppressWarnings("unused")
            final int spell = reader.readShort(ByteModification.ADDITION);

            if (mobMagic == null) {
                  player.send(new ServerMessagePacket("You tried to attack a mob that doesn't exist."));
                  return;
            }

            NpcDefinition def = NpcDefinition.get(mobMagic.getId());

            if (def == null) {
                  return;
            }


      }

      /**
       * Handles the action of using the first interaction option on a mob.
       * 
       * @param player The player clicking on the mob.
       * 
       * @param packet The packet for this action.
       */
      private void handleFirstClickNpc(Player player, IncomingPacket packet, GamePacketReader reader) {
            final Npc npc = World.WORLD.getMobs()[reader.readShort(ByteOrder.LITTLE)];

            if (npc == null) {
                  return;
            }

            player.getMovementListener().append(() -> {
                  System.out.println("appending");
                  if (player.getLocation().isWithinDistance(npc.getLocation().copy(), 1)) {
                        player.setInteractingEntity(npc);
                        npc.setInteractingEntity(player);
                        
                        player.post(new NpcFirstClickEvent(npc));
                  }
            });

      }

      /**
       * Handles the action of using the second interaction option on a mob.
       * 
       * @param player The player clicking on the mob.
       * 
       * @param packet The packet for this action.
       */
      private void handleSecondClickNpc(Player player, IncomingPacket packet, GamePacketReader reader) {
            final Npc npc = World.WORLD.getMobs()[reader.readShort(ByteOrder.LITTLE,
                        ByteModification.ADDITION)];

            if (npc == null) {
                  return;
            }

            player.getMovementListener().append(() -> {
                  if (player.getLocation().isWithinDistance(npc.getLocation().copy(), 1)) {
                        player.setInteractingEntity(npc);
                        npc.setInteractingEntity(player);

                        player.post(new NpcSecondClickEvent(npc));
                  }
            });

      }

      /**
       * Handles the action of using the third interaction option on a mob.
       * 
       * @param player The player clicking on the mob.
       * 
       * @param packet The packet for this action.
       */
      private void handleThirdClickNpc(Player player, IncomingPacket packet, GamePacketReader reader) {
            final Npc npc = World.WORLD.getMobs()[reader.readShort()];

            if (npc == null) {
                  return;
            }

            player.getMovementListener().append(() -> {
                  if (player.getLocation().isWithinDistance(npc.getLocation().copy(), 1)) {
                        player.setInteractingEntity(npc);
                        npc.setInteractingEntity(player);

                        player.post(new NpcThirdClickEvent(npc));
                  }
            });

      }

}
