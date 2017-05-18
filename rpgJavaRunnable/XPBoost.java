package rpg;

import java.util.Random;

/* This class is a boost fot 10-20% for XPBoost */
public class XPBoost extends Item { 
    protected int potency;

    public XPBoost(String description) { 
        super(description);
        generate();
    } 

    /* Getter */
    public String toString() { return "XPBoost - " + getDescription() + "by " + getPotency() + "%"; }
    protected String getType() { return "XPBoost"; }
    protected int getPotency() { return this.potency; }
    protected Element getElement() { return Element.PHYSICAL; };
    protected void generate() { 
        Random r = new Random();
        this.potency = r.nextInt(15)+5; 
    }



} 