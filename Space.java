package rpg;

/* This class is for a moves damage and duration */
public class Space { 
    public int x; 
    public int y; 

    public Space(int x, int y) { 
        this.x = x; 
        this.y = y; 
    } 

    public String toString() {
        return " s ";
    }

    /* Getter */
    protected int getX() { return this.x; }
    protected int getY() { return this.y; }

    /* Contains */
    protected boolean compareTo(Space s) {
        if (this.x == s.getX() && this.y == getY()) { return true; }
        return false;
    }

}