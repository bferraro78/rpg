package rpg;

public class Armor {
	int id;
	int armor;
	String type;
	String name;
	int vit;
	int strn;
	int dext;
	int inti;
	Element element;
	int resistance;
	int [] skills; // each number in array represents a skill
	
	Armor(int id, int armor, String name, String type, int vit, int strn, int dext, int inti, Element element, int resistance, int[] skills) {
		this.id = id;
		this.armor = armor;
		this.name = name;
		this.type = type;
		this.vit = vit;
		this.strn = strn;
		this.dext = dext;
		this.inti = inti;
		this.element = element;
		this.resistance = resistance;
		this.skills = skills;
	}

	public String toString() {
		if (getArmor() == 0) 
			return "";
		return getName() + " | Armor: " + getArmor() + " | Strn: " + getStrn() + 
			   " | Inti: " + getInti() + " | Dext: " + getDext() + " | Vit: " + getVit() + 
			   " | Element: " + getElement() + "/" + getResistance();
	}
	public int getID() { return this.id = id; }
	public String getName() { return name; }
	public String getType() { return this.type; }
	public int getStrn() { return this.strn; }
	public int getInti() { return this.inti; }
	public int getDext() { return this.dext; }
	public int getVit() { return this.vit; }
	public int getArmor() { return this.armor; }
	public int getResistance() { return this.resistance; }
	public Element getElement() { return this.element; };
	
}