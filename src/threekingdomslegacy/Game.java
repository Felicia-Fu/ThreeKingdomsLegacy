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
    private int stage = -1;
    private int index = 1;
    public void settings(){
        size(500, 400);
    }
    public void setup(){
        background(255);
    }
    public void draw(){
        switch (stage){
            case -1:
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
                break;
            case 0:
                background(255);
                fill(0);
                textAlign(CENTER);
                String texts = index == 1 ? "Shu" : index == 2 ? "Wei" : "Eastern Wu";
                text(texts, 250, 200);
                break;
        }
        System.out.println(stage);
        
    }
    public void mousePressed(){
        if (stage == -1 && mouseX < 350 && mouseX > 150 && mouseY < 337 && mouseY > 263){
            stage = 0;
        }
    }
    public void keyPressed(){
        if (stage == 0){
            if (keyCode == LEFT){
                index = (index - 1) % 3;
                if (index < 0){
                    index += 3;
                }
            } else if (keyCode == RIGHT){
                index = index % 3 + 1;
            }
        }
    }
    
}
