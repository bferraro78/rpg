package rpg;

import java.util.*;
import java.lang.Math.*;

public class Combat {

	private static Scanner s = new Scanner(System.in);

	/*** Combat ***/
	public static void initCombat(dude mainCharacter) {
		/** Reset player Health/BUFFS/DEBUFFS **/
		mainCharacter.resetHealth();
		mainCharacter.resetLibraries();
		mainCharacter.resetCombatResource();

		/* GENERATE RANOM ENEMY */
		Enemy e = EnemyDictionary.generateRandomEnemy(mainCharacter);

// TODO debug enemy
e.setHealth(1000);


		boolean death = true;

		System.out.println("START COMBAT ");
		System.out.println(mainCharacter.getName() + " VS " + e.getName() + " - " + e.getElement());
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
						int combatCost = 0;
						if (mainCharacter.getSkillSet().get(specifiedAttack).equals("BasicAttack") && mainCharacter.getClassName().equals("Barb")) {
							combatCost = 0;
						} else {
							combatCost = SkillLibrary.findSkill(mainCharacter.getSkillSet().get(specifiedAttack)).getCombatResourceCost(mainCharacter.getResource()); 
						}
						if (mainCharacter.getCombatResource() <  combatCost) {
							System.out.println("Not Enough " + mainCharacter.getResourceName());
						} else {
							validAttack = false;
							/* Spend Resource */
							mainCharacter.useCombatResource(combatCost);
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

			/** Element Damage hasmaps -- stores damage for the turn **/
			HashMap<Element, Integer> heroElementMap = new HashMap<Element, Integer>(); 
			HashMap<Element, Integer> enemyElementMap = new HashMap<Element, Integer>(); 
			/* Load damages to 0 */
			heroElementMap.put(Element.FIRE, 0);
			heroElementMap.put(Element.LIGHTNING, 0);
			heroElementMap.put(Element.COLD, 0);
			heroElementMap.put(Element.POISON, 0);
			heroElementMap.put(Element.ARCANE, 0);
			heroElementMap.put(Element.PHYSICAL, 0);

			enemyElementMap.put(Element.FIRE, 0);
			enemyElementMap.put(Element.LIGHTNING, 0);
			enemyElementMap.put(Element.COLD, 0);
			enemyElementMap.put(Element.POISON, 0);
			enemyElementMap.put(Element.ARCANE, 0);
			enemyElementMap.put(Element.PHYSICAL, 0);

			/* Select Ability, load mainCharacter buffLibrary */
			/** Hero **/
			String heroMoveName = mainCharacter.getSkillSet().get(specifiedAttack);
			/** Enemy **/
			String enemyMoveName = e.selectAttack();

			SkillLibrary.generateDamage(mainCharacter, e, heroMoveName, enemyMoveName, heroElementMap, enemyElementMap);

						// Generate Damage will load hero and enemey 
						//"elementMaps" - hold the damage for that turn.
						// Then passed into the manageBuffs to handle any buffLibrary spells.
						// Buffs libraries will be loaded. 
						// Everything that happens in Move.java/ManageBuffs() that is not a lingering spell
						// will be handled in the splles specifc class

			/** Apply damage/buffs/debuffs for both hero and enemy **/
			heroManageBuffs(mainCharacter, heroElementMap);
			enemyManageBuffs(e, enemyElementMap);


			/*** Damage Reduction Stage -- Contains all abilities that reduce damage ***/
			
			/* Quickly total hero/enemy damage for certain buffs in reduction stage */
			int heroDamage = 0;
			for (int dam : heroElementMap.values()) { heroDamage += dam; }

			int enemyDamage = 0;
			for (int dam : enemyElementMap.values()) { enemyDamage += dam; }


			/** This will reduce damage based on armor/resistances, then return a total damage **/
			/* Hero */
			int totalHeroDamageTaken = heroDamageReduction(mainCharacter, enemyElementMap, heroDamage);

			/* Enemy */
			int totalenemyDamageTaken = enemyDamageReduction(e, heroElementMap, enemyDamage);


 			/** Final Take Damage Stage **/
			/* enemy takes damage */
			System.out.println("Damage Done: " + totalenemyDamageTaken);
			e.setCombatHealth(totalenemyDamageTaken);

			/* Hero takes damage */
			System.out.println("Damage Taken: " + totalHeroDamageTaken);
			mainCharacter.setCombatHealth(totalHeroDamageTaken);


			/** Print Hero Damage Log **/
			for(String currentKey : mainCharacter.getBuffLibrary().keySet()) {
				BuffLibrary.getDescription(currentKey + " ");
				System.out.println(mainCharacter.getBuffLibrary().get(currentKey).toString());
			}

			/** Reduce Durations **/
			/* Hero */
			for(String currentKey : mainCharacter.getBuffLibrary().keySet()) {
					int currDuration = 	mainCharacter.getBuffLibrary().get(currentKey).getDuration();
					int currValue = mainCharacter.getBuffLibrary().get(currentKey).getValue();
					mainCharacter.getBuffLibrary().put(currentKey, new Buff(currValue, currDuration-1));
			}
			/* Enemy */
			for(String currentKey : e.getBuffLibrary().keySet()) {
					int currDuration = 	e.getBuffLibrary().get(currentKey).getDuration();
					int currValue = e.getBuffLibrary().get(currentKey).getValue();
					e.getBuffLibrary().put(currentKey, new Buff(currValue, currDuration-1));
			}



			/** DEATH **/
			if (e.getCombatHealth() <= 0) {
				System.out.println("Enemy Has Died!!!");
				System.out.println("\nLoot: ");

				mainCharacter.increaseExp(e.getExp(mainCharacter)); // INCRESE HERO EXP
				mainCharacter.changePurse(e.goldDrop()); // increase gold amount

				ArrayList<Object> loot = new ArrayList<Object>();
				loot.add(ItemDictionary.generateRandomItem());
				mainCharacter.receiveLoot(loot); // TODO -- RANDOMZIE LOOT WITH ITMES/ARMOR/WEPS

				death = false;
			}

			/* TODO WHAT TO DO IN TIE */
			/* Hero dies, reset active Items */
			if (mainCharacter.getCombatHealth() <= 0)  {
				System.out.println("Hero Has Died :(");
				mainCharacter.resetActiveItems();
				death = false;
			}
	
		} // End combat		
			
	}


	/*** Apply damage/buffs/debuffs/armor/resistances ***/
	/** HERO DAMAGE/BUFFS **/
	public static void heroManageBuffs(dude mainCharacter, HashMap<Element, Integer> elementMap) {

		/** Add up all damage hero does in one turn **/
		int fireDamage = elementMap.get(Element.FIRE);
		int lightningDamage = elementMap.get(Element.LIGHTNING);
		int coldDamage = elementMap.get(Element.COLD);
		int poisonDamage = elementMap.get(Element.POISON);
		int arcaneDamage = elementMap.get(Element.ARCANE);
		int physicalDamage = elementMap.get(Element.PHYSICAL);

		/* Calculate Crit Chance */
		Buff critExtraChance = mainCharacter.getBuffLibrary().get("critDamage");
		int critExtra;
		if (critExtraChance != null) {
			critExtra = critExtraChance.getValue();
			mainCharacter.getBuffLibrary().remove("critDamage");
		} else {
			critExtra = 0;
		}

		boolean crit = false;
		if (critChance(critExtra, mainCharacter)) {
			crit = true;
		}
						// TODO -- MUST CHANGE, will instad read from already loaded element map, for damage from that turn that is not a dot
						if (mainCharacter.getElementSpec() == Element.POISON) {
							/** Handle Poison Passive Here **/
							/* DOT IS 50% of what the damage is over two turns */
							for(String currentKey : mainCharacter.getBuffLibrary().keySet()) {
								int poisonDotDamage = (int)((float)mainCharacter.getBuffLibrary().get(currentKey).getValue()*0.25);
								System.out.println(poisonDotDamage);
								if (poisonDotDamage > 0) { // then this is a damage that is not a DOT
									mainCharacter.getPoisonPassiveDots().add(new Buff(poisonDotDamage, 2));
								}
							}
							/* Carry Out Damage from Poison Passive Dots */
							for (Buff b : mainCharacter.getPoisonPassiveDots()) {		
								BuffLibrary.getDescription("poisonPassiveDot");
								System.out.println(b.getValue());
								poisonDamage += b.getValue();
							}
							/* Decrease Duration */
							for (Buff b : mainCharacter.getPoisonPassiveDots()) {
								b.decreaseDuration();
							}
							/* Remove if nessicary */
							for (int i = 0; i <  mainCharacter.getPoisonPassiveDots().size(); i++) {
								if (mainCharacter.getPoisonPassiveDots().get(i).getDuration() == 0) {
									mainCharacter.getPoisonPassiveDots().remove(mainCharacter.getPoisonPassiveDots().get(i));
								}
							}
						}


		// Rend Dot
		Buff rendDot = mainCharacter.getBuffLibrary().get("rendDot");
		if (rendDot != null) { 
			int rendDamage = rendDot.getValue();
			
			/* Remove */
			if (rendDot.getDuration() == 0) {
				mainCharacter.getBuffLibrary().remove("rendDot");
			} else {
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
			}
		} 

		/** HANDLE LINGERING SPELLS **/

		// Fireball Dot
		Buff fireDot = mainCharacter.getBuffLibrary().get("fireDot");
		if (fireDot != null) { 
			int fireDotDamage = fireDot.getValue();

			/* Remove */
			if (fireDot.getDuration() == 0) {
				mainCharacter.getBuffLibrary().remove("fireDot");
			} else {
				/* Take FIREDOT Damage */
				fireDamage += fireDotDamage;
			}
		} 

		// Heal Dot
		Buff healDot = mainCharacter.getBuffLibrary().get("healDot");
		if (healDot != null) {
			int healDamage = healDot.getValue();
	
			/* Remove if times is up */
			if (healDot.getDuration() == 0) {
				mainCharacter.getBuffLibrary().remove("healDot");
			} else {	
				/* Heal Player*/
				if (crit) {
					mainCharacter.setCombatHealth(healDamage*2);
					System.out.println("Damage Healed: " + Math.abs(healDamage*2));
				} else {
					mainCharacter.setCombatHealth(healDamage);
					System.out.println("Damage Healed: " + Math.abs(healDamage));
				}
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
		System.out.println("PHYSICAL: " + physicalDamage);

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

	}

	/*** Apply damage/buffs/debuffs/armor/resistances ***/
	private static void enemyManageBuffs(Enemy e, HashMap<Element, Integer> elementMap) {
		/** enemy DAMAGE/BUFFS **/
		/** Add up all damage enemy does in one turn **/
		int fireDamage = elementMap.get(Element.FIRE);
		int lightningDamage = elementMap.get(Element.LIGHTNING);
		int coldDamage = elementMap.get(Element.COLD);
		int poisonDamage = elementMap.get(Element.POISON);
		int arcaneDamage = elementMap.get(Element.ARCANE);
		int physicalDamage = elementMap.get(Element.PHYSICAL);


		/** HANDLE LINGERING SPELLS **/

		// Rend Dot
		Buff rendDot = e.getBuffLibrary().get("rendDot");
		if (rendDot != null) { 
			int rendDamage = rendDot.getValue();
			
			/* Remove */
			if (rendDot.getDuration() == 0) {
				e.getBuffLibrary().remove("rendDot");
			} else {
				/* Deal Damage */
				if (e.getElement() == Element.FIRE) {
					fireDamage += rendDamage;
				} else if (e.getElement() == Element.COLD) {
					coldDamage += rendDamage;
				} else if (e.getElement() == Element.ARCANE) {
					arcaneDamage += rendDamage;
				} else if (e.getElement() == Element.POISON) {
					poisonDamage += rendDamage;
				} else if (e.getElement() == Element.LIGHTNING) {
					lightningDamage += rendDamage;
				} else { // NO ELEMENT
					physicalDamage += rendDamage; 
				} 
			}
		} 
		

		// Fireball Dot
		Buff fireDot = e.getBuffLibrary().get("fireDot");
		if (fireDot != null) { 
			int fireDotDamage = fireDot.getValue();

			/* Decrease Duration by 1 or Remvove */
			if (fireDot.getDuration() == 0) {
				e.getBuffLibrary().remove("fireDot");
			} else {
				/* Take FIREDOT Damage */
				fireDamage += fireDotDamage;
			}
		} 

		// Heal Dot
		Buff healDot = e.getBuffLibrary().get("healDot");
		if (healDot != null) {
			int healDamage = healDot.getValue();

			/* Remove */
			if (healDot.getDuration() == 0) {
				e.getBuffLibrary().remove("healDot");
			} else {
				/* Heal Player*/
				e.setCombatHealth(healDamage);
			}
		}

		/* Put elemental damage into hashmap */
		elementMap.put(Element.FIRE, fireDamage);
		elementMap.put(Element.LIGHTNING, lightningDamage);
		elementMap.put(Element.COLD, coldDamage);
		elementMap.put(Element.POISON, poisonDamage);
		elementMap.put(Element.ARCANE, arcaneDamage);
		elementMap.put(Element.PHYSICAL, physicalDamage);
	}


	public static int heroDamageReduction(dude mainCharacter, HashMap<Element, Integer> enemyDamage, int heroDamage) {
		/** Determine Damage Reduction **/	
		int physicalDamage = enemyDamage.get(Element.PHYSICAL);
		int fireDamage = enemyDamage.get(Element.FIRE);
		int coldDamage = enemyDamage.get(Element.COLD);
		int lightningDamage = enemyDamage.get(Element.LIGHTNING);
		int poisonDamage = enemyDamage.get(Element.POISON);
		int arcaneDamage = enemyDamage.get(Element.ARCANE);

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

			/* Remove */
			if (frozen.getDuration() == 0) {
				mainCharacter.getBuffLibrary().remove("frozen");
			} else {
				intFinalDamageReduce = (int)((float)intFinalDamageReduce*(float).25);
			}
		}

		// Vanish Buff, take no damage for 2 turns
		Buff vanish = mainCharacter.getBuffLibrary().get("vanish");
		if (vanish != null) {
				/* Remove */
				if (vanish.getDuration() == 0 || heroDamage != 0) {
					mainCharacter.getBuffLibrary().remove("vanish");
				} else {
					// HERO TAKES NO DAMAGE
					intFinalDamageReduce = 0;
				}	
		}

		if (intFinalDamageReduce < 0) {
			intFinalDamageReduce = 0;
		}
		return intFinalDamageReduce;
	}


	public static int enemyDamageReduction(Enemy e, HashMap<Element, Integer> heroDamage, int enemyDamage) {
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

			/* Remove */
			if (frozen.getDuration() == 0) {
				e.getBuffLibrary().remove("frozen");
			} else {
				intFinalDamageReduce = (int)((float)intFinalDamageReduce*(float).25);
			}
		}

		// Vanish Buff, take no damage for 2 turns
		Buff vanish = e.getBuffLibrary().get("vanish");
		if (vanish != null) {
				/* Remove */
				if (vanish.getDuration() == 0 || enemyDamage != 0) {
					e.getBuffLibrary().remove("vanish");
				} else {
					// Enemy TAKES NO DAMAGE
					intFinalDamageReduce = 0;
				}	
		}

		if (intFinalDamageReduce < 0) {
			intFinalDamageReduce = 0;
		}
		return intFinalDamageReduce;
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
	private static void healthBar(int heroCombatHealth, int heroFull, int heroCombatResource, int enemyCombatHealth, int enemyFull) {
		float fullBars = (float) 50.00;
		float hero = ((float) heroCombatHealth/(float) heroFull)*fullBars;
		float enemy = ((float)enemyCombatHealth/(float)enemyFull)*fullBars;

		/* Print Barz */
		System.out.print("HERO HEALTH: ");
		for (int i = 0; i < hero; i++) 
			System.out.print("=");
		System.out.print(" " + heroCombatHealth);
		System.out.println(" | Resource: " + heroCombatResource);


		System.out.print("ENEMY HEALTH: ");
		for (int i = 0; i < enemy; i++) 
			System.out.print("=");
		System.out.println(" " + enemyCombatHealth);
		System.out.println();
	}	


}