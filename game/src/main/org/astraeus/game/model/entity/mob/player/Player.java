package astraeus.game.model.entity.mob.player;

import astraeus.Configuration;
import astraeus.game.event.Event;
import astraeus.game.model.*;
import astraeus.game.model.entity.EntityType;
import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemContainer;
import astraeus.game.model.entity.mob.Mob;
import astraeus.game.model.entity.mob.npc.Npc;
import astraeus.game.model.entity.mob.player.attribute.AttributeKey;
import astraeus.game.model.entity.mob.player.attribute.AttributeMap;
import astraeus.game.model.entity.mob.player.io.PlayerSerializer;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.game.model.entity.object.GameObject;
import astraeus.game.model.location.Area;
import astraeus.game.model.sound.Volume;
import astraeus.game.model.widget.WidgetSet;
import astraeus.net.channel.PlayerChannel;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;
import astraeus.net.packet.out.*;
import astraeus.util.LoggerUtils;
import astraeus.util.StringUtils;

import java.util.Objects;
import java.util.logging.Logger;

public class Player extends Mob {

	/**
	 * The single logger for this class.
	 */
	private static final Logger LOGGER = LoggerUtils.getLogger(Player.class);

	/**
	 * The default appearance of a player.
	 */
	public static final Appearance DEFAULT_APPEARANCE = new Appearance(Appearance.Gender.MALE, 0, 10, 18, 26, 33, 36,
			42, 7, 8, 9, 5, 0);

	/**
	 * The default location a player will spawn.
	 */
	public static final Position DEFAULT_LOCATION = new Position(3086, 3499);

	/**
	 * The default location a player will spawn if they died.
	 */
	public static final Position DEFAULT_RESPAWN = new Position(3087, 3502);

	private ChatMessage chatMessage = new ChatMessage();
	private final PlayerRelation playerRelation = new PlayerRelation(this);
	private final Inventory inventory = new Inventory(this);
	private final Equipment equipment = new Equipment(this);
	private final Bank bank = new Bank(this);
	private final WidgetSet widgets = new WidgetSet(this);

	public int lastMessage = 1;
	@SuppressWarnings("unused")
	private int headIcon;
	private int combatLevel;
	private Appearance appearance = Player.DEFAULT_APPEARANCE;
	private PlayerRights rights = PlayerRights.PLAYER;

	private PlayerChannel session;

	private String username;
	private String password;
	private String uuid;

	private int runEnergy = 100;
	private int runRestore;

	private int specialAmount = 100;

	private int wildernessLevel;
	private boolean inWilderness;
	private boolean inMultiCombat;
	
	public static final AttributeKey<Boolean> ACCEPT_AID_KEY = AttributeKey.valueOf("accept_aid", true);
	public static final AttributeKey<Boolean> ACTIVE_KEY = AttributeKey.valueOf("active", false);
	public static final AttributeKey<Boolean> AUTO_RETALIATE_KEY = AttributeKey.valueOf("auto_retaliate", true);
	public static final AttributeKey<Boolean> BANKING_KEY = AttributeKey.valueOf("banking", false);
	public static final AttributeKey<Boolean> CHAT_EFFECTS_KEY = AttributeKey.valueOf("chat_effects", true);
	public static final AttributeKey<Boolean> DEBUG_KEY = AttributeKey.valueOf("debug", false);
	public static final AttributeKey<Boolean> DEBUG_NETWORK_KEY = AttributeKey.valueOf("debug_network", false);
	public static final AttributeKey<Boolean> DISCONNECTED_KEY = AttributeKey.valueOf("disconnected", false);
	public static final AttributeKey<Boolean> LOGOUT_KEY = AttributeKey.valueOf("logout", false);
	public static final AttributeKey<Boolean> MUSIC_KEY = AttributeKey.valueOf("music", true);
	public static final AttributeKey<Boolean> NEW_PLAYER_KEY = AttributeKey.valueOf("new_player", false);
	public static final AttributeKey<Boolean> MOUSE_BUTTON_KEY = AttributeKey.valueOf("mouse_button", false);
	public static final AttributeKey<Boolean> SAVE_KEY = AttributeKey.valueOf("save", false);
	public static final AttributeKey<Boolean> SHOPPING_KEY = AttributeKey.valueOf("shopping", false);
	public static final AttributeKey<Boolean> SOUND_KEY = AttributeKey.valueOf("sound", true);
	public static final AttributeKey<Boolean> SPLIT_CHAT_KEY = AttributeKey.valueOf("split_chat_key", true);
	
	public static final AttributeKey<Volume> AREA_SOUND_VOLUME_KEY = AttributeKey.valueOf("area_sound_volume", Volume.NORMAL);
	public static final AttributeKey<Volume> MUSIC_VOLUME_KEY = AttributeKey.valueOf("music_volume", Volume.NORMAL);
	public static final AttributeKey<Volume> SOUND_EFFECT_VOLUME_KEY = AttributeKey.valueOf("sound_effect_volume", Volume.NORMAL);
	
	public static final AttributeKey<Brightness> BRIGHTNESS_KEY = AttributeKey.valueOf("brightness", Brightness.NORMAL);
		
	private AttributeMap attr = new AttributeMap();

	// actual player
	public Player(final PlayerChannel session) {
		super(Player.DEFAULT_LOCATION);
		this.session = session;
	}

	// bots
	public Player(String username, Position location) {
		super(location);
		this.username = username;
	}
	
	public void createAttributes() {
		attr.put(ACCEPT_AID_KEY, true);
		attr.put(ACTIVE_KEY, false);
		attr.put(AREA_SOUND_VOLUME_KEY, Volume.NORMAL);
		attr.put(AUTO_RETALIATE_KEY, true);
		attr.put(BANKING_KEY, false);
		attr.put(BRIGHTNESS_KEY, Brightness.NORMAL);
		attr.put(CHAT_EFFECTS_KEY, true);
		attr.put(DEBUG_KEY, false);
		attr.put(DEBUG_NETWORK_KEY, false);
		attr.put(DISCONNECTED_KEY, false);
		attr.put(LOGOUT_KEY, false);
		attr.put(SAVE_KEY, false);
		attr.put(SHOPPING_KEY, false);
		attr.put(SOUND_KEY, true);
		attr.put(SOUND_EFFECT_VOLUME_KEY, Volume.NORMAL);
		attr.put(SPLIT_CHAT_KEY, true);
		attr.put(MOUSE_BUTTON_KEY, false);
		attr.put(MUSIC_KEY, true);
		attr.put(MUSIC_VOLUME_KEY, Volume.NORMAL);
		attr.put(NEW_PLAYER_KEY, false);
	}

	@Override
	public void tick() {
		handleRunRestore();
	}

	@Override
	public void onRegister() {
		World.WORLD.register(this);
		setRegionChange(true);
		getUpdateFlags().add(UpdateFlag.APPEARANCE);
		onStartup();
		setPosition(attr().contains(AttributeKey.valueOf("new_player", true)) ? Player.DEFAULT_LOCATION : getPosition());
		onLogin();
		LOGGER.info(String.format("[REGISTERED]: [user= %s]", username));
	}

	@Override
	public void onDeregister() {
		PlayerSerializer.encode(this);
		send(new LogoutPlayerPacket());
		resetEntityInteraction();
		attr().put(Player.DISCONNECTED_KEY, true);
		session.getChannel().close();
		World.WORLD.deregister(this);
		LOGGER.info(String.format("[DEREGISTERED]: [host= %s]", session.getHostAddress()));
	}

	@Override
	public void onLogin() {
		send(new ServerMessagePacket("Welcome to " + Configuration.SERVER_NAME + "."));
	}

	@Override
	public void onStartup() {
		send(new SetPlayerSlotPacket());
		send(new ResetCameraPositionPacket());
		send(new SetPrivacyOptionPacket(0, 0, 0));
		send(new SetSpecialAmountPacket());
		send(new SetWidgetConfigPacket(172, attr().get(AUTO_RETALIATE_KEY) ? 1 : 0));		
		send(new SetPlayerOptionPacket(PlayerOption.FOLLOW));
		send(new SetPlayerOptionPacket(PlayerOption.TRADE_REQUEST));
		send(new SetWidgetConfigPacket(152, getMovement().isRunning() ? 1 : 0));
		send(new SetWidgetConfigPacket(429, getMovement().isRunning() ? 1 : 0));
		send(new SetWidgetConfigPacket(171, attr().get(MOUSE_BUTTON_KEY) ? 1 : 0));
		send(new SetWidgetConfigPacket(172, attr().get(CHAT_EFFECTS_KEY) ? 1 : 0));
		send(new SetWidgetConfigPacket(287, attr().get(SPLIT_CHAT_KEY) ? 1 : 0));
		send(new SetWidgetConfigPacket(427, attr().get(ACCEPT_AID_KEY) ? 1 : 0));
		send(new SetWidgetConfigPacket(166, attr().get(BRIGHTNESS_KEY).getCode()));
		send(new SetWidgetConfigPacket(168, attr().get(MUSIC_VOLUME_KEY).getCode()));
		send(new SetWidgetConfigPacket(169, attr().get(SOUND_EFFECT_VOLUME_KEY).getCode()));
		send(new SetWidgetConfigPacket(170, attr().get(AREA_SOUND_VOLUME_KEY).getCode()));
		Players.createSideBarInterfaces(this, true);
		for (int i = 0; i < 23; i++) {
			send(new SetWidgetTextPacket(i));
		}
		inventory.refresh();
		equipment.refresh();
		equipment.updateWeapon();
		bank.refresh();
		bank.initBank();
		getRelation().updateLists(true);
		getRelation().sendFriends();
		send(new SetRunEnergyPacket());
		getRelation().updateLists(true);
		Players.resetPlayerAnimation(this);
		attr().put(SAVE_KEY, true);
	}

	@Override
	public void onLogout() {
		attr().put(ACTIVE_KEY, false);
		attr().put(LOGOUT_KEY, true);
		attr().put(DISCONNECTED_KEY, true);
		send(new LogoutPlayerPacket());
	}

	@Override
	public boolean canLogout() {
		return true;
	}

	@Override
	public boolean canTeleport() {
		return true;
	}

	@Override
	public boolean canClickButton() {
		return true;
	}

	@Override
	public boolean canTrade() {
		return true;
	}

	@Override
	public boolean canDuel() {
		return false;
	}

	@Override
	public boolean canAttackMob(Npc mob) {
		return true;
	}

	@Override
	public boolean canAttackPlayer(Player player) {
		return false;
	}

	@Override
	public boolean canClickMob(Npc mob) {
		return true;
	}

	@Override
	public boolean canClickObject(GameObject object) {
		return true;
	}

	@Override
	public boolean canDrink() {
		return true;
	}

	@Override
	public boolean canEat() {
		return true;
	}

	@Override
	public boolean canDrop() {
		return true;
	}

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public boolean canPickup(Item item) {
		return true;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public boolean canPray() {
		return true;
	}

	@Override
	public boolean canTalk() {
		return true;
	}

	@Override
	public boolean canUnequip(Item item) {
		return true;
	}

	@Override
	public boolean canUseSpecial() {
		return true;
	}

	@Override
	public void onDeath() {

	}

	@Override
	public void onMovement() {
		displayWalkableInterfaces();

		handleRunEnergy();
	}

	@Override
	public int size() {
		return 1;
	}

	public void update() {
		synchronized (this) {
			send(new UpdatePlayerPacket());
			send(new UpdateNpcPacket());
		}
	}

	@Override
	public void prepare() {
		getMovement().handleEntityMovement();
	}

	@Override
	public void clearUpdateFlags() {
		getUpdateFlags().clear();
		getAnimations().clear();
		getGraphics().clear();
		setRegionChange(false);
		setTeleporting(false);
	}

	@Override
	public void decrementHealth(int damage) {

	}

	@Override
	public int getCurrentHealth() {
		return 99;
	}

	@Override
	public int getMaximumHealth() {
		return 99;
	}

	public void displayWalkableInterfaces() {
		if (Area.inWilderness(this)) {
			int calculateY = this.getPosition().getY() > 6400 ? super.getPosition().getY() - 6400
					: super.getPosition().getY();
			wildernessLevel = (((calculateY - 3520) / 8) + 1);
			if (!inWilderness) {
				send(new DisplayWalkableWidgetPacket(197));
				send(new SetPlayerOptionPacket(PlayerOption.ATTACK));
				inWilderness = true;
			}
			send(new SetWidgetStringPacket("@yel@Level: " + wildernessLevel, 199));
		} else if (inWilderness) {
			send(new SetPlayerOptionPacket(PlayerOption.ATTACK, true));
			send(new DisplayWalkableWidgetPacket(-1));
			inWilderness = false;
			wildernessLevel = 0;
		}
		if (Area.inMultiCombat(this)) {
			if (!inMultiCombat) {
				send(new DisplayMultiIconPacket(false));
				inMultiCombat = true;
			}
		} else {
			send(new DisplayMultiIconPacket(true));
			inMultiCombat = false;
		}
	}

	/**
	 * Moves a {@code player} to a target {@code location.
	 *
	 * @param player
	 *            The player that is being moved.
	 *
	 * @param location
	 *            The location the player will be sent to.
	 */
	public void move(Position location) {
		setLastLocation(getPosition().copy());
		setPosition(new Position(location.copy()));
		setTeleporting(true);
		getMovement().reset();
		getUpdateFlags().add(UpdateFlag.APPEARANCE);
	}

	private void handleRunRestore() {
		if (!getMovement().isMoving()) {
			setRunRestore(getRunRestore() + 1);

			if (getRunRestore() == 3) {
				setRunRestore(0);

				int energy = getRunEnergy() + 1;

				if (energy > 100)
					energy = 100;

				setRunEnergy(energy);
				send(new SetRunEnergyPacket());
			}
		}
	}

	private void handleRunEnergy() {
		if (getRunEnergy() <= 0) {
			getMovement().setRunning(false);
			send(new SetWidgetConfigPacket(152, 0));
		} else {
			setRunEnergy(getRunEnergy() - 1);
			send(new SetRunEnergyPacket());
		}
	}

	/**
	 * Posts an event to this world's event provider.
	 *
	 * @param event
	 *            The event to post.
	 */
	public <E extends Event> void post(E event) {
		World.WORLD.post(this, event);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername(), getPassword());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Player) {
			Player other = (Player) obj;
			return other.hashCode() == hashCode();
		}

		return false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostAddress() {
		return session.getHostAddress();
	}

	public long getLongUsername() {
		return StringUtils.stringToLong(username);
	}

	public Appearance getAppearance() {
		return appearance;
	}

	public ChatMessage getChatMessage() {
		return chatMessage;
	}

	public int getCombatLevel() {
		return combatLevel;
	}

	public PlayerRelation getRelation() {
		return playerRelation;
	}

	public PlayerRights getRights() {
		return rights;
	}

	public PlayerChannel getSession() {
		return session;
	}

	/**
	 * Sends an {@link OutgoingPacket} to a users client.
	 * 
	 * @param out
	 *            The outgoing packet.
	 */
	public void send(final Sendable out) {
		this.session.queue(out);
	}

	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
		getUpdateFlags().add(UpdateFlag.APPEARANCE);
	}

	public void setChatMessage(ChatMessage chatMessage) {
		this.chatMessage = chatMessage;
	}

	public void setRights(final PlayerRights rights) {
		this.rights = rights;
	}

	public ItemContainer getInventory() {
		return inventory;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public Bank getBank() {
		return bank;
	}

	public int getRunEnergy() {
		return runEnergy;
	}

	public void setRunEnergy(int runEnergy) {
		this.runEnergy = runEnergy;
	}

	public int getRunRestore() {
		return runRestore;
	}

	public void setRunRestore(int runRestore) {
		this.runRestore = runRestore;
	}

	public int getSpecialAmount() {
		return specialAmount;
	}

	public void setSpecialAmount(int specialAmount) {
		this.specialAmount = specialAmount;
	}

	public String getUUID() {
		return uuid;
	}	

	public WidgetSet getWidgets() {
		return widgets;
	}
	
	public AttributeMap attr() {
		return attr;
	}

	@Override
	public int getHashCode() {
		return Objects.hash(username, password);
	}

	@Override
	public EntityType type() {
		return EntityType.PLAYER;
	}

}
