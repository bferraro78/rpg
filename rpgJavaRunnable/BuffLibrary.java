package rpg;

import java.util.HashMap;

/*** This class hold all of the combat descriptions of a particular buff, debuff, or skill ***/
public class BuffLibrary {

	private static HashMap<String, String> buffLib = new HashMap<String, String>();


	public static void loadBuffLibrary() {
		System.out.println("LOADING BUFFS...");
		buffLib.put("frozen", "Frozen: Take 25% Less Damage ");
		buffLib.put("fireDot", "Burning: Take Dot Damage ");
		buffLib.put("healDot", "Heal: Heals Over Time ");
		buffLib.put("rendDot", "Rend: Enemey Gushing Blood ");
		buffLib.put("vanish", "Vanish: Cannot Find You... ");

		buffLib.put("poisonPassiveDot", "Poison Passive Dot: ");

	}

	public static void getDescription(String s) { System.out.print(buffLib.get(s.trim())); }

}