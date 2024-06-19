package invaders.engine;

import java.util.ArrayList;
import java.util.List;

import invaders.ConfigReader;
import invaders.Facade.Facade;
import invaders.Observer.GamePoints;
import invaders.Observer.GameTimer;
import invaders.Observer.PointObserver;
import invaders.builder.BunkerBuilder;
import invaders.builder.Director;
import invaders.Observer.TimeObserver;
import invaders.builder.EnemyBuilder;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.memento.Caretaker;
import invaders.memento.GameOriginator;
import invaders.memento.Memento;
import invaders.entities.Player;
import invaders.factory.PlayerProjectile;
import invaders.factory.EnemyProjectile;
import invaders.rendering.Renderable;
import javafx.animation.AnimationTimer;

import org.json.simple.JSONObject;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine extends AnimationTimer {
	private List<GameObject> gameObjects = new ArrayList<>(); // A list of game objects that gets updated each frame
	private List<GameObject> pendingToAddGameObject = new ArrayList<>();
	private List<GameObject> pendingToRemoveGameObject = new ArrayList<>();

	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();

	private List<Renderable> renderables =  new ArrayList<>();

	private Player player;
	

	private boolean left;
	private boolean right;
	private boolean q;
	private boolean w;
	private boolean e;
	private boolean r;
	private boolean s;
	private boolean d;
	private int gameWidth;
	private int gameHeight;
	private int timer = 45;
	private long lastTime = 0;
    private GameTimer GT = new GameTimer();
	private GamePoints gamePoints = new GamePoints();
	private GameOriginator gameOriginator;
	private Caretaker caretaker;
	private Memento savedMemento = null;
	
	


	public GameEngine(String config){
		// Read the config here
		ConfigReader.parse(config);

		// Get game width and height
		gameWidth = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();

		//Get player info
		this.player = new Player(ConfigReader.getPlayerInfo());
		renderables.add(player);
		this.start();


		Director director = new Director();
		BunkerBuilder bunkerBuilder = new BunkerBuilder();
		//Get Bunkers info
		for(Object eachBunkerInfo:ConfigReader.getBunkersInfo()){
			Bunker bunker = director.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			gameObjects.add(bunker);
			renderables.add(bunker);
		}


		EnemyBuilder enemyBuilder = new EnemyBuilder();
		//Get Enemy info
		for(Object eachEnemyInfo:ConfigReader.getEnemiesInfo()){
			Enemy enemy = director.constructEnemy(this,enemyBuilder,(JSONObject)eachEnemyInfo);
			
			gameObjects.add(enemy);
			renderables.add(enemy);
		}

	}

	@Override
    public void handle(long now) {
  
        if (lastTime != 0) {
            long deltaTime = (now - lastTime); // Convert nanoseconds to seconds
            
            GT.incrementTime(deltaTime);
        }
        lastTime = now;
        // stop the counter when player dies or aliens all die
        if(!this.getPlayer().isAlive()){
            GT.stopTimer();
        }
		// logic for checking all enemies are dead 
		boolean allEnemiesDead = true; // Assume all enemies are dead until proven otherwise
		for(int j = 0; j < renderables.size(); j++){
			if(renderables.get(j).isAlive() && renderables.get(j) instanceof Enemy){
				allEnemiesDead = false;
				break; // Break out of the loop as soon as a living enemy is found
			}
		}
		// If after the loop, allEnemiesDead is still true, then all enemies are confirmed dead
		if(allEnemiesDead){
			GT.stopTimer();
			gamePoints.setGameOver(true);
		}

		
		// ensures game state is updated every frame improving overall speed
		update();
      
        }

		public void addObserverToGameTimer(TimeObserver observer) {
			GT.addObserver(observer);
		}
		
		public GameTimer getGameTimer() {
			return GT;
		}

		public GamePoints getGamePoints(){
			return gamePoints;
		}

		public void addObservertoGamePoints(PointObserver observer){
			gamePoints.addObserver(observer);
		}
	

	
	

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		timer+=1;

		movePlayer();

		for(GameObject go: gameObjects){ // without this loop, gameobjects will not move	
				go.update(this);		
			}
		// this includes bunkers, enemies, projectiles but not player

		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);
			for (int j = i+1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);

				if((renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
						||(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("Enemy"))||
						(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))){
				}else{
					if(renderableA.isColliding(renderableB) && (renderableA.getHealth()>0 && renderableB.getHealth()>0)) {
						// damage application to all renderables including bunkers 
						renderableA.takeDamage(1); // if removed no damage inflicted at all
						renderableB.takeDamage(1); // if removed all things die from 1 shot
						// if a player projectile impacts an enemy or enemyprojectile; we go through observer method to increment points
						if((renderableA instanceof Enemy|| renderableA instanceof EnemyProjectile) && renderableB instanceof PlayerProjectile){
							gamePoints.incrementPoints(renderableA);
							//System.out.println("WE HAVE IMPACT");
						}
					}
				}
			}
		}

		

		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}

	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	public List<GameObject> getPendingToAddGameObject() {
		return pendingToAddGameObject;
	}

	public List<GameObject> getPendingToRemoveGameObject() {
		return pendingToRemoveGameObject;
	}

	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void qReleased(){
		this.q = false;
	}

	public void wReleased(){
		this.w = false;
	}
	public void eReleased(){
		this.e = false;
	}
	public void rReleased(){
		this.r = false;
	}
	public void sReleased(){
		this.s = false;
	}
	public void dReleased(){
		this.d = false;
	}


	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public void qPressed(){ // removes slow projectiles after pressed 'q'
		this.q = true;
		Facade facadeProjectile = new Facade(renderables, gamePoints);
		facadeProjectile.removeAllSlowProjectiles();

	}

	public void wPressed(){ // removes fast proejectiles from game
		this.w = true;
		Facade facadeProjectile = new Facade(renderables, gamePoints);
		facadeProjectile.removeAllFastProjectiles();

	}
	public void ePressed(){ // removes slow aliens from game
		this.e = true;
		Facade facadeProjectile = new Facade(renderables, gamePoints);
		facadeProjectile.removeAllSlowAliens();

	}
	public void rPressed(){ // removes fast aliens from game
		this.r = true;
		Facade facadeProjectile = new Facade(renderables, gamePoints);
		facadeProjectile.removeAllFastAliens();

	}
	public void sPressed(){
		this.s = true;
		
		// create originator and caretaker
		
		// only do this if savedMemento is null
		if(savedMemento == null){
		caretaker = new Caretaker();
		gameOriginator = new GameOriginator(renderables,gameObjects,GT, gamePoints);
		savedMemento = gameOriginator.save(); // creates new memento
		//caretaker.saveState(gameOriginator); // save game state
		
		
		
		
		System.out.println("game state is being saved");			
		}
		

	}
	public void dPressed(){
		this.d = true;
		//undo operation
		if(savedMemento != null){	
		caretaker.undoState(gameOriginator);	
		//this.renderables = savedMemento.getGameState();
		//this.gameObjects = savedMemento.getGameObjects();
		
		this.GT = savedMemento.getSavedTime();
		this.gamePoints = savedMemento.getSavedPoints();
		gamePoints.notifyObservers(); // because notify observers is only called inside gamepoints when enemies are killed
		savedMemento = null; // set momento to null to use again 
		}
		
	}

	public boolean shootPressed(){
		if(timer>45 && player.isAlive()){
			Projectile projectile = player.shoot();
			gameObjects.add(projectile);
			renderables.add(projectile);
			timer=0;
			return true;
		}
		return false;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}
}
