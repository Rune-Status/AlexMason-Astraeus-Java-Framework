package astraeus.game.model.entity.mob.player.attribute;

import astraeus.game.model.entity.mob.player.Brightness;
import astraeus.game.model.entity.mob.player.Volume;

public enum Attribute {
      ACTIVE(false),
      SHOPPING(false),
      TRADING(false),
      BANKING(false),
      CHANGING_APPEARANCE(false),
      FOLLOWING(false),
      SOUND(true),
      MUSIC(true),
      CHAT_EFFECT(true),
      SPLIT_CHAT(true),
      ACCEPT_AID(true),
      MOUSE_BUTTON(true),
      PROFANITY(true),
      DEBUG(false),
      AUTO_RETALIATE(true),
      NEW_PLAYER(false),
      DEBUG_NETWORK(false),
      SAVE(true),
      DISCONNECTED(false),
      LOGOUT(false),
      BRIGHTNESS(Brightness.NORMAL),
      MUSIC_VOLUME(Volume.NORMAL),
      SOUND_EFFECT_VOLUME(Volume.NORMAL),
      AREA_SOUND_VOLUME(Volume.NORMAL);

      private final Object defaultValue;

      private Attribute(Object defaultValue) {
            this.defaultValue = defaultValue;
      }

      public Object getDefaultValue() {
            return defaultValue;
      }

}
