package threekingdomslegacy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author FFC03
 */
public class DynamicTrigger extends Trigger{
    private int[] centerPositions;
    private int horizontalOffset, verticalOffset;
    public DynamicTrigger(SketchObject[] objects, Action action, int horizontalOffset, int verticalOffset, int[] centerPositions){
        super(objects, action, horizontalOffset, verticalOffset);
        this.centerPositions = centerPositions;
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
        }
    }
    
}
