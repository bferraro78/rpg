package rpg;

import java.util.*;
import java.util.HashMap;
import java.lang.Math.*;

public class driver {

	public static Dungeon d;
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		/* Load Libraries */
		BuffLibrary.loadBuffLibrary();
		SkillLibrary.loadSkillLibrary();
		EnemyDictionary.loadEnemies();
		ArmorDictionary.loadArmors();
		WeaponDictionary.loadWeapons();
		ItemDictionary.loadItems();


		System.out.println("\nWelcome To Element");

		/* Load or New CHAR */
		System.out.println("1 to New Chracter, 2 for Load character");
		Scanner s = new Scanner(System.in);
		String newOrLoad = s.next();

		dude mainCharacter = null;
		boolean valid = true;
		while (valid) {
			if (newOrLoad.equals("1")) { // NEW
				System.out.println("NEW CHARACTER CREATION");
				/* Read name and class thru stdin */
				System.out.println("1 for Barbarian, 2 for Wizard, 3 for Rogue");
				String startClass = s.next();

				/* Class Error Check*/
				if (!startClass.equals("1") && !startClass.equals("2") && !startClass.equals("3")) {
					System.out.println("CLASS NOT ADDED. COMING SOON IN EXPANSION");
					System.exit(0);
				}

				System.out.println("Heroes Name: ");
				String startName = s.next();

				/* Load beginnerArmor / weapon */
				mainCharacter = loadBegChar(startClass, startName);
				valid = false;
			} else if (newOrLoad.equals("2")) { // LOAD
				int id = s.nextInt();
				mainCharacter = loadChar(id);
				valid = false;
			} else {
				System.out.println("Enter a damn right number");
				System.exit(0);
			}
		}



		/* LOAD DUNGEON HERE WITH CHARACTERS DUNGEON LEVEL */
		if (newOrLoad.equals("1")) { // new character
			d = new Dungeon(mainCharacter.getDungeonLevel(), 0 ,0);
		} else {

		}
		
		/****************************************DEBUGGGIN********************************************/
		Armor armorOfGods = new Armor(1000, 100, "ARMOR OF THE GAWDS", "Helmet", 100, 100, 100, 100, Element.ARCANE, 100, null);
		Weapon sworfOf1000Truths = new Weapon(1, 1000, 0, "1000 Truths", "MH", null, Element.ARCANE);
		mainCharacter.addToInventory(new Armor(1, 1, "Health BOOST", "Torso", 10000, 0, 0, 0, Element.FIRE, 100, null));
		mainCharacter.equipMH(sworfOf1000Truths);
		mainCharacter.addToInventory(armorOfGods);



		/* START SESSION */
		startGame(mainCharacter);

	}

	/* START GAME */
	protected static void startGame(dude mainCharacter) {

		boolean gameSession = true; // game quit
		while (gameSession) {

// Prints Map
d.printMap();
d.printMapStepsTaken(mainCharacter);

			System.out.println("\n------------------------------------Options Before Entering Dunegon:------------------------------------");
			System.out.print("	1. fight monster");
			System.out.println(" 2. show armor/wep  			W. UP");
			System.out.print("	3. reveal inventory");
			System.out.println(" 4. Stats 			A. LEFT		D. RIGHT");
			System.out.println("	5. Equip/Unequip 9. quit 				S. DOWN");


			String option = s.next();
			int fightCode = 0;
			/** Fight Monster **/
			if (option.equals("1")) {
				fightCode = 2;
			} else
			/** Eqiupment/Weapons **/
			if (option.equals("2")) {
				mainCharacter.printBody();
				continue;
			} else
			/** Inventory **/
			if (option.equals("3")) {
				mainCharacter.revealInventory();
				continue;
			} else 
			/** Show Stats **/
			if (option.equals("4")) {
				System.out.println("\nHERO'S STATS: ");
				System.out.println("CLASS: " + mainCharacter.getClassName());
				System.out.println("NAME: " + mainCharacter.getName());
				System.out.println("DUNGEON LEVEL: " + mainCharacter.getDungeonLevel());
				System.out.println("ELEMENT SPEC: " + mainCharacter.getElementSpec());

				System.out.println("LEVEL: " + mainCharacter.getLevel());
				System.out.println("XP TO NEXT LEVEL : " + mainCharacter.getLevelExp());
				System.out.println("XP: " + mainCharacter.getExp());
				System.out.println("GOLD: " + mainCharacter.getPurse());

				System.out.println("\nSTATS: ");

				System.out.println("	Health: " + mainCharacter.getHealth());
				System.out.println("	Armor: " + mainCharacter.getTotalArmor());
				System.out.println("	Vitality: " + mainCharacter.getVitality());
				System.out.println("	Strength: " + mainCharacter.getStrn());
				System.out.println("	Intelligence: " + mainCharacter.getInti());
				System.out.println("	Dexterity: " + mainCharacter.getDext());
				System.out.println("	Critical Hit Chance: " + mainCharacter.getCritical() + "\n");
				mainCharacter.printOffenseResMap();
				mainCharacter.printDefenseResMap();
				System.out.println();
				mainCharacter.printActiveItems(); 

				System.out.println();
				continue;
			} else 
			if (option.equals("w")) {
				System.out.println("Up");
				fightCode = d.moveHero("up", mainCharacter);
			} else 
			if (option.equals("a")) {
				System.out.println("Left");
				fightCode = d.moveHero("left", mainCharacter);
			} else 
			if (option.equals("s")) {
				System.out.println("Down");
				fightCode = d.moveHero("down", mainCharacter);
			} else 
			if (option.equals("d")) {
				System.out.println("Right");
				fightCode = d.moveHero("right", mainCharacter);
			} else 
			if (option.equals("9")) {
				System.out.println("THANKS FOR PLAYING...SAVING HERO...");
				gameSession = false;
				break;
			} else 
			if (option.equals("5")) {
				mainCharacter.unequip();
			} else {
				continue;
			}


			/** Analyze movement **/
			if (fightCode == 2) {
				Combat.initCombat(mainCharacter);
			} else if (fightCode == 3) {
				System.out.println("Ya win ya bish");
				/* 1. Increase Hero's dungeon level/clear PathWalked array */
				/* 2. Go To Town --TODO */
				/* 3. Generate new dungeon */
				mainCharacter.increaseDungeonLevel();
				d = new Dungeon(mainCharacter.getDungeonLevel(), 0 ,0);
			} 

			

		} //  end GAME

		/* SAVE HERO TO DB */


	}

	


	/*** CHARACTER RETRIEVE OR CREATION ***/
	protected static dude loadChar(int id) {
		dude d = new dude("bean", 1, 0,0,0,0, 0, 0, 0);
		// create dude based on DB loaded option
		// id on character in DB will hold id's for all armor and weps
		
		
		return d;
	}

	protected static dude loadBegChar(String start, String name) {
		dude d = null;
		int startClass = Integer.parseInt(start);
		if (startClass == 1) { 
			d = new barb(name, startClass, 40, 20, 10, 10, 0, 0, 0);
		} else if (startClass == 2) {
			d = new wizard(name, startClass, 40, 10, 20, 10, 0, 0, 0);
		} else if (startClass == 3) {
			d = new rogue(name, startClass, 40, 10, 10, 20, 0, 0, 0);
		}
		
		
		// Create beginning Equipment for all characters
		int[] i = null;
		Armor begTorso = new Armor(1, 10, "Ragged Leather Torso", "Torso", 10, 1, 1, 1, Element. PHYSICAL, 0, i);
		Armor begLegs = new Armor(2, 10, "Ragged Leather Leggings", "Legs", 10, 1, 1, 1, Element. PHYSICAL, 0, i);
		Armor begBoots = new Armor(3, 10, "Ragged Leather Boots", "Boots", 10, 1, 1, 1, Element. PHYSICAL, 0, i);
		Weapon begDaga = new Weapon(1, 10, 0, "Rugged Daga", "OH", i, Element.PHYSICAL);
	



		/* Set Hero's armor/weapons */
		d.equipArmor(begTorso);
		d.equipArmor(begLegs);
		d.equipArmor(begBoots);
		d.equipOH(begDaga);

		return d;
	}
	
}