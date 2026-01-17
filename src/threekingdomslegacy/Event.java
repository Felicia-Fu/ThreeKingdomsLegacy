/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;

/**
 *
 * @author FFC03
 */
public class Event {
    //Class objects
    private Trigger trigger;
    private String description;
    
    /**
     * Constructor for a class object
     * @param trigger
     * @param description 
     */
    public Event(Trigger trigger, String description){
        this.trigger = trigger;
        this.description = description;
    }
    
    /**
     * Allow description variable to be accessed in other classes
     * @return String description
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Allow trigger variable to be accessed in other classes
     * @return Trigger trigger
     */
    public Trigger getTrigger(){
        return trigger;
    }
    
}
