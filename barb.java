package rpg;

public class barb extends dude {

	private int rage = 100;
	private int combatRage;

 	public barb(String name, int classID, int health, int strn, int inti, int dext, int startX, int startY, int dungeonLvl) {
	 	super(name, classID, health, strn, inti, dext, startX, startY, dungeonLvl);
	 	/* Tuple: (Name of skill, Resource cost) */
	 	loadSkills();
	 	this.combatRage = 0;
    }

    public void loadSkills() {
    	addSkill(new Skill("BasicAttack", 0));
    	addSkill(new Skill("Frenzy", 20));
    	addSkill(new Skill("Heal", 10));

	 	if (getLevel() >= 5) {
			addSkill(new Skill("Rend", 15));
		}	
		// ElementAbilites.getElementalAbilities(this);
	}

	public int getResource() { return rage; }
	public int getCombatResource() { return combatRage; }
	public String getResourceName() { return "Rage"; }

	public void setResource() { }
	public void increaseResource(int rageIncrease) { this.rage = rageIncrease; }
	public void useCombatResource(int resourceUsed) { this.combatRage = (this.combatRage-resourceUsed); }
	public void regenCombatResource(int resourceGain) { this.combatRage = (this.combatRage+resourceGain); }
	public void resetCombatResource() { this.combatRage = 0; }
	public void setMaxCombatResource() { this.combatRage = this.rage; }



}
