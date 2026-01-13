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
    private Action action;
    private int counter;
    private SketchObject[] objects;
    private boolean triggered;
    private int[] centerPositions;
    private int horizontalOffset, verticalOffset;
    private int mouseCenterX, mouseCenterY;
    public Trigger(SketchObject[] objects, Action action, int[] centerPositions, int horizontalOffset, int verticalOffset){
        this.objects = objects;
        triggered = false;
        this.action = action;
        this.centerPositions = centerPositions;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }
    public Trigger(Action action, int mouseCenterX, int mouseCenterY, int horizontalOffset, int verticalOffset, int counter){
        triggered = false;
        this.action = action;
        this.counter = counter;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }
    public void trigger(){
        if (centerPositions.length / 2 == objects.length){
            for (int i = 0; i < objects.length; i ++){
                int centerPositionX = centerPositions[2 * i];
                int centerPositionY = centerPositions[2 * i + 1];
                boolean yWithinRange = (objects[i].y > centerPositionY - verticalOffset) && (objects[i].y < centerPositionY + verticalOffset);
                boolean xWithinRange = (objects[i].x > centerPositionX - horizontalOffset) && (objects[i].x < centerPositionX + horizontalOffset);
                if (!yWithinRange || !xWithinRange){
                    triggered = false;
                    return;
                }
            }
            triggered = true;
        } else{
            triggered = false;
            return;
        }
    }
    public void trigger(int currentCount){
        triggered = currentCount == counter;
    }
    public Action getAction(){
        return action;
    }
    public boolean getTriggered(){
        return triggered;
    }
}
