package src.Misc;

import java.util.Random;
import java.util.*;

public abstract class Skill { 
    public String moveName; 
    public String description; 
    public boolean spell;

    public Skill(String moveName, String description, boolean spell) { 
        this.moveName = moveName;
        this.description = description;
        this.spell = spell;
    } 


    public String toString() {
    	return getMoveName() + " | " + getDescription();
    }

    /* Getter */
    public String getMoveName() { return this.moveName; }
    public boolean isSpell() { return this.spell; }
    public String getDescription() { return this.description; }

    /* CompareTo */
    public boolean compareTo(Skill s) {
        if (getMoveName().equals(s.getMoveName())) {
            return true;
        } 
        return false;
    }

    public int getCombatResourceCost(int totalResource) {
        return (int)((float)(getResourceCost()/100.0)*(float)totalResource);
    }

    public abstract void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap);
    public abstract void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter);
    public abstract int getResourceCost();
} 