package rpg;

import java.util.*;

public interface HeroInterface { 
	final static int MOVELISTMAX = 20;

	void selectAttack(int selectedAttack);
	void addSkill(Skill ability);
	void toStringSkills();

	String getClassName();
	int getStrn();
	int getInti();
	int getDext();
	int getPrimaryStat();
	int getHealth();
	int getCombatHealth();
	int getLevel();
	int getExp();
	String getName();
	int getCritical();
	ArrayList<Skill> getSkillSet();

	void equipHelm(Armor a);
	void equipShoulders(Armor a);
	void equipBracers(Armor a);
	void equipGloves(Armor a);
	void equipTorso(Armor a);
	void equipLegs(Armor a);
	void equipBoots(Armor a);
	void equipMH(Weapon w);
	void equipOH(Weapon w);

	void resetHealth();
	void setCombatHealth(int healthReductionOrIncrease);
	void increaseStrn(int strn);
	void increaseInti(int inti);
	void increaseDext(int dext);
	void increaseLevel();
	void increaseExp(int xp);
	int getLevelExp();

	HashMap<String,Buff> getBuffLibrary();
	void resetBuffLibrary();

	int getResource();
	int getCombatResource();
	String getResourceName();
	void increaseResource(int rageIncrease);
	void regenCombatResource(int resourceGain);
	void useCombatResource(int resourceUsed);
	void resetCombatResource();
	void setMaxCombatResource();

	void loadSkills();
	void resetVariables();

	int getTotalArmor();
	float getArmorRating();

	Weapon getMH();
	Weapon getOH();
	Armor getTorso();
	Armor getShoulders();
	Armor getHelm();
	Armor getBoots();
	Armor getLegs();
	Armor getGloves();
	Armor getBracers();
	void printBody();
}