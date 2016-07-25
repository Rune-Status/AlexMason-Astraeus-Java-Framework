package plugin.combat.dmg;

/**
 * Represents the enumerated types of damages that can be performed.
 *
 * @author Vult-R
 */
public enum DamageType {
    NONE(-1),
    MELEE(0),
    RANGED(1),
    MAGIC(2),
    DEFLECT(3),
    CANNON(4);

    private int id;

    private DamageType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
