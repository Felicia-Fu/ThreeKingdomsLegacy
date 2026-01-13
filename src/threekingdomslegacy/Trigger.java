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
    public SketchObject[] objects;
    public boolean triggered;
    private int horizontalOffset, verticalOffset;
    
    public Trigger(SketchObject[] objects, Action action, int horizontalOffset, int verticalOffset){
        this.objects = objects;
        this.action = action;
        triggered = false;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }
    public Trigger(Action action, int horizontalOffset, int verticalOffset){
        this.objects = null;
        this.action = action;
        triggered = false;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }
    public Action getAction(){
        return action;
    }
    public boolean getTriggered(){
        return triggered;
    }
    public SketchObject[] getObjects(){
        return objects;
    }
}
