package astraeus.game.model.entity.mob.player.skill;

/**
 * The skill-related requirement.
 * 
 * @author SeVen
 */
public class SkillRequirement {

	/**
	 * The level that is required.
	 */
	private final int level;

	/**
	 * The skill that is required.
	 */
	private final SkillData skill;

	/**
	 * Creates a new {@link SkillRequirement}.
	 * 
	 * @param level
	 *            The level required.
	 * 
	 * @param skill
	 *            The skill required.
	 */
	public SkillRequirement(int level, SkillData skill) {
		this.level = level;
		this.skill = skill;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the skill
	 */
	public SkillData getSkill() {
		return skill;
	}

}

