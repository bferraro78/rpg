package src.Misc;

/* This class is for a moves damage and duration */
public class Space { 
    public int x; 
    public int y; 
    public Item item;

    public Space(int x, int y, Item item) { 
        this.x = x; 
        this.y = y; 
        this.item = item;
    } 

    public String toString() {
        return " s ";
    }

    /* Getter */
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public Item getItem() { return this.item; }

}