package GameObject;

import java.awt.Graphics;
import java.util.ArrayList;

import Vector.Matrix3x3f;
import Vector.Vector2f;

public class CityBlockManager {

	private ArrayList<CityBlock> cityBlocks; // List of city blocks in the world
	private static final int blocks = 8; // The # of city blocks spread through the world
	
	public CityBlockManager(float appWorldWidth){
		cityBlocks = new ArrayList<CityBlock>(); // Initialize
		float blockWidth = appWorldWidth / blocks; // Calculate block width
		
		// Spreads all city blocks evenly along the bottom of the world
		for(int i = 0; i < blocks; i++)
		{
			//cityBlocks.add(new CityBlock(-blockWidth*((-blocks/2f)+i), blockWidth));
			cityBlocks.add(new CityBlock((i - (blocks/2))*blockWidth + blockWidth/2, blockWidth/2));
		}
	}
	
	// Used to communicate with the cannonball manager whether there are city blocks being impacted by cannonballs
	public void manageImpacts(CannonBallManager cBallMan) {
		ArrayList<CannonBall> cannonBalls = cBallMan.getCannonBalls(); // Initialize list
		
		// For each cannonball...
		for(CannonBall ball : cannonBalls)
		{
			Vector2f ballLoc = ball.getLocation(); // Get its location,
			// And check each city block...
			for(int i = 0; i < cityBlocks.size(); i++)
			{
				// Whether the city block is impacted by the current cannonball
				if(cityBlocks.get(i).isImpacted(ballLoc))
				{
					// If impacted, remove this city block
					cityBlocks.remove(i);
					i--;
				}
			}
		}
	}

	// Used to render the city blocks to the world
	public void render(Graphics g, Matrix3x3f viewport) {
		for(CityBlock cityBlk : cityBlocks)
		{
			cityBlk.setViewport(viewport);
			cityBlk.render(g);
		}
	}

	// Will indicate the city is destroyed if no city blocks remain
	public boolean isDestroyed() {
		return cityBlocks.size() == 0;
	}

	// Will return the number of city blocks remaining
	public int remaining() {
		return cityBlocks.size();
	}

}
