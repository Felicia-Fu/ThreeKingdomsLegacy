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
    private int mouseCenterX, mouseCenterY, counter;
    public StationaryTrigger(Action action, int horizontalOffset, int verticalOffset, int mouseCenterX, int mouseCenterY, int counter){
        super(action, horizontalOffset, verticalOffset);
        this.mouseCenterX = mouseCenterX;
        this.mouseCenterY = mouseCenterY;
        this.counter = counter;
    }
    public void trigger(int currentCount){
        triggered = currentCount == counter;
    }
}
