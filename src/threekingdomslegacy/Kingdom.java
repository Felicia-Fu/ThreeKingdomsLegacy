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
    private PImage image;
    private Event[] keyPoints = new Event[5];
    public static int stage = -1;
    public Kingdom(PImage image, Event[] keyPoints){
        this.image = image; 
        this.keyPoints = keyPoints;
    }
    public static int getStage(){
        return stage;
    }
}
