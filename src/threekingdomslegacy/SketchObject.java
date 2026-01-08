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
    public SketchObject(PApplet app, int x, int y, PImage image){
        this.app = app;
        this.x = x;
        this.y = y;
        this.image = image;
        controllable = false;
        speed = 0;
    }
    public SketchObject(int x, int y, PImage image, boolean controllable, int speed){
        this.x = x;
        this.y = y;
        this.image = image;
        this.controllable = controllable;
        this.speed = controllable ? speed : 0;
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
    
}
