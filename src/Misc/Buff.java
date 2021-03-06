package src.Misc;

import java.lang.Math.*;

/* This class is for a moves damage and duration and damage type */
public class Buff { 
    public int x; 
    public int y; 

    public Buff(int x, int y) { 
        this.x = x; 
        this.y = y; 
    } 

    public String toString() {
        if (getValue() < 0) {
            if (getDuration() == 0) {
                return "Heal: " + Integer.toString(Math.abs(getValue()));
            } else {
                return "Heal: " + Integer.toString(Math.abs(getValue())) + " | Duration: " + Integer.toString(getDuration());        
            }
        } else {
            if (getDuration() == 0) {
                 return "Damage: " + Integer.toString(getValue());
            } else {
                 return "Damage: " + Integer.toString(getValue()) + " | Duration: " + Integer.toString(getDuration());    
            }
        }
    }


    /* Getter */
    public int getValue() { return this.x; }
    public int getDuration() { return this.y; }

    /* Setters */
    public void setDamage(int damage) { this.x = damage; }
    public void decreaseDuration() { this.y = this.y-1; }
} 