/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;

/**
 *
 * @author FFC03
 */
public class StationaryTrigger extends Trigger{
    //Class variables
    public int mouseCenterX, mouseCenterY;
    private int counter;
    private int currentCount;
    
    /**
     * Constructor for a class object
     * @param action
     * @param horizontalOffset
     * @param verticalOffset
     * @param mouseCenterX
     * @param mouseCenterY
     * @param counter
     * @param currentCount 
     */
    public StationaryTrigger(Action action, int horizontalOffset, int verticalOffset, int mouseCenterX, int mouseCenterY, int counter, int currentCount){
        super(action, horizontalOffset, verticalOffset);
        this.mouseCenterX = mouseCenterX;
        this.mouseCenterY = mouseCenterY;
        this.counter = counter;
        this.currentCount = currentCount;
    }
    
    /**
     * Sets triggered variable based on the comparison between currentCount and counter
     */
    public void trigger(){
        triggered = currentCount == counter;
    }
    
    /**
     * Increments currentCount variable by 1
     */
    public void updateCurrentCount(){
        currentCount ++;
    }
    
    /**
     * Allow currentCount variable to be accessed in other classes
     * @return int currentCount
     */
    public int getCurrentCount(){
        return currentCount;
    }
    
    /**
     * Allow counter variable to be accessed in other classes
     * @return int counter
     */
    public int getCounter(){
        return counter;
    }
}
