package rpg;


/* This class hold the structure for an Item */
public abstract class Item { 
    public String description; 

    public Item(String description) { 
        this.description = description; 
    } 

    /* Getter */
    protected String getDescription() { return this.description; }

    /* Abstract methods*/
    public abstract String toString();
    protected abstract String getType();
    protected abstract Element getElement();
    protected abstract int getPotency();

} 