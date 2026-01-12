/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;
import processing.core.*;
import java.io.*;
/**
 *
 * @author FFC03
 */
public class Game extends PApplet{
    public int stage = -1;
    public int index = 1;
    public final int screenWidth = 700, screenHeight = 500;
    public PFont titleFont, subtitleFont, contentFont;
    public String username = "";
    public String password = "";
    public String placeholder = "";
    public boolean finishUsername = false;
    public int count = 0;
    public String chosenKingdom = "";
    public Kingdom[] kingdoms = new Kingdom[3];
    public final int DEFAULT_STAGE = 1;
    
    public void settings(){
        size(screenWidth, screenHeight);
    }
    public void setup(){
        background(255);
        titleFont = createFont("fonts/rogenz.otf", 120);
        subtitleFont = createFont("fonts/rogenz.otf", 50);
        contentFont = createFont("fonts/rogenz.otf", 25);
        
    }
    public void draw(){
        switch (stage){
            case -1:
                fill(255, 0, 0);
                textFont(subtitleFont);
                textAlign(CENTER);
                text("Three Kingdoms", screenWidth/2, 150);
                fill(0);
                textFont(titleFont);
                text("Legacy", screenWidth/2, 250);
                fill(23, 100, 100);
                rect(screenWidth/2 - 100, screenHeight/2 + 74/2, 200, 74);
                fill(0);
                textFont(subtitleFont);
                text("Start", screenWidth/2, screenHeight/2 + 93); 
                break;
            case 0:
                background(255);
                textFont(contentFont);
                textAlign(LEFT);
                text("Enter username: ", 150, 100);
                text(username, 400, 100);
                text("Enter password: ", 150, 150);
                text(placeholder, 400, 150);
                break;
            case 1:
                background(255);
                fill(0);
                textAlign(CENTER);
                String texts = index == 1 ? "Shu" : index == 2 ? "Wei" : "Eastern Wu";
                text(texts, screenWidth/2, screenHeight/2);
                break;
        }
        
    }
    public void mousePressed(){
        if (stage == -1 && mouseX < screenWidth/2 + 100 && mouseX > screenWidth/2 - 100 && mouseY < screenHeight/2 + 74 * 3 / 2 && mouseY > screenHeight/2 + 74/2){
            stage = 0;
        }
    }
    public void keyPressed(){
        switch(stage){
            case 0:
                if (keyCode != ENTER){
                    if (keyCode != CODED){
                        if (count == 0){
                            username += key;
                        } else{
                            password += key;
                            placeholder += "0";
                        }
                    }
                    if (keyCode == BACKSPACE){
                        if (count == 0){
                            username = username.length() <= 1 ? "" : username.substring(0, username.length() - 2);
                        } else{
                            password = password.length() <= 1 ? "" : password.substring(0, password.length() - 2);
                            placeholder = placeholder.length() <= 1 ? "" : placeholder.substring(0, placeholder.length() - 2);
                        }
                    }
                } else{
                    if ((count == 0 && !username.isEmpty()) || (count == 1 && !password.isEmpty())){
                        count ++;
                    }
                    if (count == 2){
                        count = 0;
                        stage = 1;
                        try{
                            FileWriter fw = new FileWriter("users.csv", true);
                            PrintWriter pw = new PrintWriter(fw);
                            pw.println(username + "," + password + "," + DEFAULT_STAGE);
                            pw.close();
                        } catch (IOException e){
                            text("Error occured while processing", screenWidth/2, screenHeight/2);
                        }
                    }
                }
                
                break;
            case 1:
                if (keyCode == LEFT){
                    index = (index - 1) % 3;
                    if (index < 0){
                        index += 3;
                    }
                } else if (keyCode == RIGHT){
                    index = index % 3 + 1;
                }
                break;
        }
    }
    public static void updateObject(SketchObject object, boolean controllable){
        if (object instanceof Kingdom){
            Kingdom kingdom = (Kingdom) object;
            int nextIndex = kingdom.getCurrentStatusIndex();
            kingdom.updateStatus(Status.values()[nextIndex]);
        } else{
            object.updateControllable(controllable);
        }
        object.draw();
    }
    
    
}
