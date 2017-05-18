package rpg;

import java.util.Random;

/* This class is a boost fot 10-20% for Lightning resistance */
public class Lightningstone extends Item { 
    public Element element;
    protected int potency;

    public Lightningstone(String description, Element element) { 
        super(description);
        this.element = element; 
        generate();
    } 

    /* Getter */
    public String toString() { return "Lightningstone - " + getDescription() + "by " + getPotency() + "%"; }
    protected Element getElement() { return this.element; }
    protected String getType() { return "Lightningstone"; }
    protected int getPotency() { return this.potency; }
    protected void generate() { 
        Random r = new Random();
        this.potency = r.nextInt(15)+5; 
    }



} 