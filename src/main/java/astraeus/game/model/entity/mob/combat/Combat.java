package astraeus.game.model.entity.mob.combat;

import astraeus.game.model.entity.mob.Mob;
import astraeus.game.model.entity.mob.player.attr.AttributeKey;

public final class Combat extends CombatFields {
	
	public static final AttributeKey<Boolean> TELE_BLOCK_KEY = AttributeKey.valueOf("teleblock", false);
	
	public Combat(Mob mob) {
		super(mob);
	}
	
	public void execute() {

	}
	
	public void attack(Mob victim) {
		attack(victim, false);
	}

	public void attack(Mob victim, boolean ignoreDistance) {

	}

}
