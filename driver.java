package rpg;

import java.util.*;
import java.util.HashMap;
import java.lang.Math.*;

public class driver {

	public static Dungeon d;
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) throws InputMismatchException {
		// TODO -- apply dungeonssss
			// 1. generate dungeon
			// 2. place hero at start postion right before startGame(), Dunegon holds heros coordinates. It is put in when map is created new or loaded
			// 3. have hero pick a movement or 1-5 options options every time. start combat if nessicary. move combat to a different function

		/* Load Buff Library*/
		BuffLibrary.loadBuffLibrary();
		SkillLibrary.loadSkillLibrary();

		System.out.println("Welcome To Element");

		/* Load or New CHAR */
		System.out.println("1 to New Chracter, 2 for Load character");
		Scanner s = new Scanner(System.in);
		int newOrLoad = s.nextInt();

		dude mainCharacter = null;
		if (newOrLoad == 1) { // NEW
			System.out.println("NEW CHARACTER CREATION");
			/* Read name and class thru stdin */
			System.out.println("1 for Barb, 2 for Zard, 3 for Rogue");
			int startClass = s.nextInt();

			/* Class Error Check*/
			if (startClass != 1 && startClass != 2 && startClass != 3) {
				System.out.println("CLASS NOT ADDED. COMING SOON IN EXPANSION");
				System.exit(0);
			}

			System.out.println("Heroes Name: ");
			String startName = s.next();

			/* Load beginnerArmor / weapon */
			mainCharacter = loadBegChar(startClass, startName);
		} else { // LOAD
			int id = s.nextInt();
			mainCharacter = loadChar(id);
		}

		/* LOAD DUNGEON HERE WITH CHARACTERS DUNGEON LEVEL */
		if (newOrLoad == 1) { // new character
			d = new Dungeon(mainCharacter.getDungeonLevel(), 0 ,0);
		} else {

		}
		
		mainCharacter.addToInventory(new Weapon(1, 5, 0, "Rugged Daga", "OH", null, Element.FIRE));
		mainCharacter.addToInventory(new Armor(1, 1, "Health BOOST", "Torso", 10000, 0, 0, 0, null));



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
			System.out.println("	5. quit 6. Equip/Unequip				S. DOWN");


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
				System.out.println("NAME: \n" + mainCharacter.getName());
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
			if (option.equals("5")) {
				System.out.println("THANKS FOR PLAYING...SAVING HERO...");
				gameSession = false;
				break;
			} else 
			if (option.equals("6")) {
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

	protected static dude loadBegChar(int startClass, String name) {
		dude d = null;
		if (startClass == 1) { 
			d = new barb(name, startClass, 40, 20, 10, 10, 0, 0, 0);
		} else if (startClass == 2) {
			d = new wizard(name, startClass, 40, 10, 20, 10, 0, 0, 0);
		} else if (startClass == 3) {
			d = new rogue(name, startClass, 40, 10, 10, 20, 0, 0, 0);
		}
		
		
		// Create beginning Equipment for all characters
		int[] i = null;
		Armor begTorso = new Armor(1, 10, "Ragged Leather Torso", "Torso", 10, 1, 1, 1, i);
		Armor begLegs = new Armor(2, 10, "Ragged Leather Leggings", "Legs", 10, 1, 1, 1, i);
		Armor begBoots = new Armor(3, 10, "Ragged Leather Boots", "Boots", 10, 1, 1, 1, i);
		Armor armorOfGods = new Armor(1000, 100, "ARMOR OF THE GAWDS", "Helmet", 100, 100, 100, 100, i);
		Weapon begDaga = new Weapon(1, 5, 0, "Rugged Daga", "OH", i, Element.FIRE);
		Weapon sworfOf1000Truths = new Weapon(1, 1000, 0, "1000 Truths", "MH", i, Element.ARCANE);



		/* Set Hero's armor/weapons */
		d.equipArmor(begTorso);
		d.equipArmor(begLegs);
		d.equipArmor(begBoots);
		// d.equipOH(begDaga);
		d.equipMH(sworfOf1000Truths);
		d.equipArmor(armorOfGods);

		return d;
	}
	
}