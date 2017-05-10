package rpg;

import java.util.Random;
import java.util.*;
import java.util.HashMap; 

public class Enemey {
	String name;
	int health;
	int combatHealth;
	int strn;
	int inti;
	int dext;
	int armor;
	ArrayList<String> skillSet = new ArrayList<String>();
	HashMap<String, Buff> buffLibrary = new HashMap<String, Buff>();
	int Exp;

	public Enemey(String name, int health, int strn, int inti, int dext, int armor) {
		 	this.name = name;
		 	this.health = health;
		 	this.strn = strn;
		 	this.inti = inti;
		 	this.dext = dext;
		 	this.armor = armor;
		 	this.combatHealth = this.health;
		 	skillSet.add("BasicAttack");
		 	skillSet.add("Fireball");
		 	setExp();
	}

    /** ATTACKS and ABILITIES**/
     public void selectAttack(dude mainCharacter) {
     	/* Enemey has 25 percent chance to use his non BasicAttack ability */
     	Random r = new Random();
     	int ability = r.nextInt(100)+1;
     	int selectedSkill = 0;
     	if (ability < 25) { selectedSkill = 1; }
        EnemeyMove m = new EnemeyMove(skillSet.get(selectedSkill), this, mainCharacter);
        m.moveDamage();
    }

    public HashMap<String, Buff> getBuffLibrary() { return this.buffLibrary; }
    public ArrayList<String> getSkillSet() { return this.skillSet; } 

    protected void setExp() { this.Exp = (getHealth()/3); } 
    protected int getExp() { return this.Exp; }

	protected int getStrn() { return this.strn; }
	protected int getInti() { return this.inti; }
	protected int getDext() { return this.dext; }
    protected int getArmor() { return this.armor; }
    public float getArmorRating() { return (float)getArmor() * (float)0.12; }
    protected int getHealth() { return this.health; }
    protected int getCombatHealth() { return this.combatHealth; }
    protected String getName() { return this.name; } 

    protected int goldDrop() {
    	Random r = new Random();
    	return r.nextInt(100)+1;
    }

	protected void setCombatHealth(int healthReductionOrIncrease) { this.combatHealth = (getCombatHealth()-healthReductionOrIncrease); }



    /* Generate Enemey based on Heroes level/basic attack) */
    // basic attack = (mainCharacter.getLevel()+mainCharacter.getPrimaryStat()+5)+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());
    protected void generateRadomEnemey(dude mainCharacter) {

    }
}


