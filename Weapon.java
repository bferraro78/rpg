package rpg;

// TODO -- Weapons will either be physical, or any of the ELEMENT TYPES
	// element types with attack with that kind of element
	// ex. Rend/Basic attack will now attack with lightning damage

public class Weapon {
	int id;
	int attack;
	String name;
	String type;
	int range;
	int [] skills;
	Element element;
	
	
	Weapon(int id, int attack, int range, String name, String type, int[] skills, Element element) {
		this.id = id;
		this.attack = attack;
		this.range = range; // attack - (attack + range) Ex. 5-10 for "Rugged Daga" ?????A MAYBE?????
		this.name = name;
		this.type = type;
		this.skills = skills;
		this.element = element;
	}

	public String toString() { 
		if (getAttack() == 0) 
			return "";
		return name + " | Attack: " + attack + " | Element: " + element; }
	public String getType() { return type; }
	public int getAttack() { return this.attack; }
	public int getRange() { return this.range; }
	public Element getElement() { return this.element; }


}
