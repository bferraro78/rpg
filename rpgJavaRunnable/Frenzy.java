package rpg;

import java.util.Random;
import java.util.*;

public class Frenzy extends Skill { 

    int resourceCost;

    public Frenzy(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        System.out.println("----FRENZY----");
        int damage = (mainCharacter.getStrn()+(15*mainCharacter.getLevel()))+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());

        Random rand = new Random();
        int doubleStrike = rand.nextInt(100)+1;
        if(doubleStrike < 10) { damage = damage*2; System.out.println("Double Strike"); }

        Element element = mainCharacter.getMH().getElement();
        elementMap.put(element, damage);

    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        System.out.println("Enemey uses Frenzy...");
        int damage = (e.getStrn()+(5*mainCharacter.getLevel()));

        Random rand = new Random();
        int doubleStrike = rand.nextInt(100)+1;
        if(doubleStrike < 10) { damage = damage*2; }

        Element element = e.getElement();
        elementMap.put(element, damage); 


    }

} 