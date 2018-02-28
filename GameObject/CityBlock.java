package GameObject;

import java.awt.Color;
import java.awt.Point;

import Vector.Matrix3x3f;
import Vector.Vector2f;

public class CityBlock extends VectorObject {
	
	private float width; // The width of the city block

	public CityBlock(float xPos, float width) {
		this.width = width;
		color = Color.BLUE;
		location = new Vector2f(xPos, -4.5f);
		rotation = 0;
		scale = 1;
		
		// Defining the shape of a city block via vectors
		poly = new Vector2f[] {
			new Vector2f( -width,  0.5f ),
			new Vector2f(  width,  0.5f ),
			new Vector2f(  width, -0.5f ),
			new Vector2f( -width, -0.5f ),
		};
		
		// Update the world matrix
		updateWorld();
	}

	// Returns whether this city block is impacted by the given vector location
	public boolean isImpacted(Vector2f ballLoc) {
		
		// Create a fake box given the city block's location
		float minX = location.x - width;
		float maxX = location.x + width;
		float minY = location.y - 0.5f;
		float maxY = location.y + 0.5f;
		
		// Return whether the given vector location is within the fake box
		return (ballLoc.x > minX && ballLoc.x < maxX
				&& ballLoc.y > minY && ballLoc.y < maxY);
	}
}
