package src.Dictionaries;

import java.util.*;

/*** List of Enemy names and their skills. Stats will be randomly generated ***/
public class EnemyDictionary {

	public static ArrayList<Enemy> enemyLib = new ArrayList<Enemy>(); // Skill to Skill Description

	/* ArrayList.add() appends to the end. Monster near the end can be higher level monsters? */
	public static void loadEnemies() {
		System.out.println("LOADING MONSTERS...");
		enemyLib.add(new Enemy("Murloc Scvanger", Element.PHYSICAL, new String[]{"BasicAttack", "Frenzy"}));
		enemyLib.add(new Enemy("Murloc Savage", Element.PHYSICAL, new String[]{"BasicAttack", "Rend"}));	
		enemyLib.add(new Enemy("Murloc Fire Wizard", Element.PHYSICAL, new String[]{"BasicAttack", "Fireball"}));
		enemyLib.add(new Enemy("Murloc Cold Wizard", Element.PHYSICAL, new String[]{"BasicAttack", "FreezeCone"}));
		enemyLib.add(new Enemy("Murloc Thief", Element.PHYSICAL, new String[]{"BasicAttack", "BackStab"}));
		enemyLib.add(new Enemy("Murloc Rogue", Element.PHYSICAL, new String[]{"BasicAttack", "Vanish"}));
		enemyLib.add(new Enemy("Murloc Cleric", Element.PHYSICAL, new String[]{"BasicAttack", "Heal"}));
	}

	public static ArrayList<Enemy> getEnemyDictionary() {
		return enemyLib;
	}

	 /* Generate Enemey based on Heroes level/ Hero's stats) */
    // basic attack = (mainCharacter.getLevel()+mainCharacter.getPrimaryStat()+5)+(mainCharacter.getMH().getAttack()+mainCharacter.getOH().getAttack());
    public static Enemy generateRandomEnemy(dude mainCharacter) {
    	int size = EnemyDictionary.getEnemyDictionary().size();

    	Random r = new Random();
    	int choice = r.nextInt(size);
    	Enemy chosenEnemy = EnemyDictionary.getEnemyDictionary().get(choice);
    

    	/* generate random stats */
    	int heroPrimaryStat = mainCharacter.getPrimaryStat();
    	int heroLevel = mainCharacter.getLevel();
    	int heroArmor = mainCharacter.getTotalArmor();
    	int heroHeatlh = mainCharacter.getHealth();

    	chosenEnemy.setStrn(r.nextInt(heroPrimaryStat/2)+heroLevel);
    	chosenEnemy.setInti(r.nextInt(heroPrimaryStat/2)+heroLevel);
    	chosenEnemy.setDext(r.nextInt(heroPrimaryStat/2)+heroLevel);
    	chosenEnemy.setHealth((r.nextInt(heroHeatlh)+heroLevel)+20);
    	chosenEnemy.setArmor(r.nextInt(heroArmor)+heroLevel);

System.out.println("ESTRN: " + chosenEnemy.getStrn());
System.out.println("EINTI " + chosenEnemy.getInti());
System.out.println("EDEXT " + chosenEnemy.getDext());
System.out.println("EHEALTH " + chosenEnemy.getHealth());
System.out.println("EARMOR " + chosenEnemy.getArmor());

    	return chosenEnemy;
    }


    public static Enemy findEnemy(String s) {
    	for (int i = 0; i < getEnemyDictionary().size(); i++) {
    		if (getEnemyDictionary().get(i).getName().equals(s)) {
    			return getEnemyDictionary().get(i);
    		}
    	}
    	return null;
    }


}