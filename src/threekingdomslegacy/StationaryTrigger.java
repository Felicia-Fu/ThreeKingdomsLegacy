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
    public int mouseCenterX, mouseCenterY;
    private int counter;
    private int currentCount;
    public StationaryTrigger(Action action, int horizontalOffset, int verticalOffset, int mouseCenterX, int mouseCenterY, int counter, int currentCount){
        super(action, horizontalOffset, verticalOffset);
        this.mouseCenterX = mouseCenterX;
        this.mouseCenterY = mouseCenterY;
        this.counter = counter;
        this.currentCount = currentCount;
    }
    public void trigger(){
        triggered = currentCount == counter;
    }
    public void updateCurrentCount(){
        currentCount ++;
    }
    public int getCurrentCount(){
        return currentCount;
    }
    public int getCounter(){
        return counter;
    }
}
