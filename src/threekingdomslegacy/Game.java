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
    public static int stage = -1;
    public static int index = 0;
    public final int screenWidth = 700, screenHeight = 500;
    public PFont titleFont, subtitleFont, contentFont, descriptionFont;
    public static String username = "";
    public static String password = "";
    public String placeholder = "";
    public boolean finishUsername = false;
    public int count = 0;
    public static String chosenKingdom = "";
    public static ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
    public final int DEFAULT_STAGE = 1;
    public static LinkedHashMap<String, String[]> userInfo = new LinkedHashMap();
    public Boolean correctPassword = null;
    private int mapSizeMultiplier = 80;
    private static String[] kingdomName = new String[] {"Shu", "Wei", "Eastern Wu"};
    private SketchObject chosenObject = null;
    private static Event currentEvent;
    private Trigger currentTrigger;
    private static Kingdom kingdom;
            
    public void settings(){
        size(screenWidth, screenHeight);
    }
    public void setup(){
        background(255);
        titleFont = createFont("fonts/rogenz.otf", 120);
        subtitleFont = createFont("fonts/rogenz.otf", 50);
        contentFont = createFont("fonts/rogenz.otf", 25);
        descriptionFont = createFont("fonts/rogenz.otf", 17);
        stage = -1;
        this.setupKingdom(new File("Shu.tsv"));
        this.setupKingdom(new File("Wei.tsv"));
        this.setupKingdom(new File("Eastern Wu.tsv"));
        userInfo = loadContents(new File("users.csv"));
        
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
                fill(0);
                text("Enter username: ", 150, 100);
                text(username, 400, 100);
                text("Enter password: ", 150, 150);
                text(placeholder, 400, 150);
                if (correctPassword != null){
                    if (!correctPassword){
                        fill(255, 0, 0);
                        text("Wrong password entered", screenWidth/2, screenHeight/2);
                    }
                }
                fill(255, 0, 0);
                textAlign(CENTER);
                text("Press ENTER after finishing entering please", screenWidth / 2, screenHeight/2 + 150);
                break;
            case 1:
                background(255);
                fill(0);
                textFont(contentFont);
                text("Pick a kingdom to learn about below: ", screenWidth / 2 - 100, screenHeight / 2 - 200);
                String texts = kingdomName[index];
                String imagePathway = "images/" + texts.toLowerCase() + ".png";
                PImage map = loadImage(imagePathway);
                imageMode(CENTER);
                image(map, screenWidth/2, screenHeight/2, map.pixelWidth * mapSizeMultiplier / 100, map.pixelHeight * mapSizeMultiplier / 100);
                break;
            case 2:
                background(255);
                imageMode(CENTER);
                size(screenWidth, screenHeight);
                textFont(descriptionFont);
                textAlign(CENTER);
                kingdom.draw();
                currentTrigger = currentEvent.getTrigger();
                if (!currentTrigger.getTriggered()){
                    if (currentTrigger instanceof DynamicTrigger){
                        DynamicTrigger t = (DynamicTrigger) currentTrigger;
                        for (SketchObject object: t.getObjects()){
                            if (object.getControllable() && mouseX < object.x + object.image.width / 2 && mouseX > object.x - object.image.width / 2
                                    && mouseY < object.y + object.image.height / 2 && mouseY > object.y - object.image.height / 2){
                                fill(255);
                                rectMode(CENTER);
                                rect(object.x - 5, object.y - 5, 90, 32);
                                fill(0);
                                text("Move ME!", object.x, object.y, 80, 17);
                            }
                        }
                        t.trigger();
                    } else{
                        StationaryTrigger t = (StationaryTrigger) currentTrigger;
                        if (mouseX < t.mouseCenterX + t.horizontalOffset && mouseX >  t.mouseCenterX - t.horizontalOffset
                                    && mouseY <  t.mouseCenterY + t.verticalOffset && mouseY >  t.mouseCenterY - t.verticalOffset){
                                fill(255);
                                rectMode(CENTER);
                                rect(t.mouseCenterX - 5, t.mouseCenterY - 5, 170, 37);
                                fill(0);
                                int difference = t.getCounter() - t.getCurrentCount();
                                if (difference > 0){
                                    String str = "Click ME " + difference + (difference > 1 ? " times!" : " time!");
                                    text(str, t.mouseCenterX, t.mouseCenterY, 165, 17);
                                }
                                
                            }
                        t.trigger();
                    }
                }
                if (chosenObject != null) chosenObject.move();
                fill(23, 100, 100);
                rectMode(CORNER);
                rect(screenWidth - 225, screenHeight - 80, 200, 74);
                fill(0);
                textAlign(CENTER);
                textFont(subtitleFont);
                text("Exit", screenWidth - 125, screenHeight - 28); 
                break;
            case 3:
                background(255);
                fill(0);
                textFont(contentFont);
                text("The brief story of " + chosenKingdom + " is over. Please choose one of the options below.", screenWidth/2 - 25, 75, screenWidth - 50, 100);
                fill(23, 100, 100);
                rectMode(CENTER);
                rect(screenWidth/2 - 120, screenHeight/2, 190, 74);
                fill(0);
                textAlign(CENTER);
                textFont(subtitleFont);
                text("Restart", screenWidth/2 - 120, screenHeight/2 + 39 / 2); 
                fill(23, 100, 100);
                rectMode(CENTER);
                rect(screenWidth/2 + 100, screenHeight/2, 150, 74);
                fill(0);
                textAlign(CENTER);
                textFont(subtitleFont);
                text("Exit", screenWidth/2 + 100, screenHeight/2 + 39 / 2); 
                break;
        }
    }
    
    
    
    
    
    
    public void mousePressed(){
        switch (stage){
            case -1:
                if (mouseX < screenWidth/2 + 100 && mouseX > screenWidth/2 - 100 && mouseY < screenHeight/2 + 74 * 3 / 2 && mouseY > screenHeight/2 + 74/2){
                    background(255);
                    stage = 0;
                } 
                break;
            case 1:
                background(255);
                chosenKingdom = kingdomName[index];
                userInfo.put(username, new String[]{password, Integer.toString(DEFAULT_STAGE), chosenKingdom, "0"});
                kingdom = kingdoms.get(index);
                kingdom.setChosen();
                currentEvent = kingdom.getEvents().get(kingdom.getCurrentStatusIndex());
                currentTrigger = currentEvent.getTrigger();
                for (int i = 0; i < 3; i ++){
                    if (i != index){
                        kingdoms.get(i).setInvisible();
                    }
                }
                stage = 2;
                break;
            case 2:
                if (mouseX < screenWidth - 25 && mouseX > screenWidth - 225 && mouseY < screenHeight - 6 && mouseY > screenHeight - 80){
                    background(255);
                    userInfo.put(username, new String[]{password, Integer.toString(stage)});
                    exitGame();
                    exit();
                } else{
                    if (currentTrigger.getAction() == Action.MOVE){
                        SketchObject[] objects = currentTrigger.getObjects();
                        for (SketchObject object: objects){
                            if (mouseX > object.x - object.image.width / 2 && mouseX < object.x + object.image.width / 2 && mouseY > object.y - object.image.height / 2 && mouseY < object.y + object.image.height/2){
                                object.updateClicked();
                                chosenObject = object; 
                                break;
                            }
                        }
                        for (SketchObject object: objects){
                            if (object != chosenObject){
                                object.resetClicked();
                            }
                        }
                    } else{
                        StationaryTrigger trigger = (StationaryTrigger) currentTrigger;
                        if (mouseX > trigger.mouseCenterX - trigger.horizontalOffset && mouseX < trigger.mouseCenterX + trigger.horizontalOffset && mouseY > trigger.mouseCenterY - trigger.verticalOffset && mouseY < trigger.mouseCenterY + trigger.verticalOffset){
                            trigger.updateCurrentCount();
                        }
                    }
                    if (currentTrigger.getTriggered()){
                        updateObject(kingdom, false);
                        currentEvent = kingdom.getEvents().get(kingdom.getCurrentStatusIndex());
                        currentTrigger = currentEvent.getTrigger();
                    }
                }
                break;
            case 3:
                if (mouseX < screenWidth/2 - 25 && mouseX > screenWidth/2 - 215 && mouseY < screenHeight/2 + 39 && mouseY > screenHeight / 2 - 39){
                    stage = 1;
                    index = 0;
                } else if (mouseX > screenWidth/2 + 25 && mouseX < screenWidth/2 + 175 && mouseY < screenHeight/2 + 39 && mouseY > screenHeight / 2 - 39){
                    background(255);
                    userInfo.put(username, new String[]{password, Integer.toString(stage)});
                    exitGame();
                    exit();
                }
                break;
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
                            username = username.length() <= 1 ? "" : username.substring(0, username.length() - 1);
                        } else{
                            password = password.length() <= 1 ? "" : password.substring(0, password.length() - 1);
                            placeholder = placeholder.length() <= 1 ? "" : placeholder.substring(0, placeholder.length() - 1);
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
                                if (stage == 2){
                                    chosenKingdom = userInfo.get(username)[2];
                                    int chosenIndex = Arrays.asList(kingdomName).indexOf(chosenKingdom);
                                    kingdom = kingdoms.get(chosenIndex);
                                    kingdom.setChosen();
                                    kingdom.updateStatus(Status.values()[Integer.parseInt(userInfo.get(username)[3])]);
                                    currentEvent = kingdom.getEvents().get(kingdom.getCurrentStatusIndex());
                                    for (int i = 0; i < kingdoms.size(); i ++){
                                        if (i != chosenIndex){
                                            kingdoms.get(i).setInvisible();
                                        }
                                    }
                                }
                                background(255);
                                correctPassword = true;
                            } else{
                                background(255);
                                textAlign(CENTER);
                                password = "";
                                placeholder = "";
                                count = 1;
                                correctPassword = false;
                            }
                        } else{
                            try{
                            FileWriter fw = new FileWriter("users.csv", true);
                            PrintWriter pw = new PrintWriter(fw);
                            pw.println(username + "," + password + "," + DEFAULT_STAGE);
                            pw.close();
                            userInfo.put(username, new String[]{password, Integer.toString(DEFAULT_STAGE)});
                            } catch (IOException e){
                                text("Error occured while processing", screenWidth/2, screenHeight/2);
                            }
                            stage = DEFAULT_STAGE;
                            background(255);
                        }
                    }
                }
                
                break;
            case 1:
                if (keyCode == LEFT){
                    index --;
                    if (index < 0){
                        index += 3;
                    }
                } else if (keyCode == RIGHT){
                    index = (index + 1) % 3;
                }
                break;
        }
    }
    
    
    
    
    
    
    
    public static void updateObject(SketchObject object, boolean controllable){
        if (object instanceof Kingdom){
            Kingdom kingdom = (Kingdom) object;
            int nextIndex = kingdom.getCurrentStatusIndex() + 1;
            if (nextIndex < kingdom.getEvents().size()) 
                kingdom.updateStatus(Status.values()[nextIndex]);
            else
                stage = 3;
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
                if (Integer.parseInt(content[2]) == 2){
                    userInfo.put(content[0], new String[]{content[1], content[2], content[3], content[4]});
                } else{
                    userInfo.put(content[0], new String[]{content[1], (Integer.parseInt(content[2]) == 3 ? Integer.toString(1) : content[2])});
                }
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
                if (!key.equals(username)){
                    pw.println(key + "," + value[0] + "," + value[1] + (Integer.parseInt(value[1]) == 2 ? ("," + value[2] + "," + value[3]) : ""));
                } else{
                    pw.println(username + "," + password + "," + stage + (stage == 2 ? ("," + chosenKingdom + "," + kingdom.getCurrentStatusIndex()) : ""));
                }
                
            });
            pw.close();
        } catch (IOException e){
            System.out.println("Error occured");
        }
       
    }
    
    
    
    
    
    
    
    
    public void setupKingdom(File file){
        ArrayList<Event> events = new ArrayList<Event>();
        ArrayList<PImage> imagesBefore = new ArrayList<PImage>(), imagesAfter = new ArrayList<PImage>();
        try{
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                String line = scan.nextLine();
                String[] contents = line.split("\t");
                Action action = Action.valueOf(contents[0].toUpperCase());
                if (!contents[1].isEmpty()){
                    imagesBefore.add(loadImage(contents[1]));
                } else{
                    imagesBefore.add(null);
                }
                if (!contents[2].isEmpty()){
                    imagesAfter.add(loadImage(contents[2]));
                } else{
                    imagesAfter.add(null);
                }
                int numControllable = 0;
                if (action == Action.MOVE){
                    int arraySize = Integer.parseInt(contents[3]);
                    int[] centerPositions;
                    SketchObject[] objects = new SketchObject[arraySize];
                    for (int i = 0; i < arraySize; i ++){
                        String imagePathway = contents[3 + 5 * i + 1];
                        int x = Integer.parseInt(contents[3 + 5 * i + 2]);
                        int y = Integer.parseInt(contents[3 + 5 * i + 3]);
                        int sizeFactor = Integer.parseInt(contents[3 + 5 * i + 4]);
                        boolean controllable = Boolean.parseBoolean(contents[3 + 5 * i + 5]);
                        numControllable = controllable ? numControllable + 1 : numControllable;
                        objects[i] = new SketchObject(this, x, y, loadImage(imagePathway), controllable, sizeFactor);
                    }
                    centerPositions = new int[numControllable * 2];
                    int startingIndex = 4 + 5 * arraySize;
                    for (int i = 0; i < centerPositions.length; i++){
                        centerPositions[i] = Integer.parseInt(contents[startingIndex + i]);
                    }
                    int horizontalOffset = Integer.parseInt(contents[4 + 5 * arraySize + centerPositions.length]);
                    int verticalOffset = (4 + 5 * arraySize + centerPositions.length) < contents.length - 2 ? 
                            Integer.parseInt(contents[5 + 5 * arraySize + centerPositions.length]) : horizontalOffset;
                    String description = contents[contents.length - 1];
                    events.add(new Event(new DynamicTrigger(objects, action, horizontalOffset, verticalOffset, centerPositions), description));
                } else{
                    int counter = Integer.parseInt(contents[3]);
                    int mouseCenterX = Integer.parseInt(contents[4]);
                    int mouseCenterY = Integer.parseInt(contents[5]);
                    int horizontalOffset = Integer.parseInt(contents[6]);
                    int verticalOffset = 7 < contents.length - 1 ? Integer.parseInt(contents[7]) : horizontalOffset;
                    String description = contents[contents.length - 1];
                    events.add(new Event(new StationaryTrigger(action, horizontalOffset, verticalOffset, mouseCenterX, mouseCenterY, counter, 0), description));
                } 
            }
        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }
        String fileName = file.getName();
        String mapName = "images/" + fileName.substring(0, fileName.length() - 3).toLowerCase() + "png";
        kingdoms.add(new Kingdom(this, loadImage(mapName), imagesBefore, imagesAfter, events));
    }
    
    
}