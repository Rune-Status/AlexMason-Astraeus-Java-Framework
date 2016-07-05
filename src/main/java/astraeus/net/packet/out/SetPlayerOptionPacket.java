package astraeus.net.packet.out;

import astraeus.game.model.PlayerOption;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;

/**
 * Shows a context menu while right clicking another player.
 *
 * @author Seven
 */
public final class SetPlayerOptionPacket extends OutgoingPacket {

	/**
	 * The option to display.
	 */
	private PlayerOption option;

	/**
	 * If this option should be shown on top.
	 */
	private boolean top;

	/**
	 * The flag to remove this option.
	 */
	private boolean disable;

	/**
	 * Creates a new {@link PlayerOption}.
	 *
	 * @param option
	 * 		The option to show.
	 */
	public SetPlayerOptionPacket(PlayerOption option) {
		this(option, false, false);
	}

	/**
	 * Creates a new {@link PlayerOption}.
	 *
	 * @param option
	 * 		The option to show.
	 *
	 * @param disable
	 * 		The flag to remove the context menu.
	 */
	public SetPlayerOptionPacket(PlayerOption option, boolean disable) {
		this(option, false, disable);
	}

	/**
	 * Creates a new {@link PlayerOption}.
	 *
	 * @param option
	 * 		The option to show.
	 *
	 * @param top
	 * 		The flag to display this as the first option.
	 *
	 * @param disable
	 * 		The flag to remove this option.
	 */
	public SetPlayerOptionPacket(PlayerOption option, boolean top, boolean disable) {
		super(104, PacketHeader.VARIABLE_BYTE);
		this.option = option;
		this.top = top;
		this.disable = disable;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.write(option.getSlot(), ByteModification.NEGATION)
				.write(top ? 1 : 0, ByteModification.ADDITION)
				.writeString(disable ? "null" : option.getName());
		return builder;
	}

}
