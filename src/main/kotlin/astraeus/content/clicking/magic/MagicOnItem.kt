package main.astraeus.content.clicking.magic

import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.ServerMessagePacket


/**
 * Handles the action of using spells with items.
 *
 * @author Seven
 */
open class MagicOnItem(val player: Player, val itemId: Int, val slot: Int, val childId: Int, val spellId: Int) : ClickEvent {

    /**
     * Handles the action of using spells with {@link Item}s for a player.
     */
    override fun handleAction() {
        if (player.isDead) {
            return
        }

        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER)) {
            player.send(ServerMessagePacket("[MagicOnItem] - ItemId: $itemId Slot: $slot ChildId: $childId SpellId: $spellId"));
        }

        when(spellId) {

        }

    }

}
