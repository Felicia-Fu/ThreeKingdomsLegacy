/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threekingdomslegacy;
import processing.core.*;
import java.io.*;
import java.util.*;
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
    public static LinkedHashMap<String, String[]> userInfo = new LinkedHashMap();
    
    public void settings(){
        size(screenWidth, screenHeight);
    }
    public void setup(){
        background(255);
        titleFont = createFont("fonts/rogenz.otf", 120);
        subtitleFont = createFont("fonts/rogenz.otf", 50);
        contentFont = createFont("fonts/rogenz.otf", 25);
        userInfo = loadContents(new File("users.csv"));
        stage = -1;
        
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
                textFont(contentFont);
                textAlign(LEFT);
                fill(0);
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
            case 2:
                background(255);
                fill(0);
                text(chosenKingdom, screenWidth/2, screenHeight/2);
        }
        fill(23, 100, 100);
        rect(screenWidth - 225, screenHeight - 80, 200, 74);
        fill(0);
        textAlign(CENTER);
        textFont(subtitleFont);
        text("Exit", screenWidth - 125, screenHeight - 28); 
        
    }
    public void mousePressed(){
        if (stage == -1 && mouseX < screenWidth/2 + 100 && mouseX > screenWidth/2 - 100 && mouseY < screenHeight/2 + 74 * 3 / 2 && mouseY > screenHeight/2 + 74/2){
            background(255);
            stage = 0;
        }  
        if (stage == 1){
            background(255);
            stage = 2;
            System.out.println("Kingdom chosen");
            chosenKingdom = index == 1 ? "Shu" : index == 2 ? "Wei" : "Eastern Wu";
        }
        if (mouseX < screenWidth/2 + 100 && mouseX > screenWidth/2 - 100 && mouseY < screenHeight - 6 && mouseY > screenHeight - 80){
            background(255);
            stage = -2;
            userInfo.put(username, new String[]{password, Integer.toString(stage)});
            exitGame();
            exit();
        }  
    }
    public void keyPressed(){
        switch(stage){
            case 0:
                if (keyCode != ENTER){
                    if (keyCode != CODED){
                        if (count == 0){
                            username += (Character.isLetter(key) || Character.isDigit(key)) ? key : "";
                        } else{
                            password += (Character.isLetter(key) || Character.isDigit(key)) ? key : "";
                            placeholder += (Character.isLetter(key) || Character.isDigit(key)) ? 0 : "";
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
                        if (userInfo.containsKey(username)){
                            if (password.equals(userInfo.get(username)[0])){
                                stage = Integer.parseInt(userInfo.get(username)[1]);
                                background(255);
                            } else{
                                background(255);
                                textAlign(CENTER);
                                fill(255, 0, 0);
                                text("Wrong password entered", screenWidth/2, screenHeight/2);
                                password = "";
                                placeholder = "";
                                count = 1;
                            }
                        } else{
                            try{
                            FileWriter fw = new FileWriter("users.csv", true);
                            PrintWriter pw = new PrintWriter(fw);
                            pw.println(username + "," + password + "," + DEFAULT_STAGE);
                            pw.close();
                            } catch (IOException e){
                                text("Error occured while processing", screenWidth/2, screenHeight/2);
                            }
                            stage = 1;
                            background(255);
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
    public static LinkedHashMap<String, String[]> loadContents(File file){
        LinkedHashMap<String, String[]> userInfo = new LinkedHashMap<String, String[]>();
        try{
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                String[] content = scan.nextLine().split(",");
                userInfo.put(content[0], new String[]{content[1], content[2]});
            }
        } catch(FileNotFoundException e){
            System.out.println("File Not Found!!!");
        }
        return userInfo;
    }
    public static void exitGame(){
        try{
            FileWriter fw = new FileWriter("users.csv", false);
            PrintWriter pw = new PrintWriter(fw);
            userInfo.forEach((key, value) -> {
                pw.println(key + "," + value[0] + "," + value[1]);
            });
            pw.close();
        } catch (IOException e){
            System.out.println("Error occured");
        }
       
    }
    
    
}
