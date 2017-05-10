package rpg;

import java.util.Random;

public class Dungeon { 

	//private String name;
	public static Space [][] map;
	private int dungeonLevel;
	private int size; // Based on dungeon level
	private Space start = new Space(0, 0);
	private int heroX;
	private int heroY;
	private Space end;
	private Random spaceGenerator = new Random();

	public Dungeon(int dungeonLevel, int herox, int heroy) {
		this.dungeonLevel = dungeonLevel;
		this.size = dungeonLevel + 10;
		this.heroX = heroX;
		this.heroY = heroY;
		this.map = generateRandomDungeon(getStart().getX(), getStart().getY());
		map[heroX][heroY] = new Space(0, 0); // HERO IS A SPACE WITH 0,0
	}



	protected int moveHero(String dir, dude mainCharacter) {
		int newX = heroX;
		int newY = heroY;
		int code;

		if (dir.equals("up")) {
			newX--;
		} else if (dir.equals("left")) {
			newY--;
		} else if (dir.equals("right")) {
			newY++;
		} else { // dir.equals("down")
			newX++;
		}

		/* Check if a Path or a Wall */
		if (!(newX >= 0 && newX < getSize() && newY >= 0 && newY < getSize()) || (getMap()[newX][newY] == null) ) {
			/* Fight monster, do nothing with hero */
			// ADD SPOT TO ALREADY TRAVELED?
			System.out.println("FIGHTT!!!!!");
			code = 2;
		} else if (newX == getEnd().getX() && newY == getEnd().getY()) { // YOU WIIN
			System.out.println("VICTORY SCREEEECH!!");
			code = 3;
		} else {
			/* Move hero */
			getMap()[newX][newY] = new Space(0, 0);
			getMap()[heroX][heroY] = new Space(100, 100);
			this.heroX = newX;
			this.heroY = newY;
			code = 1;
		}
		/* Add to steps already taken */
		mainCharacter.addStep(new Space(newX, newY));
		return code;
	}

	protected void getHeroLoc() {
		System.out.print("X: " + this.heroX);
		System.out.println("Y: " + this.heroY);
	}

	private Space[][] generateRandomDungeon (int x, int y) {

		Space[][] m = new Space[size][size];
		
		/* Set Start **/
		m[getStart().getX()][getStart().getY()] =  new Space(100,100);

		while (x != getSize()-1 && y != getSize()-1) {
			boolean valid = true; 
			int count = 0;
			while (valid) {

				count++;
				int direction = spaceGenerator.nextInt(100)+1;

				if (direction >= 0 && direction < 25) { // Create Space UP Direction
					if (x-1 >= 0 && x-1 < getSize() && y >= 0 && y < getSize() && m[x-1][y] == null) { // Check bounds, if not in bounds, try another move
						m[x-1][y] = new Space(100, 100);
						x = x-1;
						valid = false;
					}
				} 

				if (direction >= 26 && direction < 50) { // Create Space RIGHT Direction
					if (x >= 0 && x < getSize() && y+1 >= 0 && y+1 < getSize() && m[x][y+1] == null) { // Check bounds, if not in bounds, try another move
						m[x][y+1] = new Space(100, 100);
						y = y+1;
						valid = false;
					}
				} 

				if (direction >= 51 && direction < 75) { // Create Space LEFT Direction
					if (x >= 0 && x < getSize() && y-1 >= 0 && y-1 < getSize() && m[x][y-1] == null) { // Check bounds, if not in bounds, try another move
						m[x][y-1] = new Space(100, 100);
						y = y-1;
						valid = false;
					}
				} 

				if (direction >= 76 && direction <= 100) { // Create Space DOWN Direction
					if (x+1 >= 0 && x+1 < getSize() && y >= 0 && y < getSize() && m[x+1][y] == null) { // Check bounds, if not in bounds, try another move
						m[x+1][y] = new Space(100, 100);
						x = x+1;
						valid = false;
					}
				}

				if (valid && count > 20) { // generator is stuck
					if (x+1 >= 0 && x+1 < getSize() && y >= 0 && y < getSize()) { m[x+1][y]=null; 
					} else if (x-1 >= 0 && x-1 < getSize() && y >= 0 && y < getSize()) { m[x-1][y]=null; 
					} else if (x >= 0 && x < getSize() && y+1 >= 0 && y+1 < getSize()) { m[x][y+1]=null; 
					} else { m[x][y-1]=null; }
				}

			} // END WHILE

			/** Set END **/
			this.end = new Space(x,y);
		}	
		return m;
	}

	public void printMap() {
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				if (getMap()[x][y] == null) { // No spot, fight monster
					System.out.print(" N ");
				} else if (x == getEnd().getX() && y == getEnd().getY()) { // END
					System.out.print(" E ");
				} else 	if (getMap()[x][y].getX() == 0) { // Hero loc
					System.out.print(" H ");
				} else if (x == getStart().getX() && y == getStart().getY()) {
					System.out.print(" S "); // Start
				} else {
					System.out.print(" P "); // Path
				}

			}
				System.out.println();
		}
		System.out.println();
	}

	public void printMapStepsTaken(dude mainCharacter) {
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				if (mainCharacter.containsSpace(new Space(x, y)) || (x == 0 && y == 0)) {
					if (getMap()[x][y] == null) { // No spot, fight monster
						System.out.print(" N ");
					} else if (x == getEnd().getX() && y == getEnd().getY()) { // END
						System.out.print(" E ");
					} else 	if (getMap()[x][y].getX() == 0) { // Hero loc
						System.out.print(" H ");
					} else if (x == getStart().getX() && y == getStart().getY()) {
						System.out.print(" S "); // Start
					} else {
						System.out.print(" P "); // Path
					}
				} else {
					System.out.print("   ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private Space getStart() { return start; }
	private Space getEnd() { return end; }
	private int getSize() { return size; }
	private int getDungeonLevel() { return dungeonLevel; }
	private Random getSpaceGenerator() { return spaceGenerator; }
	private Space[][] getMap() { return map; }

}