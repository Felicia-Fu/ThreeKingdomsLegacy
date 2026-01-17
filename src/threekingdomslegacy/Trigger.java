/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;

/**
 *
 * @author FFC03
 */
public class Trigger {
    //Class variables
    private Action action;
    public SketchObject[] objects;
    public boolean triggered;
    public int horizontalOffset, verticalOffset;
    
    /**
     * Constructor for a class object
     * @param objects
     * @param action
     * @param horizontalOffset
     * @param verticalOffset 
     */
    public Trigger(SketchObject[] objects, Action action, int horizontalOffset, int verticalOffset){
        this.objects = objects;
        this.action = action;
        triggered = false;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }
    
    /**
     * Overloaded constructor for a class object
     * @param action
     * @param horizontalOffset
     * @param verticalOffset 
     */
    public Trigger(Action action, int horizontalOffset, int verticalOffset){
        this.objects = null;
        this.action = action;
        triggered = false;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }
    
    /**
     * Allow action variable to be accessed in other classes
     * @return Action action
     */
    public Action getAction(){
        return action;
    }
    
    /**
     * Allow triggered variable to be accessed in other classes
     * @return boolean triggered
     */
    public boolean getTriggered(){
        return triggered;
    }
    
    /**
     * Allow objects array to be accessed in other classes
     * @return SketchObject[] objects
     */
    public SketchObject[] getObjects(){
        return objects;
    }
}
