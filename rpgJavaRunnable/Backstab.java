package rpg;

import java.util.Random;
import java.util.*;

public class Backstab extends Skill { 

    int resourceCost;

    public Backstab(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        System.out.println("----BACKSTAB!!!----");

        int damage = (mainCharacter.getDext()+(15*mainCharacter.getLevel()))+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());
        Element element = mainCharacter.getMH().getElement();
        elementMap.put(element, damage);

        mainCharacter.getBuffLibrary().put("critDamage", new Buff(50, 0));
    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        System.out.println("Enemey uses BackStab...");
        int damage = (e.getDext()+(5*mainCharacter.getLevel()));
        
        Element element = e.getElement();
        elementMap.put(element, damage); 
        
        e.getBuffLibrary().put("critDamage", new Buff(50, 0));
    }

} 