package plugin.combat.type;

/**
 * Represents the enumerated types of combat
 *
 * @author Vult-R
 */
public enum CombatType {

    MELEE(4),
    RANGE(4),
    MAGIC(2),
    ROAR(-1),
    DRAGONFIRE(-1),
    SHOCK_DRAGONFIRE(-1),
    FREEZE_DRAGONFIRE(-1),
    POISON_DRAGONFIRE(-1);

    private int rate;

    private CombatType(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

}
