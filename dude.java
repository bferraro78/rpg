package rpg;

import java.util.*;

public class dude implements HeroInterface {

    private final static int MOVELISTMAX = 20;
    private final static int INVENTORYMAX = 50;

    private String name;
    private int classID;
    private int level = 18;
    private int health; // HEALTH IS HEROES TOTAL HEALTH
    private int combatHealth; // COMBAT HEALTH IS USED TO DETERMINE HEALTH IN ONE INSTANCE OF COMBAT
    private int strn;
    private int inti;
    private int dext;
    private int vit;
    private int Exp;
    private int levelExp;
    private int purse;
    private Element elementSpec = Element.PHYSICAL;


    private ArrayList<Skill> skillSet = new ArrayList<Skill>(MOVELISTMAX);
    private HashMap<String, Buff> buffLibrary = new HashMap<String, Buff>();
    private ArrayList<Space> stepsTaken = new ArrayList<Space>();
    private ArrayList<Object> inventory = new ArrayList<Object>();


    /* Resisatnces: Fire, Lightning, Cold, Poison, Arcane  */
    private HashMap<Element, Integer> resistanceDefenseMap = new HashMap<Element, Integer>();
    private HashMap<Element, Integer> resistanceOffenseMap = new HashMap<Element, Integer>();


    /* Armor/Weapons */
    private Weapon mainHand = null;
    private Weapon offHand = null;
    private Armor helm = null;
    private Armor shoulders = null;
    private Armor bracers = null;
    private Armor gloves = null;
    private Armor torso = null;
    private Armor legs = null;
    private Armor boots = null;

    /* Dungeon Info */
    protected int dungeonLvl;
    protected int startX;
    protected int startY;
    


    public dude(String name, int classID, int vit, int strn, int inti, int dext, int startX, int startY, int dungeonLvl) {
        this.name = name;
        this.classID = classID;
        this.vit = vit;
        this.strn = strn;
        this.inti = inti;
        this.dext = dext;
        this.Exp = 0;
        this.levelExp = 100; // TODO -- make this a ratio
        this.startX  = startX;
        this.startY = startY;
        this.dungeonLvl = dungeonLvl;
        this.purse = 0;

        this.resistanceDefenseMap.put(Element.FIRE, 0);
        this.resistanceDefenseMap.put(Element.COLD, 0);
        this.resistanceDefenseMap.put(Element.ARCANE, 0);
        this.resistanceDefenseMap.put(Element.POISON, 0);
        this.resistanceDefenseMap.put(Element.LIGHTNING, 0);

        this.resistanceOffenseMap.put(Element.FIRE, 0);
        this.resistanceOffenseMap.put(Element.COLD, 0);
        this.resistanceOffenseMap.put(Element.ARCANE, 0);
        this.resistanceOffenseMap.put(Element.POISON, 0);
        this.resistanceOffenseMap.put(Element.LIGHTNING, 0);


        /* Set Heatlh Based On Vit */
        resetVariables();

    }

    /*** Dungeon Info ***/
    public int getDungeonLevel() { return this.dungeonLvl; }
    /* Increase dungeon level by one, resets steps taken */
    public void increaseDungeonLevel() { this.dungeonLvl += 1; this.stepsTaken = new ArrayList<Space>(); }

    /** MAP SPACES **/
    public void addStep(Space s) {
        getStepsTaken().add(s);
    }

    public boolean containsSpace(Space s) {
        for (int i = 0; i < getStepsTaken().size(); i++) {
            if (getStepsTaken().get(i).getX() == s.getX() && getStepsTaken().get(i).getY() == s.getY()) {
                return true;
            }
        }
        return false;
    }

    /** ATTACKS and ABILITIES**/
     public void selectAttack(int selectedAttack) {
        Move m = new Move(skillSet.get(selectedAttack).getMoveName(), this);
        m.moveDamage();
    }

    public void addSkill(Skill ability) {
        int count = 0;
        for (Skill s : getSkillSet()) {
            if (s.compareTo(ability)) {
                count++;
            }
        }
        if (count == 0) { getSkillSet().add(ability); } 
    }

    public void toStringSkills() {
        for (int i = 0; i < skillSet.size(); i++) {
            if (skillSet.get(i).getMoveName().equals("BasicAttack")) {
                if (getClassName().equals("Barb")) {
                    System.out.println(i + ". " + "Swing | " + skillSet.get(i).getResourceCost());
                } else if (getClassName().equals("Wizard")) {
                    System.out.println(i + ". " + "Shoot | " + skillSet.get(i).getResourceCost());
                } else { // Rogue
                    System.out.println(i + ". " + "Shank | " + skillSet.get(i).getResourceCost());
                }
            } else {
                System.out.println(i + ". " + skillSet.get(i).toString());
            }
        }
    }

    /** Get Classes name based on ClassID **/
    public String getClassName() {
        if (classID == 1) {
            return "Barb";
        } else if (classID == 2) {
            return "Wizard";
        } else {
            return "Rogue";
        }        
    }

    /** Gold Functions **/
    public int getPurse() { return this.purse; }
    public void changePurse(int gold) { this.purse = (this.purse+gold); }

    /* Get Stats */
    public int getStrn() { return this.strn; }
    public int getInti() { return this.inti; }
    public int getDext() { return this.dext; }
    public int getVitality() { return this.vit; }
    public int getPrimaryStat() { 
         if (getClassName().equals("Barb")) {
            return this.strn;
        } else if (getClassName().equals("Wizard")) {
            return this.inti;
        } else { // Rogue
            return this.dext;
        } 
    }
    public int getHealth() { return this.health; }
    public int getCombatHealth() { return this.combatHealth; }
    public int getLevel() { return this.level; }
    public int getExp() { return this.Exp; }
    public String getName() { return this.name; } 
    public int getCritical() { return  (int)(((float)getPrimaryStat()/(float)((getStrn()+getDext()+getInti())*2))*(100.00)); } // TODO -- REWORK
    public ArrayList<Skill> getSkillSet() { return this.skillSet; }
    public HashMap<Element, Integer> getResistanceDefenseMap() { return this.resistanceDefenseMap; }
    public HashMap<Element, Integer> getResistanceOffenseMap() { return this.resistanceOffenseMap; }
    public ArrayList<Space> getStepsTaken() { return this.stepsTaken; }
    public ArrayList<Object> getInventory() { return this.inventory; }

    /** Set Armor/Weapons **/
    public void equipHelm(Armor a) { this.helm = a; increaseStrn(a.getStrn()); increaseInti(a.getInti()); 
        increaseDext(a.getDext()); increaseVitality(a.getVit()); removeFromInventory(a); }

    public void equipShoulders(Armor a) { this.shoulders = a; increaseStrn(a.getStrn()); increaseInti(a.getInti()); 
        increaseDext(a.getDext()); increaseVitality(a.getVit()); removeFromInventory(a); }

    public void equipBracers(Armor a) { this.bracers = a; increaseStrn(a.getStrn()); increaseInti(a.getInti()); 
        increaseDext(a.getDext()); increaseVitality(a.getVit()); removeFromInventory(a);}

    public void equipGloves(Armor a) { this.gloves = a; increaseStrn(a.getStrn()); increaseInti(a.getInti());
     increaseDext(a.getDext()); increaseVitality(a.getVit()); removeFromInventory(a); }

    public void equipTorso(Armor a) { this.torso = a; increaseStrn(a.getStrn()); increaseInti(a.getInti()); removeFromInventory(a);increaseDext(a.getDext()); increaseVitality(a.getVit()); removeFromInventory(a); }

    public void equipLegs(Armor a) { this.legs = a; increaseStrn(a.getStrn()); increaseInti(a.getInti()); 
        increaseDext(a.getDext()); increaseVitality(a.getVit()); removeFromInventory(a); }

    public void equipBoots(Armor a) { this.boots = a; increaseStrn(a.getStrn()); increaseInti(a.getInti());
     increaseDext(a.getDext()); increaseVitality(a.getVit()); removeFromInventory(a); }
    public void equipMH(Weapon w) { this.mainHand = w; removeFromInventory(w); }
    public void equipOH(Weapon w) { this.offHand = w; removeFromInventory(w); }
    public void equipArmor(Armor a) {
        if (a.getType().equals("Helmet")) {
            equipHelm(a);
        } else if (a.getType().equals("Torso")) {
            equipTorso(a);
        } else if (a.getType().equals("Shoulders")) {
            equipShoulders(a);
        } else if (a.getType().equals("Bracers")) {
            equipBracers(a);
        } else if (a.getType().equals("Gloves")) {
            equipGloves(a);
        } else if (a.getType().equals("Legs")) {
            equipLegs(a);
        } else if (a.getType().equals("Boots")) {
            equipBoots(a);
        } else {
            System.out.println("GEAR DOESN'T EXIST");
        }
        resetVariables();
    }
    public void equipWeapon(Weapon w) { 
        if (w.getType().equals("MH")) {
            equipMH(w);
        } else { // OH
            equipOH(w);
        }
        resetVariables();
    }

    /** Unequip Items **/
     public void unequipHelm() { decreaseStrn(getHelm().getStrn()); decreaseInti(getHelm().getInti()); 
        decreaseDext(getHelm().getDext()); decreaseVitality(getHelm().getVit()); addToInventory(getHelm()); this.helm = null; }

    public void unequipShoulders() { decreaseStrn(getShoulders().getStrn()); decreaseInti(getShoulders().getInti()); 
        decreaseDext(getShoulders().getDext()); decreaseVitality(getShoulders().getVit()); addToInventory(getShoulders()); this.shoulders = null; }

    public void unequipBracers() { decreaseStrn(getBracers().getStrn()); decreaseInti(getBracers().getInti()); 
        decreaseDext(getBracers().getDext()); decreaseVitality(getBracers().getVit()); addToInventory(getBracers()); this.bracers = null; }

    public void unequipGloves() { decreaseStrn(getGloves().getStrn()); decreaseInti(getGloves().getInti()); 
        decreaseDext(getGloves().getDext()); decreaseVitality(getGloves().getVit()); addToInventory(getGloves()); this.gloves = null; }

    public void unequipTorso() { decreaseStrn(getTorso().getStrn()); decreaseInti(getTorso().getInti()); 
        decreaseDext(getTorso().getDext()); decreaseVitality(getTorso().getVit()); addToInventory(getTorso()); this.torso = null; }

    public void unequipLegs() { decreaseStrn(getLegs().getStrn()); decreaseInti(getLegs().getInti()); 
        decreaseDext(getLegs().getDext()); decreaseVitality(getLegs().getVit()); addToInventory(getLegs()); this.legs = null; }

    public void unequipBoots() { decreaseStrn(getBoots().getStrn()); decreaseInti(getBoots().getInti()); 
        decreaseDext(getBoots().getDext()); decreaseVitality(getBoots().getVit()); addToInventory(getBoots()); this.boots = null; }

    public void unequipMH() { addToInventory(getMH()); this.mainHand = null; }
    public void unequipOH() { addToInventory(getOH()); this.offHand = null; }
    /* unequip eneters the Equip/unquip screen for inventory / on Hero selection */
    public void unequip() {
        Scanner s = new Scanner(System.in);

        System.out.println();
        System.out.println("\nSuit up");
        System.out.println("Type in the name of the slot you wish to unequip.\nOr Type in the number of an inventory slot to equip.\n");
        boolean mode = true;
        while (mode) {
            printBody();
            revealInventory();
            String selection = s.next();
            if (selection.equals("Helmet")) {
                if (getHelm().getArmor() != 0) {
                    unequipHelm();
                }
            } else if (selection.equals("Torso")) {
                 if (getTorso().getArmor() != 0) {
                    unequipTorso();
                }
            } else if (selection.equals("Shoulders")) {
                 if (getShoulders().getArmor() != 0) {
                    unequipShoulders();
                }
            } else if (selection.equals("Gloves")) {
                 if (getGloves().getArmor() != 0) {
                    unequipGloves();
                }
            } else if (selection.equals("Bracers")) {
                 if (getBracers().getArmor() != 0) {
                    unequipBracers();
                }
            } else if (selection.equals("Legs")) {
                 if (getLegs().getArmor() != 0) {
                    unequipLegs();
                }
            } else if (selection.equals("Boots")) {
                 if (getBoots().getArmor() != 0) {
                    unequipBoots();
                }
            } else if (selection.equals("MH")) {
                 if (getMH().getAttack() != 0) {
                    unequipMH();
                }
            } else if (selection.equals("OH")) {
                 if (getOH().getAttack() != 0) {
                    unequipOH();
                }
            } else if (selection.equals("quit") || selection.equals("q")) {
                break;
            } else { // I am equiping from inventory
                try {
                    int invSelection = Integer.parseInt(selection);
                    if (getInventory().get(invSelection) != null) {
                        if (getInventory().get(invSelection).getClass().equals(getHelm().getClass())) { // Armor
                            Armor tmp = (Armor) getInventory().get(invSelection);
                            String type = tmp.getType();
                            // 1. get the type
                            // 2. use the getMethod to see if the slot is full
                            // 3. if it is, unequip, then equip the guy from the inventory
                            // 3.5. if the slot is empty, just equip jawn
                            if (type.equals("Helmet")) {
                                if (getHelm().getArmor() != 0) { // SLOT IS FULL
                                    unequipHelm(); // unequip
                                }
                                equipArmor(tmp); // Equip
                            } else if (type.equals("Torso")) {
                                 if (getTorso().getArmor() != 0) {
                                    unequipTorso();
                                }
                                equipArmor(tmp); 
                            } else if (type.equals("Shoulders")) {
                                 if (getShoulders().getArmor() != 0) {
                                    unequipShoulders();
                                }
                                equipArmor(tmp); 
                            } else if (type.equals("Gloves")) {
                                 if (getGloves().getArmor() != 0) {
                                    unequipGloves();
                                }
                                equipArmor(tmp); 
                            } else if (type.equals("Bracers")) {
                                 if (getBracers().getArmor() != 0) {
                                    unequipBracers();
                                }
                                equipArmor(tmp); 
                            } else if (type.equals("Legs")) {
                                 if (getLegs().getArmor() != 0) {
                                    unequipLegs();
                                }
                                equipArmor(tmp); 
                            } else if (type.equals("Boots")) {
                                 if (getBoots().getArmor() != 0) {
                                    unequipBoots();
                                }
                                equipArmor(tmp); 
                            }
                        } else if (getInventory().get(invSelection).getClass().equals(getMH().getClass())) { // Weapon
                            Weapon tmp = (Weapon) getInventory().get(invSelection);
                            String type = tmp.getType();
                            if (type.equals("MH")) { // MH
                                if (getMH().getAttack() != 0) {
                                    unequipMH();
                                }
                                equipWeapon(tmp);
                            } else { // OH
                                if (getOH().getAttack() != 0) {
                                    unequipOH();
                                }
                                equipWeapon(tmp);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Enter an Eqiupment OR a Inventory Number");
                }
            }
        } // End while
        resetVariables();
    }

    public void revealInventory() {
        System.out.println("\n-----------------------------INVENTORY-----------------------------");
        for (int i = 0; i < getInventory().size(); i++) {
            if (getInventory().get(i).getClass().equals(getHelm().getClass())) { // Armor
                Armor tmp = (Armor) getInventory().get(i);
                String type = tmp.getType();
                System.out.println(i + ". " + type + " | " + getInventory().get(i));
            } else if (getInventory().get(i).getClass().equals(getMH().getClass())) { // Wep
                Weapon tmp = (Weapon) getInventory().get(i);
                String type = tmp.getType();
                System.out.println(i + ". " + type + " | " + getInventory().get(i));
            } else {
                System.out.println(i + ". " + getInventory().get(i));
            }
        }
        System.out.println("-------------------------------------------------------------------\n");
    }
    public void addToInventory(Object o) {
        getInventory().add(o);
    }
    public void removeFromInventory(Object o) {
        getInventory().remove(o);
    }



    /* Set Health */
    public void resetHealth() { this.combatHealth = this.health; }
    public void setHealth() { 
        if (getClassName().equals("Barb")) { // Barb gets 2 health : 1 Vit
            this.health = (getVitality()*2);
        } else if (getClassName().equals("Wizard")) {
            this.health = (int)((float)getVitality()*(float)(0.5)); // Wizard gets .5 health : 1 Vit
        } else { // Rogue
            this.health = (int)((float)getVitality()*(float)(0.75)); // Rogue gets .75 health : 1 Vit
        } 
        /* Set Combat Health */
        this.combatHealth = this.health; 
    }
    public void setCombatHealth(int healthReductionOrIncrease) { 
        this.combatHealth = (getCombatHealth()-healthReductionOrIncrease); 
        if (getCombatHealth() > getHealth()) {
            this.combatHealth = getHealth();
        }
    }

    /* Set Stats */
    // Increase
    public void increaseStrn(int strn) { this.strn = getStrn() + strn; }
    public void increaseInti(int inti) { this.inti = getInti() + inti; }
    public void increaseDext(int dext) { this.dext = getDext() + dext; }
    public void increaseVitality(int vit) { this.vit = getVitality() + vit; };
    // Decrease
    public void decreaseStrn(int strn) { this.strn = getStrn() - strn; }
    public void decreaseInti(int inti) { this.inti = getInti() - inti; }
    public void decreaseDext(int dext) { this.dext = getDext() - dext; }
    public void decreaseVitality(int vit) { this.vit = getVitality() - vit; };

    /* TODO - DETERMINES WHAT HAPPEN WHEN WE LEVEL 
     * 1. Increase Stats
     * 2. Lootbox? 
     * 3. Skills? */
    public void increaseLevel() { 
        System.out.println("\nLEVEL THE FUCK UP!!!\n");
        if (getClassName().equals("Barb")) {
            increaseStrn(10);
            increaseInti(5);
            increaseDext(5);
            increaseVitality(10);
        } else if (getClassName().equals("Wizard")) {
            increaseStrn(5);
            increaseInti(10);
            increaseDext(5);
            increaseVitality(10);
        } else { // Dext
            increaseStrn(5);
            increaseInti(5);
            increaseDext(10);
            increaseVitality(10);
        }
        /* 2 times increase the XP it takes to level */
        this.levelExp = (int)((float)this.levelExp*1.5);
        this.level = getLevel()+1; 
    }

    /* Increases XP by paramater. 
     * If level is grown, increase by 1 and carry over Exp */
    public void increaseExp(int xp) { 
        this.Exp = getExp()+xp; 
        while (this.Exp >= getLevelExp()) { // Increase level
            int difference = getExp()-getLevelExp();
            this.Exp = difference;
            increaseLevel();
            /* Set health/resource/Skill Cost after stat changes */
            resetVariables();
            /* SET ELEMENTAL SPEC */
            if (getLevel() == 20) {
                setElementSpec();
            }
        }
    }
    public int getLevelExp() { return this.levelExp; }

    /** ELEMENT SPEC **/
    public Element getElementSpec() { return this.elementSpec; }
    public void setElementSpec() {
        System.out.println("\nThe time has come to advance your element. Choose Wisly...");
        System.out.println("You will get an immediate boot into Damage Increase and Reduction to your chosen element, ");
        System.out.println("as well as next elemental specific abilities\n");
        System.out.println("1. Fire: (Extra Damage, specific spell Dots)");
        System.out.println("2. Cold: (Damage Reduction");
        System.out.println("3. Lightning: Harnese the power of the sky as you fry your opponents into dust (Stuns, chance to attack again)");
        System.out.println("4. Poison: Melt your opponents from the inside (everything does damage, then is also a DOT)");
        System.out.println("5. Arcane: Utilize holy power to smite your enemies (HEALING HOTS?)");

        Scanner s = new Scanner(System.in);
        while (true) {
            try {
                int ele = s.nextInt();
                Element chosenElement = Element.PHYSICAL;
                boolean correctSlection = false;
                if (ele == 1) { // fire
                    chosenElement = Element.FIRE;
                    correctSlection = true;
                } else if (ele == 2) { // cold
                    chosenElement = Element.COLD;
                    correctSlection = true;
                } else if (ele == 3) { // lightning
                    chosenElement = Element.LIGHTNING;
                    correctSlection = true;
                } else if (ele == 4) { // poison
                    chosenElement = Element.POISON;
                    correctSlection = true;
                } else if (ele == 5) { // arcane
                    chosenElement = Element.ARCANE;
                    correctSlection = true;
                }
                if (correctSlection) {
                    this.elementSpec = chosenElement;
                    int ogOffenseRes = getResistanceOffenseMap().get(chosenElement);
                    int ogDefenseRes = getResistanceDefenseMap().get(chosenElement);
                    getResistanceOffenseMap().put(chosenElement, (ogOffenseRes+25));
                    getResistanceDefenseMap().put(chosenElement, (ogDefenseRes+25));
                    break;
                } else {
                    // continue;
                }
            } catch (Exception e) {
                String badData = s.next();
                continue;
            }
        }
    }

    /* Buff Library */
    public HashMap<String,Buff> getBuffLibrary() { return this.buffLibrary; } 
    public void resetBuffLibrary() { this.buffLibrary = new HashMap<String, Buff>(); }
    
    /* Skill Set */
    public void loadSkills() { }

    /* RESOURCES-- OVERIDE BY SUBCLASSES */
    public int getResource() { return 0; }
    public int getCombatResource() { return 0; }
    public String getResourceName() { return ""; }
    public void increaseResource(int resourceIncrease) { }
    public void regenCombatResource(int resourceGain) { }
    public void useCombatResource(int resourceUsed) { }
    public void resetCombatResource() { }
    public void setResource() { }
    public void setMaxCombatResource() { }  // RAGE RESOURCE SPECIFIC

    /** RESET variables that are based on other variables 
     * (Ex. Health, Resources, skill costs, Resistances)
     * Do this when you, 1. grow a level, 2. equip new armor/wep */
    public void resetVariables() {
        setResource(); // located in specific classes
        setHealth();
        loadSkills(); // This is to redo how much the skills cost (When based on stats)
        // TODO resetResistances()
    }


    /** Determine Total Armor and Armor Rating **/
    public int getTotalArmor() {
        int armor = 0;
        int count = 0; // Hidden armor rating
        if (getTorso().getID() != 0) { armor += getTorso().getArmor(); count++; }
        if (getShoulders().getID() != 0) { armor += getShoulders().getArmor(); count++; }
        if (getHelm().getID() != 0) { armor += getHelm().getArmor(); count++; }
        if (getBoots().getID() != 0) { armor += getBoots().getArmor(); count++; }
        if (getLegs().getID() != 0) { armor += getLegs().getArmor(); count++; }
        if (getGloves().getID() != 0) { armor += getGloves().getArmor(); count++; }
        if (getBracers().getID() != 0) { armor += getBracers().getArmor(); count++; }

        return (armor+(count*25));
    }
    public float getArmorRating() {
        return (float)getTotalArmor() * (float)0.12;
    }


    /* Get Armor/Weapons */
    public Weapon getMH() {
        int[] i = null;
        if (mainHand == null) { 

        } else { 
            return this.mainHand; 
        }
        return new Weapon(0, 0, 0, "", "", i, Element.PHYSICAL);
    }

     public Weapon getOH() {
        int[] i = null;
        if (offHand == null) { 

        } else { 
            return this.offHand; 
        }
        return new Weapon(0, 0, 0, "", "", i, Element.PHYSICAL);
    }

    public Armor getTorso() {
        int[] i = null;
        if (torso == null) { 

        } else { 
            return this.torso; 
        }
        return new Armor(0, 0, "", "", 0, 0, 0, 0, i);
    }

     public Armor getShoulders() {
        int[] i = null;
        if (shoulders == null) { 

        } else { 
            return this.shoulders; 
        }
        return new Armor(0, 0, "", "", 0, 0, 0, 0, i);
    }
    
    public Armor getHelm() {
        int[] i = null;
        if (helm == null) { 

        } else { 
            return this.helm; 
        }
        return new Armor(0, 0, "", "", 0, 0, 0, 0, i);
    }
    
    public Armor getBoots() {
        int[] i = null;
        if (boots == null) { 

        } else { 
            return this.boots; 
        }
        return new Armor(0, 0, "", "", 0, 0, 0, 0, i);
    }
    
    public Armor getLegs() {
        int[] i = null;
        if (legs == null) { 

        } else { 
            return this.legs; 
        }
        return new Armor(0, 0, "", "", 0, 0, 0, 0, i);
    }
    
    public Armor getGloves() {
        int[] i = null;
        if (gloves == null) { 

        } else { 
            return this.gloves; 
        }
        return new Armor(0, 0, "", "", 0, 0, 0, 0, i);
    }

    public Armor getBracers() {
        int[] i = null;
        if (bracers == null) { 

        } else { 
            return this.bracers; 
        }
        return new Armor(0, 0, "", "", 0, 0, 0, 0, i);
    }

        /** Show Eqiupment/Weapons **/
    public void printBody() {
        System.out.println("-----------------------------EQUIPED-----------------------------");
        if (getMH() != null) {
            System.out.println("Main Hand: " + getMH().toString());
        } else {
            System.out.println("Main Hand: " );
        }

        if (getOH() != null) {
            System.out.println("Off Hand: " + getOH().toString());
        } else {
            System.out.println("Off Hand: " );
        }

        if (getHelm() != null) {
            System.out.println("Helmet: " + getHelm().toString());
        } else {
            System.out.println("Helmet: ");
        }

        if (getShoulders() != null) {
            System.out.println("Shoulders: " + getShoulders().toString());
        } else {
            System.out.println("Shoulders: ");
        }


        if (getTorso() != null) {
            System.out.println("Torso: " + getTorso().toString());
        } else {
            System.out.println("Torso: ");
        }

        if (getGloves() != null) {
            System.out.println("Gloves: " + getGloves().toString());
        } else {
            System.out.println("Gloves: ");
        }

        if (getBracers() != null) {
            System.out.println("Bracers: " + getBracers().toString());
        } else {
            System.out.println("Bracers: ");
        }

        if (getLegs() != null) {
           System.out.println("Legs: " + getLegs().toString());
        } else {
            System.out.println("Legs: ");
        }

        if (getBoots() != null) {
            System.out.println("Boots: " + getBoots().toString());
        } else {
            System.out.println("Boots: ");
        }
         System.out.println("-----------------------------------------------------------------\n");
    }

    /** Print Resistance Maps **/
    public void printOffenseResMap() {
        System.out.println("Elemental Damage Boost (%): ");
        for (Element res : getResistanceOffenseMap().keySet()) {
            System.out.print(res + ": " + getResistanceOffenseMap().get(res) + " ");
        }
        System.out.println("\n");
    }
    public void printDefenseResMap() {
        System.out.println("Elemental Damage Reduction (%): ");
        for (Element res : getResistanceOffenseMap().keySet()) {
            System.out.print(res + ": " + getResistanceDefenseMap().get(res) + " ");
        }
        System.out.println();
    }


}