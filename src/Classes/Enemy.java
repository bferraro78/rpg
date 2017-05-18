package src.Classes;

import java.util.*;
import java.util.HashMap; 

public class Enemy {
	String name;
	int health;
	int combatHealth;
	int strn;
	int inti;
	int dext;
	int armor;
	Element element;
	ArrayList<String> skillSet = new ArrayList<String>();
	HashMap<String, Buff> buffLibrary = new HashMap<String, Buff>();
	int Exp;

	public Enemy(String name, Element e, String[] skills) {
		 	this.name = name;
		 	this.element = e;
		 	setSkills(skills);
	}

    /** ATTACKS and ABILITIES**/
     public String selectAttack() {
     	/* Enemey has 25 percent chance to use his non BasicAttack ability */
     	Random r = new Random();
     	int ability = r.nextInt(100)+1;
     	int selectedSkill = 0;
     	if (ability < 25) {
     		selectedSkill = r.nextInt(getSkillSet().size()-1)+1;
     	}

       	return getSkillSet().get(selectedSkill);
    }

    public void setSkills(String[] skills) {
    	for (int i = 0; i < skills.length; i++) {
    		getSkillSet().add(skills[i]);
    	}
    }

    public HashMap<String, Buff> getBuffLibrary() { return this.buffLibrary; }
    public ArrayList<String> getSkillSet() { return this.skillSet; } 

    public int goldDrop() {
    	Random r = new Random();
    	int gold = r.nextInt(100)+1;
    	System.out.println("	Gold: " + gold);
    	return gold;
    }

    /* Stat Getters */
	public int getStrn() { return this.strn; }
	public int getInti() { return this.inti; }
	public int getDext() { return this.dext; }
    public int getArmor() { return this.armor; }
    public int getExp(dude mainCharacter) { 
    	/* Check for XPBoost Item */
    	for (Item i : mainCharacter.getActiveItems()) {
    		if (i.getType().contains("XPBoost")) {
    			this.Exp = (int)(this.Exp+(((float)i.getPotency()/100.0)*(float)this.Exp));
    		}
    	}
    	System.out.println("	XP: " + this.Exp);
    	return this.Exp; 
    }
    
    public float getArmorRating() { return (float)getArmor() * (float)0.12; }
    public int getHealth() { return this.health; }
    public int getCombatHealth() { return this.combatHealth; }
    public String getName() { return this.name; } 
    public Element getElement() { return this.element; }

    /* Stat Setters */
	public void setStrn(int strn) { this.strn = strn; }
	public void setInti(int inti) { this.inti = inti; }
	public void setDext(int dext) { this.dext = dext; }
	public void setExp() { this.Exp = (getHealth()/3); } 
	public void setArmor(int armor) { this.armor = armor; }
	public void setHealth(int health) {
		this.health = health;	
		this.combatHealth = this.health;
		setExp(); /* Exp is based on health */
	}
    public void setCombatHealth(int healthReductionOrIncrease) { 
        this.combatHealth = (getCombatHealth()-healthReductionOrIncrease); 
        if (getCombatHealth() > getHealth()) {
            this.combatHealth = getHealth();
        }
    }


}


