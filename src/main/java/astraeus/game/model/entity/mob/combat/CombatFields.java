package astraeus.game.model.entity.mob.combat;

import astraeus.game.model.entity.mob.Mob;
import astraeus.game.model.entity.mob.combat.attack.AttackType;
import astraeus.util.Stopwatch;

public class CombatFields {
	
	private final Mob mob;
	private Mob defender;
	private Mob attackedBy;	
	private Mob lastAttacked;
	
	private final Stopwatch combatDelay = new Stopwatch();
	
	private AttackType attackType;
	private AttackStyle attackStyle;
	
	public CombatFields(Mob mob) {
		this.mob = mob;
		combatDelay.reset(Stopwatch.currentTime() - 10_000);
	}

	public Mob getMob() {
		return mob;
	}	
	
	public AttackType getAttackType() {
		return attackType;
	}
	
	public Mob getLastAttacked() {
		return lastAttacked;
	}
	
	public void setLastAttacked(Mob lastAttacked) {
		this.lastAttacked = lastAttacked;
	}

	public void setAttackType(AttackType attackType) {
		this.attackType = attackType;
	}

	public AttackStyle getAttackStyle() {
		return attackStyle;
	}

	public void setAttackStyle(AttackStyle attackStyle) {
		this.attackStyle = attackStyle;
	}
	
	public Mob getDefender() {
		return defender;
	}

	public void setDefender(Mob defender) {
		this.defender = defender;
	}
	
	public Mob getAttackedBy() {
		return attackedBy;
	}
	
	public void setAttackedBy(Mob attackedBy) {
		this.attackedBy = attackedBy;
	}

	public Stopwatch getCombatDelay() {
		return combatDelay;
	}
}
