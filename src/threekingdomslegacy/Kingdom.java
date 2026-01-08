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
public class Kingdom{
    private PApplet app;
    private PImage[] imagesBeforeTrigger, imagesAfterTrigger;
    private PImage map;
    private Event[] keyPoints;
    private static int x, y;
    private static Status status;
    public Kingdom(PApplet app, PImage map, PImage[] imagesBeforeTrigger, PImage[] imagesAfterTrigger, Event[] keyPoints){
        this.app = app;
        this.imagesBeforeTrigger = imagesBeforeTrigger; 
        this.imagesAfterTrigger = imagesAfterTrigger;
        this.map = map;
        this.keyPoints = keyPoints;
        x = app.width/2;
        y = app.height/2;
        status = Status.BIRTH;
    }
    public void draw(boolean start){
        boolean triggered;
        PImage image;
        int stage = status.ordinal();
        triggered = keyPoints[stage].getTrigger().getTriggered();
        image = start ? triggered ? imagesAfterTrigger[stage] : imagesBeforeTrigger[stage] : map;
        app.image(image, x, y);
    }
    public void updateStatus(Status status){
        this.status = status;
    }
}
