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

public class Enemy extends SpriteBase {
	
	double enemyShipMinX;
    double enemyShipMaxX;
    double enemyShipMinY;
    double enemyShipMaxY;
    protected boolean fleemode=false;

   

    public Enemy(Pane layer, Image image, double x, double y, double r,
			double dx, double dy, double dr, double health, double damage,
			Duration duration, int count, int columns, int offsetX,
			int offsetY, double w, double h) {
		super(layer, image, x, y, r, dx, dy, dr, health, damage, duration, count,
				columns, offsetX, offsetY, w, h);
		
		
		init();
		
	}
    
    private void init() {

        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen 
    	
    	
    	 
        enemyShipMinX = 0 - 18.5 / 2.0;
        enemyShipMaxX = Settings.SCENE_WIDTH - 18.5 / 2.0;
        enemyShipMinY = 0 - 19.5 / 2.0;
        enemyShipMaxY = Settings.SCENE_HEIGHT -19.5 / 2.0;

    }
    
 public void move() {
	 
	
    	

        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();


    }
 
 private void checkBounds() {
	  // vertical
   if( Double.compare( y, enemyShipMinY) < 0) {
       y = enemyShipMinY;
   } else if( Double.compare(y, enemyShipMaxY) > 0) {
       y = enemyShipMaxY;
   }

   // horizontal
   if( Double.compare( x, enemyShipMinX) < 0) {
       x = enemyShipMinX;
   } else if( Double.compare(x, enemyShipMaxX) > 0) {
       x = enemyShipMaxX;
   }}
	@Override
    public void checkRemovability() {

        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(false);
        }
        


    }
	
	 public void setVisible(boolean bool) {
	    	imageView.setVisible(bool);
	    }
   
}
