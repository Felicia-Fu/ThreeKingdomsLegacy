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
    //Instance variables
    public int x, y;
    public PImage image;
    public PApplet app;
    private boolean controllable;
    private int speed;
    private int sizeFactor;
    private static final int DEFAULT_SPEED = 5;
    private static final int DEFAULT_SIZE_FACTOR = 80;
    private boolean clicked;
    
    /**
     * Constructor for a class object
     * @param app
     * @param x
     * @param y
     * @param image
     * @param controllable 
     */
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
    
    /**
     * Overloaded constructor for a class object
     * @param app
     * @param x
     * @param y
     * @param image
     * @param controllable
     * @param sizeFactor 
     */
    public SketchObject(PApplet app, int x, int y, PImage image, boolean controllable, int sizeFactor){
        this.app = app;
        this.x = x;
        this.y = y;
        this.image = image;
        this.controllable = controllable;
        this.speed = controllable ? DEFAULT_SPEED : 0;
        this.sizeFactor = sizeFactor;
    }
    
    /**
     * Draws the image of the class object on the screen
     */
    public void draw(){
        //Defines that the coordiates inputted in the image() method is the center of the image
        app.imageMode(PApplet.CENTER);
        if (image != null){
            //Draw the image of the class object (modified by the sizeFactor) on the screen
            app.image(image, x, y, image.pixelWidth * sizeFactor / 100, image.pixelHeight * sizeFactor / 100);
        }
    }
    
    /**
     * Controls the object with left, right, up and down keys if the object is controllable and clicked
     */
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
    
    /**
     * Updates controllable variable
     * @param controllable 
     */
    public void updateControllable(boolean controllable){
        this.controllable = controllable;
        this.speed = controllable ? DEFAULT_SPEED : 0;
    }
    
    /**
     * Allow controllable variable to be accessed in other classes
     * @return boolean controllable
     */
    public boolean getControllable(){
        return controllable;
    }
    
    /**
     * Updates clicked to its opposite value
     */
    public void updateClicked(){
        clicked = !clicked;
    }
    
    /**
     * Resets clicked variable to false
     */
    public void resetClicked(){
        clicked = false;
    }
    
}