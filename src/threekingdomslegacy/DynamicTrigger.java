package threekingdomslegacy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import processing.core.PApplet;
/**
 *
 * @author FFC03
 */
public class DynamicTrigger extends Trigger{
    private int[] centerPositions;
    public DynamicTrigger(SketchObject[] objects, Action action, int horizontalOffset, int verticalOffset, int[] centerPositions){
        super(objects, action, horizontalOffset, verticalOffset);
        this.centerPositions = centerPositions;
    }
    public void trigger(){
        if (!triggered){
            boolean previousNull = false;
            int numValid = 0;
            for (SketchObject object : objects){
                if (object.getControllable()){
                    numValid ++;
                }
            }
            if (centerPositions.length / 2 == numValid){
                for (int i = 0; i < objects.length; i ++){
                    if (objects[i].getControllable()){
                        int centerPositionX = centerPositions[!previousNull ? (2 * i) : (2 * (i - 1))];
                        int centerPositionY = centerPositions[!previousNull ? (2 * i + 1) : (2 * (i - 1) + 1)];
                        previousNull = false;
                        boolean yWithinRange = (objects[i].y > centerPositionY - verticalOffset) && (objects[i].y < centerPositionY + verticalOffset);
                        boolean xWithinRange = (objects[i].x > centerPositionX - horizontalOffset) && (objects[i].x < centerPositionX + horizontalOffset);
                        if (!yWithinRange || !xWithinRange){
                             triggered = false;
                            return;
                        }
                    } else{
                        previousNull = true;
                    }
                }
                triggered = true;
            } else{
                triggered = false;
            }
        }
    }
    
}
