package astraeus.plugins.clicking

import astraeus.game.model.entity.mob.player.Brightness
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.entity.mob.player.Volume
import astraeus.game.model.entity.mob.player.attribute.Attribute
import astraeus.game.plugin.ClickEvent
import astraeus.net.packet.out.SetWidgetConfigPacket
import astraeus.net.packet.out.ServerMessagePacket
import astraeus.plugins.dialogue.Dialogue

/**
 * Handles the actions of clicking on buttons from the client.
 *
 * @author Seven
 */
open class ButtonClick(val player: Player, val button: Int) : ClickEvent {

    /**
     * Handles the button click for a player.
     */
    override fun handleAction() {

        if (!player.canClickButton()) {
            return;
        }

        if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER)
                && player.attr().get(Attribute.DEBUG)) {
            player.send(ServerMessagePacket("[click= button], [id= $button]"));
        }

        when(button) {
            2458 -> {
                if (player.canLogout()) {
                    player.onLogout()
                }
            }

            1050, 19158 ->  {
                if (player.movement.isRunning == true) player.movement.isRunning = false else player.movement.isRunning = true
                // run orb toggle
                player.send(SetWidgetConfigPacket(152, if (player.movement.isRunning) 1 else 0))
                // run button in the wrench tab
                player.send(SetWidgetConfigPacket(429, if (player.movement.isRunning) 1 else 0))
            }

            906 -> {
            player.attr().put(Attribute.BRIGHTNESS, Brightness.VERY_DARK)
            player.send(SetWidgetConfigPacket(166, player.attr().get<Brightness>(Attribute.BRIGHTNESS).code))
            }

            908 -> {
                player.attr().put(Attribute.BRIGHTNESS, Brightness.DARK)
                player.send(SetWidgetConfigPacket(166, player.attr().get<Brightness>(Attribute.BRIGHTNESS).code))
            }

            910 -> {
                player.attr().put(Attribute.BRIGHTNESS, Brightness.NORMAL)
                player.send(SetWidgetConfigPacket(166, player.attr().get<Brightness>(Attribute.BRIGHTNESS).code))
            }

            912 -> {
                player.attr().put(Attribute.BRIGHTNESS, Brightness.BRIGHT)
                player.send(SetWidgetConfigPacket(166, player.attr().get<Brightness>(Attribute.BRIGHTNESS).code))
            }

            930 -> {
                player.attr().put(Attribute.MUSIC_VOLUME, Volume.SILENT)
                player.send(SetWidgetConfigPacket(168, player.attr().get<Volume>(Attribute.MUSIC_VOLUME).code))
            }

            931 -> {
                player.attr().put(Attribute.MUSIC_VOLUME, Volume.QUIET)
                player.send(SetWidgetConfigPacket(168, player.attr().get<Volume>(Attribute.MUSIC_VOLUME).code))
            }

            932 -> {
                player.attr().put(Attribute.MUSIC_VOLUME, Volume.NORMAL)
                player.send(SetWidgetConfigPacket(168, player.attr().get<Volume>(Attribute.MUSIC_VOLUME).code))
            }

            933 -> {
                player.attr().put(Attribute.MUSIC_VOLUME, Volume.HIGH)
                player.send(SetWidgetConfigPacket(168, player.attr().get<Volume>(Attribute.MUSIC_VOLUME).code))
            }

            934 -> {
                player.attr().put(Attribute.MUSIC_VOLUME, Volume.LOUD)
                player.send(SetWidgetConfigPacket(168, player.attr().get<Volume>(Attribute.MUSIC_VOLUME).code))
            }

            941 -> {
                player.attr().put(Attribute.SOUND_EFFECT_VOLUME, Volume.SILENT)
                player.send(SetWidgetConfigPacket(169, player.attr().get<Volume>(Attribute.SOUND_EFFECT_VOLUME).code))
            }

            942 -> {
                player.attr().put(Attribute.SOUND_EFFECT_VOLUME, Volume.QUIET)
                player.send(SetWidgetConfigPacket(169, player.attr().get<Volume>(Attribute.SOUND_EFFECT_VOLUME).code))
            }

            943 -> {
                player.attr().put(Attribute.SOUND_EFFECT_VOLUME, Volume.NORMAL)
                player.send(SetWidgetConfigPacket(169, player.attr().get<Volume>(Attribute.SOUND_EFFECT_VOLUME).code))
            }

            944 -> {
                player.attr().put(Attribute.SOUND_EFFECT_VOLUME, Volume.HIGH)
                player.send(SetWidgetConfigPacket(169, player.attr().get<Volume>(Attribute.SOUND_EFFECT_VOLUME).code))
            }

            945 -> {
                player.attr().put(Attribute.SOUND_EFFECT_VOLUME, Volume.LOUD)
                player.send(SetWidgetConfigPacket(169, player.attr().get<Volume>(Attribute.SOUND_EFFECT_VOLUME).code))
            }

            19150 -> {
                player.attr().put(Attribute.AREA_SOUND_VOLUME, Volume.SILENT)
                player.send(SetWidgetConfigPacket(170, player.attr().get<Volume>(Attribute.AREA_SOUND_VOLUME).code))
            }

            19151 -> {
                player.attr().put(Attribute.AREA_SOUND_VOLUME, Volume.QUIET)
                player.send(SetWidgetConfigPacket(170, player.attr().get<Volume>(Attribute.AREA_SOUND_VOLUME).code))
            }

            19152 -> {
                player.attr().put(Attribute.AREA_SOUND_VOLUME, Volume.NORMAL)
                player.send(SetWidgetConfigPacket(170, player.attr().get<Volume>(Attribute.AREA_SOUND_VOLUME).code))
            }

            19153 -> {
                player.attr().put(Attribute.AREA_SOUND_VOLUME, Volume.HIGH)
                player.send(SetWidgetConfigPacket(170, player.attr().get<Volume>(Attribute.AREA_SOUND_VOLUME).code))
            }

            19154 -> {
                player.attr().put(Attribute.AREA_SOUND_VOLUME, Volume.LOUD)
                player.send(SetWidgetConfigPacket(170, player.attr().get<Volume>(Attribute.AREA_SOUND_VOLUME).code))
            }

            914 ->  {
                    player.attr().toggle(Attribute.MOUSE_BUTTON);
                    player.send(SetWidgetConfigPacket(171, player.attr().get<Boolean>(Attribute.MOUSE_BUTTON)))
            }

            915 -> {
                player.attr().toggle(Attribute.CHAT_EFFECT);
                player.send(SetWidgetConfigPacket(172, player.attr().get<Boolean>(Attribute.CHAT_EFFECT)))
            }

            957 -> {
                player.attr().toggle(Attribute.SPLIT_CHAT);
                player.send(SetWidgetConfigPacket(287, player.attr().get<Boolean>(Attribute.SPLIT_CHAT)))
            }

            12464 -> {
                player.attr().toggle(Attribute.ACCEPT_AID);
                player.send(SetWidgetConfigPacket(427, player.attr().get<Boolean>(Attribute.ACCEPT_AID)))
            }

            153 -> player.movement.isRunning = true
            152 -> player.movement.isRunning = false

            22845 -> if (player.attr().contains(Attribute.AUTO_RETALIATE, true)) player.attr().put(Attribute.AUTO_RETALIATE, false) else player.attr().put(Attribute.AUTO_RETALIATE, true);

        }

        if (Dialogue.isDialogueButton(button) && player.optionDialogue.isPresent) {
            when(button) {
                2461, 2471, 2482, 2494 -> player.dialogueFactory.executeOption(0, player.optionDialogue)
                2495, 2462, 2472, 2483 -> player.dialogueFactory.executeOption(1, player.optionDialogue)
                2496, 2473, 2484 -> player.dialogueFactory.executeOption(2, player.optionDialogue)
                2497, 2485 -> player.dialogueFactory.executeOption(3, player.optionDialogue)
                2498 -> player.dialogueFactory.executeOption(4, player.optionDialogue)
            }
        }


    }

}
