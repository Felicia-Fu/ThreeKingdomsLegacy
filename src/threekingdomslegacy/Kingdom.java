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
    private ArrayList<PImage> imagesBeforeTrigger, imagesAfterTrigger;
    private ArrayList<Event> keyPoints;
    private static Status status;
    private static boolean chosen = false;
    private static boolean visible = true;
    public Kingdom(PApplet app, PImage map, ArrayList<PImage> imagesBeforeTrigger, ArrayList<PImage> imagesAfterTrigger, ArrayList<Event> keyPoints){
        super(app, app.width/2, app.height/2, map, false);
        this.imagesBeforeTrigger = imagesBeforeTrigger; 
        this.imagesAfterTrigger = imagesAfterTrigger;
        this.keyPoints = keyPoints;
        status = Status.BIRTH;
    }
    public void draw(){
        boolean triggered;
        PImage displayedImage;
        int stage = status.ordinal();
        triggered = keyPoints.get(stage).getTrigger().getTriggered();
        displayedImage = visible ? chosen ? triggered ? imagesAfterTrigger.get(stage) : imagesBeforeTrigger.get(stage) : image : null;
        if (displayedImage != null) app.image(displayedImage, x, y);
        if (triggered) app.text(keyPoints.get(stage).getDescription(), app.width/2, app.height/2);
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
    public void setInvisible(){
        visible = false;
    }
}
