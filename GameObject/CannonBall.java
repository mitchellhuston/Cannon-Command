package GameObject;

import java.awt.Color;

import Vector.Vector2f;

public class CannonBall extends VectorObject {

	public CannonBall(){
		float xPos = (float) (Math.random() * 20f - 10f); // Randomly generate the cannonball's initial x position
		color = Color.BLACK;
		location = new Vector2f(xPos, 5f); // Place the cannonball at the top of the screen in a random x position
		rotation = 0;
		scale = 1;
		
		// Define the cannonball's shape via vectors
		poly = new Vector2f[] {
				new Vector2f(  .16f, -0.4f),
				new Vector2f( -.16f, -0.4f),
				new Vector2f( -0.4f, -.16f),
				new Vector2f( -0.4f,  .16f),
				new Vector2f( -.16f,  0.4f),
				new Vector2f(  .16f,  0.4f),
				new Vector2f(  0.4f,  .16f),
				new Vector2f(  0.4f, -.16f),
		};
		
		// Update the cannonball's world matrix
		updateWorld();
	}

	// Returns whether this cannonball was hit by the player's given vector location
	public boolean isShot(Vector2f playerLoc) {
		// Create a fake box around the cannonball give its location
		float xMin = location.x - 0.4f;
		float xMax = location.x + 0.4f;
		float yMin = location.y - 0.4f;
		float yMax = location.y + 0.4f;
		
		// Check whether the player's location is inside the fake box
		return (playerLoc.x > xMin && playerLoc.x < xMax
				&& playerLoc.y > yMin && playerLoc.y < yMax);
	}
}
