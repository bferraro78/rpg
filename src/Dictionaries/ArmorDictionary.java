package src.Dictionaries;

import java.util.*;

/*** List of Enemy names and their skills. Stats will be randomly generated ***/
public class ArmorDictionary {

	public static ArrayList<Armor> armorLib = new ArrayList<Armor>(); // Skill to Skill Description

	/* ArrayList.add() appends to the end. Monster near the end can be higher level monsters? */
	public static void loadArmors() {
	   System.out.println("LOADING ARMOR...");
	}

	public static ArrayList<Armor> getArmorDictionary() {
		





        return armorLib;
	}

	 /* Generate Armor based on Heroes level/ Hero's stats) */
    public static Armor generateRandomArmor(dude mainCharacter) {
    	int size = ArmorDictionary.getArmorDictionary().size();

    	Random r = new Random();
    	int choice = r.nextInt(size);
    	Armor chosenArmor = ArmorDictionary.getArmorDictionary().get(choice);
    
    	/* generate random stats */
    	int heroPrimaryStat = mainCharacter.getPrimaryStat();
    	int heroLevel = mainCharacter.getLevel();
    	int heroArmor = mainCharacter.getTotalArmor();
    	int heroHeatlh = mainCharacter.getHealth();



    	return chosenArmor;
    }


    protected static Armor findArmor(String s) {
    	for (int i = 0; i < getArmorDictionary().size(); i++) {
    		if (getArmorDictionary().get(i).getName().equals(s)) {
    			return getArmorDictionary().get(i);
    		}
    	}
    	return null;
    }
}