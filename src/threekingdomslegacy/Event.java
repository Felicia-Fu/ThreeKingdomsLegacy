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
    private Trigger trigger;
    private String description;
    public Event(Trigger trigger, String description){
        this.trigger = trigger;
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    public Trigger getTrigger(){
        return trigger;
    }
    
}
