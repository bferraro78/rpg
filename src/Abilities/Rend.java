package src.Abilities;

import java.util.Random;
import java.util.*;

public class Rend extends Skill { 

    int resourceCost;

    public Rend(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        System.out.println("----Rend----");
        int damage = (mainCharacter.getStrn()/2)+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());

        mainCharacter.getBuffLibrary().put("rendDot", new Buff(damage, 3));

    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        System.out.println("Enemey uses Rend...");
        int damage = (e.getStrn()/2)+(3*mainCharacter.getLevel());

        e.getBuffLibrary().put("rendDot", new Buff(damage, 3));        


    }

} 