/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;

import processing.core.*;
/**
 *
 * @author FFC03
 */
public class SketchObject {
    public int x, y;
    public PImage image;
    public PApplet app;
    private boolean controllable;
    private int speed;
    private static final int DEFAULT_SPEED = 5;
    private int sizeFactor;
    private static final int DEFAULT_SIZE_FACTOR = 80;
    private boolean clicked;
    public SketchObject(PApplet app, int x, int y, PImage image, boolean controllable){
        this.app = app;
        this.x = x;
        this.y = y;
        this.image = image;
        this.controllable = controllable;
        this.speed = controllable ? DEFAULT_SPEED : 0;
        sizeFactor = DEFAULT_SIZE_FACTOR;
        clicked = false;
    }
    public SketchObject(PApplet app, int x, int y, PImage image, boolean controllable, int sizeFactor){
        this.app = app;
        this.x = x;
        this.y = y;
        this.image = image;
        this.controllable = controllable;
        this.speed = controllable ? DEFAULT_SPEED : 0;
        this.sizeFactor = sizeFactor;
    }
    public void draw(){
        app.imageMode(PApplet.CENTER);
        if (image != null){
            app.image(image, x, y, image.pixelWidth * sizeFactor / 100, image.pixelHeight * sizeFactor / 100);
        }
    }
    public void move(){
        if (controllable && clicked && app.keyPressed){
            if (app.keyCode == PApplet.LEFT){
                x -= speed;
            }
            if (app.keyCode == PApplet.RIGHT){
                x += speed;
            }
            if (app.keyCode == PApplet.UP){
                y -= speed;
            }
            if (app.keyCode == PApplet.DOWN){
                y += speed;
            }
        }
    }
    public void updateControllable(boolean controllable){
        this.controllable = controllable;
        this.speed = controllable ? DEFAULT_SPEED : 0;
    }
    public boolean getControllable(){
        return controllable;
    }
    public void updateClicked(){
        clicked = !clicked;
    }
    public void resetClicked(){
        clicked = false;
    }
    
}