package rpg;

import java.util.Random;
import java.util.*;
 /* Disappear, take no damage for two turns */
public class Vanish extends Skill { 

    int resourceCost;

    public Vanish(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        System.out.println("----Vanish...----");
        mainCharacter.getBuffLibrary().put("vanish", new Buff(0, 2));

    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        System.out.println("Enemey uses Vanish...");
        e.getBuffLibrary().put("vanish", new Buff(0, 2));
    }

} 