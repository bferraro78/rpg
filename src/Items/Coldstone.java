package src.Items;

import java.util.Random;

/* This class is a boost fot 10-20% for Cold resistance */
public class Coldstone extends Item { 
    public Element element;
    protected int potency;

    public Coldstone(String description, Element element) { 
        super(description);
        this.element = element;
        generate();
    } 

    /* Getter */
    public String toString() { return "Coldstone - " + getDescription() + "by " + getPotency() + "%"; }
    protected Element getElement() { return this.element; }
    protected String getType() { return "Coldstone"; }
    protected int getPotency() { return this.potency; }
    protected void generate() { 
        Random r = new Random();
        this.potency = r.nextInt(15)+5; 
    }


} 