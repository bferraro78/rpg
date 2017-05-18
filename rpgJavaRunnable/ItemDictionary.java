package rpg;

import java.util.*;

/*** List of Enemy names and their skills. Stats will be randomly generated ***/
public class ItemDictionary {

	public static ArrayList<Item> itemLib = new ArrayList<Item>(); // Skill to Skill Description

	/* ArrayList.add() appends to the end. Monster near the end can be higher level monsters? */
	public static void loadItems() {
		System.out.println("LOADING ITEMS...");
        itemLib.add(new Firestone("Boost Fire Damage and Resistance ", Element.FIRE));
        itemLib.add(new Lightningstone("Boost Lightning Damage and Resistance ", Element.LIGHTNING));
        itemLib.add(new Poisonstone("Boost Poison Damage and Resistance ", Element.POISON));
        itemLib.add(new Coldstone("Boost Cold Damage and Resistance ", Element.COLD));
        itemLib.add(new Arcanestone("Boost Arcane Damage and Resistance ", Element.ARCANE));
        itemLib.add(new XPBoost("Boost XP "));
	}

	public static ArrayList<Item> getItemDictionary() {
		return itemLib;
	}

	 /* Generate Random Item */
    protected static Item generateRandomItem() {
        int size = getItemDictionary().size();

        Random r = new Random();
        int choice = r.nextInt(size);
        Item chosenItem = ItemDictionary.getItemDictionary().get(choice);

    	return chosenItem;
    }


    protected static Item findItem(String s) {
    	for (int i = 0; i < getItemDictionary().size(); i++) {
    		if (getItemDictionary().get(i).getType().equals(s)) {
    			return getItemDictionary().get(i);
    		}
    	}
    	return null;
    }


}