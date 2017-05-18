package rpg;

import java.util.Random;
import java.util.*;

public class Heal extends Skill { 

    int resourceCost;

    public Heal(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        System.out.println("----Heal Over Time----");
        int heal = -(mainCharacter.getInti()/2);

        mainCharacter.getBuffLibrary().put("healDot", new Buff(heal, 3));

    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        System.out.println("Enemey is Healing...");
        int heal = -(e.getInti()/2);

        e.getBuffLibrary().put("healDot", new Buff(heal, 3));


    }

} 