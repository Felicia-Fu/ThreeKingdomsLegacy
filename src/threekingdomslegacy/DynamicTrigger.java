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
    //Class variables
    private int[] centerPositions;
    
    /**
     * Constructor for a class object
     * @param objects
     * @param action
     * @param horizontalOffset
     * @param verticalOffset
     * @param centerPositions 
     */
    public DynamicTrigger(SketchObject[] objects, Action action, int horizontalOffset, int verticalOffset, int[] centerPositions){
        super(objects, action, horizontalOffset, verticalOffset);
        this.centerPositions = centerPositions;
    }
    
    /**
     * Sets triggered variable based on if all SketchObjects in the objects array have been moved to their desired positions if controllable
     */
    public void trigger(){
        //If the user hasn't completed all required interactions...
        if (!triggered){
            boolean previousNull = false; //Defines a variable to check if the previous item is not controllable
            int numValid = 0; //Define a variable to check the number of objects in the array that is controllable
            int prevInvalid = 0;
            
            //Loop through the SketchObject array and increment variable numValid by one if an object in the array is controllable
            for (SketchObject object : objects){
                if (object.getControllable()){
                    numValid ++;
                }
            }
            //Since the centerPositions array stores desired x and y center positions for each controllable object, 
            //its length divided by 2 should be equil to the value of numValid variable
            //If these two are not equivalent, then triggered variable will be set to false
            if (centerPositions.length / 2 == numValid){
                //Loop through object array
                for (int i = 0; i < objects.length; i ++){
                    //If the object at the index is controllable...
                    if (objects[i].getControllable()){
                        //Get the desired x and y center positions from the centerPositions array
                        //If the previous item is not controllable, the index of the corresponding x and y center positions will each be moved 2 * preInvalid number slots
                        //(2 * preInvalid) slots forward because x and y center positions come in pairs
                        int centerPositionX = centerPositions[!previousNull ? (2 * i) : (2 * (i - prevInvalid))];
                        int centerPositionY = centerPositions[!previousNull ? (2 * i + 1) : (2 * (i - prevInvalid) + 1)];
                        //Reset the variables for tracking uncontrollable objects
                        previousNull = false;
                        prevInvalid = 0;
                        //Check if the mouse positions is within the range of valid values (center position +/- acceptable offsets)
                        boolean yWithinRange = (objects[i].y > centerPositionY - verticalOffset) && (objects[i].y < centerPositionY + verticalOffset);
                        boolean xWithinRange = (objects[i].x > centerPositionX - horizontalOffset) && (objects[i].x < centerPositionX + horizontalOffset);
                        //If either x or y is not in the range, set triggered to false and return right away
                        if (!yWithinRange || !xWithinRange){
                            triggered = false;
                            return;
                        }
                    //If the object at the index is not controllable, set previousNull to true and increment prevInvalid by 1
                    } else{
                        previousNull = true;
                        prevInvalid ++;
                    }
                }
                //If all x and y center positions are within their accepted range, set triggered to true
                triggered = true;
            } else{
                triggered = false;
            }
        }
    }
    
}
