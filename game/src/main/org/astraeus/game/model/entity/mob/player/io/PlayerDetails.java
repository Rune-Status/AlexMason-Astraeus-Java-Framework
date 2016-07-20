package astraeus.game.model.entity.mob.player.io;

import astraeus.game.model.Brightness;
import astraeus.game.model.Position;
import astraeus.game.model.entity.mob.player.Appearance;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.game.model.sound.Volume;

import java.io.*;
import java.util.List;

/**
 * The class used to create an object and convert to json object.
 *
 * @author Seven
 */
public final class PlayerDetails {

    /**
     * Determines if a {@code player} can be deserialized.
     *
     * @param player The player to check.
     *
     * @return {@code true} If a player can be deserialized, {@code false} otherwise.
     */
    public static boolean deserialize(Player player) throws Exception {
        BufferedReader reader = null;
        try {
            final File file = new File(
                    "./Data/characters/details/" + player.getUsername() + ".json");

            if (!file.exists()) {
                return false;
            }

            reader = new BufferedReader(new FileReader(file));

            final PlayerDetails details =
                    PlayerSerializer.GSON.fromJson(reader, PlayerDetails.class);
            player.setUsername(details.username);
            player.setPassword(details.password);
            player.setRights(details.rights);
            player.setPosition((boolean) player.attr().get(Attribute.NEW_PLAYER) ? Player.DEFAULT_LOCATION
                    : details.location);
            player.attr().put(Attribute.NEW_PLAYER, details.newPlayer);
            player.attr().put(Attribute.BRIGHTNESS, details.brightness);
            player.attr().put(Attribute.MUSIC_VOLUME, details.musicVolume);
            player.attr().put(Attribute.SOUND_EFFECT_VOLUME, details.soundEffectVolume);
            player.attr().put(Attribute.AREA_SOUND_VOLUME, details.areaSoundVolume);
            player.getMovement().setRunning(details.running);
            player.attr().put(Attribute.AUTO_RETALIATE, details.autoRetaliate);
            player.attr().put(Attribute.SOUND, details.enableSound);
            player.attr().put(Attribute.MUSIC, details.enableMusic);
            player.attr().put(Attribute.DEBUG, details.debugMode);
            player.attr().put(Attribute.MOUSE_BUTTON, details.mouseButtons);
            player.attr().put(Attribute.CHAT_EFFECT, details.chatEffects);
            player.attr().put(Attribute.SPLIT_CHAT, details.splitChat);
            player.attr().put(Attribute.ACCEPT_AID, details.acceptAid);

            if (details.appearance == null) {
                player.getAppearance().getDefaultAppearance();
            } else {
                player.setAppearance(details.appearance);
            }

            if (details.friendList.size() > 0) {
                player.getRelation().setFriendList(details.friendList);
            }
            if (details.ignoreList.size() > 0) {
                player.getRelation().setIgnoreList(details.ignoreList);
            }
            return true;

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final String username;
    private final String password;
    @SuppressWarnings("unused")
	private final String hostAddress;
    private final PlayerRights rights;
    private final Position location;
    private final Brightness brightness;
    private final Volume musicVolume;
    private final Volume soundEffectVolume;
    private final Volume areaSoundVolume;
    private final boolean newPlayer;
    private final boolean running;
    private final boolean autoRetaliate;
    private final boolean enableSound;
    private final boolean enableMusic;
    private final boolean debugMode;
    private final boolean mouseButtons;
    private final boolean chatEffects;
    private final boolean splitChat;
    private final boolean acceptAid;
    private final Appearance appearance;
    private final List<Long> friendList;
    private final List<Long> ignoreList;

    /**
     * Creates a new {@link PlayerDetails}.
     *
     * @param player The player to serialize.
     *
     *        Note any of the attributes placed in the constructor will be serialized.
     */
    public PlayerDetails(Player player) {
        username = player.getUsername();
        password = player.getPassword();
        hostAddress = player.getHostAddress();
        rights = player.getRights();
        location = player.getPosition();
        brightness = player.attr().get(Attribute.BRIGHTNESS);
        musicVolume = player.attr().get(Attribute.MUSIC_VOLUME);
        soundEffectVolume = player.attr().get(Attribute.SOUND_EFFECT_VOLUME);
        areaSoundVolume = player.attr().get(Attribute.AREA_SOUND_VOLUME);
        newPlayer = player.attr().get(Attribute.NEW_PLAYER);
        running = player.getMovement().isRunning();
        autoRetaliate = player.attr().get(Attribute.AUTO_RETALIATE);
        enableSound = player.attr().get(Attribute.SOUND);
        enableMusic = player.attr().get(Attribute.MUSIC);
        debugMode = player.attr().get(Attribute.DEBUG);
        mouseButtons = player.attr().get(Attribute.MOUSE_BUTTON);
        chatEffects = player.attr().get(Attribute.CHAT_EFFECT);
        splitChat = player.attr().get(Attribute.SPLIT_CHAT);
        acceptAid = player.attr().get(Attribute.ACCEPT_AID);
        appearance = player.getAppearance();
        friendList = player.getRelation().getFriendList();
        ignoreList = player.getRelation().getIgnoreList();
    }

    public String getUsername() {
        return username;
    }

    public PlayerRights getRights() {
        return rights;
    }

    public Position getLocation() {
        return location;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    /**
     * Serializes the converted object into json.
     */
    public void serialize() throws Exception {
        BufferedWriter writer = null;
        
        final File dir = new File("./Data/characters/details/");
        
        if (!dir.exists()) {
        	dir.mkdirs();
        }        
        
        try {
            writer = new BufferedWriter(new FileWriter(
                    dir.toString() + File.separator + username + ".json", false));
            writer.write(PlayerSerializer.GSON.toJson(this));
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
