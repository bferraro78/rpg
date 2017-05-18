package rpg;

import java.util.Random;
import java.util.*;

public class FireBall extends Skill { 

    int resourceCost;

    public FireBall(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        System.out.println("----Fireball BOOOOOM!!!----");
        int damage = (mainCharacter.getInti()+(10*mainCharacter.getLevel()))+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());

        elementMap.put(Element.FIRE, damage);

        /* %20 chance to Fire Dot */
        Random rand = new Random();
        int dot = rand.nextInt(100)+1;
        if(dot < 20) { 
            System.out.println("Enemey In On Fire! "); 
            mainCharacter.getBuffLibrary().put("fireDot", new Buff((mainCharacter.getInti()/2), 3));
        }

    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        System.out.println("Enemey uses Fireball...");
        int damage = (e.getInti()+(2*mainCharacter.getLevel()));

        elementMap.put(Element.FIRE, damage);

        /* %20 chance to Fire Dot */
        Random rand = new Random();
        int dot = rand.nextInt(100)+1;
        if(dot < 20) { 
            e.getBuffLibrary().put("fireDot", new Buff((mainCharacter.getInti()/2), 3));
        }

    }

} 