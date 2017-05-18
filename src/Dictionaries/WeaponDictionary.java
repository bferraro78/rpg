package src.Dictionaries;

import java.util.*;

/*** List of Enemy names and their skills. Stats will be randomly generated ***/
public class WeaponDictionary {

	public static ArrayList<Weapon> weaponLib = new ArrayList<Weapon>(); // Skill to Skill Description

	/* ArrayList.add() appends to the end. Monster near the end can be higher level monsters? */
	public static void loadWeapons() {
	   System.out.println("LOADING WEAPONS...");
	}

	public static ArrayList<Weapon> getWeaponDictionary() {
		





        return weaponLib;
	}

	 /* Generate Weapon based on Heroes level/ Hero's stats) */
    public static Weapon generateRandomWeapon(dude mainCharacter) {
    	int size = WeaponDictionary.getWeaponDictionary().size();

    	Random r = new Random();
    	int choice = r.nextInt(size);
    	Weapon chosenWeapon = WeaponDictionary.getWeaponDictionary().get(choice);
    
    	/* generate random stats */
    	int heroPrimaryStat = mainCharacter.getPrimaryStat();
    	int heroLevel = mainCharacter.getLevel();
    	int heroWeaponMH = mainCharacter.getMH().getAttack();
        int heroWeaponOH = mainCharacter.getOH().getAttack();
    	int heroHeatlh = mainCharacter.getHealth();



    	return chosenWeapon;
    }


    public static Weapon findWeapon(String s) {
    	for (int i = 0; i < getWeaponDictionary().size(); i++) {
    		if (getWeaponDictionary().get(i).getName().equals(s)) {
    			return getWeaponDictionary().get(i);
    		}
    	}
    	return null;
    }
}