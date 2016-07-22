package astraeus.game.model.entity.mob.player.update.mask;

import astraeus.game.model.entity.mob.player.Appearance;
import astraeus.game.model.entity.mob.player.Equipment;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.update.PlayerUpdateBlock;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.util.StringUtils;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players
 * appearance.
 * 
 * @author Seven
 */
public class PlayerAppearanceUpdateBlock extends PlayerUpdateBlock {

	/**
	 * Creates a new {@link PlayerAppearanceUpdateBlock}.
	 */
	public PlayerAppearanceUpdateBlock() {
		super(0x10, UpdateFlag.APPEARANCE);
	}

	@Override
	public void encode(Player player, GamePacketBuilder builder) {

		/**
		 * The container for the players properties.
		 */
		final GamePacketBuilder properties = new GamePacketBuilder();

		//gender
		properties.write(player.getAppearance().getGender().getCode());

		// headicon
		properties.write(-1);

		// these two custom remove this if you want to use a #317 client
		properties.write(-1);
		properties.write(-1);

		// pnpc
		if (player.getId() == -1) {

		   // helm or hat
			if (player.getEquipment().getItem(Equipment.HEAD) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.HEAD).getId());
			} else {
				properties.write(0);
			}
				
				// cape
			if (player.getEquipment().getItem(Equipment.CAPE) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.CAPE).getId());
			} else {
				properties.write(0);
			}

				// amulet
			if (player.getEquipment().getItem(Equipment.AMULET) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.AMULET).getId());
			} else {
				properties.write(0);
			}

				//weapon

			if (player.getEquipment().getItem(Equipment.WEAPON) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.WEAPON).getId());
			} else {
				properties.write(0);
			}

				// torso
			if (player.getEquipment().getItem(Equipment.CHEST) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.CHEST).getId());
			} else {
				properties.writeShort(0x100 + player.getAppearance().getTorso());
			}

				// shield
			if (player.getEquipment().getItem(Equipment.SHIELD) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.SHIELD).getId());
			} else {
				properties.write(0);
			}
				
				// full body
			if (player.getEquipment().getItem(Equipment.CHEST) != null) {
				if (!Equipment.isFullBody(player.getEquipment().getItem(Equipment.CHEST).getId())) {
					properties.writeShort(0x100 + player.getAppearance().getArms());
				} else {
					properties.write(0);
				}
			} else {
				properties.writeShort(0x100 + player.getAppearance().getArms());
			}
				
				// legs
			if (player.getEquipment().getItem(Equipment.LEGS) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.LEGS).getId());
			} else {
				properties.writeShort(0x100 + player.getAppearance().getLegs());
			}
				
				// full helm
				if (player.getEquipment().getItem(Equipment.HEAD) != null) {
					if (!Equipment.isFullHat(player.getEquipment().getItem(Equipment.HEAD).getId())) {
						properties.writeShort(0x100 + player.getAppearance().getHead());
					} else {
						properties.write(0);
					}
				} else {
					properties.writeShort(0x100 + player.getAppearance().getHead());
				}
				
				// hands
			if (player.getEquipment().getItem(Equipment.HANDS) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.HANDS).getId());
			} else {
				properties.writeShort(0x100 + player.getAppearance().getHands());
			}
				
				// feet
			if (player.getEquipment().getItem(Equipment.FEET) != null) {
				properties.writeShort(0x200 + player.getEquipment().getItem(Equipment.FEET).getId());
			} else {
				properties.writeShort(0x100 + player.getAppearance().getFeet());
			}

				// beard
			if (player.getEquipment().getItem(Equipment.HEAD) != null) {
				if (Equipment.isFullHat(player.getEquipment().getItem(Equipment.HEAD).getId())) {
					properties.writeShort(0x100 + player.getAppearance().getJaw());
				} else {
					properties.write(0);
				}
			} else {
				if (player.getAppearance().getGender() == Appearance.Gender.MALE) {
					properties.writeShort(0x100 + player.getAppearance().getJaw());
				} else {
					properties.write(0);
				}
			}
		} else {
			properties.writeShort(-1);
			properties.writeShort(player.getId());
		}
		properties.write(player.getAppearance().getHairColor());
		properties.write(player.getAppearance().getTorsoColor());
		properties.write(player.getAppearance().getLegsColor());
		properties.write(player.getAppearance().getFeetColor());
		properties.write(player.getAppearance().getSkinColor());
		properties.writeShort(player.getMobAnimation().getStand());
		properties.writeShort(player.getMobAnimation().getTurn());
		properties.writeShort(player.getMobAnimation().getWalk());
		properties.writeShort(player.getMobAnimation().getTurn180());
		properties.writeShort(player.getMobAnimation().getTurn90CW());
		properties.writeShort(player.getMobAnimation().getTurn90CCW());
		properties.writeShort(player.getMobAnimation().getRun());
		properties.writeLong(StringUtils.stringToLong(player.getUsername()));
		properties.writeString(Integer.toHexString(0));
		properties.writeString("");
		properties.write(player.getCombatLevel());
		properties.writeShort(0);
		builder.write(properties.buffer().writerIndex(), ByteModification.NEGATION);
		builder.writeBytes(properties.buffer());
	}

}
