package src.Abilities;

import java.util.Random;
import java.util.*;

public class Freezecone extends Skill { 

    int resourceCost;

    public Freezecone(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        System.out.println("----Freeze Cone----");
        int damage = (mainCharacter.getInti()+(5*mainCharacter.getLevel()))+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());

        elementMap.put(Element.COLD, damage);
        mainCharacter.getBuffLibrary().put("frozen", new Buff(0, 3));
      

    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        System.out.println("Enemey uses FreezeCone...");
        int damage = (e.getInti()+(5*mainCharacter.getLevel()));

        elementMap.put(Element.COLD, damage);
        e.getBuffLibrary().put("frozen", new Buff(0, 3));
        


    }

} 