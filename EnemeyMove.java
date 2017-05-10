package rpg;

import java.lang.reflect.*;
import java.util.HashMap; 
import java.util.Random;


public class EnemeyMove { 

	private String attack;
	private Enemey e;
	private dude mainCharacter;

	public EnemeyMove(String attack, Enemey e, dude mainCharacter) {
		this.attack = attack;
		this.e = e;
		this.mainCharacter = mainCharacter;
	}
	
    protected String getAttack() { return this.attack; }

    /** Reads attack, carries out ability: RETURN damage of the attack **/
	protected void moveDamage() {
		Integer damage = 0;
		try {
		Method meth = this.getClass().getDeclaredMethod(getAttack());
		damage = (Integer) meth.invoke(this);
		} catch (NoSuchMethodException e) { 
			System.exit(0); 
		} catch (IllegalAccessException e) {
			System.out.println(e); 
			System.exit(0);
		} catch (InvocationTargetException e) {
			System.out.println(e); 
			System.exit(0);
		}
	}



	/*** ABILITIES ***/
    protected void BasicAttack() {
		int damage = (e.getStrn()+e.getInti()+e.getDext());

		e.getBuffLibrary().put("damage", new Buff(damage, 0));
    }

	private void Heal() { // Heal over time
		System.out.println("Enemey is Healing...");
		int heal = -(e.getInti()/2);

		e.getBuffLibrary().put("healDot", new Buff(heal, 3));
	}


	private void Frenzy() { // Chance to do double on top of Crit
		System.out.println("Enemey uses Frenzy...");
		int damage = (e.getStrn()+(5*mainCharacter.getLevel()));

		Random rand = new Random();
		int doubleStrike = rand.nextInt(100)+1;
		if(doubleStrike < 10) { damage = damage*2; }

		e.getBuffLibrary().put("damage", new Buff(damage, 0));
	}

	private void Rend() { 
		System.out.println("Enemey uses Rend...");
		int damage = (e.getStrn()/2)+(5*mainCharacter.getLevel());

		e.getBuffLibrary().put("rendDot", new Buff(damage, 3));
	}
	
	private void Fireball() { // Chance to add DOT
			System.out.println("Enemey uses Fireball...");
		int damage = (e.getInti()+(5*mainCharacter.getLevel()));

		e.getBuffLibrary().put("fireDamage", new Buff(damage, 0));

		/* %20 chance to Fire Dot */
		Random rand = new Random();
		int dot = rand.nextInt(100)+1;
		if(dot < 20) { 
			e.getBuffLibrary().put("fireDot", new Buff((e.getInti()/2), 3));
		}
	}

	private void FreezeCone() { // Chance to reduce target enemey damage for few turns
			System.out.println("Enemey uses FreezeCone...");
		int damage = (e.getInti()+(5*mainCharacter.getLevel()));

		e.getBuffLibrary().put("coldDamage", new Buff(damage, 0));
		e.getBuffLibrary().put("frozen", new Buff(0, 3));
	}

	private void BackStab() { // Huge Crit increase
			System.out.println("Enemey uses BackStab...");
		int damage = (e.getDext()+(5*mainCharacter.getLevel()));

		e.getBuffLibrary().put("critDamage", new Buff(50, 0));
		e.getBuffLibrary().put("damage", new Buff(damage, 0));
	}

	private void Vanish() { // Disappear, take no damage for two turns
		System.out.println("Enemey uses Vanish...");
		e.getBuffLibrary().put("vanish", new Buff(0, 3));
	}






}