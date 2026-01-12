/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;
import processing.core.PApplet;
import processing.core.PImage;
/**
 *
 * @author FFC03
 */
public class Kingdom extends SketchObject{
    private PImage[] imagesBeforeTrigger, imagesAfterTrigger;
    private Event[] keyPoints;
    private static Status status;
    private static boolean chosen = false;
    public Kingdom(PApplet app, PImage map, PImage[] imagesBeforeTrigger, PImage[] imagesAfterTrigger, Event[] keyPoints){
        super(app, app.width/2, app.height/2, map);
        this.imagesBeforeTrigger = imagesBeforeTrigger; 
        this.imagesAfterTrigger = imagesAfterTrigger;
        this.keyPoints = keyPoints;
        status = Status.BIRTH;
    }
    public void draw(){
        boolean triggered;
        PImage displayedImage;
        int stage = status.ordinal();
        triggered = keyPoints[stage].getTrigger().getTriggered();
        displayedImage = chosen ? triggered ? imagesAfterTrigger[stage] : imagesBeforeTrigger[stage] : image;
        app.image(displayedImage, x, y);
    }
    public void updateStatus(Status status){
        this.status = status;
    }
    public int getCurrentStatusIndex(){
        return status.ordinal();
    }
    public void getChosen(){
        chosen = true;
    }
}
