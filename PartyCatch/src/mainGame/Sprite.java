/**********************************************************
 * The Sprite class has the following functions:
 *
 * Represents the basic game objects
 * Manages the position, image, and state of the sprite
 * Provides methods for rendering and updating the sprite on the screen
 ***********************************************************/

package mainGame;
/*

 * 
 * Represents a basic game object (e.g., a fruit).
 * Manages the position, image, and state of the sprite.
 * May have methods for rendering and updating the sprite on the screen.
 */
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {
	protected Image img; //Image object representing the sprite's visual representation
	
	//Double values representing the sprite's current position on the screen
	//Double values representing the sprite's velocity in the x and y directions
	protected double xPos, yPos, dx, dy;	
	
	protected boolean visible; //Boolean indicating whether the sprite is currently visible on the screen
	
	//Double values representing the dimensions of the sprite
	protected double width;
	protected double height;

	//Constructor - Initializes the sprite with a given initial position and image
    public Sprite(double xPos, double yPos, Image image){
		this.xPos = xPos;
		this.yPos = yPos;
		this.loadImage(image);
		this.visible = true;
	}

    //Returns the bounding rectangle of the sprite
	private Rectangle2D getBounds(){
		return new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
	}

	//Sets the width and height of the sprite based on its image
	private void setSize(){
		this.width = this.img.getWidth();
        this.height = this.img.getHeight();
	}

	//Checks if the current sprite collides with another sprite
	protected boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}

	//Loads the image for the sprite
	protected void loadImage(Image image){
		try{
			this.img = image;
	        this.setSize();
		} catch(Exception e)	{
			e.printStackTrace();
		}
	}
	
	//Renders the sprite on the screen using the provided GraphicsContext
	public void render(GraphicsContext gc){
        gc.drawImage( this.img, this.xPos, this.yPos );
    }

	//Returns the image associated with the sprite
	public Image getImage(){
		return this.img;
	}
	
	//Returns the current X position of the sprite
	public double getXPos(){
		return this.xPos;
	}

	//Returns the current Y position of the sprite
	public double getYPos(){
		return this.yPos;
	}

	//Sets the velocity of the sprite in the X direction
	public void setDX(int val){
		this.dx = val;
	}

	//Sets the velocity of the sprite in the Y direction
	public void setDY(int val){
		this.dy = val;
	}

	//Returns whether the sprite is currently visible
	public boolean isVisible(){
		return visible;
	}
	
	//Sets the visibility of the sprite to false
	public void vanish(){
		this.visible = false;
	}
}

