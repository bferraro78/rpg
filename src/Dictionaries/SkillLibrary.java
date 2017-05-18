package src.Dictionaries;

import java.util.*;

/*** This class hold all toolTips of skills ***/
public class SkillLibrary {

	private static ArrayList<Skill> skillLib = new ArrayList<Skill>(); // Skill to Skill Description


	public static void loadSkillLibrary() {
		System.out.println("LOADING SKILLS...");

		skillLib.add(new Heal("Heal", "Healing over 3 turns", 15, true));
		skillLib.add(new Frenzy("Frenzy", "Chance to swing for double damage", 25, false));
		skillLib.add(new Rend("Rend", "Applys a DOT", 10, false));
		skillLib.add(new Freezecone("FreezeCone", "Take 25% less damage for 3 turns", 30, true));
		skillLib.add(new Backstab("BackStab", "50% crit chance increase", 50, false));
		skillLib.add(new Vanish("Vanish", "Take no damage for two turns", 25, true));
		skillLib.add(new FireBall("Fireball", "Huge fire damage, chance to ignite enemy for 3 turns", 25, true));
		skillLib.add(new BasicAttack("BasicAttack", "A Basic attack", 8, false));
	}

	public static void generateDamage(dude mainCharacter, Enemy e, String heroMoveName, String enemyMoveName, 
		HashMap<Element, Integer> heroElementMap, HashMap<Element, Integer> enemeyElementMap) {

		Skill heroSkill = findSkill(heroMoveName);
		Skill enemySkill = findSkill(enemyMoveName);
		heroSkill.activateHeroMove(mainCharacter, heroElementMap); // this will do all heavy work inside the Abilitie's class
		enemySkill.activateEnemyMove(e, enemeyElementMap, mainCharacter);
	}



	public static ArrayList<Skill> getSkillLib() { return skillLib; }

    public static Skill findSkill(String s) {
    	for (int i = 0; i < getSkillLib().size(); i++) {
    		if (getSkillLib().get(i).getMoveName().equals(s)) {
    			return getSkillLib().get(i);
    		}
    	}
    	return null;
    }

}