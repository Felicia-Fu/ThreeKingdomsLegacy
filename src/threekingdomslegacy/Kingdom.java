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
    private Status status;
    private boolean chosen = false;
    private boolean visible = true;
    public Kingdom(PApplet app, PImage map, ArrayList<PImage> imagesBeforeTrigger, ArrayList<PImage> imagesAfterTrigger, ArrayList<Event> keyPoints){
        super(app, app.width/2, app.height/2, map, false);
        this.imagesBeforeTrigger = imagesBeforeTrigger; 
        this.imagesAfterTrigger = imagesAfterTrigger;
        this.keyPoints = keyPoints;
        status = Status.BIRTH;
    }
    @Override
    public void draw(){
        PImage displayedImage;
        int stage = status.ordinal();
        Trigger trigger = keyPoints.get(stage).getTrigger();
        boolean triggered = trigger.getTriggered();
        displayedImage = visible ? chosen ? triggered ? imagesAfterTrigger.get(stage) : imagesBeforeTrigger.get(stage) : image : null;
        if (displayedImage != null){
            app.image(displayedImage, x, y, displayedImage.pixelWidth * app.height / displayedImage.pixelHeight, app.height);
        } else{
            app.background(255);
        }
        if (trigger.getAction() == Action.MOVE && !triggered){
                for (SketchObject object: trigger.getObjects()){
                    object.draw();
                }
            }
        if (triggered){
            app.textAlign(PApplet.CORNER);
            app.fill(255);
            app.rect(50, 300, app.width - 400, 200);
            app.fill(0);
            app.text(keyPoints.get(stage).getDescription(), 75, 325, app.width - 450, 150);
        }
    }
    public void updateStatus(Status status){
        this.status = status;
    }
    public int getCurrentStatusIndex(){
        return status.ordinal();
    }
    public void setChosen(){
        this.chosen = true;
        this.visible = true;
    }
    public void setInvisible(){
        this.visible = false;
    }
    public ArrayList<Event> getEvents(){
        return keyPoints;
    }
}