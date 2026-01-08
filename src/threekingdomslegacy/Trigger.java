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
    private SketchObject[] objects;
    private Action actionRequired;
    private boolean triggered;
    public Trigger(SketchObject[] objects, Action actionRequired){
        this.objects = objects;
        this.actionRequired = actionRequired;
        triggered = false;
    }
    public boolean getTriggered(){
        return triggered;
    }
    public void setTriggered(boolean triggered){
        this.triggered = triggered;
    }
}
