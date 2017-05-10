package rpg;

public class rogue extends dude {

	private int energy = 100;
	private int combatEnergy;

 	public rogue(String name, int classID, int health, int strn, int inti, int dext, int startX, int startY, int dungeonLvl) {
	 	super(name, classID, health, strn, inti, dext, startX, startY, dungeonLvl);
	 	/* Tuple: (Name of skill, Resource cost) */
	 	loadSkills();
	 	this.combatEnergy = this.energy;
    }

    public void loadSkills() {
    	addSkill(new Skill("BasicAttack", 5));
	 	addSkill(new Skill("BackStab", 50));
		addSkill(new Skill("Heal", 10));

	 	if (getLevel() >= 5) {
			addSkill(new Skill("Vanish", 25));
		}
		// ElementAbilites.getElementalAbilities(this);
	}

	public int getResource() { return energy; }
	public int getCombatResource() { return combatEnergy; }
	public String getResourceName() { return "Energy"; }

	public void setResource() { }
	public void increaseResource(int energyIncrease) { this.energy = energyIncrease; }
	public void useCombatResource(int resourceUsed) { this.combatEnergy = (this.combatEnergy-resourceUsed); }
	public void regenCombatResource(int resourceGain) { this.combatEnergy = (this.combatEnergy+resourceGain); }
	public void resetCombatResource() { this.combatEnergy = this.energy; }

}
