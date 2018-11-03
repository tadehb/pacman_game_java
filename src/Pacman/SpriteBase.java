/* Tadeh Boghoz Ahmad Abadi
 * Class: Java Programming
 * Instructor: Zareh Gorjian
 * Date:04/26/2015
 * Assignment14
 */
package Pacman;



import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SpriteBase extends Transition {

    Image image;
    ImageView imageView;

    Pane layer;

    double x;
    double y;
   double r;

    double dx;
    double dy;
    double dr;
     int count;
    int columns;
    int offsetX;
    int offsetY;
    int lastIndex;

    double health;
    double damage;

    boolean removable = false;

    double w;
    double h;

    boolean canMove = true;
   
    public SpriteBase(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage,
    		Duration duration,int count,int columns,int offsetX, int offsetY,double w, double h) {

        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
       

        this.health = health;
        this.damage = damage;
        this.imageView=new ImageView(image);
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, w, h));
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.relocate(x,y);        
        this.imageView.setRotate(r);

        this.w = w; // imageView.getBoundsInParent().getWidth();
        this.h = h; // imageView.getBoundsInParent().getHeight();

        addToLayer();

    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void move() {

        if( !canMove)
            return;

        x += dx;
       y += dy;
        r += dr;

    }

    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }

    public ImageView getView() {
        return imageView;
    }

    public void updateUI() {

        imageView.relocate(x, y);
        imageView.setRotate(r);

    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }
    
   

    // TODO: per-pixel-collision
    public boolean collidesWith( SpriteBase otherSprite) {

        return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);

    }
   

    /**
     * Reduce health by the amount of damage that the given sprite can inflict
     * @param sprite
     */
    public void getDamagedBy( SpriteBase sprite) {
        health -= sprite.getDamage();
    }

    /**
     * Set health to 0
     */
    public void kill() {
        setHealth( 0);
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
    }

    /**
     * Set flag that the sprite can't move anymore.
     */
    public void stopMovement() {
        this.canMove = false;
    }

    public  void checkRemovability(){

}

	@Override
	protected void interpolate(double k) {
		 final int index = Math.min((int) Math.floor(k * count), count - 1);
	        if (index != lastIndex) {
	            final double m = (index % columns) * w  + offsetX;
	            final double n = (index / columns) * h + offsetY;
	            imageView.setViewport(new Rectangle2D(m, n, w, h));
	            lastIndex = index;
		
	}}}