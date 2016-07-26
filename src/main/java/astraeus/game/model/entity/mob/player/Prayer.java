package astraeus.game.model.entity.mob.player;

import astraeus.game.model.entity.mob.player.collect.Equipment;
import astraeus.game.model.entity.mob.player.skill.Skill;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.packet.out.ServerMessagePacket;
import astraeus.net.packet.out.SetWidgetConfigPacket;
import astraeus.net.packet.out.SetWidgetStringPacket;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Resembles a players prayer.
 *
 * @author Vult-R
 */
public final class Prayer {
	
	/**
	 * The immutable map of buttons mapped to their prayers.
	 */
	public static final ImmutableMap<Integer, PrayerType> VALUES = ImmutableMap.copyOf(Stream.of(PrayerType.values()).collect(Collectors.toMap(p -> p.getButtonId(), Function.identity())));

    /**
     * Represents the enumerated types of prayer
     *
     * @author Vult-R
     */
    public enum PrayerType {    	
        THICK_SKIN("Thick Skin", 0, 5609, 0, 12.0, 83, 446, PrayerGroup.DEFENCE),
        BURST_OF_STRENGTH("Burst of Strength", 1, 5610, 4, 12.0, 84, 449, PrayerGroup.STRENGTH),
        CLARITY_OF_THOUGHT("Clarity of Thought", 2, 5611, 7, 12.0, 85, 436, PrayerGroup.ATTACK),
        SHARP_EYE("Sharp Eye", 3, 19812, 8, 12.0, 700, -1, PrayerGroup.MAGE_RANGE),
        MYSTIC_WILL("Mystic Will", 4, 19814, 9, 12.0, 701, -1, PrayerGroup.MAGE_RANGE),
        ROCK_SKIN("Rock Skin", 5, 5612, 10, 8.0, 86, 441, PrayerGroup.DEFENCE),
        SUPERHUMAN_STRENGTH("Superhuman Strength", 6, 5613, 13, 8.0, 87, 434, PrayerGroup.STRENGTH),
        IMPROVED_REFLEXES("Improved Reflexes", 7, 5614, 16, 8.0, 88, 448, PrayerGroup.ATTACK),
        RAPID_RESTORE("Rapid Restore", 8, 5615, 19, 60.0, 89, 452, PrayerGroup.DEFAULT),
        RAPID_HEAL("Rapid Heal", 9, 5616, 22, 60.0, 90, 443, PrayerGroup.DEFAULT),
        PROTECT_ITEM("Protect Item", 10, 5617, 25, 30.0, 91, -1, PrayerGroup.DEFAULT),
        HAWK_EYE("Hawk Eye", 11, 19816, 26, 6.0, 702, -1, PrayerGroup.MAGE_RANGE),
        MYSTIC_LORE("Mystic Lore", 12, 19818, 27, 6.0, 703, -1, PrayerGroup.MAGE_RANGE),
        STEEL_SKIN("Steel Skin", 13, 5618, 28, 6.0, 92, 439, PrayerGroup.DEFENCE),
        ULTIMATE_STRENGTH("Ultimate Strength", 14, 5619, 31, 6.0, 93, 450, PrayerGroup.STRENGTH),
        INCREDIBLE_REFLEXES("Incredible Reflexes", 15, 5620, 34, 6.0, 94, 440, PrayerGroup.ATTACK),
        PROTECT_FROM_MAGIC("Protect from Magic", 16, 5621, 37, 4.0, 95, 438, PrayerGroup.OVER_HEAD),
        PROTECT_FROM_RANGE("Protect from Range", 17, 5622, 40, 4.0, 96, 444, PrayerGroup.OVER_HEAD),
        PROTECT_FROM_MELEE("Protect from Melee", 18, 5623, 43, 4.0, 97, 433, PrayerGroup.OVER_HEAD),
        EAGLE_EYE("Eagle Eye", 19, 19821, 44, 6.0, 704, -1, PrayerGroup.MAGE_RANGE),
        MYSTIC_MIGHT("Mystic Might", 20, 19823, 45, 6.0, 705, -1, PrayerGroup.MAGE_RANGE),
        RETRIBUTION("Retribution", 21, 683, 46, 4.0, 98, -1, PrayerGroup.OVER_HEAD),
        REDEMPTION("Redemption", 22, 684, 49, 3.0, 99, -1, PrayerGroup.OVER_HEAD),
        SMITE("Smite", 23, 685, 52, 4.0, 100, -1, PrayerGroup.OVER_HEAD),
        CHIVALRY("Chivalry", 24, 19825, 60, 3.0, 706, -1, PrayerGroup.COMBAT),
        PIETY("Piety", 25, 19827, 70, 3.0, 707, -1, PrayerGroup.COMBAT);
    	
        /**
         * The immutable list of all prayers that belong to the over head group and only one can be activated at a given time.
         */
        static final ImmutableList<PrayerType> OVER_HEAD_DISABLED = ImmutableList.of(PrayerType.PROTECT_FROM_MAGIC, PrayerType.PROTECT_FROM_RANGE, PrayerType.PROTECT_FROM_MELEE, PrayerType.RETRIBUTION, PrayerType.REDEMPTION, PrayerType.SMITE);
        
        /**
         * The immutable list of all prayers that belong to the defense group and only one can be activated at a given time.
         */
        static final ImmutableList<PrayerType> DEFENCE_DISABLED = ImmutableList.of(PrayerType.THICK_SKIN, PrayerType.ROCK_SKIN, PrayerType.STEEL_SKIN, PrayerType.CHIVALRY, PrayerType.PIETY);
        
        /**
         * The immutable list of all prayers that belong to the attack group and only one can be activated at a given time.
         */
        static final ImmutableList<PrayerType> ATTACK_DISABLED = ImmutableList.of(PrayerType.CLARITY_OF_THOUGHT, PrayerType.IMPROVED_REFLEXES, PrayerType.INCREDIBLE_REFLEXES, PrayerType.SHARP_EYE, PrayerType.HAWK_EYE, PrayerType.EAGLE_EYE, PrayerType.MYSTIC_WILL, PrayerType.MYSTIC_LORE, PrayerType.MYSTIC_MIGHT, PrayerType.CHIVALRY, PrayerType.PIETY);
        
        /**
         * The immutable list of all prayers that belong to the strength group and only one can be activated at a given time.
         */
        static final ImmutableList<PrayerType> STRENGTH_DISABLED = ImmutableList.of(PrayerType.BURST_OF_STRENGTH, PrayerType.SUPERHUMAN_STRENGTH, PrayerType.ULTIMATE_STRENGTH, PrayerType.SHARP_EYE, PrayerType.HAWK_EYE, PrayerType.EAGLE_EYE, PrayerType.MYSTIC_WILL, PrayerType.MYSTIC_LORE, PrayerType.MYSTIC_MIGHT, PrayerType.CHIVALRY, PrayerType.PIETY);
        
        /**
         * The immutable list of all prayers that belong to the attack and strength group and only one can be activated at a given time.
         */
        static final ImmutableList<PrayerType> ATT_STR_DISABLED = ImmutableList.of(PrayerType.CLARITY_OF_THOUGHT, PrayerType.IMPROVED_REFLEXES, PrayerType.INCREDIBLE_REFLEXES, PrayerType.BURST_OF_STRENGTH, PrayerType.SUPERHUMAN_STRENGTH, PrayerType.ULTIMATE_STRENGTH, PrayerType.SHARP_EYE, PrayerType.HAWK_EYE, PrayerType.EAGLE_EYE, PrayerType.MYSTIC_WILL, PrayerType.MYSTIC_LORE, PrayerType.MYSTIC_MIGHT, PrayerType.CHIVALRY, PrayerType.PIETY);
        
        /**
         * The immutable list of all prayers that belong to the combat group and only one can be activated at a given time.
         */
        static final ImmutableList<PrayerType> COMBAT_DISABLED = ImmutableList.of(PrayerType.CLARITY_OF_THOUGHT, PrayerType.IMPROVED_REFLEXES, PrayerType.INCREDIBLE_REFLEXES, PrayerType.BURST_OF_STRENGTH, PrayerType.SUPERHUMAN_STRENGTH, PrayerType.ULTIMATE_STRENGTH, PrayerType.THICK_SKIN, PrayerType.ROCK_SKIN, PrayerType.STEEL_SKIN, PrayerType.SHARP_EYE, PrayerType.HAWK_EYE, PrayerType.EAGLE_EYE, PrayerType.MYSTIC_WILL, PrayerType.MYSTIC_LORE, PrayerType.MYSTIC_MIGHT, PrayerType.CHIVALRY, PrayerType.PIETY);

        /**
         * The name of this prayer.
         */
        private final String name;
        
        /**
         * The id of this prayer.
         */
        private final int id;

        /**
         *The buttonId associated with this prayer.
         */
        private final int buttonId;

        /**
         * The required level to use this prayer.
         */
        private final int requiredLevel;

        /**
         * The rate in ticks at which a players prayer level drains.
         */
        private final double drainRate;

        /**
         * The config id associated with this prayer.
         */
        private final int configId;

        /**
         * The sound effect associated with this prayer.
         */
        private final int soundId;

        /**
         * The group in which this prayer belongs to.
         */
        private final PrayerGroup group;

        private PrayerType(String name, int id, int buttonId, int level, double drainRate, int configId, int soundId, PrayerGroup group) {
            this.name = name;
            this.id = id;
            this.buttonId = buttonId;
            this.requiredLevel = level;
            this.drainRate = drainRate;
            this.configId = configId;
            this.soundId = soundId;
            this.group = group;
        }        

        /**
         * Gets the disabled prayers.
         */
        public ImmutableList<PrayerType> getDisabledPrayers() {
            switch (group) {

                case OVER_HEAD:
                    return OVER_HEAD_DISABLED;

                case DEFENCE:
                    return DEFENCE_DISABLED;

                case ATTACK:
                    return ATTACK_DISABLED;

                case STRENGTH:
                    return STRENGTH_DISABLED;

                case MAGE_RANGE:
                    return ATT_STR_DISABLED;

                case COMBAT:
                    return COMBAT_DISABLED;

                default:
                    throw new IllegalStateException(String.format("%s is illegal!", group.name()));
            }
        }
        
        /**
         * Gets the id of this prayer.
         */
        public int getId() {
        	return id;
        }

        /**
         * Gets the button for this prayer.
         */
        public int getButtonId() {
            return buttonId;
        }

        /**
         * Gets the config value for this prayer.
         */
        public int getConfigId() {
            return configId;
        }

        /**
         * Gets the sound effect id for this prayer.
         */
        public int getSoundId() {
            return soundId;
        }
        
        /**
         * Gets the drain rate of this prayer.
         */
        public double getDrainRate() {
            return drainRate;
        }

        /**
         * Gets the required level for this prayer.
         */
        public int getRequiredLevel() {
            return requiredLevel;
        }

        /**
         * Gets the name of this prayer.
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the prayer group this prayer belongs to.
         */
        public PrayerGroup getGroup() {
            return group;
        }

    }

    /**
     * Represents a prayer group
     */
    private enum PrayerGroup {    	
        OVER_HEAD,
        DEFENCE,
        ATTACK,
        STRENGTH,
        MAGE_RANGE,
        COMBAT,
        DEFAULT
    }

    /**
     * The player who's set of prayer this belongs to.
     */
    private final Player player;

    /**
     * The list of activated prayers
     */
    private final boolean[] activated = new boolean[PrayerType.values().length];

    /**
     * The list of activated quick prayers.
     */
    private boolean[] quickPrayers = new boolean[PrayerType.values().length];

    /**
     * The flag that denotes quick prayer is enabled.
     */
    private boolean quickPrayerEnabled;

    /**
     * The list of prayers currently draining.
     */
    private final int[] drain = new int[PrayerType.values().length];

    /**
     * Creates a new {@link Prayer}.
     *
     * @param player
     *      The player who owns these prayers.
     */
    public Prayer(Player player) {
        this.player = player;
    }

    /**
     * Determines if a prayer can be activated.
     */
    public boolean active(PrayerType prayer) {
        return activated[prayer.getId()];
    }

    /**
     * Determines if a button is a prayer button.
     */
    public static boolean isPrayerButton(int buttonId) {
        return Arrays.stream(PrayerType.values()).anyMatch(it -> buttonId == it.getButtonId());
    }

    /**
     * Determines if a player can toggle a specified prayer.
     */
    private boolean canToggle(PrayerType prayer) {
        if (!player.canPray()) {
            return false;
        }

        if (player.isDead()) {
            return false;
        }

        if (player.getSkills().getLevel(Skill.PRAYER) == 0) {
            player.queuePacket(new ServerMessagePacket("You have run out of prayer points; you must recharge at an altar."));
            return false;
        }

        if (player.getSkills().getMaxLevel(Skill.PRAYER) < prayer.getRequiredLevel()) {
            player.getDialogueFactory().sendStatement(String.format("You need a Prayer level of <col=255>%d</col> to use <col=255>%s</col>.", prayer.getRequiredLevel(), prayer.getName())).execute();
            return false;
        }

        if (prayer == PrayerType.CHIVALRY && player.getSkills().getMaxLevel(Skill.PRAYER) < 65) {
            player.getDialogueFactory().sendStatement("You need a Defence level of <col=255>65</col> to use Chivalry.").execute();
            return false;
        }

        if (prayer == PrayerType.PIETY && player.getSkills().getMaxLevel(Skill.PRAYER) < 70) {
            player.getDialogueFactory().sendStatement("You need a Defence level of <col=255>70</col> to use Piety.").execute();
            return false;
        }

        return true;
    }

    /**
     * Determines if a button can be clicked.
     */
    public boolean clickButton(int button) {
    	// quick prayer
        if (button >= 17202 && button <= 17227) {
            final int prayerId = button - 17202;
            final PrayerType prayer = PrayerType.values()[prayerId];

            if (!quickPrayers[prayerId]) {
                if (!canToggle(prayer)) {
                    player.queuePacket(new SetWidgetConfigPacket(630 + prayerId, 0));
                    return true;
                } else {
                    quickPrayers[prayer.getId()] = true;
                    player.queuePacket(new SetWidgetConfigPacket(630 + prayer.getId(), 1));
                    if (prayer.getDisabledPrayers() != null) {
                        for (final PrayerType override : prayer.getDisabledPrayers()) {
                            if (override != prayer) {
                                quickPrayers[override.getId()] = false;
                                player.queuePacket(new SetWidgetConfigPacket(630 + override.getId(), 0));
                            }
                        }
                    }
                }
            } else {
                quickPrayers[prayerId] = false;
                player.queuePacket(new SetWidgetConfigPacket(630 + prayerId, 0));
            }
            return true;
        }
        
        search(button).ifPresent(it -> toggle(it));        

        switch (button) {

            case 5000:
                boolean has = false;
                for (final boolean pray : quickPrayers) {
                    has |= pray;
                }
                if (!has) {
                    player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:off", 0));
                    quickPrayerEnabled = false;
                    player.queuePacket(new ServerMessagePacket("Your don't have any quick prayers set."));
                } else {
                    toggleQuickPrayers(!quickPrayerEnabled);
                }
                break;

            case 5001:
                for (final PrayerType prayer : PrayerType.values()) {
                    player.queuePacket(new SetWidgetConfigPacket(630 + prayer.getId(), quickPrayers[prayer.getId()] ? 1 : 0));
                }
                break;

            case 17231:
                player.queuePacket(new ServerMessagePacket("Your quick prayers have been saved."));
                player.getWidgets().openSidebarWidget(5, 5608);
                break;
        }
        return false;
    }

    /**
     * Gets the associated head icon from an over head type prayer.
     */
    private int getHeadIcon(PrayerType prayer) {
        switch (prayer) {
            case PROTECT_FROM_MAGIC:
                return 2;
            case PROTECT_FROM_RANGE:
                return 1;
            case PROTECT_FROM_MELEE:
                return 0;
            case RETRIBUTION:
                return 3;
            case REDEMPTION:
                return 5;
            case SMITE:
                return 4;
            default:
                return -1;
        }
    }

    /**
     * Disables all prayer buttons.
     */
    public void disable() {
        for (final PrayerType prayer : PrayerType.values()) {
            if (active(prayer)) {
                toggle(prayer, false);
            }
        }
    }

    /**
     * Disables a specific prayer.
     */
    public void disable(PrayerType prayer) {
        toggle(prayer, false);
    }

    /**
     * The method that will cause a players prayer level to drain based upon which prayers are activated.
     */
    public void drain() {
        int amount = 0;
        for (final PrayerType prayer : PrayerType.values()) {
            if (active(prayer)) {
                if (++drain[prayer.getId()] >= getAffectedDrainRate(prayer)) {
                    amount++;
                    drain[prayer.getId()] = 0;
                }
            }
        }
        if (amount > 0) {
            drain(amount);
        }
    }

    /**
     * Drains a players prayer by a specified amount.
     */
    public void drain(int drain) {
        player.getSkills().changeLevel(Skill.PRAYER, drain < 1 ? -1 : (int) -Math.ceil(drain));

        if (player.getSkills().getLevel(Skill.PRAYER) <= 0) {
            disable();
            player.queuePacket(new ServerMessagePacket("You have run out of prayer points; you must recharge at an altar."));
            Arrays.fill(this.drain, 0);
        }
    }

    /**
     * Gets the affected drain rate.
     */
    public double getAffectedDrainRate(PrayerType prayer) {
        final int prayerBonus = player.getBonuses()[Equipment.PRAYER];
        return prayer.getDrainRate() * (1 + prayerBonus / 30.0);
    }

    /**
     * The method that toggles a specified prayer.
     */
    public boolean toggle(PrayerType prayer) {
        if (!canToggle(prayer)) {
            player.queuePacket(new SetWidgetConfigPacket(prayer.getConfigId(), 0));
            player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:off", 0));
            quickPrayerEnabled = false;
            return false;
        }
        toggle(prayer, !activated[prayer.getId()]);
        return true;
    }
    
    /**
     * Gets an {@link Optional} describing the result of retrieving a mapped button to its prayer.
     */
    public Optional<PrayerType> search(int button) {
    	return Optional.ofNullable(VALUES.get(button));
    }

    /**
     * The method that toggles a specified prayer.
     */
    public void toggle(PrayerType prayer, boolean isEnabled) {
        if (player.isDead() || player.getCurrentHealth() <= 0) {
            player.queuePacket(new SetWidgetConfigPacket(prayer.getConfigId(), 0));
            player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:off", 0));
            quickPrayerEnabled = false;
            return;
        }

        activated[prayer.getId()] = isEnabled;
        player.queuePacket(new SetWidgetConfigPacket(prayer.getConfigId(), isEnabled ? 1 : 0));
        if (isEnabled) {
            if (prayer.getDisabledPrayers() != null) {
                for (final PrayerType override : prayer.getDisabledPrayers()) {
                    if (override != prayer) {
                        toggle(override, false);
                    }
                }
            }
            final int icon = getHeadIcon(prayer);
            if (icon != player.getHeadIcon() && icon != -1) {
                player.setHeadIcon(icon);
                player.getUpdateFlags().add(UpdateFlag.APPEARANCE);
            }
        } else if (prayer.getGroup() == PrayerGroup.OVER_HEAD) {
            final int icon = getHeadIcon(prayer);
            if (icon == player.getHeadIcon()) {
                player.setHeadIcon(-1);
            }
        }

        if (quickPrayerEnabled) {
            player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:off", 0));
            quickPrayerEnabled = false;
        }

    }

    /**
     * The method that toggles a quick prayer.
     */
    public void toggleQuickPrayers(boolean enabled) {
        boolean success = true;

        for (final PrayerType prayer : PrayerType.values()) {
            if (!active(prayer) && !enabled) {
                continue;
            }

            if (active(prayer) && enabled && quickPrayers[prayer.getId()]) {
                continue;
            }

            toggle(prayer, enabled && quickPrayers[prayer.getId()]);

            if (!active(prayer) && quickPrayers[prayer.getId()] || !enabled) {
                success = false;
            }
        }

        if (!enabled) {
            player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:off", 0));
            quickPrayerEnabled = false;
        } else {
            player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:" + (success ? "on" : "off"), 0));
            quickPrayerEnabled = success;
        }
    }

}
