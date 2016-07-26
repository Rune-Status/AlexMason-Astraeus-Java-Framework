package astraeus.game.model.entity.mob.player;

import astraeus.game.model.entity.mob.player.collect.Equipment;
import astraeus.game.model.entity.mob.player.skill.Skill;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.packet.out.ServerMessagePacket;
import astraeus.net.packet.out.SetWidgetConfigPacket;
import astraeus.net.packet.out.SetWidgetStringPacket;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;

/**
 * Resembles a player's prayer book.
 *
 * @author Vult-R
 */
public final class PrayerBook {

    /**
     * Represents the enumerated types of prayer
     *
     * @author Vult-R
     */
    public enum Prayer {
        THICK_SKIN("Thick Skin", 5609, 1, 12.0, 83, 446, PrayerGroup.DEFENCE),
        BURST_OF_STRENGTH("Burst of Strength", 5610, 4, 12.0, 84, 449, PrayerGroup.STRENGTH),
        CLARITY_OF_THOUGHT("Clarity of Thought", 5611, 7, 12.0, 85, 436, PrayerGroup.ATTACK),
        SHARP_EYE("Sharp Eye", 19812, 8, 12.0, 700, -1, PrayerGroup.MAGE_RANGE),
        MYSTIC_WILL("Mystic Will", 19814, 9, 12.0, 701, -1, PrayerGroup.MAGE_RANGE),
        ROCK_SKIN("Rock Skin", 5612, 10, 8.0, 86, 441, PrayerGroup.DEFENCE),
        SUPERHUMAN_STRENGTH("Superhuman Strength", 5613, 13, 8.0, 87, 434, PrayerGroup.STRENGTH),
        IMPROVED_REFLEXES("Improved Reflexes", 5614, 16, 8.0, 88, 448, PrayerGroup.ATTACK),
        RAPID_RESTORE("Rapid Restore", 5615, 19, 60.0, 89, 452, PrayerGroup.DEFAULT),
        RAPID_HEAL("Rapid Heal", 5616, 22, 60.0, 90, 443, PrayerGroup.DEFAULT),
        PROTECT_ITEM("Protect Item", 5617, 25, 30.0, 91, -1, PrayerGroup.DEFAULT),
        HAWK_EYE("Hawk Eye", 19816, 26, 6.0, 702, -1, PrayerGroup.MAGE_RANGE),
        MYSTIC_LORE("Mystic Lore", 19818, 27, 6.0, 703, -1, PrayerGroup.MAGE_RANGE),
        STEEL_SKIN("Steel Skin", 5618, 28, 6.0, 92, 439, PrayerGroup.DEFENCE),
        ULTIMATE_STRENGTH("Ultimate Strength", 5619, 31, 6.0, 93, 450, PrayerGroup.STRENGTH),
        INCREDIBLE_REFLEXES("Incredible Reflexes", 5620, 34, 6.0, 94, 440, PrayerGroup.ATTACK),
        PROTECT_FROM_MAGIC("Protect from Magic", 5621, 37, 4.0, 95, 438, PrayerGroup.OVER_HEAD),
        PROTECT_FROM_RANGE("Protect from Range", 5622, 40, 4.0, 96, 444, PrayerGroup.OVER_HEAD),
        PROTECT_FROM_MELEE("Protect from Melee", 5623, 43, 4.0, 97, 433, PrayerGroup.OVER_HEAD),
        EAGLE_EYE("Eagle Eye", 19821, 44, 6.0, 704, -1, PrayerGroup.MAGE_RANGE),
        MYSTIC_MIGHT("Mystic Might", 19823, 45, 6.0, 705, -1, PrayerGroup.MAGE_RANGE),
        RETRIBUTION("Retribution", 683, 46, 4.0, 98, -1, PrayerGroup.OVER_HEAD),
        REDEMPTION("Redemption", 684, 49, 3.0, 99, -1, PrayerGroup.OVER_HEAD),
        SMITE("Smite", 685, 52, 4.0, 100, -1, PrayerGroup.OVER_HEAD),
        CHIVALRY("Chivalry", 19825, 60, 3.0, 706, -1, PrayerGroup.COMBAT),
        PIETY("Piety", 19827, 70, 3.0, 707, -1, PrayerGroup.COMBAT);

        /**
         * The name of this prayer.
         */
        private final String name;

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

        private Prayer(String name, int buttonId, int level, double drainRate, int configId, int soundId, PrayerGroup group) {
            this.name = name;
            this.buttonId = buttonId;
            this.requiredLevel = level;
            this.drainRate = drainRate;
            this.configId = configId;
            this.soundId = soundId;
            this.group = group;
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
         * The immutable list of all prayers that belong to the over-head-disabled group.
         */
        static final ImmutableList<Prayer> OVER_HEAD_DISABLED = ImmutableList.of(Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_RANGE, Prayer.PROTECT_FROM_MELEE, Prayer.RETRIBUTION, Prayer.REDEMPTION, Prayer.SMITE);
        static final ImmutableList<Prayer> DEFENCE_DISABLED = ImmutableList.of(Prayer.THICK_SKIN, Prayer.ROCK_SKIN, Prayer.STEEL_SKIN, Prayer.CHIVALRY, Prayer.PIETY);
        static final ImmutableList<Prayer> ATTACK_DISABLED = ImmutableList.of(Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES, Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.EAGLE_EYE, Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_MIGHT, Prayer.CHIVALRY, Prayer.PIETY);
        static final ImmutableList<Prayer> STRENGTH_DISABLED = ImmutableList.of(Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.EAGLE_EYE, Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_MIGHT, Prayer.CHIVALRY, Prayer.PIETY);
        static final ImmutableList<Prayer> ATT_STR_DISABLED = ImmutableList.of(Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES, Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.EAGLE_EYE, Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_MIGHT, Prayer.CHIVALRY, Prayer.PIETY);
        static final ImmutableList<Prayer> COMBAT_DISABLED = ImmutableList.of(Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES, Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.THICK_SKIN, Prayer.ROCK_SKIN, Prayer.STEEL_SKIN, Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.EAGLE_EYE, Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_MIGHT, Prayer.CHIVALRY, Prayer.PIETY);

        /**
         * Gets the disabled prayers.
         */
        public ImmutableList<Prayer> getDisabledPrayers() {
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
                    throw new IllegalStateException(group.name() + " is illegal.");
            }
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

        public PrayerGroup getGroup() {
            return group;
        }
    }

    /**
     * Represents a prayer group
     */
    public enum PrayerGroup {
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
    private final boolean[] activated = new boolean[Prayer.values().length];

    /**
     * The list of activated quick prayers.
     */
    private boolean[] quickPrayers = new boolean[Prayer.values().length];

    /**
     * The flag that denotes quick prayer is enabled.
     */
    private boolean quickPrayerEnabled;

    /**
     * The list of prayers currently draining.
     */
    private final int[] drain = new int[Prayer.values().length];

    /**
     * Creates a new {@link PrayerBook}.
     *
     * @param player
     *      The player who owns these prayers.
     */
    public PrayerBook(Player player) {
        this.player = player;
    }

    /**
     * Determines if a prayer can be activated.
     */
    public boolean active(Prayer prayer) {
        return activated[prayer.ordinal()];
    }

    /**
     * Determines if a button is a prayer button.
     */
    public static boolean isPrayerButton(int buttonId) {
        return Arrays.stream(Prayer.values()).anyMatch(it -> buttonId == it.getButtonId());
    }

    /**
     * Determines if a player can toggle a specified prayer.
     */
    private boolean canToggle(Prayer prayer) {
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
            player.getDialogueFactory().sendStatement("You need a Prayer level of <col=255>" + prayer.getRequiredLevel() + "</col> to use <col=255>" + prayer.getName() + "</col>.").execute();
            return false;
        }

        if (prayer == Prayer.CHIVALRY && player.getSkills().getMaxLevel(Skill.PRAYER) < 65) {
            player.getDialogueFactory().sendStatement("You need a Defence level of <col=255>65</col> to use Chivalry.").execute();
            return false;
        }

        if (prayer == Prayer.PIETY && player.getSkills().getMaxLevel(Skill.PRAYER) < 70) {
            player.getDialogueFactory().sendStatement("You need a Defence level of <col=255>70</col> to use Piety.").execute();
            return false;
        }

        return true;
    }

    /**
     * Determines if a button can be clicked.
     */
    public boolean clickButton(int button) {
        if (button >= 17202 && button <= 17227) {
            final int prayerId = button - 17202;
            final Prayer prayer = Prayer.values()[prayerId];

            if (!quickPrayers[prayerId]) {
                if (!canToggle(prayer)) {
                    player.queuePacket(new SetWidgetConfigPacket(630 + prayerId, 0));
                    return true;
                } else {
                    quickPrayers[prayer.ordinal()] = true;
                    player.queuePacket(new SetWidgetConfigPacket(630 + prayer.ordinal(), 1));
                    if (prayer.getDisabledPrayers() != null) {
                        for (final Prayer override : prayer.getDisabledPrayers()) {
                            if (override != prayer) {
                                quickPrayers[override.ordinal()] = false;
                                player.queuePacket(new SetWidgetConfigPacket(630 + override.ordinal(), 0));
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
                for (final Prayer prayer : Prayer.values()) {
                    player.queuePacket(new SetWidgetConfigPacket(630 + prayer.ordinal(), quickPrayers[prayer.ordinal()] ? 1 : 0));
                }
                break;

            case 17231:
                player.queuePacket(new ServerMessagePacket("Your quick prayers have been saved."));
                player.getWidgets().openSidebarWidget(5, 5608);
                break;

            case 5609:
                toggle(Prayer.THICK_SKIN);
                return true;

            case 5610:
                toggle(Prayer.BURST_OF_STRENGTH);
                return true;

            case 5611:
                toggle(Prayer.CLARITY_OF_THOUGHT);
                return true;

            case 19812:
                toggle(Prayer.SHARP_EYE);
                return true;

            case 19814:
                toggle(Prayer.MYSTIC_WILL);
                return true;

            case 5612:
                toggle(Prayer.ROCK_SKIN);
                return true;

            case 5613:
                toggle(Prayer.SUPERHUMAN_STRENGTH);
                return true;

            case 5614:
                toggle(Prayer.IMPROVED_REFLEXES);
                return true;

            case 5615:
                toggle(Prayer.RAPID_RESTORE);
                return true;

            case 5616:
                toggle(Prayer.RAPID_HEAL);
                return true;

            case 5617:
                toggle(Prayer.PROTECT_ITEM);
                return true;

            case 19816:
                toggle(Prayer.HAWK_EYE);
                return true;

            case 19818:
                toggle(Prayer.MYSTIC_LORE);
                return true;

            case 5618:
                toggle(Prayer.STEEL_SKIN);
                return true;

            case 5619:
                toggle(Prayer.ULTIMATE_STRENGTH);
                return true;

            case 5620:
                toggle(Prayer.INCREDIBLE_REFLEXES);
                return true;

            case 5621:
                toggle(Prayer.PROTECT_FROM_MAGIC);
                return true;

            case 5622:
                toggle(Prayer.PROTECT_FROM_RANGE);
                return true;

            case 5623:
                toggle(Prayer.PROTECT_FROM_MELEE);
                return true;

            case 19821:
                toggle(Prayer.EAGLE_EYE);
                return true;

            case 19823:
                toggle(Prayer.MYSTIC_MIGHT);
                return true;

            case 683:
                toggle(Prayer.RETRIBUTION);
                return true;

            case 684:
                toggle(Prayer.REDEMPTION);
                return true;

            case 685:
                toggle(Prayer.SMITE);
                return true;

            case 19825:
                toggle(Prayer.CHIVALRY);
                return true;

            case 19827:
                toggle(Prayer.PIETY);
                return true;
        }
        return false;
    }

    /**
     * Gets the associated head icon from an over head type prayer.
     */
    private int determineHeadIcon(Prayer prayer) {
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
        for (final Prayer prayer : Prayer.values()) {
            if (active(prayer)) {
                toggle(prayer, false);
            }
        }
    }

    /**
     * Disables a specific prayer.
     */
    public void disable(Prayer prayer) {
        toggle(prayer, false);
    }

    /**
     * The method that will cause a players prayer level to drain based upon which prayers are activated.
     */
    public void drain() {
        int amount = 0;
        for (final Prayer prayer : Prayer.values()) {
            if (active(prayer)) {
                if (++drain[prayer.ordinal()] >= getAffectedDrainRate(prayer)) {
                    amount++;
                    drain[prayer.ordinal()] = 0;
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
    public double getAffectedDrainRate(Prayer prayer) {
        final int prayerBonus = player.getBonuses()[Equipment.PRAYER];
        return prayer.getDrainRate() * (1 + prayerBonus / 30.0);
    }

    /**
     * The method that toggles a specified prayer.
     */
    public boolean toggle(Prayer prayer) {
        if (!canToggle(prayer)) {
            player.queuePacket(new SetWidgetConfigPacket(prayer.getConfigId(), 0));
            player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:off", 0));
            quickPrayerEnabled = false;
            return false;
        }
        toggle(prayer, !activated[prayer.ordinal()]);
        return true;
    }

    /**
     * The method that toggles a specified prayer.
     */
    public void toggle(Prayer prayer, boolean isEnabled) {
        if (player.isDead() || player.getCurrentHealth() <= 0) {
            player.queuePacket(new SetWidgetConfigPacket(prayer.getConfigId(), 0));
            player.queuePacket(new SetWidgetStringPacket(Math.random() + ":quicks:off", 0));
            quickPrayerEnabled = false;
            return;
        }

        activated[prayer.ordinal()] = isEnabled;
        player.queuePacket(new SetWidgetConfigPacket(prayer.getConfigId(), isEnabled ? 1 : 0));
        if (isEnabled) {
            if (prayer.getDisabledPrayers() != null) {
                for (final Prayer override : prayer.getDisabledPrayers()) {
                    if (override != prayer) {
                        toggle(override, false);
                    }
                }
            }
            final int icon = determineHeadIcon(prayer);
            if (icon != player.getHeadIcon() && icon != -1) {
                player.setHeadIcon(icon);
                player.getUpdateFlags().add(UpdateFlag.APPEARANCE);
            }
        } else if (prayer.getGroup() == PrayerGroup.OVER_HEAD) {
            final int icon = determineHeadIcon(prayer);
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

        for (final Prayer prayer : Prayer.values()) {
            if (!active(prayer) && !enabled) {
                continue;
            }

            if (active(prayer) && enabled && quickPrayers[prayer.ordinal()]) {
                continue;
            }

            toggle(prayer, enabled && quickPrayers[prayer.ordinal()]);

            if (!active(prayer) && quickPrayers[prayer.ordinal()] || !enabled) {
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
