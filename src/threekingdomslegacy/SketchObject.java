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
    public SketchObject(PApplet app, int x, int y, PImage image, boolean controllable){
        this.app = app;
        this.x = x;
        this.y = y;
        this.image = image;
        this.controllable = controllable;
        this.speed = controllable ? DEFAULT_SPEED : 0;
        sizeFactor = DEFAULT_SIZE_FACTOR;
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
        app.image(image, x, y);
    }
    public void move(){
        if (controllable){
            x += speed;
            y += speed;
        }
    }
    public void updateControllable(boolean controllable){
        this.controllable = controllable;
        this.speed = controllable ? speed : 0;
    }
    
}
