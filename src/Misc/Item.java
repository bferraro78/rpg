package src.Misc;


/* This class hold the structure for an Item */
public abstract class Item { 
    public String description; 

    public Item(String description) { 
        this.description = description; 
    } 

    /* Getter */
    public String getDescription() { return this.description; }

    /* Abstract methods*/
    public abstract String toString();
    public abstract String getType();
    public abstract Element getElement();
    public abstract int getPotency();

} 