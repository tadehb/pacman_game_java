/* Tadeh Boghoz Ahmad Abadi
 * Class: Java Programming
 * Instructor: Zareh Gorjian
 * Date:04/26/2015
 * Assignment14
 */
package Pacman;




import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Player extends SpriteBase {
	
	double playerShipMinX;
    double playerShipMaxX;
    double playerShipMinY;
    double playerShipMaxY;
    

    Input input;

    double speed;
    MySounds mySounds;

    public Player(Pane layer, Image image, double x, double y, double r,
			double dx, double dy, double dr, double health, double damage, double speed, Input input,MySounds ms,
			Duration duration, int count, int columns, int offsetX,
			int offsetY, double w, double h) {
		super(layer, image, x, y, r, dx, dy, dr, health, damage, duration, count,
				columns, offsetX, offsetY, w, h);
		
	 

        this.speed = speed;
        this.input = input;
        
        mySounds=ms;

        init();
    }


    private void init() {

        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen 
    	
    	
    	 
        playerShipMinX = 0 - 18.5 / 2.0;
        playerShipMaxX = Settings.SCENE_WIDTH - 18.5 / 2.0;
        playerShipMinY = 0 - 19.5 / 2.0;
        playerShipMaxY = Settings.SCENE_HEIGHT -19.5 / 2.0;

    }

    public void processInput() {

        // ------------------------------------
        // movement
        // ------------------------------------

        // vertical direction
        if( input.isMoveUp()) {
            dy = -speed;
            
        } else if( input.isMoveDown()) {
            dy = speed;
        } else {
            dy = 0d;
        }

        // horizontal direction
        if( input.isMoveLeft()) {
        
            dx = -speed;
            
            
            
        } else if( input.isMoveRight()) {
            dx = speed;
          
            
        } else {
            dx = 0d;
        }

    }

    @Override
    public void move() {
    	

        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();


    }

    private void checkBounds() {
    	  // vertical
        if( Double.compare( y, playerShipMinY) < 0) {
            y = playerShipMinY;
        } else if( Double.compare(y, playerShipMaxY) > 0) {
            y = playerShipMaxY;
        }

        // horizontal
        if( Double.compare( x, playerShipMinX) < 0) {
            x = playerShipMinX;
        } else if( Double.compare(x, playerShipMaxX) > 0) {
            x = playerShipMaxX;
        }


    }
    
    public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	 public void setVisible(boolean bool) {
	    	imageView.setVisible(bool);
	    }
    
   


    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

}