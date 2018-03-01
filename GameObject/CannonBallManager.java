package GameObject;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import Vector.Matrix3x3f;
import Vector.Vector2f;

public class CannonBallManager {

	private ArrayList<CannonBall> cannonBalls; // The list of cannonballs in the world
	
	private float wind; // The amount of wind affecting the cannonball
	private float windMultiplier; // The amount of possible change to the wind per progression
	private float maxWind; // The max amount of wind allowed affecting the cannonball
	
	private float speed; // The velocity at which the cannonball falls
	
	private float spawnTimeLeft; // The amount of time left before another cannonball spawns
	private float totalSpawnTime; // The total amount of time between each cannonball spawn
	
	public CannonBallManager(){
		cannonBalls = new ArrayList<CannonBall>(); // Initialize the list of cannonballs to empty
		cannonBalls.add(new CannonBall()); // Add the intial cannonball
		wind = 0.0f; // Initialize the wind to none
		windMultiplier = 0.8f; // Initialize the wind multiplier to a small value
		maxWind = 15.0f; // Initialize the max allowed wind
		speed = 0.5f; // Initialize the starting velocity of cannonballs in the game
		totalSpawnTime = spawnTimeLeft = 3.5f; // Initialize both the total spawn time and the current time left
	}
	
	public void progress(float delta) {
		// Calculate the amount of wind affecting the cannonball this progression
		wind += (float) ((Math.random() * windMultiplier) - (windMultiplier/2)) * delta;
		
		// Check whether the wind is within the max boundaries
		if (wind > maxWind) 
		{ 
			wind = maxWind;
		}
		else if (wind < -maxWind) 
		{ 
			wind = -maxWind; 
		}
		
		// For each cannonball in the world...
		for(int i = 0; i < cannonBalls.size(); i++)
		{
			CannonBall ball = cannonBalls.get(i); // Get the current cannonball...
			Vector2f curLoc = ball.getLocation(); // ... and its location
			
			// First check whether the cannonball is within the world boundaries
			if (curLoc.y < -5f || curLoc.x < -10f || curLoc.y > 10f)
			{
				// Remove and continue if outside boundaries
				cannonBalls.remove(i);
				continue;
			}
			
			// Move the cannonball according to the current velocity and wind this progression
			ball.setLocation(new Vector2f(curLoc.x + (wind*delta), curLoc.y - (speed*delta)));
			// Update the cannonball's world matrix
			ball.updateWorld();
		}
		
		// Calculate the amount of spawn time left
		spawnTimeLeft -= delta;
		// If spawn time reaches zero...
		if (spawnTimeLeft < 0)
		{
			// Add a new cannonball and reset the timer
			cannonBalls.add(new CannonBall());
			spawnTimeLeft = totalSpawnTime + spawnTimeLeft; // "+ spawnTimeLeft" added to more accurately match total spawn time during execution
		}
	}

	// Return whether a cannonball was successfully shot in the world
	public boolean shotAt(Vector2f playerLoc) {
		// For each cannonball...
		for(int i = 0; i < cannonBalls.size(); i++)
		{
			// If this cannonball was shot,
			if (cannonBalls.get(i).isShot(playerLoc))
			{
				// Remove the cannonball, increase difficulty, and return success
				cannonBalls.remove(i);
				i--;
				increaseDifficulty();
				return true;
			}
		}
		// Else failed to shoot at cannonball
		return false;
	}

	// Increase several values of the cannonball's spawn time and movement to increase player difficulty
	private void increaseDifficulty() {
		speed += 0.05f; // Linearly increase speed infinitely by a small amount
		
		// Linearly decrease spawn time up until a set minimum (so it doesn't get absurdly unfair)
		if (totalSpawnTime > 0.7f)
		{
			totalSpawnTime -= 0.15f;
		}
		// Linearly increase the wind multiplier up until a set maximum (so it doesn't get unreasonably jittery)
		if (windMultiplier < 5f)
		{
			windMultiplier += 0.08f;
		}
	}

	// Used to render all the cannonballs in the world
	public void render(Graphics g, Matrix3x3f viewport) {
		for(CannonBall cannonBall : cannonBalls)
		{
			cannonBall.setViewport(viewport);
			cannonBall.render(g);
		}
	}

	// Returns the list of cannonballs in the world
	public ArrayList<CannonBall> getCannonBalls() {
		return this.cannonBalls;
	}

}
