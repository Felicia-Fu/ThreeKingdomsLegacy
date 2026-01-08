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
public class Game extends PApplet{
    public void settings(){
        size(500, 400);
    }
    public void setup(){
        background(255);
    }
    public void draw(){
        fill(255, 0, 0);
        textSize(40);
        PFont font = createFont("fonts/rogenz.otf", 50);
        textFont(font);
        textAlign(CENTER);
        text("Three Kingdoms", 250, 150);
        fill(0);
        text("Legacy", 250, 200);
        fill(23, 100, 100);
        rect(250 - 100, 300 - 74/2, 200, 74);
        fill(0);
        text("Start", 250, 320);
    }
    
}
