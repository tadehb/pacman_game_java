/* Tadeh Boghoz Ahmad Abadi
 * Class: Java Programming
 * Instructor: Zareh Gorjian
 * Date:04/26/2015
 * Assignment14-finall
 */
package Pacman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Game extends Application {
	
	Random rnd = new Random();
	Timeline timeline = new Timeline();

	
    Pane playfieldLayer;
    Pane scoreLayer;

    Image playerImage;
    Image enemyImage1,enemyImage2,enemyImage3,enemyImage4,blueEnemy;
    Image Pacmandead;
    Image BG_Maze;
    Image Dot;
    Image BonusRing;
    Image redwall;
    
  
    Label lable1,lable2,lable3;
    Enemy Pinky;
    Enemy Inky;
    Enemy Blinky;
    Enemy Clyde;
    
    int score = 0;
    int health=3;
    int power=4; 

    
    int theRandomNumber;
    
    double r;
    Player player;
    
    MySounds mySounds,chomp;

   
    List<Enemy> enemies = new ArrayList<>();
    List<Walls> walls = new ArrayList<>();
    List<Dots> dots = new ArrayList<>();
    List<Bonus> bonuses = new ArrayList<>();
    List<Walls> leftwall = new ArrayList<>();
    List<Walls> rightwall = new ArrayList<>();
    
    ArrayList<Walls> walls2 = new ArrayList<>();
    ArrayList<Walls> walls3 = new ArrayList<>();
    ArrayList<Walls> walls4 = new ArrayList<>();
   

    Text collisionText = new Text();
    boolean collision1 = false;
    boolean collision2 = false;
    boolean collision3 = false;
    boolean collision4 = false;
    boolean fleemode= false;
    boolean powerCollision = false;
   

    Scene scene;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);
        
        BG_Maze = new Image ("images/PacManMaze.jpg");  // read the image in
        ImageView imageView = new ImageView (BG_Maze); // create an ImageView object to hold this image.
		
		
		Text text1 = new Text(10,25, "Scores:");
		text1.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 20));
		text1.setFill(Color.RED);
		Text text2 = new Text(300,570, "Healths:");
		text2.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 20));
		text2.setFill(Color.RED);
		Text text3 = new Text(50,570, "Power:");
		text3.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 20));
		text3.setFill(Color.RED);
		
		lable1 = new Label();
        lable1.relocate(90, 5);
        lable1.setPrefSize(200, 25);
        lable1.setStyle(STYLESHEET_CASPIAN);
		lable1.setTextFill(Color.RED);
		lable1.setAlignment(Pos.BASELINE_RIGHT);
		lable1.setPrefSize(40, 25);
		lable1.textOverrunProperty();
		lable1.setFont(Font.font ("Digital-7", 16));
		lable1.setStyle("-fx-background-color:black;-fx-border-color: black;");
		lable1.setText(Integer.toString(score));
		
		
		lable2 = new Label();
        lable2.relocate(400,550);
        lable2.setPrefSize(200,25);
        lable2.setStyle(STYLESHEET_CASPIAN);
		lable2.setTextFill(Color.RED);
		lable2.setAlignment(Pos.BASELINE_RIGHT);
		lable2.setPrefSize(40, 25);
		lable2.textOverrunProperty();
		lable2.setFont(Font.font ("Digital-7", 16));
		lable2.setStyle("-fx-background-color:black;-fx-border-color: black;");
		lable2.setText("3");
		
		lable3 = new Label();
        lable3.relocate(120,550);
        lable3.setPrefSize(20,25);
        lable3.setStyle(STYLESHEET_CASPIAN);
		lable3.setTextFill(Color.RED);
		lable3.setAlignment(Pos.BASELINE_RIGHT);
		lable3.setPrefSize(40, 25);
		lable3.textOverrunProperty();
		lable3.setFont(Font.font ("Digital-7", 16));
		lable3.setStyle("-fx-background-color:black;-fx-border-color: black;");
		lable3.setText("4");
		
		
		
		
		playfieldLayer.getChildren().addAll(imageView,text1,text2,text3,lable1,lable2,lable3); 

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene( scene);
        primaryStage.setTitle("Tadeh Boghosian _ Pacman Game");
        primaryStage.show();
        primaryStage.setResizable(false);
        
        mySounds = new MySounds();
        
        mySounds.playClip(1);

        loadGame();

        createScoreLayer();
        createPlayers();

        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
            	player.processInput();

                // add random enemies
               // spawnEnemies( true);

                // movement
                player.move();
                
                enemies.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();

                // update sprites in scene
                player.updateUI();
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);

                // update score, health, etc
                updateScore();
                checkCollisions();
            }

        };
        gameLoop.start();

    }

    private void loadGame() {
        playerImage = new Image("images/pacman1.jpg");
       
        enemyImage1 = new Image("images/GhostRed.png");
        enemyImage2= new Image ("images/GhostPink.png");
        enemyImage3= new Image ("images/GhostBlue.png");
        enemyImage4= new Image ("images/GhostOrange.png");
        blueEnemy = new Image ("images/BlueGhost.png");
        BonusRing = new Image ("images/BonusRing.png");
        Dot = new Image("images/dot.png");
        
    }

    private void createScoreLayer() {


        collisionText.setFont( Font.font( null, FontWeight.BOLD, 30));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        scoreLayer.getChildren().add( collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        
        collisionText.relocate(150, 300);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);


    }
    private void createPlayers() {

        // player input
        Input input = new Input( scene);
        redwall = new Image ("images/redwall.png");

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over
        
        Walls wall1 = new Walls(playfieldLayer, null, 8, 42, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 447,13);walls.add(wall1);
        Walls wall2 = new Walls(playfieldLayer, null, 222,55, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,22,62);walls.add(wall2);
        Walls wall3 = new Walls(playfieldLayer, null, 220,55, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,23,63);walls.add(wall3);
        Walls wall4 = new Walls(playfieldLayer, null, 274,84, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,63,33);walls.add(wall4);
        Walls wall5 = new Walls(playfieldLayer, null, 368,84, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 49,32);walls.add(wall5);
        Walls wall6 = new Walls(playfieldLayer, null, 8,42, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 447,13);walls.add(wall6);
        Walls wall7 = new Walls(playfieldLayer, null, 7, 42, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 12,165);walls.add(wall7);
        Walls wall8 = new Walls(playfieldLayer, null, 5,195, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 93,15);walls.add(wall8);
        Walls wall9 = new Walls(playfieldLayer,null, 80,195, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,17,64);walls.add(wall9);
        Walls wall10 = new Walls(playfieldLayer, null,5,248, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,91,13);walls.add(wall10);
        Walls wall11 = new Walls(playfieldLayer, null,5,291, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,91,15);walls.add(wall11);
        Walls wall12 = new Walls(playfieldLayer, null, 85,291, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,13,66);walls.add(wall12);
        Walls wall13 = new Walls(playfieldLayer, null,9,343, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,89,15);walls.add(wall13);
        Walls wall14 = new Walls(playfieldLayer, null,4,346, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 14,198);walls.add(wall14);
        Walls wall15 = new Walls(playfieldLayer, null,5,528, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 454,14);walls.add(wall15);
        Walls wall16 = new Walls(playfieldLayer, null,448,344, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,14,199);walls.add(wall16);
        Walls wall17 = new Walls(playfieldLayer, null,368,343, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,91,15);walls.add(wall17);
        Walls wall18 = new Walls(playfieldLayer, null,366,292, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,15,66);walls.add(wall18);
        Walls wall19 = new Walls(playfieldLayer, null,369,291, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,90,13);walls.add(wall19);
        Walls wall20 = new Walls(playfieldLayer, null,368,247, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,91,15);walls.add(wall20);
        Walls wall21 = new Walls(playfieldLayer, null,367,196, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,15,66);walls.add(wall21);
        Walls wall22 = new Walls(playfieldLayer, null,368,195, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,92,12);walls.add(wall22);
        Walls wall23 = new Walls(playfieldLayer, null,446,42, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,14,165);walls.add(wall23);
        Walls wall24= new Walls(playfieldLayer, null,49,148, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0, 50,19);walls.add(wall24);
        Walls wall25 = new Walls(playfieldLayer, null,128,148, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,20,110);walls.add(wall25);
        Walls wall26 = new Walls(playfieldLayer,null,149,194, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,44,20);walls.add(wall26);
        Walls wall27 = new Walls(playfieldLayer,null,177,147, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,113,18);walls.add(wall27);
        Walls wall28 = new Walls(playfieldLayer, null,222,167, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,20,46);walls2.add(wall28);
        Walls wall29 = new Walls(playfieldLayer, null,319,149, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,18,113);walls.add(wall29);
        Walls wall30 = new Walls(playfieldLayer, null,274,194, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,46,20);walls.add(wall30);
        Walls wall31 = new Walls(playfieldLayer, null,368,146, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,52,19);walls.add(wall31);
       
        Walls wall33 = new Walls(playfieldLayer, null,126,294, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,20,66);walls.add(wall33);
        Walls wall34 = new Walls(playfieldLayer, null,175,340, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,114,18);walls.add(wall34);
        Walls wall35 = new Walls(playfieldLayer, null,220,358, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,22,48);walls.add(wall35);
        Walls wall36 = new Walls(playfieldLayer, null,318,292, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,21,66);walls.add(wall36);
        Walls wall37 = new Walls(playfieldLayer, null,47,386, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,51,20);walls.add(wall37);
        Walls wall38 = new Walls(playfieldLayer, null,80,390, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,18,64);walls.add(wall38);
        Walls wall39 = new Walls(playfieldLayer, null,129,386, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,64,20);walls.add(wall39);
        Walls wall40 = new Walls(playfieldLayer, null,269,386, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,67,19);walls.add(wall40);
        Walls wall41 = new Walls(playfieldLayer, null,367,386, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,52,19);walls.add(wall41);
        Walls wall42 = new Walls(playfieldLayer, null,365,388, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,21,66);walls.add(wall42);
        Walls wall43= new Walls(playfieldLayer, null, 126,434, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,20,52);walls.add(wall43);
        Walls wall44 = new Walls(playfieldLayer, null,48,483, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,145,19);walls.add(wall44);
        Walls wall45 = new Walls(playfieldLayer, null,176,435, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,113,18);walls.add(wall45);
        Walls wall46 = new Walls(playfieldLayer, null,221,454, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,21,47);walls.add(wall46);
        Walls wall47 = new Walls(playfieldLayer, null,320,435, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,18,50);walls.add(wall47);
        Walls wall48 = new Walls(playfieldLayer, null,273,483, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,144,19);walls.add(wall48);
        Walls wall49 = new Walls(playfieldLayer, null,175,339, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,115,20);walls.add(wall49);
        Walls wall50 = new Walls(playfieldLayer, null,49,85, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,47,32);walls.add(wall50);
        Walls wall51 = new Walls(playfieldLayer, null,130,85, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,63,33);walls.add(wall51);
        Walls wall52 = new Walls(playfieldLayer, null,374,42, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,76,13);walls.add(wall52);
        Walls wall53 = new Walls(playfieldLayer, null,378,530, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,76,13);walls.add(wall53);
        Walls wall54 = new Walls(playfieldLayer, null,20,435, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,30,19);walls.add(wall54);
        Walls wall55 = new Walls(playfieldLayer, null,415,435, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,30,19);walls.add(wall55);
        Walls wall56 = new Walls(playfieldLayer, null,176,301, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,114,9);walls.add(wall56);
        Walls wall57 = new Walls(playfieldLayer, null,176,244, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,8,66);walls.add(wall57);
        Walls wall58 = new Walls(playfieldLayer, null,282,244, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,8,66);walls.add(wall58);
        Walls wall59 = new Walls(playfieldLayer, null,176,244, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,33,8);walls.add(wall59);
        Walls wall60 = new Walls(playfieldLayer, null,257,244, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,33,8);walls.add(wall60);
        Walls wall61 = new Walls(playfieldLayer, null,6,262, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,3,30);leftwall.add(wall61);
        Walls wall62 = new Walls(playfieldLayer, null,457,262, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,5,29);rightwall.add(wall62);
        Walls wall63 = new Walls(playfieldLayer, null,85,261, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,6,31);walls3.add(wall63);
        Walls wall64 = new Walls(playfieldLayer, null,372,260, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,7,32);walls3.add(wall64);
        Walls wall65 = new Walls(playfieldLayer, null,208,245, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,48,9);walls4.add(wall65);
        Walls wall66 = new Walls(playfieldLayer, null,222,167, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 0, 0, 0, 0,20,46);walls4.add(wall66);
;        
        
      
        
        Image Dotimage = Dot;
        Image Bonus = BonusRing;
        
      
       Bonus bonus1 = new Bonus(playfieldLayer, Bonus, 25, 60, 0, 0, 0, 0, 0, 0, Duration.millis(500),4,4, 0, 0,23.5,23.5);bonuses.add(bonus1);
       bonus1.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
       bonus1.play();          
       Dots dot2 = new Dots(playfieldLayer, Dotimage, 30, 100, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot2);
       Dots dot3 = new Dots(playfieldLayer, Dotimage, 30, 125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot3);
       Dots dot4 = new Dots(playfieldLayer, Dotimage, 30, 180, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot4);
       Dots dot5 = new Dots(playfieldLayer, Dotimage, 70, 180, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot5);
       Dots dot6 = new Dots(playfieldLayer, Dotimage, 110, 180, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot6);
       Dots dot7 = new Dots(playfieldLayer, Dotimage, 110, 220, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot7);
       Dots dot8 = new Dots(playfieldLayer, Dotimage, 110, 260, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot8);
       Dots dot9 = new Dots(playfieldLayer, Dotimage, 110, 300, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot9);
       Dots dot10 = new Dots(playfieldLayer, Dotimage, 110, 340, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot10);
       Dots dot11 = new Dots(playfieldLayer, Dotimage, 110, 370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot11);
       Dots dot12 = new Dots(playfieldLayer, Dotimage, 70, 370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot12);
       Dots dot13 = new Dots(playfieldLayer, Dotimage, 30, 370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot13); 
       Dots dot14 = new Dots(playfieldLayer, Dotimage, 30, 415, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot14);
       Dots dot15 = new Dots(playfieldLayer, Dotimage, 60, 415, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot15);
       Dots dot16 = new Dots(playfieldLayer, Dotimage, 60, 440, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot16);
       Dots dot17 = new Dots(playfieldLayer, Dotimage, 60, 465, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot17);
       Dots dot18 = new Dots(playfieldLayer, Dotimage, 110, 415, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot18);
       Dots dot19 = new Dots(playfieldLayer, Dotimage, 110, 440, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot19);
       Dots dot20 = new Dots(playfieldLayer, Dotimage, 110, 465, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot20);
       Dots dot21 = new Dots(playfieldLayer, Dotimage, 85, 465, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot21);
       Dots dot22 = new Dots(playfieldLayer, Dotimage, 30, 465, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot22);
       Dots dot23 = new Dots(playfieldLayer, Dotimage, 30,490, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot23);
       Bonus bonus2 = new Bonus(playfieldLayer, Bonus, 25, 510, 0, 0, 0, 0, 0, 0, Duration.millis(500),4,4, 0, 0,23.5,23.5);bonuses.add(bonus2);
       bonus2.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
       bonus2.play();    
       Dots dot24 = new Dots(playfieldLayer, Dotimage, 65,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot24);
       Dots dot25 = new Dots(playfieldLayer, Dotimage, 105,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot25);
       Dots dot26 = new Dots(playfieldLayer, Dotimage, 145,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot26);
       Dots dot27 = new Dots(playfieldLayer, Dotimage, 185,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot27);
       Dots dot28 = new Dots(playfieldLayer, Dotimage, 225,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot28);
       Dots dot29 = new Dots(playfieldLayer, Dotimage, 265,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot29);
       Dots dot30 = new Dots(playfieldLayer, Dotimage, 305,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot30);
       Dots dot31 = new Dots(playfieldLayer, Dotimage, 345,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot31);
       Dots dot32 = new Dots(playfieldLayer, Dotimage, 385,515, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot32);
       Bonus bonus3 = new Bonus(playfieldLayer, Bonus, 420, 510, 0, 0, 0, 0, 0, 0, Duration.millis(500),4,4, 0, 0,23.5,23.5);bonuses.add(bonus3);
       bonus3.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
       bonus3.play();        
       Dots dot34 = new Dots(playfieldLayer, Dotimage, 425,490, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot34);
       Dots dot35 = new Dots(playfieldLayer, Dotimage, 425,460, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot35);
       Dots dot36 = new Dots(playfieldLayer, Dotimage, 395,460, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot36);
       Dots dot37 = new Dots(playfieldLayer, Dotimage, 350,460, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot37);
       Dots dot38 = new Dots(playfieldLayer, Dotimage, 350,430, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot38);
       Dots dot39 = new Dots(playfieldLayer, Dotimage, 350,400, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot39);
       Dots dot40 = new Dots(playfieldLayer, Dotimage, 350,370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot40);
       Dots dot41 = new Dots(playfieldLayer, Dotimage, 350,340, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot41);
       Dots dot42 = new Dots(playfieldLayer, Dotimage, 350,310, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot42);
       Dots dot43 = new Dots(playfieldLayer, Dotimage, 350,280, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot43);
       Dots dot44 = new Dots(playfieldLayer, Dotimage, 350,250, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot44);
       Dots dot45 = new Dots(playfieldLayer, Dotimage, 350,220, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot45);
       Dots dot46 = new Dots(playfieldLayer, Dotimage, 350,180, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot46);
       Dots dot47 = new Dots(playfieldLayer, Dotimage, 350,150, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot47);
       Dots dot48 = new Dots(playfieldLayer, Dotimage, 350,120, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot48);
       Dots dot49 = new Dots(playfieldLayer, Dotimage, 350,90, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot49);
       Dots dot50 = new Dots(playfieldLayer, Dotimage, 350,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot50);
       Dots dot51 = new Dots(playfieldLayer, Dotimage, 310,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot51);
       Dots dot52 = new Dots(playfieldLayer, Dotimage, 250,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot52);
       Dots dot53 = new Dots(playfieldLayer, Dotimage, 390,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot53);
       Bonus bonus4 = new Bonus(playfieldLayer, Bonus, 420,60, 0, 0, 0, 0, 0, 0, Duration.millis(500),4,4, 0, 0,23.5,23.5);bonuses.add(bonus4);
       bonus4.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
       bonus4.play();
       Dots dot54 = new Dots(playfieldLayer, Dotimage, 430,100, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot54);
       Dots dot55 = new Dots(playfieldLayer, Dotimage, 430,120, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot55);
       Dots dot56 = new Dots(playfieldLayer, Dotimage, 430,150, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot56);
       Dots dot57 = new Dots(playfieldLayer, Dotimage, 430,180, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot57);
       Dots dot58 = new Dots(playfieldLayer, Dotimage, 390,180, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot58);
       Dots dot59 = new Dots(playfieldLayer, Dotimage, 390,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot59);
      Dots dot60 = new Dots(playfieldLayer, Dotimage,310 ,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot60);
      
      Dots dot61 = new Dots(playfieldLayer, Dotimage,155,229, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot61);
      Dots dot62 = new Dots(playfieldLayer, Dotimage,155,259, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot62);
      Dots dot63 = new Dots(playfieldLayer, Dotimage,155,289, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot63);
      Dots dot64 = new Dots(playfieldLayer, Dotimage,155,310, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot64);
      Dots dot65 = new Dots(playfieldLayer, Dotimage,155,340, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot65);
      Dots dot66 = new Dots(playfieldLayer, Dotimage,155,370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot66);
      Dots dot67 = new Dots(playfieldLayer, Dotimage,185,370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot67);
      Dots dot68 = new Dots(playfieldLayer, Dotimage,205,370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot68);
      Dots dot69 = new Dots(playfieldLayer, Dotimage,185,229, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot69);
      Dots dot70 = new Dots(playfieldLayer, Dotimage,215,229, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot70);
      Dots dot71 = new Dots(playfieldLayer, Dotimage,245,229, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot71);
      Dots dot72 = new Dots(playfieldLayer, Dotimage,275,229, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot72);
      Dots dot73 = new Dots(playfieldLayer, Dotimage,305,229, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot73);
      Dots dot74 = new Dots(playfieldLayer, Dotimage,305,259, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot74);
      Dots dot75 = new Dots(playfieldLayer, Dotimage,305,289, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot75);
      Dots dot76 = new Dots(playfieldLayer, Dotimage,305,310, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot76);
      Dots dot77 = new Dots(playfieldLayer, Dotimage,305,340, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot77);
      Dots dot78 = new Dots(playfieldLayer, Dotimage,305,370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot78);
      Dots dot79 = new Dots(playfieldLayer, Dotimage,280,370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot79);
      Dots dot80 = new Dots(playfieldLayer, Dotimage,255,370, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot80);
      Dots dot81 = new Dots(playfieldLayer, Dotimage,255,400, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot81);
      Dots dot82 = new Dots(playfieldLayer, Dotimage,200,322, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot82);
      Dots dot83 = new Dots(playfieldLayer, Dotimage,240,322,0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot83);
      Dots dot84 = new Dots(playfieldLayer, Dotimage,280,322, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot84);
      Dots dot85 = new Dots(playfieldLayer, Dotimage,163,462, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot85);
      Dots dot86 = new Dots(playfieldLayer, Dotimage,200,462, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot86);
      Dots dot87 = new Dots(playfieldLayer, Dotimage,255,462, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot87);
      Dots dot88 = new Dots(playfieldLayer, Dotimage,295,462, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot88);
      Dots dot89 = new Dots(playfieldLayer, Dotimage,430,372, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot89);
      Dots dot90 = new Dots(playfieldLayer, Dotimage,430,410, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot90);
      Dots dot91 = new Dots(playfieldLayer, Dotimage,400,410, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot91);
      Dots dot92 = new Dots(playfieldLayer, Dotimage,387,372, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot92);
      Dots dot93 = new Dots(playfieldLayer, Dotimage,165,415, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot93);
      Dots dot94 = new Dots(playfieldLayer, Dotimage,205,415, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot94);
      Dots dot95 = new Dots(playfieldLayer, Dotimage,255,415, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot95);
      Dots dot96 = new Dots(playfieldLayer, Dotimage,310,415, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot96);
      Dots dot97 = new Dots(playfieldLayer, Dotimage,158,178, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot97);
      Dots dot98 = new Dots(playfieldLayer, Dotimage,198,178, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot98);
      Dots dot99 = new Dots(playfieldLayer, Dotimage,258,178, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot99);
      Dots dot100 = new Dots(playfieldLayer, Dotimage,298,178, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot100);
      Dots dot101 = new Dots(playfieldLayer, Dotimage,110,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot101);
      Dots dot102 = new Dots(playfieldLayer, Dotimage,110,100, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot102);
      Dots dot103 = new Dots(playfieldLayer, Dotimage,110,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot103);
      Dots dot104 = new Dots(playfieldLayer, Dotimage,150,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot104);
      Dots dot105 = new Dots(playfieldLayer, Dotimage,190,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot105);
      
      Dots dot106 = new Dots(playfieldLayer, Dotimage,230,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot106);
      Dots dot107 = new Dots(playfieldLayer, Dotimage,270,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot107);
      Dots dot108 = new Dots(playfieldLayer, Dotimage,160,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot108);
      Dots dot109 = new Dots(playfieldLayer, Dotimage,200,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot109);
      Dots dot110 = new Dots(playfieldLayer, Dotimage,70,70, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot110);
      Dots dot111 = new Dots(playfieldLayer, Dotimage,70,125, 0, 0, 0, 0, 0, 0, Duration.INDEFINITE, 1,1, 0, 0, 10, 10);dots.add(dot111);
      
      
     
       
       
       Image image1 = playerImage;

        // center horizontally, position at 70% vertically
        double x = 228;
        double y = 315;

        // create player
         player = new Player(playfieldLayer, image1, x, y, 0, 0, 0, 0, 3, 0,1.5, input, mySounds, Duration.millis(300), 12, 6, 0,0, 18.5, 19.5);
         player.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
         player.play();
         

        // register player
       
         
    

  

        // image
        Image image2 = enemyImage1;

        // random speed
        //double speed2 = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x1 = 230;
        double y1 = 240;
       

        // create a sprite
         Blinky = new Enemy(playfieldLayer, image2, x1, y1, 0, 0, -1, 0, 1, 1, Duration.millis(800), 2, 8, 0, 0, 23,22);
Blinky.setDx(-1);
        Blinky.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
        Blinky.play();      
        enemies.add( Blinky);
       
        
        Image image3 = enemyImage2;       
        //double speed3 = rnd.nextDouble() * 1.0 + 2.0;        
        double x2 = 230;
        double y2 = 240;
       
         Pinky = new Enemy(playfieldLayer, image3, x2, y2,0,0, -1, 0, 1, 1, Duration.millis(800), 2, 8, 0, 0, 23,22);
        Pinky.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
        Pinky.play();      
        enemies.add(Pinky);
        
        Image image4 = enemyImage3;       
       // double speed4 = rnd.nextDouble() * 1.0 + 2.0;        
        double x3 = 230;
        double y3 = 240;
        
        
        Inky = new Enemy(playfieldLayer, image4, x3, y3, 0, 0, -1, 0, 1, 1, Duration.millis(800), 2, 8, 0, 0, 23,22);
        Inky.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
        Inky.play();      
        enemies.add( Inky);
        
        Image image5 = enemyImage4;       
        //double speed5 = rnd.nextDouble() * 1.0 + 2.0;        
        double x4 = 230;
        double y4 = 240;
        
        Clyde = new Enemy(playfieldLayer, image5, x4, y4, 0, 0, -1, 0, 1, 1, Duration.millis(800), 2, 8, 0, 0, 23,22);
        Clyde.setCycleCount(Animation.INDEFINITE);	// sets animation to run indefinitely
        Clyde.play();   
        Clyde.move();
        enemies.add(Clyde);
        
       
        

    }

    private void removeSprites(  List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while( iter.hasNext()) {
            SpriteBase sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }
    
    
    public void returnGhostToNormal(Enemy enemy) {
    	if (enemy == Blinky) {
    		enemy.imageView.setImage(enemyImage1);
    		enemy.offsetX = 0;
    		enemy.offsetY = 0;
    		enemy.columns=8;
    		enemy.count=2;
    	}
    	else if (enemy == Pinky) {
    		enemy.imageView.setImage(enemyImage2);
    		enemy.offsetX = 0;
    		enemy.offsetY = 0;
    		enemy.columns=8;
    		enemy.count=2;
    	}
    	else if (enemy == Inky) {
    		enemy.imageView.setImage(enemyImage3);
    		enemy.offsetX = 0;
    		enemy.offsetY = 0;
    		enemy.columns=8;
    		enemy.count=2;
    	}
    	else if (enemy == Clyde) {
    		enemy.imageView.setImage(enemyImage4);
    		enemy.offsetX = 0;
    		enemy.offsetY = 0;
    		enemy.columns=8;
    		enemy.count=2;
    	}
    	
    	
    	
		enemy.fleemode = false;
    } // returnGhostToNormal
    
    public void BlueGhosts(Enemy enemy){
    	
    		
    		enemy.imageView.setImage(blueEnemy);
        		enemy.offsetX = 0;
        		enemy.offsetY = 0;
        		enemy.count = 1;
        		enemy.columns = 2;
        		
    		}
    public void blinkBlueGhosts (Enemy enemy){
    	
    	
    		if (enemy.fleemode){
    			enemy.imageView.setImage(blueEnemy);
    			enemy.offsetX = 0;
    			enemy.offsetY = 0;
    			enemy.count = 4;
    			enemy.columns = 4;
    		
    	}
    	
    	
    }
    
    public void eatPower(){
    	
    	
    	
    	for (Enemy enemy : enemies){
    		
    		enemy.fleemode=true;
    		
    		timeline = new Timeline(new KeyFrame(
    				Duration.millis(1),
    				ae -> BlueGhosts(enemy)));
    		timeline.play();
    		player.setSpeed(2);
    		
    		
    	
    		timeline = new Timeline(new KeyFrame(
    				Duration.millis(8000),
    				ae -> blinkBlueGhosts(enemy)));
    		timeline.play();
    		player.setSpeed(2);
    		
    		
    		
    		
    	}
    }
    
    public void exiteatPower(){
    	
    	
    	
    	
    	for (Enemy enemy : enemies){
    		
    		enemy.fleemode=false;
    		
    		
    		returnGhostToNormal(enemy);
    		player.setSpeed(1.5);
    		
    		
    		
    		
    		
    	}
    }
    
    public void PacManDead(){
    	
    	
    	
    	for (Enemy enemy : enemies){
    		enemy.removeFromLayer();
    		enemy.fleemode=false;
    		player.imageView.setImage(playerImage);
    		player.offsetX=0;
    		player.offsetY=0;
    		player.count=1;
    		player.columns=12;
    		player.setCycleCount(1);
    		player.play();	
    		player.removeFromLayer();
    		
    		
    		collisionText.setText("Pacman death");
    		  
    		
    		  
    		
    		
    		
    		
    		
    	}
    }
    
    public void restartGame(){
    	
    	
    	
    	player.offsetX=0;
		player.offsetY=0;
		player.count=12;
		player.columns=6;
		player.setX(228);
		player.setY(316);
		player.imageView.setImage(playerImage);
		player.play();	
		player.addToLayer();
		collisionText.setText("");
		lable2.setText(Integer.toString(health-=1));
		
		
    	
    	for (Enemy enemy :enemies ){
    		enemy.move();
    		enemy.setVisible(true);
    		enemy.setX(230);
    		enemy.setY(230);
    		enemy.setDx(0);
    		enemy.setDy(-1);
    		enemy.fleemode=false;
    		
    		enemy.columns=8;
    		enemy.count=2;
    		enemy.offsetX=0;
    		enemy.offsetY=0;
    		enemy.addToLayer();
    		returnGhostToNormal(enemy);
    		
    		
    		
    	}
    	
    	
    }
    
    
    
    

    private void checkCollisions() {
    	
    	collision1 = false;
        collision2 = false;
        collision3=false;
        collision4=false;
        
        for (Walls wall : walls2){
        for (Enemy enemy : enemies){
        	 if (enemy.collidesWith(wall)){
             	enemy.setDx(-1);
             	enemy.setDy(0);
             	
             	
        }
        }
        }
        
        
        for (Walls wall : walls3){
        for (Enemy enemy : enemies){
        	if (enemy.collidesWith(wall)){
        		enemy.setX(enemy.x-=enemy.dx);
  	  			enemy.setY(enemy.y-=enemy.dy); 
  	  		 int theRandomNumber = (int)(Math.random()*2);
		       
		        
	   		      enemy.setX(enemy.x-=enemy.dx);
	  			enemy.setY(enemy.y-=enemy.dy);       	                
	              
				switch(theRandomNumber) {
	   		     case 0:
	   		    	enemy.setDx(1);enemy.setDy(0);
	   		    
	   		         break;
	   		     case 1:
	   		        enemy.setDx(-1);enemy.setDy(0);
	   		    
	   		         break;
	   		    
	   		         
        	}
        }
        }
        }
        
        for (Walls wall : walls){
        	for (Enemy enemy : enemies){
        		if (enemy.collidesWith(wall)){
        			
        			
        			
        			
        			
        		        int theRandomNumber = (int)(Math.random()*4);
        		       
        		        
      	   		      enemy.setX(enemy.x-=enemy.dx);
      	  			enemy.setY(enemy.y-=enemy.dy);       	                
       	              
        				switch(theRandomNumber) {
        	   		     case 0:
        	   		         enemy.setDx(0);enemy.setDy(-1);
        	   		    
        	   		         break;
        	   		     case 1:
        	   		        enemy.setDx(-1);enemy.setDy(0);
        	   		    
        	   		         break;
        	   		     case 2:
        	   		         enemy.setDx(1);enemy.setDy(0);
        	   		         break;
        	   		     case 3:
        	   		         enemy.setDx(0);enemy.setDy(1);
        	   		         break;
        	   		         
        	   		    
        	   		         
        	   		     
        	   		         
        	        	}
        				
        				
        				
        			} 
        			
        				
        			
        			
        			
        				
        				
        			
        			 
        		}	
        		
        	}
        	
        
        
        for (Walls wall : walls4){
        	if (player.collidesWith(wall)){
        		
        		player.setX(player.x-=player.dx);

                player.setY(player.y-=player.dy);
        		
        	}
        
        }
        
        
        
        
        
       
        
        
        for (Walls wall2 : walls){
        	if (player.collidesWith(wall2)){
        		player.setX(player.x-=player.dx);

                player.setY(player.y-=player.dy);
        	}
        }
        
        
        
       
        
        for (Dots dot : dots){
        	if (player.collidesWith(dot)){
        		collision1=true;
        		dot.removeFromLayer();
        		dots.remove(dot);
        		score+=10;
        		lable1.setText(Integer.toString(score));
        	}
        }
        
        
        for (Bonus bonus : bonuses){
        	
        	if (player.collidesWith(bonus)){
        		bonus.removeFromLayer();
        		bonuses.remove(bonus);        		
        		score+=1000; 
        		power-=1;
        		lable3.setText(Integer.toString(power));
        		lable1.setText(Integer.toString(score));
        		
        		
        		mySounds.playClip(6);
        		eatPower();
        			
        			timeline = new Timeline(new KeyFrame(
            				Duration.millis(15000),
            				ae -> exiteatPower()));
            		timeline.play();
            		
            		
            		      		
        	}
        }
        
        for (Enemy enemy : enemies){
        	if (player.collidesWith(enemy) && enemy.fleemode==false){
        		
        			
        			
            		
            		mySounds.playClip(3);
        			PacManDead();
        			
        		      		
        		
        		timeline = new Timeline(new KeyFrame(
        				Duration.millis(1000),
        				ae -> restartGame()));
        		timeline.play();
        		
        		
        		
        		
        		
        	}
        	
        	
        }
        
    for (Enemy enemy : enemies){
    	
    	if (enemy.fleemode){
			if (player.collidesWith(enemy)){
				enemy.setX(230);
				enemy.setY(230);
				enemy.setDx(0);
				enemy.setDy(-1);
				returnGhostToNormal(enemy);
				enemy.fleemode=false;
			}
		}
    	
    }
    
    
    
    for (Walls wall : leftwall){
    	if (player.collidesWith(wall)){
    		player.setX(410);
    		player.setY(270);
    	}
    }
    
    for (Walls wall : rightwall){
    	if (player.collidesWith(wall)){
    		player.setX(56);
    		player.setY(270);
    	}
    }
    
    if (dots.isEmpty()){
    	mySounds.stopClip(2);
    	player.stopMovement();
    	player.setVisible(false);
    	for (Enemy enemy : enemies){
    		enemy.stopMovement();
    		enemy.setVisible(false);
    	}
    	
    	collisionText.setText("You Win");
    }
    
    
    if (health==0){
    	player.stopMovement();
    	player.setVisible(false);
    	
    	for (Enemy enemy : enemies){
    		enemy.stopMovement();
    		enemy.setVisible(false);
    	}
    	
    	collisionText.setText("Game Over");
    }
    	
    }
    
    
    
    
        	
        
        
          	
            		
        	


   

	private void updateScore() {
    	
    	
    	if (player.input.isMoveDown()||player.input.isMoveLeft()||player.input.isMoveRight()||player.input.isMoveUp()){
    		mySounds.playClip(2);
    	}
    	
    	
    	
    	
    
    }  	
    	
    

    public static void main(String[] args) {
        launch(args);
    }

}