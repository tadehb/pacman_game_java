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

public class Bonus extends SpriteBase{

	public Bonus(Pane layer, Image image, double x, double y, double r,
			double dx, double dy, double dr, double health, double damage,
			Duration duration, int count, int columns, int offsetX,
			int offsetY, double w, double h) {
		super(layer, image, x, y, r, dx, dy, dr, health, damage, duration, count,
				columns, offsetX, offsetY, w, h);
		
	}
	
	public void checkRemovability() {
		if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(false);
		
	}

}}
