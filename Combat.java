package rpg;

import java.util.*;
import java.util.HashMap;
import java.lang.Math.*;

public class Combat {

	private static Scanner s = new Scanner(System.in);

	/*** Combat ***/
	public static void initCombat(dude mainCharacter) {
		/** Reset player Health/BUFFS/DEBUFFS **/
		mainCharacter.resetHealth();
		mainCharacter.resetBuffLibrary();
		mainCharacter.resetCombatResource();

		/* TODO GENERATE RANOM ENEMEY */
				Enemey e = new Enemey("BITCH BOIII", 500, 25, 25, 25, 20);
		boolean death = true;

		System.out.println("START COMBAT ");
		System.out.println(mainCharacter.getName() + " VS " + e.getName());
		while (death) {

			/** PRINT HEALTH/RESOURCE BARS **/
			healthBar(mainCharacter.getCombatHealth(), mainCharacter.getHealth(), mainCharacter.getCombatResource(), e.getCombatHealth(), e.getHealth());

			/** Print Move List for specific class **/
			System.out.println("Ability List:");
			if (mainCharacter.getClassName().equals("Barb")) {
				mainCharacter.toStringSkills();
			} else if (mainCharacter.getClassName().equals("Wizard")) {
				mainCharacter.toStringSkills();
			} else { // Rogue
				mainCharacter.toStringSkills();
			}

			/** Checks if attack is valid move or not **/
			/* 1. If move exists */
			/* 2. If there is enough resource */
			int specifiedAttack = 100;
			boolean validAttack = true;
			while (validAttack) {
				try {
					specifiedAttack = s.nextInt();
					if (mainCharacter.getSkillSet().get(specifiedAttack) != null) {
						int cost = mainCharacter.getSkillSet().get(specifiedAttack).getResourceCost();
						if (mainCharacter.getCombatResource() <  cost) {
							System.out.println("Not Enough " + mainCharacter.getResourceName());
						} else {
							validAttack = false;
							/* Spend Resource */
							mainCharacter.useCombatResource(cost);
						}
					}
				} catch (InputMismatchException exception) {
					// Invalid attack, ask again
					String badStr = s.next();
					System.out.println("Select A Correct Ability");
					continue;
				} catch (IndexOutOfBoundsException exception1) {
					System.out.println("Select A Correct Ability");
					continue;
				}
			}


			/*** Damage Gathering Stage -- Contains all abilities that deal damage ***/

			/** Hero **/
			/* Select Ability, load mainCharacter buffLibrary */
			mainCharacter.selectAttack(specifiedAttack);
			/*** Apply damage/buffs/debuffs/armor/resistances for both hero and enemey ***/
			HashMap<Element, Integer> heroDamages = heroManageBuffs(mainCharacter, mainCharacter.getSkillSet().get(specifiedAttack).getMoveName());

			/** Enemey **/
			/* Select Ability, load mainCharacter buffLibrary */
			e.selectAttack(mainCharacter);
			// This will select enemey abilities and return elemental damages
			HashMap<Element, Integer> enemeyDamages = enemeyManageBuffs(e);


			/*** Damage Reduction Stage -- Contains all abilities that reduce damage ***/
			
			/* Quickly total hero/enemey damage for certain buffs in reduction stage */
			int heroDamage = 0;
			for (int dam : heroDamages.values()) { heroDamage += dam; }

			int enemeyDamage = 0;
			for (int dam : enemeyDamages.values()) { enemeyDamage += dam; }


			/** This will reduce damage based on armor/resistances, then return a total damage **/
			/* Hero */
			int totalHeroDamageTaken = heroDamageReduction(mainCharacter, enemeyDamages, heroDamage);

			/* Enemey */
			int totalEnemeyDamageTaken = enemeyDamageReduction(e, heroDamages, enemeyDamage);




 			/** Final Take Damage Stage **/
			/* Enemey takes damage */
			System.out.println("Damage Done: " + totalEnemeyDamageTaken);
			e.setCombatHealth(totalEnemeyDamageTaken);

			/* Hero takes damage */
			System.out.println("Damage Taken: " + totalHeroDamageTaken);
			mainCharacter.setCombatHealth(totalHeroDamageTaken);



			/** Print Hero Damage Log **/
			for(String currentKey : mainCharacter.getBuffLibrary().keySet()) {
				if (currentKey.indexOf("Damage") == -1 && currentKey.indexOf("damage") == -1) {
					BuffLibrary.getDescription(currentKey + " ");
					System.out.println(mainCharacter.getBuffLibrary().get(currentKey).toString());
				}
			}


			if (e.getCombatHealth() <= 0) {
				System.out.println("Enemey Has Died!!!");
				mainCharacter.increaseExp(e.getExp()); // INCRESE HERO EXP
				mainCharacter.changePurse(e.goldDrop()); // increase gold amount

				death = false;
			}

			/* TODO WHAT TO DO IN TIE */
			/* Check if someone is dead */
			if (mainCharacter.getCombatHealth() <= 0)  {
				System.out.println("Hero Has Died :(");
				death = false;
			}

			
	
		} // End combat		
			
	}





	public static int heroDamageReduction(dude mainCharacter, HashMap<Element, Integer> enemeyDamage, int heroDamage) {
		/** Determine Damage Reduction **/	
		int physicalDamage = enemeyDamage.get(Element.PHYSICAL);
		int fireDamage = enemeyDamage.get(Element.FIRE);
		int coldDamage = enemeyDamage.get(Element.COLD);
		int lightningDamage = enemeyDamage.get(Element.LIGHTNING);
		int poisonDamage = enemeyDamage.get(Element.POISON);
		int arcaneDamage = enemeyDamage.get(Element.ARCANE);

		/* Armor Reduction */
		float floatFinalDamage = (float)physicalDamage * (float)(mainCharacter.getArmorRating()/100);
		int intFinalDamageReduce = (int)(physicalDamage - floatFinalDamage);

		/* Resistance damage reduction, this this from resistanceDefenseMap. Use those elemental numbers to decrease that elements damage by percentage */
		fireDamage -= (int)(((float)mainCharacter.getResistanceDefenseMap().get(Element.FIRE)/(float)100.00)*(float)fireDamage);
		lightningDamage -= (int)(((float)mainCharacter.getResistanceDefenseMap().get(Element.LIGHTNING)/(float)100.00)*(float)lightningDamage);
		coldDamage -= (int)(((float)mainCharacter.getResistanceDefenseMap().get(Element.COLD)/(float)100.00)*(float)coldDamage);
		poisonDamage -= (int)(((float)mainCharacter.getResistanceDefenseMap().get(Element.POISON)/(float)100.00)*(float)poisonDamage); 
		arcaneDamage -= (int)(((float)mainCharacter.getResistanceDefenseMap().get(Element.ARCANE)/(float)100.00)*(float)arcaneDamage);
		
		/* Total damage reduction */
		intFinalDamageReduce += (fireDamage + lightningDamage + coldDamage + poisonDamage + arcaneDamage);

		/* Freeze Cone Reeduction */
		Buff frozen = mainCharacter.getBuffLibrary().get("frozen");
		if (frozen != null) {
			int frozenDamageReduce = frozen.getValue();
			int newDuration = frozen.getDuration()-1;

			intFinalDamageReduce = (int)((float)intFinalDamageReduce*(float).25);

			/* Decrease Duration by 1 or Remove */
			if (newDuration == 0) {
				mainCharacter.getBuffLibrary().remove("frozen");
			} else {
				mainCharacter.getBuffLibrary().put("frozen", new Buff(0, newDuration));
			}
		}

		// Vanish Buff, take no damage for 2 turns
		Buff vanish = mainCharacter.getBuffLibrary().get("vanish");
		if (vanish != null) {
						System.out.println("intDam: "+intFinalDamageReduce);
				/* Decrease Duration by 1 or Remove */
				if (vanish.getDuration() == 1 || heroDamage != 0) {
					mainCharacter.getBuffLibrary().remove("vanish");
				} else {
					// HERO TAKES NO DAMAGE
					intFinalDamageReduce = 0;
					mainCharacter.getBuffLibrary().put("vanish", new Buff(0, vanish.getDuration()-1));
				}	
		}

		return intFinalDamageReduce;
	}



	public static int enemeyDamageReduction(Enemey e, HashMap<Element, Integer> heroDamage, int enemeyDamage) {
		/** Determine Damage Reduction **/	
		int physicalDamage = heroDamage.get(Element.PHYSICAL);
		int fireDamage = heroDamage.get(Element.FIRE);
		int coldDamage = heroDamage.get(Element.COLD);
		int lightningDamage = heroDamage.get(Element.LIGHTNING);
		int poisonDamage = heroDamage.get(Element.POISON);
		int arcaneDamage = heroDamage.get(Element.ARCANE);

		/* Armor Reduction */
		float floatFinalDamage = (float)physicalDamage * (float)(e.getArmorRating()/100);
		int intFinalDamageReduce = (int)(physicalDamage - floatFinalDamage);

		/* Total damage reduction */
		intFinalDamageReduce += (fireDamage + lightningDamage + coldDamage + poisonDamage + arcaneDamage);

		/* Freeze Cone Reeduction */
		Buff frozen = e.getBuffLibrary().get("frozen");
		if (frozen != null) {
			int frozenDamageReduce = frozen.getValue();
			int newDuration = frozen.getDuration()-1;

			intFinalDamageReduce = (int)((float)intFinalDamageReduce*(float).25);

			/* Decrease Duration by 1 or Remove */
			if (newDuration == 0) {
				e.getBuffLibrary().remove("frozen");
			} else {
				e.getBuffLibrary().put("frozen", new Buff(0, newDuration));
			}
		}

		// Vanish Buff, take no damage for 2 turns
		Buff vanish = e.getBuffLibrary().get("vanish");
		if (vanish != null) {
				/* Decrease Duration by 1 or Remove */
				if (vanish.getDuration() == 1 || enemeyDamage != 0) {
					e.getBuffLibrary().remove("vanish");
				} else {
					// HERO TAKES NO DAMAGE
					intFinalDamageReduce = 0;
					e.getBuffLibrary().put("vanish", new Buff(0, vanish.getDuration()-1));
				}	
		}

		return intFinalDamageReduce;

	}



	/*** Apply damage/buffs/debuffs/armor/resistances ***/
	public static HashMap<Element, Integer> heroManageBuffs(dude mainCharacter, String specifiedAttackName) {
		HashMap<Element, Integer> elementMap = new HashMap<Element, Integer>(); 
		/** HERO DAMAGE/BUFFS **/
		
		/* Calculate Crit Chance */
		Buff critExtraChance = mainCharacter.getBuffLibrary().get("critDamage");
		int critExtra;
		if (critExtraChance != null) {
			critExtra = critExtraChance.getValue();
		} else {
			critExtra = 0;
		}

		boolean crit = false;
		if (critChance(critExtra, mainCharacter)) {
			crit = true;
		}

		/** Add up all damage hero does in one turn **/
		int heroDamage = 0;
		int fireDamage = 0;
		int lightningDamage = 0;
		int coldDamage = 0;
		int poisonDamage = 0;
		int arcaneDamage = 0;
		int physicalDamage = 0;

		/** PHYSICAL DAMAGE -- Will change based on what elemental weapon you are using **/
		// Damage
		Buff damage = mainCharacter.getBuffLibrary().get("damage");
		if (damage != null) { 
			/* Deal Damage */
			if (mainCharacter.getMH().getElement() == Element.FIRE) {
				fireDamage += damage.getValue();
			} else if (mainCharacter.getMH().getElement() == Element.COLD) {
				coldDamage += damage.getValue();
			} else if (mainCharacter.getMH().getElement() == Element.ARCANE) {
				arcaneDamage += damage.getValue();
			} else if (mainCharacter.getMH().getElement() == Element.POISON) {
				poisonDamage += damage.getValue();
			} else if (mainCharacter.getMH().getElement() == Element.LIGHTNING) {
				lightningDamage += damage.getValue();
			} else { // NO ELEMENT
				physicalDamage += damage.getValue(); 
			}

			/** Rage is increased by Basic Attacks, reset if regen past full **/
			if (mainCharacter.getResourceName().equals("Rage") && specifiedAttackName.equals("BasicAttack")) {
				int regen = (12);
				if ((mainCharacter.getCombatResource()+regen)  <= mainCharacter.getResource()) {
					mainCharacter.regenCombatResource(regen);
				} else {
					/* Set to max rage */
					mainCharacter.setMaxCombatResource();
				}
			}
			mainCharacter.getBuffLibrary().remove("damage");
		}

		// Rend Dot
		Buff rendDot = mainCharacter.getBuffLibrary().get("rendDot");
		if (rendDot != null) { 
			int rendDamage = rendDot.getValue();
			int newDuration = rendDot.getDuration()-1;
			
			/* Deal Rend DOT Damage */
			if (mainCharacter.getMH().getElement() == Element.FIRE) {
				fireDamage += rendDamage;
			} else if (mainCharacter.getMH().getElement() == Element.COLD) {
				coldDamage += rendDamage;
			} else if (mainCharacter.getMH().getElement() == Element.ARCANE) {
				arcaneDamage += rendDamage;
			} else if (mainCharacter.getMH().getElement() == Element.POISON) {
				poisonDamage += rendDamage;
			} else if (mainCharacter.getMH().getElement() == Element.LIGHTNING) {
				lightningDamage += rendDamage;
			} else { // NO ELEMENT
				physicalDamage += rendDamage; 
			}
			
			/* Decrease Duration by 1 or Remove */
			if (newDuration == 0) {
				mainCharacter.getBuffLibrary().remove("rendDot");
			} else {
				mainCharacter.getBuffLibrary().put("rendDot", new Buff(rendDamage, newDuration));
			}
		} 

		/** SPELLS/MOVES THAT ARE ALWAYS THE SAME ELEMETAL TYPE **/
		/* Fire Damage */
		Buff fire = mainCharacter.getBuffLibrary().get("fireDamage");
		if (fire != null) { 

			/* Deal Damage */
			fireDamage += fire.getValue();

			mainCharacter.getBuffLibrary().remove("fireDamage");
		}

		/* Cold Damage */
		Buff cold = mainCharacter.getBuffLibrary().get("coldDamage");
		if (cold != null) { 

			/* Deal Damage */
			coldDamage += cold.getValue();

			mainCharacter.getBuffLibrary().remove("coldDamage");
		}

		/* Arcane Damage */
		Buff arcane = mainCharacter.getBuffLibrary().get("arcaneDamage");
		if (arcane != null) { 

			/* Deal Damage */
			arcaneDamage += arcane.getValue();

			mainCharacter.getBuffLibrary().remove("arcaneDamage");
		}

		/* Lightning Damage */
		Buff lightning = mainCharacter.getBuffLibrary().get("lightningDamage");
		if (lightning != null) { 

			/* Deal Damage */
			lightningDamage += lightning.getValue();

			mainCharacter.getBuffLibrary().remove("lightningDamage");
		}

		/* Poison Damage */
		Buff poison = mainCharacter.getBuffLibrary().get("poisonDamage");
		if (poison != null) { 

			/* Deal Damage */
			poisonDamage += poison.getValue();

			mainCharacter.getBuffLibrary().remove("poisonDamage");
		}

		// Fireball Dot
		Buff fireDot = mainCharacter.getBuffLibrary().get("fireDot");
		if (fireDot != null) { 
			int fireDotDamage = fireDot.getValue();
			int newDuration = fireDot.getDuration()-1;

			/* Take FIREDOT Damage */
			fireDamage += fireDotDamage;

			/* Decrease Duration by 1 or Remvove */
			if (newDuration == 0) {
				mainCharacter.getBuffLibrary().remove("fireDot");
			} else {
				mainCharacter.getBuffLibrary().put("fireDot", new Buff(fireDotDamage, newDuration));
			}
		} 

		// Heal Dot
		Buff healDot = mainCharacter.getBuffLibrary().get("healDot");
		if (healDot != null) {
			int healDamage = healDot.getValue();
			int newDuration = healDot.getDuration()-1;

			/* Heal Player*/
			if (crit) {
				mainCharacter.setCombatHealth(healDamage*2);
				System.out.println("Damage Healed: " + Math.abs(healDamage*2));
			} else {
				mainCharacter.setCombatHealth(healDamage);
				System.out.println("Damage Healed: " + Math.abs(healDamage));
			}

	
			/* Decrease Duration by 1 or Remove */
			if (newDuration == 0) {
				mainCharacter.getBuffLibrary().remove("healDot");
			} else {
				mainCharacter.getBuffLibrary().put("healDot", new Buff(healDamage, newDuration));
			}
		}
		

		/* Resistance damage bonus, this this from resistanceOffenseMap. Use those elemental numbers to increase that elements damage by percentage */
		fireDamage = (fireDamage+(int)(((float)mainCharacter.getResistanceOffenseMap().get(Element.FIRE)/(float)100.00)*(float)fireDamage));
		lightningDamage = (lightningDamage+(int)(((float)mainCharacter.getResistanceOffenseMap().get(Element.LIGHTNING)/(float)100.00)*(float)lightningDamage)); 
		coldDamage = (coldDamage+(int)(((float)mainCharacter.getResistanceOffenseMap().get(Element.COLD)/(float)100.00)*(float)coldDamage)); 
		poisonDamage = (poisonDamage+(int)(((float)mainCharacter.getResistanceOffenseMap().get(Element.POISON)/(float)100.00)*(float)poisonDamage)); 
		arcaneDamage = (arcaneDamage+(int)(((float)mainCharacter.getResistanceOffenseMap().get(Element.ARCANE)/(float)100.00)*(float)arcaneDamage));
		

		
		/* Applys crits */
		if (crit) { 
			fireDamage = fireDamage*2;
			lightningDamage = lightningDamage*2;
			coldDamage = coldDamage*2;
			poisonDamage = poisonDamage*2;
			arcaneDamage = arcaneDamage*2;
			physicalDamage = physicalDamage*2;
		}

		



		/* ADD UP ALL ELEMENTAL/PHYSICAL damage */
		System.out.println("FIRE: " + fireDamage);
		System.out.println("LIGHTNING: " + lightningDamage);
		System.out.println("COLD: " + coldDamage);
		System.out.println("ARCANE: " + arcaneDamage);
		System.out.println("POISON: " + poisonDamage);


		/* Put elemental damage into hashmap */
		elementMap.put(Element.FIRE, fireDamage);
		elementMap.put(Element.LIGHTNING, lightningDamage);
		elementMap.put(Element.COLD, coldDamage);
		elementMap.put(Element.POISON, poisonDamage);
		elementMap.put(Element.ARCANE, arcaneDamage);
		elementMap.put(Element.PHYSICAL, physicalDamage);

		/** Resource Regain **/
		/* INCREASE RESOURCES ONLY IF IT IS BELOW THEIR MAX RESOURCE */
		if (mainCharacter.getResourceName().equals("Mana")) {
			int regen = mainCharacter.getInti()/2;
				if ((mainCharacter.getCombatResource()+regen)  <= mainCharacter.getResource()) {
					mainCharacter.regenCombatResource(regen);
				} else {
					/* Set Resource to Max */
					mainCharacter.resetCombatResource();
				}

		} else if (mainCharacter.getResourceName().equals("Energy")) {
			int regen = 15;
				if ((mainCharacter.getCombatResource()+regen)  <= mainCharacter.getResource()) {
					mainCharacter.regenCombatResource(regen);
				} else {
					/* Set Resource to Max */
					mainCharacter.resetCombatResource();
				}
		}

		return elementMap;
	}



	/*** Apply damage/buffs/debuffs/armor/resistances ***/
	private static HashMap<Element, Integer> enemeyManageBuffs(Enemey e) {
		HashMap<Element, Integer> elementMap = new HashMap<Element, Integer>(); 
		/** Enemey DAMAGE/BUFFS **/
		/** Add up all damage enemey does in one turn **/
		int heroDamage = 0;
		int fireDamage = 0;
		int lightningDamage = 0;
		int coldDamage = 0;
		int poisonDamage = 0;
		int arcaneDamage = 0;
		int physicalDamage = 0;

		/** PHYSICAL DAMAGE -- Will change based on what elemental weapon you are using **/
		// Damage
		Buff damage = e.getBuffLibrary().get("damage");
		if (damage != null) { 
			
			/* Deal Damage */
			physicalDamage += damage.getValue(); 

			e.getBuffLibrary().remove("damage");
		}

		// Rend Dot
		Buff rendDot = e.getBuffLibrary().get("rendDot");
		if (rendDot != null) { 
			int rendDamage = rendDot.getValue();
			int newDuration = rendDot.getDuration()-1;
			
			physicalDamage += rendDamage; 
			
			/* Decrease Duration by 1 or Remove */
			if (newDuration == 0) {
				e.getBuffLibrary().remove("rendDot");
			} else {
				e.getBuffLibrary().put("rendDot", new Buff(rendDamage, newDuration));
			}
		} 

		/** SPELLS/MOVES THAT ARE ALWAYS THE SAME ELEMETAL TYPE **/
		/* Fire Damage */
		Buff fire = e.getBuffLibrary().get("fireDamage");
		if (fire != null) { 

			/* Deal Damage */
			fireDamage += fire.getValue();

			e.getBuffLibrary().remove("fireDamage");
		}

		/* Cold Damage */
		Buff cold = e.getBuffLibrary().get("coldDamage");
		if (cold != null) { 

			/* Deal Damage */
			coldDamage += cold.getValue();

			e.getBuffLibrary().remove("coldDamage");
		}

		/* Arcane Damage */
		Buff arcane = e.getBuffLibrary().get("arcaneDamage");
		if (arcane != null) { 

			/* Deal Damage */
			arcaneDamage += arcane.getValue();

			e.getBuffLibrary().remove("arcaneDamage");
		}

		/* Lightning Damage */
		Buff lightning = e.getBuffLibrary().get("lightningDamage");
		if (lightning != null) { 

			/* Deal Damage */
			lightningDamage += lightning.getValue();

			e.getBuffLibrary().remove("lightningDamage");
		}

		/* Poison Damage */
		Buff poison = e.getBuffLibrary().get("poisonDamage");
		if (poison != null) { 

			/* Deal Damage */
			poisonDamage += poison.getValue();

			e.getBuffLibrary().remove("poisonDamage");
		}

		// Fireball Dot
		Buff fireDot = e.getBuffLibrary().get("fireDot");
		if (fireDot != null) { 
			int fireDotDamage = fireDot.getValue();
			int newDuration = fireDot.getDuration()-1;

			/* Take FIREDOT Damage */
			fireDamage += fireDotDamage;

			/* Decrease Duration by 1 or Remvove */
			if (newDuration == 0) {
				e.getBuffLibrary().remove("fireDot");
			} else {
				e.getBuffLibrary().put("fireDot", new Buff(fireDotDamage, newDuration));
			}
		} 

		// Heal Dot
		Buff healDot = e.getBuffLibrary().get("healDot");
		if (healDot != null) {
			int healDamage = healDot.getValue();
			int newDuration = healDot.getDuration()-1;

			/* Heal Player*/
			e.setCombatHealth(healDamage);

			/* Decrease Duration by 1 or Remove */
			if (newDuration == 0) {
				e.getBuffLibrary().remove("healDot");
			} else {
				e.getBuffLibrary().put("healDot", new Buff(healDamage, newDuration));
			}
		}


		/* Put elemental damage into hashmap */
		elementMap.put(Element.FIRE, fireDamage);
		elementMap.put(Element.LIGHTNING, lightningDamage);
		elementMap.put(Element.COLD, coldDamage);
		elementMap.put(Element.POISON, poisonDamage);
		elementMap.put(Element.ARCANE, arcaneDamage);
		elementMap.put(Element.PHYSICAL, physicalDamage);


		return elementMap;
	}




	/** CRIT Calculations **/	 
	private static boolean critChance(int extraChance, dude mainCharacter) {
		Random rand = new Random();
		int critChance = rand.nextInt(100) + 1;
		if (critChance >= 1 && critChance <= (mainCharacter.getCritical()+extraChance)) {
			System.out.println("~~~~~~~~~~~~CRITICAL HIT~~~~~~~~~~~~");
			return true;
		}
		return false;
	} 



	/** Write Health Bars **/
	private static void healthBar(int heroCombatHealth, int heroFull, int heroCombatResource, int enemeyCombatHealth, int enemeyFull) {
		float fullBars = (float) 50.00;
		float hero = ((float) heroCombatHealth/(float) heroFull)*fullBars;
		float enemey = ((float)enemeyCombatHealth/(float)enemeyFull)*fullBars;

		/* Print Barz */
		System.out.print("HERO HEALTH: ");
		for (int i = 0; i < hero; i++) 
			System.out.print("=");
		System.out.print(" " + heroCombatHealth);
		System.out.println(" | Resource: " + heroCombatResource);


		System.out.print("ENEMEY HEALTH: ");
		for (int i = 0; i < enemey; i++) 
			System.out.print("=");
		System.out.println(" " + enemeyCombatHealth);
		System.out.println();
	}	


}