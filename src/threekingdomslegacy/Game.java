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
    //Class variables
    private static int stage = -1;
    private static int index = 0;
    private final int screenWidth = 700, screenHeight = 500;
    private PFont titleFont, subtitleFont, contentFont, descriptionFont;
    private static String username = "";
    private static String password = "";
    private String placeholder = "";
    private boolean finishUsername = false;
    private int count = 0; //num of ENTER's
    private static String chosenKingdom = "";
    private static ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
    private final int DEFAULT_STAGE = 1;
    private static LinkedHashMap<String, String[]> userInfo = new LinkedHashMap();
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
        //Initialize the background of the screen, the fonts that will be used, the stage, the kingdom and userInfo ArrayLists
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
        //A switch for items that will be drawn on the screen
        switch (stage){
            //At stage -1, the title of the game along with a start box for the user to click on
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
            //At stage 0, the user will be prompted for their username and password
            //Things that will be drawn: "Enter username: " and "Enter password: " texts in black, "Press ENTer after finishing entering please" text in red
            case 0:
                background(255);
                textFont(contentFont);
                textAlign(LEFT);
                fill(0);
                text("Enter username: ", 150, 100);
                text(username, 400, 100);
                text("Enter password: ", 150, 150);
                text(placeholder, 400, 150);
                //If the Boolean object correctPassword isn't null and is false, "Wrong password entered" text will be drawn in red in the center of the screen
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
            //At stage 1, maps will be drawn on the screen with text "Pick a kingdom to learn about below: " in black on the top of the screen
            //The map that will be drawn on the screen will be controlled by keyboard
            case 1:
                background(255);
                fill(0);
                textFont(contentFont);
                text("Pick a kingdom to learn about below: ", screenWidth / 2 - 100, screenHeight / 2 - 200);
                String texts = kingdomName[index];
                //Define the map image pathway based on the kingdomName controlled by the index
                String imagePathway = "images/" + texts.toLowerCase() + ".png";
                PImage map = loadImage(imagePathway);
                //Defines that the coordinates inputted in image() method is the center of the image
                //Draw the map modified by sizeMultiplier on the screen
                imageMode(CENTER);
                image(map, screenWidth/2, screenHeight/2, map.pixelWidth * mapSizeMultiplier / 100, map.pixelHeight * mapSizeMultiplier / 100);
                break;
            //At stage 2, in which a kingdom is chosen by the user, 
            case 2:
                background(255);
                //Defines that the coordinates inputted in image() is the center of the image and the size of the image is equivalent to the size of the screen
                imageMode(CENTER);
                size(screenWidth, screenHeight);
                //Defines the font of the text drawn on the screen and it aligns with the center of the textbox
                textFont(descriptionFont);
                textAlign(CENTER);
                //Draws the object on the screen
                kingdom.draw();
                currentTrigger = currentEvent.getTrigger();
                //If the user hasn't completed the required interactions...
                if (!currentTrigger.triggered){
                    //If the trigger is an instance of DynamicTrigger...
                    if (currentTrigger instanceof DynamicTrigger){
                        //If the mouse is on the images of the objects that are involved in the interaction, draw a box with instructions in its center
                        DynamicTrigger t = (DynamicTrigger) currentTrigger;
                        for (SketchObject object: t.objects){
                            if (object.getControllable() && mouseX < object.x + object.image.width / 2 && mouseX > object.x - object.image.width / 2
                                    && mouseY < object.y + object.image.height / 2 && mouseY > object.y - object.image.height / 2){
                                fill(255);
                                //Defines that the coordinates inputted for rect() is the center of the rectangle rather than corner
                                rectMode(CENTER);
                                rect(object.x - 5, object.y - 5, 90, 32);
                                fill(0);
                                text("Move ME!", object.x, object.y, 80, 17);//Instruction
                            }
                        }
                        t.trigger();//Call trigger() method such that the triggered variable can be modified accordingly
                    //If the trigger is an instance of StationaryTrigger...
                    } else{
                        //If the mouse is on the image of the objects that are involved in the interactions, draw a box with instructors in its center
                        StationaryTrigger t = (StationaryTrigger) currentTrigger;
                        if (mouseX < t.mouseCenterX + t.horizontalOffset && mouseX >  t.mouseCenterX - t.horizontalOffset
                                    && mouseY <  t.mouseCenterY + t.verticalOffset && mouseY >  t.mouseCenterY - t.verticalOffset){
                                fill(255);
                                rectMode(CENTER);
                                rect(t.mouseCenterX - 5, t.mouseCenterY - 5, 170, 37);
                                fill(0);
                                //Get the number of times the user has to click on the screen and then draw the instruction on the screen
                                int difference = t.getCounter() - t.getCurrentCount();
                                if (difference > 0){
                                    String str = "Click ME " + difference + (difference > 1 ? " times!" : " time!");
                                    text(str, t.mouseCenterX, t.mouseCenterY, 165, 17);
                                }
                                
                            }
                        t.trigger(); //Call trigger() method such that the triggered variable can be modified accordingly
                    }
                }
                //If the chosenObject is not null, call move() method
                if (chosenObject != null) chosenObject.move();
                //Draw the exit box so that the user can pause whenever they want in this stage
                fill(23, 100, 100);
                rectMode(CORNER);
                rect(screenWidth - 225, screenHeight - 80, 200, 74);
                fill(0);
                //Defines that the text is aligned to the center of the textbox
                textAlign(CENTER);
                textFont(subtitleFont);
                text("Exit", screenWidth - 125, screenHeight - 28); 
                break;
            //At stage 3, the user is allowed to choose between restarting or exiting the program
            case 3:
                background(255);
                //Draw the text on the screen
                fill(0);
                textFont(contentFont);
                text("The brief story of " + chosenKingdom + " is over. Please choose one of the options below.", screenWidth/2 - 25, 75, screenWidth - 50, 100);
                //Draw the restart box on the screen
                fill(23, 100, 100);
                rectMode(CENTER);
                rect(screenWidth/2 - 120, screenHeight/2, 190, 74);
                fill(0);
                textAlign(CENTER);
                textFont(subtitleFont);
                text("Restart", screenWidth/2 - 120, screenHeight/2 + 39 / 2); 
                
                //Draw the exit box on the screen
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
        //A switch for operation taken when the mouse is clicked
        switch (stage){
            //At stage -1, if the user clicks on the start box, the screen will be cleared and the stage will be transferred to 0
            case -1:
                if (mouseX < screenWidth/2 + 100 && mouseX > screenWidth/2 - 100 && mouseY < screenHeight/2 + 74 * 3 / 2 && mouseY > screenHeight/2 + 74/2){
                    background(255);
                    stage = 0;
                } 
                break;
            //At stage 1, if the user clicks on the screen, the kingdom whose map is shown on the screen will be chosen
            case 1:
                background(255);
                chosenKingdom = kingdomName[index];
                //Append user's choice along with their username and password to the LinkedHashMap
                userInfo.put(username, new String[]{password, Integer.toString(DEFAULT_STAGE), chosenKingdom, "0"});
                //Set the current kingdom object in the ArrayList to be chosen, get the event at current status index in the Arraylist and its corresponding Trigger
                kingdom = kingdoms.get(index);
                kingdom.setChosen();
                currentEvent = kingdom.getEvents().get(kingdom.getCurrentStatusIndex());
                currentTrigger = currentEvent.getTrigger();
                //For other kingdoms in the ArrayList, set them to invisible
                for (int i = 0; i < 3; i ++){
                    if (i != index){
                        kingdoms.get(i).setInvisible();
                    }
                }
                //Transfer to stage 2
                stage = 2;
                break;
            //At stage 2...
            case 2:
                //If the mouse clicks on the exit box, corresponding userInfo would be appended to the LinkedHashMap and the game will be exitted
                if (mouseX < screenWidth - 25 && mouseX > screenWidth - 225 && mouseY < screenHeight - 6 && mouseY > screenHeight - 80){
                    background(255);
                    userInfo.put(username, new String[]{password, Integer.toString(stage)});
                    exitGame();
                    exit();
                //Otherwise...
                } else{
                    //If the user is asked to move objects to their desired positions, if a mouse is clicked on any of the objects, that object will be chosen
                    //Only the chosen object can be moved and the others will be set to not chosen
                    if (currentTrigger.getAction() == Action.MOVE){
                        SketchObject[] objects = currentTrigger.objects;
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
                    //If the user is asked to click on something instead, once the mouse is clicked on the desired positions, updateCurrentCount() is called
                    } else{
                        StationaryTrigger trigger = (StationaryTrigger) currentTrigger;
                        if (mouseX > trigger.mouseCenterX - trigger.horizontalOffset && mouseX < trigger.mouseCenterX + trigger.horizontalOffset && mouseY > trigger.mouseCenterY - trigger.verticalOffset && mouseY < trigger.mouseCenterY + trigger.verticalOffset){
                            trigger.updateCurrentCount();
                        }
                    }
                    //If the trigger has been triggered, updateObject() is called on the kingdom object.
                    //Event and Trigger objects will be updated correspondingly
                    if (currentTrigger.triggered){
                        updateObject(kingdom, false);
                        currentEvent = kingdom.getEvents().get(kingdom.getCurrentStatusIndex());
                        currentTrigger = currentEvent.getTrigger();
                    }
                }
                break;
            //At stage 3, if the user clicks on the restart box, stage would be set to 1 and index would be reset to 0
            case 3:
                if (mouseX < screenWidth/2 - 25 && mouseX > screenWidth/2 - 215 && mouseY < screenHeight/2 + 39 && mouseY > screenHeight / 2 - 39){
                    stage = 1;
                    index = 0;
                //If the user clicks on the exit box instead, users choices will be updated in the userInfo LinkedHashMap and the game would be exitted
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
        //A switch for operation taken when a key is pressed
        switch(stage){
            //At stage 0, user can enter and delete their username and password
            case 0:
                //A series of letters and numbers entered before the ENTER key is pressed for the first time would be taken as the user's username
                if (keyCode != ENTER){
                    if (keyCode != CODED){
                        if (count == 0){
                            username += (Character.isLetter(key) || Character.isDigit(key)) ? key : "";
                        //A series of letters and numbers before the ENTER key is pressed for the second time would be taken as the user's password
                        //To protect user's privacy, their password would be displayed as a series of 0's on the screen
                        } else{
                            password += (Character.isLetter(key) || Character.isDigit(key)) ? key : "";
                            placeholder += (Character.isLetter(key) || Character.isDigit(key)) ? 0 : "";
                        }
                    }
                    //If the user presses BACKSPACE, the last character of the inputted series of letters or numbers would be deleted
                    //Correspondingly, the length of 0's would be reduced by 1
                    if (keyCode == BACKSPACE){
                        if (count == 0){
                            username = username.length() <= 1 ? "" : username.substring(0, username.length() - 1);
                        } else{
                            password = password.length() <= 1 ? "" : password.substring(0, password.length() - 1);
                            placeholder = placeholder.length() <= 1 ? "" : placeholder.substring(0, placeholder.length() - 1);
                        }
                    }
                //If the ENTER key is pressed...
                //If the number of times the ENTER key have been pressed is less than 2 and the required area for inputs (usernamae and password) isn't empty,
                //it would be considered as a valid input, therefore, the count variable would be incremented by 1
                } else{
                    if ((count == 0 && !username.isEmpty()) || (count == 1 && !password.isEmpty())){
                        count ++;
                    }
                    //If the ENTER key is pressed twice, reset the count variable to 0
                    if (count == 2){
                        count = 0;
                        //If the username entered can be found in the userInfo LinkedHashMap...
                        //check if the inputted password is equivalent to the password entered when this account is created.
                        //If the password is correct, set stage variable to the value stored in the LinkedHashMap
                        //If the stage value stored in the LinkedHashMap is equivalent to 2, get the kingdom the user has chosen in the previous time and modify the variables accordingly
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
                                    //Set other kingdoms that are not chosen invisible
                                    for (int i = 0; i < kingdoms.size(); i ++){
                                        if (i != chosenIndex){
                                            kingdoms.get(i).setInvisible();
                                        }
                                    }
                                }
                                background(255);
                                //Set correctPassword variable to true so that the "Wrong Password" text won't be displayed on the screen
                                correctPassword = true;
                            //If the password is incorrect, the inputted password would be emptied (so is the series of 0's)
                            //and count variable would be set to 1 to prompt the user for the password again
                            } else{
                                background(255);
                                textAlign(CENTER);
                                password = "";
                                placeholder = "";
                                count = 1;
                                //Set correctPassword variable to false so that the "Wrong Password" text will be displayed on the screen
                                correctPassword = false;
                            }
                        //If there isn't an account with such username, write the new username and password to the file and append it to userInfo LinkedHashMap
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
                            //Set stage to DEFAULT_STAGE for the program to run smoothly
                            stage = DEFAULT_STAGE;
                            background(255);
                        }
                    }
                }
                break;
            //At stage 1, user is allowed to control the map they see on the screen by pressing left or right key
            case 1:
                //If the left key is pressed, the value of index variable would be decreased by 1
                //If the value is less then 0, meaning it would be out of bound, increment it by 3
                if (keyCode == LEFT){
                    index --;
                    if (index < 0){
                        index += 3;
                    }
                //If the right key is pressed, the value of index variable would be incremented by 1
                //The mod 3 of that value would be taken
                } else if (keyCode == RIGHT){
                    index = (index + 1) % 3;
                }
                break;
        }
    }
    
    /**
     * This method updates the status of a SketchObject object
     * @param object
     * @param controllable 
     */
    public static void updateObject(SketchObject object, boolean controllable){
        //If the SketchObject is an instance of Kingdom class, its status would be updated according to their current status's index in the Status enum
        //If the current one plus 1 (the next status's index in the Status enum) is less than the size of hte ArrayList of Events this kingdom has,
        //the Kingdom object would be updated accordingly.
        //Otherwise, the stage variable would be incremented by 1.
        if (object instanceof Kingdom){
            Kingdom kingdom = (Kingdom) object;
            int nextIndex = kingdom.getCurrentStatusIndex() + 1;
            if (nextIndex < kingdom.getEvents().size()) 
                kingdom.updateStatus(Status.values()[nextIndex]);
            else
                stage = 3;
        //If the SketchObject is not an instance of Kingdom class, the object would be simply set to not controllable
        } else{
            object.updateControllable(controllable);
        }
        //Draw the SketchObject object on the screen
        object.draw();
    }
    
    /**
     * This method reads and modifies contents in the File object and store them in a LinkedHashMap
     * @param file
     * @return LinkedHashMap<String, String[]> userInfo
     */
    public static LinkedHashMap<String, String[]> loadContents(File file){
        //Declare and initialize a LinkedHashMap with a String variable as keyword and a String array as value
        LinkedHashMap<String, String[]> users = new LinkedHashMap<String, String[]>();
        try{
            //Scans through the File object, append the contents in the file until it has no next lines
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                String[] content = scan.nextLine().split(",");
                //If the last stage this account is at is 2, meaning they've already chosen a kingdom and started to learn about their story
                if (Integer.parseInt(content[2]) == 2){
                    //Append the password, the stage, the chosen kingdom and the last status of the kingdom they've viewed to the LinkedHashMap along with the username
                    users.put(content[0], new String[]{content[1], content[2], content[3], content[4]});
                //Otherwise, append only the password and the stage to the LinkedHashMap under the username
                } else{
                    //If the user's last stage was at 3, get the user to start at stage 1 instead of 3 this time
                    users.put(content[0], new String[]{content[1], (Integer.parseInt(content[2]) == 3 ? Integer.toString(1) : content[2])});
                }
            }
        } catch(FileNotFoundException e){
            System.out.println("File Not Found!!!");
        }
        return users;
    }
    
    /**
     * This method writes the updated values in the LinkedHashMap back to the file
     */
    public static void exitGame(){
        try{
            FileWriter fw = new FileWriter("users.csv", false);
            PrintWriter pw = new PrintWriter(fw);
            userInfo.forEach((key, value) -> {
                //If the user is currently logged in, meaning their values would be changed, write the current information stored in the LinkedHashMap to the file.
                //Otherwise, write the stored information to the file as they are most likely not modified during the process
                if (!key.equals(username)){
                    //If the user has reached stage 2 and chosen their kingdom, write this information to the file for next time usage
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
    
    /**
     * This method loads all information regarding a kingdom from the File object to an ArrayList of Kingdom objects
     * @param file 
     */
    public void setupKingdom(File file){
        //Declare and initialize some ArrayLists
        ArrayList<Event> events = new ArrayList<Event>();
        ArrayList<PImage> imagesBefore = new ArrayList<PImage>(), imagesAfter = new ArrayList<PImage>();
        try{
            //Scan through the File object, append stored information to each of the ArrayLists with modifications until there's no next line in the file
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                //Split the line by tabs
                String line = scan.nextLine();
                String[] contents = line.split("\t");
                //Get the required action to trigger each event (represented by a line in the file) by the value in 0th first index
                Action action = Action.valueOf(contents[0].toUpperCase());
                //If no image pathway is mentioned in the line at first and second index, add a null PImage object to the ArrayList imagesBefore
                //Otherwise, load the image from the pathway and add it to the ArrayList
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
                //If the action required is to move an object to its desired positions
                if (action == Action.MOVE){
                    //Declare and initialize variables and arrays that will be used in the following blocks of code
                    int arraySize = Integer.parseInt(contents[3]);
                    int[] centerPositions;
                    SketchObject[] objects = new SketchObject[arraySize];
                    int numControllable = 0;
                    //For the following SketchObject that are involved in the reaction, each would be represented by 5 parameters: image pathway, x and y positions (center),
                    //image size factor and controllable or not
                    //Get the information from the file and store them to corresponding variables
                    //Increment numControllable variable by 1 if the object is controllable (this will be used later on)
                    for (int i = 0; i < arraySize; i ++){
                        String imagePathway = contents[3 + 5 * i + 1];
                        int x = Integer.parseInt(contents[3 + 5 * i + 2]);
                        int y = Integer.parseInt(contents[3 + 5 * i + 3]);
                        int sizeFactor = Integer.parseInt(contents[3 + 5 * i + 4]);
                        boolean controllable = Boolean.parseBoolean(contents[3 + 5 * i + 5]);
                        numControllable = controllable ? numControllable + 1 : numControllable;
                        objects[i] = new SketchObject(this, x, y, loadImage(imagePathway), controllable, sizeFactor); //Instantiate the SketchObject at i-th index in the array
                    }
                    //The array of desired x and y coordinates (center) will be storeed starting from the (4 + 5 * arraySize)th index
                    //And since x and y coordinates come in pair, the size of the array would be twice the num of objects in the SketchObject array that is controllable
                    centerPositions = new int[numControllable * 2];
                    int startingIndex = 4 + 5 * arraySize;
                    //Get the information stored in the contents array and store them into the centerPositions array
                    for (int i = 0; i < centerPositions.length; i++){
                        centerPositions[i] = Integer.parseInt(contents[startingIndex + i]);
                    }
                    //The allowed horizontal and vertical offsets would be stated before the description for each event
                    //If there's only one number before the description and after the desired x and y coordinates for each controllable SketchObject object
                    //set both horizontal and vertical offset to that value
                    //Otherwise, set the horizontal offset to the first value and the vertical offset to the second valud
                    int horizontalOffset = Integer.parseInt(contents[4 + 5 * arraySize + centerPositions.length]);
                    int verticalOffset = (4 + 5 * arraySize + centerPositions.length) < contents.length - 2 ? 
                            Integer.parseInt(contents[5 + 5 * arraySize + centerPositions.length]) : horizontalOffset;
                    //Get the description at last
                    String description = contents[contents.length - 1];
                    //Add the Event object to the ArrayList of Event with all the information
                    events.add(new Event(new DynamicTrigger(objects, action, horizontalOffset, verticalOffset, centerPositions), description));
                //If the action is to click on something instead...
                } else{
                    //The information required would be stored in the following order in the file:
                    //the number of times required to click, the x and y coordinates (center), the horizontal and vertical offsets that are accepted, description of the event
                    //If there's only one number before the description and after the desired x and y coordinates for each controllable SketchObject object
                    //set both horizontal and vertical offset to that value
                    //Otherwise, set the horizontal offset to the first value and the vertical offset to the second valud
                    int counter = Integer.parseInt(contents[3]);
                    int mouseCenterX = Integer.parseInt(contents[4]);
                    int mouseCenterY = Integer.parseInt(contents[5]);
                    int horizontalOffset = Integer.parseInt(contents[6]);
                    int verticalOffset = 7 < contents.length - 1 ? Integer.parseInt(contents[7]) : horizontalOffset;
                    String description = contents[contents.length - 1];
                    //Add the Event object to the ArrayList of Event with all the information
                    events.add(new Event(new StationaryTrigger(action, horizontalOffset, verticalOffset, mouseCenterX, mouseCenterY, counter, 0), description));
                } 
            }
        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }
        //The file name of the kingdom would be used to get the image pathway to the map of the kingdom
        String fileName = file.getName();
        String mapName = "images/" + fileName.substring(0, fileName.length() - 3).toLowerCase() + "png";
        //With all information gathered, a Kingdom object can be created and added to the kingdoms ArrayList
        kingdoms.add(new Kingdom(this, loadImage(mapName), imagesBefore, imagesAfter, events));
    }
    
    
}