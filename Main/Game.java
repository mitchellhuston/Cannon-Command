package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import GameObject.CannonBallManager;
import GameObject.CityBlockManager;
import Helper.Utility;
import Vector.Matrix3x3f;
import Vector.Vector2f;

public class Game extends SimpleFramework {

	private boolean startGame; // Whether the user has indicated to start the game
	private boolean gameInProgress; // Whether the game is currently in progress
	
	private Vector2f playerLoc; // The player's current mouse position
	private boolean playerShooting; // Whether the player is attempting to shoot a cannonball
	
	private CannonBallManager cannonBallMan; // The manager for all cannonballs in the game
	private CityBlockManager cityBlockMan; // The manager for all city blocks in the game
	
	private int score; // The player's score counter for the current game
	
	public Game() {
		appTitle = "Cannon Command";
		appBackground = Color.WHITE;
		appBorderScale = 0.95f;
		appWidth = 800;
		appHeight = 400;
		appWorldWidth = 20.0f;
		appWorldHeight = 10.0f;
		appMaintainRatio = true;
		
		// Initialize game logic flags
		startGame = false;
		gameInProgress = false;
		playerShooting = false;
		
		// Initialize player's score to zero
		score = 0;
	}

	@Override
	protected void initialize() {
		super.initialize();
		// Initialize the city when the game launches even before the game itself begins to create a nice background
		cityBlockMan = new CityBlockManager(appWorldWidth);
	}

	@Override
	protected void processInput(float delta) {
		super.processInput(delta);

		// Check whether the player pressed the spacebar and associate it with the "start game" request
		startGame = (keyboard.keyDown(KeyEvent.VK_SPACE));
		
		// Get the player's mouse position according to the world
		playerLoc = getWorldMousePosition(); 
		// Check whether the player clicked and associate it with the "shooting" request
		playerShooting = (mouse.buttonDownOnce(MouseEvent.BUTTON1));
	}

	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);
		
		// If the player requests to start the game and it hasn't begun already, start the game
		if (startGame && !gameInProgress)
		{
			// Initialize the city (again) to handle playing again after a game over
			cityBlockMan = new CityBlockManager(appWorldWidth);
			// Initialize the cannon ball manager and create the first cannon ball
			cannonBallMan = new CannonBallManager();
			// Indicate that the game is in progress to activate respective game logic
			gameInProgress = true;
			// Initialize the player's new score counter for the current game
			score = 0;
		}
		
		// GameInProgress game logic
		if (gameInProgress)
		{
			// If the player is shooting, check if they shot a cannon ball
			if (playerShooting)
			{
				if (cannonBallMan.shotAt(playerLoc))
				{
					// If successfully shot, increase score according to city blocks remaining
					score += cityBlockMan.remaining();
				}
			}
			// Progress the cannonball movement according to delta time (also handles out of bounds cannonballs)
			cannonBallMan.progress(delta);
			// Check whether cannonballs are impacting city blocks
			cityBlockMan.manageImpacts(cannonBallMan);
			
			// If the entire city is destroyed, then indicate game over
			if (cityBlockMan.isDestroyed())
			{
				gameInProgress = false;
			}
		}
	}
	
	@Override
	protected void render(Graphics g) {
		super.render(g);
		
		// Game In Progress rendering logic
		if (gameInProgress){
			cannonBallMan.render(g, getViewportTransform());
			g.drawString("Building multiplier: " + cityBlockMan.remaining(), 20, 50);
		}
		
		// Pre-Game logic
		else{
			g.drawString("Press SPACE to Start", 20, 50);
		}
		
		// Universal render logic
		cityBlockMan.render(g, getViewportTransform());
		
		g.drawString("Score: " + score, 20, 65);
	}

	@Override
	protected void terminate() {
		super.terminate();
	}

	public static void main(String[] args) {
		launchApp(new Game());
	}
}
