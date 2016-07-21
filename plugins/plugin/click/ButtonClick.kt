package plugin.click

import astraeus.game.event.EventContext
import astraeus.game.event.EventSubscriber
import astraeus.game.event.SubscribesTo
import astraeus.game.event.impl.ButtonActionEvent
import astraeus.game.model.entity.mob.player.Player
import astraeus.game.model.entity.mob.player.PlayerRights
import astraeus.game.model.Brightness
import astraeus.game.model.sound.Volume

import astraeus.net.packet.out.SetWidgetConfigPacket
import astraeus.net.packet.out.ServerMessagePacket

@SubscribesTo(ButtonActionEvent::class)
class ButtonClick : EventSubscriber<ButtonActionEvent> {

	override fun subscribe(context: EventContext, player: Player, event: ButtonActionEvent) {
		if (!player.canClickButton()) {
			return;
		}

		if (player.rights.greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Player.DEBUG_KEY)) {
			player.send(ServerMessagePacket("[button= ${event.button}]"))
		}

		when (event.button) {

			906 -> {
				player.attr().put(Player.BRIGHTNESS_KEY, Brightness.VERY_DARK)
				player.send(SetWidgetConfigPacket(166, player.attr().get(Player.BRIGHTNESS_KEY).code))
			}

			908 -> {
				player.attr().put(Player.BRIGHTNESS_KEY, Brightness.DARK)
				player.send(SetWidgetConfigPacket(166, player.attr().get(Player.BRIGHTNESS_KEY).code))
			}

			910 -> {
				player.attr().put(Player.BRIGHTNESS_KEY, Brightness.NORMAL)
				player.send(SetWidgetConfigPacket(166, player.attr().get(Player.BRIGHTNESS_KEY).code))
			}

			912 -> {
				player.attr().put(Player.BRIGHTNESS_KEY, Brightness.BRIGHT)
				player.send(SetWidgetConfigPacket(166, player.attr().get(Player.BRIGHTNESS_KEY).code))
			}

			930 -> {
				player.attr().put(Player.MUSIC_VOLUME_KEY, Volume.SILENT)
				player.send(SetWidgetConfigPacket(168, player.attr().get(Player.MUSIC_VOLUME_KEY).code))
			}

			931 -> {
				player.attr().put(Player.MUSIC_VOLUME_KEY, Volume.QUIET)
				player.send(SetWidgetConfigPacket(168, player.attr().get(Player.MUSIC_VOLUME_KEY).code))
			}

			932 -> {
				player.attr().put(Player.MUSIC_VOLUME_KEY, Volume.NORMAL)
				player.send(SetWidgetConfigPacket(168, player.attr().get(Player.MUSIC_VOLUME_KEY).code))
			}

			933 -> {
				player.attr().put(Player.MUSIC_VOLUME_KEY, Volume.HIGH)
				player.send(SetWidgetConfigPacket(168, player.attr().get(Player.MUSIC_VOLUME_KEY).code))
			}

			934 -> {
				player.attr().put(Player.MUSIC_VOLUME_KEY, Volume.LOUD)
				player.send(SetWidgetConfigPacket(168, player.attr().get(Player.MUSIC_VOLUME_KEY).code))
			}

			941 -> {
				player.attr().put(Player.SOUND_EFFECT_VOLUME_KEY, Volume.SILENT)
				player.send(SetWidgetConfigPacket(169, player.attr().get(Player.SOUND_EFFECT_VOLUME_KEY).code))
			}

			942 -> {
				player.attr().put(Player.SOUND_EFFECT_VOLUME_KEY, Volume.QUIET)
				player.send(SetWidgetConfigPacket(169, player.attr().get(Player.SOUND_EFFECT_VOLUME_KEY).code))
			}

			943 -> {
				player.attr().put(Player.SOUND_EFFECT_VOLUME_KEY, Volume.NORMAL)
				player.send(SetWidgetConfigPacket(169, player.attr().get(Player.SOUND_EFFECT_VOLUME_KEY).code))
			}

			944 -> {
				player.attr().put(Player.SOUND_EFFECT_VOLUME_KEY, Volume.HIGH)
				player.send(SetWidgetConfigPacket(169, player.attr().get(Player.SOUND_EFFECT_VOLUME_KEY).code))
			}

			945 -> {
				player.attr().put(Player.SOUND_EFFECT_VOLUME_KEY, Volume.LOUD)
				player.send(SetWidgetConfigPacket(169, player.attr().get(Player.SOUND_EFFECT_VOLUME_KEY).code))
			}

			19150 -> {
				player.attr().put(Player.AREA_SOUND_VOLUME_KEY, Volume.SILENT)
				player.send(SetWidgetConfigPacket(170, player.attr().get(Player.AREA_SOUND_VOLUME_KEY).code))
			}

			19151 -> {
				player.attr().put(Player.AREA_SOUND_VOLUME_KEY, Volume.QUIET)
				player.send(SetWidgetConfigPacket(170, player.attr().get(Player.AREA_SOUND_VOLUME_KEY).code))
			}

			19152 -> {
				player.attr().put(Player.AREA_SOUND_VOLUME_KEY, Volume.NORMAL)
				player.send(SetWidgetConfigPacket(170, player.attr().get(Player.AREA_SOUND_VOLUME_KEY).code))
			}

			19153 -> {
				player.attr().put(Player.AREA_SOUND_VOLUME_KEY, Volume.HIGH)
				player.send(SetWidgetConfigPacket(170, player.attr().get(Player.AREA_SOUND_VOLUME_KEY).code))
			}

			19154 -> {
				player.attr().put(Player.AREA_SOUND_VOLUME_KEY, Volume.LOUD)
				player.send(SetWidgetConfigPacket(170, player.attr().get(Player.AREA_SOUND_VOLUME_KEY).code))
			}

			914 -> {
				player.attr().toggle(Player.MOUSE_BUTTON_KEY);
				player.send(SetWidgetConfigPacket(171, player.attr().get(Player.MOUSE_BUTTON_KEY)))
			}

			915 -> {
				player.attr().toggle(Player.CHAT_EFFECTS_KEY);
				player.send(SetWidgetConfigPacket(172, player.attr().get<Boolean>(Player.CHAT_EFFECTS_KEY)))
			}

			957 -> {
				player.attr().toggle(Player.SPLIT_CHAT_KEY);
				player.send(SetWidgetConfigPacket(287, player.attr().get<Boolean>(Player.SPLIT_CHAT_KEY)))
			}

			12464 -> {
				player.attr().toggle(Player.ACCEPT_AID_KEY);
				player.send(SetWidgetConfigPacket(427, player.attr().get<Boolean>(Player.ACCEPT_AID_KEY)))
			}

			2458 -> {
				if (player.canLogout()) {
					player.onLogout()
				}
			}

			1050, 19158 -> {
				if (player.movement.isRunning == true) player.movement.isRunning = false else player.movement.isRunning = true
				// run orb toggle
				player.send(SetWidgetConfigPacket(152, if (player.movement.isRunning) 1 else 0))
				// run button in the wrench tab
				player.send(SetWidgetConfigPacket(429, if (player.movement.isRunning) 1 else 0))
			}

		}


	}

}