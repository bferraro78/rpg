package rpg;

public class wizard extends dude {

	private int mana;
	private int combatMana;

 	public wizard(String name, int classID, int health, int strn, int inti, int dext, int startX, int startY, int dungeonLvl) {
	 	super(name, classID, health, strn, inti, dext, startX, startY, dungeonLvl);
	 	/* Tuple: (Name of skill, Resource cost) */
	 	loadSkills();
	 	setResource();
	 	this.combatMana = this.mana;
    }

    public void loadSkills() {
    	addSkill("BasicAttack");
    	addSkill("Fireball");
    	addSkill("Heal");
	 	
	 	if (getLevel() >= 5) {
				addSkill("FreezeCone");
		}	
		// ElementAbilites.getElementalAbilities(this);
    }

	public int getResource() { return mana; }
	public int getCombatResource() { return combatMana; }
	public String getResourceName() { return "Mana"; }

	public void setResource() { this.mana = (100+getInti()); }
	public void increaseResource(int manaIncrease) { this.mana = manaIncrease; }
	public void useCombatResource(int resourceUsed) { this.combatMana = (this.combatMana-resourceUsed); }
	public void regenCombatResource(int resourceGain) { this.combatMana = (this.combatMana+resourceGain); }
	public void resetCombatResource() { this.combatMana = this.mana; }


}
