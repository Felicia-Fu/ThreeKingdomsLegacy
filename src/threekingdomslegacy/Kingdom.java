/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
/**
 *
 * @author FFC03
 */
public class Kingdom extends SketchObject{
    //Class variables
    private ArrayList<PImage> imagesBeforeTrigger, imagesAfterTrigger;
    private ArrayList<Event> keyPoints;
    private Status status;
    private boolean chosen;
    private boolean visible;
    
    /**
     * Constructor for a class object
     * @param app
     * @param map
     * @param imagesBeforeTrigger
     * @param imagesAfterTrigger
     * @param keyPoints 
     */
    public Kingdom(PApplet app, PImage map, ArrayList<PImage> imagesBeforeTrigger, ArrayList<PImage> imagesAfterTrigger, ArrayList<Event> keyPoints){
        super(app, app.width/2, app.height/2, map, false);
        this.imagesBeforeTrigger = imagesBeforeTrigger; 
        this.imagesAfterTrigger = imagesAfterTrigger;
        this.keyPoints = keyPoints;
        status = Status.BIRTH;
        chosen = false;
        visible = true;
    }
    
    @Override
    /**
     * Draws the images on the screen based on the status the kingdom is at and if the user has completed the interactions required
     */
    public void draw(){
        PImage displayedImage;
        int stage = status.ordinal();
        Trigger trigger = keyPoints.get(stage).getTrigger();
        boolean triggered = trigger.triggered;
        
        //Decides what image will be drawn on the screen as the following:
        //If the kingdom is visible ->
        //                            If the kingdom is chosen ->
        //                                                        If the interaction is completed -> Draw the image at index stage in imagesAfterTrigger ArrayList
        //                                                        Else -> Draw the image at index stage in imagesBeforeTrigger ArrayList
        //                            Else -> Draw the map of the kingdom (image in SketchObject class as Kingdom extends from there)
        //Else -> No image will be drawn on the screen
        displayedImage = visible ? chosen ? triggered ? imagesAfterTrigger.get(stage) : imagesBeforeTrigger.get(stage) : image : null;
        
        //If the PImage object displayedImage is valid, the program draws in on the screen
        //Else the background of the screen will be emptied (painted white)
        if (displayedImage != null){
            //Defines that the coordinates inputted in the image() method is the center of the image
            app.imageMode(PApplet.CENTER);
            //Draw the image with size modified such that the height of the image on the screen is equivalent to the height of the screen
            //Width calculation is based on the proportionality between height of the screen and pixel height
            //Height calculation is omitted but basically multiplied the same multiplier (displayedImage.pixelHeight * app.height / displayedImage.pixelHeight)
            app.image(displayedImage, x, y, displayedImage.pixelWidth * app.height / displayedImage.pixelHeight, app.height);
        } else{
            app.background(255);
        }
        //If the interaction is to move objects to a certain postiion and the user hasn't completed the interactions required, 
        //draw all objects that are involved in the interaction on the screen along with the background image
        if (trigger.getAction() == Action.MOVE && !triggered){
            for (SketchObject object: trigger.objects){
                object.draw();
            }
        }
        //If the user completed all interactions required, a description of the kingdom at a particular status is displayed on the screen
        if (triggered){
            //Defines the text align (aligned to the left corner)
            app.textAlign(PApplet.CORNER);
            //Defines the color of the rectangle that acts like a background for the text and draw the rectangle on the screen
            app.fill(255);
            app.rect(50, 300, app.width - 400, 200);
            //Defines the color of the text and draw the text on the screen
            app.fill(0);
            app.text(keyPoints.get(stage).getDescription(), 75, 325, app.width - 450, 150);
        }
    }
    
    /**
     * Updates status variable
     * @param status 
     */
    public void updateStatus(Status status){
        this.status = status;
    }
    
    /**
     * Get the index of the currentStatus in Status enum
     * @return int currentStatusIndex
     */
    public int getCurrentStatusIndex(){
        return status.ordinal();
    }
    
    /**
     * Modify chosen and visible variables if a kingdom is chosen
     */
    public void setChosen(){
        this.chosen = true;
        this.visible = true;
    }
    
    /**
     * Modify visible variable to make the kingdom invisible on the screen
     */
    public void setInvisible(){
        this.visible = false;
    }
    
    /**
     * Allow the events ArrayList to be accessed in other classes
     * @return ArrayList of Events keyPoints
     */
    public ArrayList<Event> getEvents(){
        return keyPoints;
    }
}