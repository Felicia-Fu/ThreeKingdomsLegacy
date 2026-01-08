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
    private Status status;
    public Event(Trigger trigger, String description, Status status){
        this.trigger = trigger;
        this.description = description;
        this.status = status;
    }
    public String getDescription(){
        return description;
    }
    
}
