/* Tadeh Boghoz Ahmad Abadi
 * Class: Java Programming
 * Instructor: Zareh Gorjian
 * Date:04/26/2015
 * Assignment14
 */
package Pacman;


import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;



public class Walls extends SpriteBase {
	
	
	
	
	public Walls(Pane layer, Image image, double x, double y, double r,
			double dx, double dy, double dr, double health, double damage,
			Duration duration, int count, int columns, int offsetX,
			int offsetY, double w, double h) {
		super(layer, image, x, y, r, dx, dy, dr, health, damage, duration, count,
				columns, offsetX, offsetY, w, h);
		
	
	

		Rectangle rect1 = new Rectangle();
		
		rect1.setX(x);
		rect1.setY(y);
		rect1.setWidth(w);
		rect1.setHeight(h);
		rect1.setArcWidth(10);
		rect1.setArcHeight(10);
		rect1.setFill(Color.RED);
		
	}
	
	
	

}
