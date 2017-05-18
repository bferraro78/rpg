package src.Abilities;

import java.util.*;

public class BasicAttack extends Skill { 

    int resourceCost;

    public BasicAttack(String x, String y, int resourceCost, boolean spell) { 
        super(x, y, spell);
        this.resourceCost = resourceCost;
    } 

    public int getResourceCost() { return resourceCost; }

    public void activateHeroMove(dude mainCharacter, HashMap<Element, Integer> elementMap) {
        if (mainCharacter.getClassName().equals("Barb")) {
            System.out.println("----Swing----");
        } else if (mainCharacter.getClassName().equals("Wizard")) {
            System.out.println("----Shoot----");
        } else { // Rogue
            System.out.println("----Shank----");
        }

        /* Insert Damage */
        int damage = (mainCharacter.getLevel()+mainCharacter.getPrimaryStat()+5)+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());
        Element element = mainCharacter.getMH().getElement();
        elementMap.put(element, damage);

        /** Rage is increased by Basic Attacks, reset if regen past full **/
        if (mainCharacter.getResourceName().equals("Rage")) {
            int regen = (12);
            if ((mainCharacter.getCombatResource()+regen)  <= mainCharacter.getResource()) {
                mainCharacter.regenCombatResource(regen);
            } else {
                /* Set to max rage */
                mainCharacter.setMaxCombatResource();
            }
        } 
    }

    public void activateEnemyMove(Enemy e, HashMap<Element, Integer> elementMap, dude mainCharacter) {
        /* Insert Damage */
        int damage = 2; //(e.getStrn()+e.getInti()+e.getDext());
        Element element = e.getElement();
        elementMap.put(element, damage); 
    }



} 