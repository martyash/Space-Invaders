package invaders.memento;

import invaders.Observer.GamePoints;
import invaders.Observer.GameTimer;
import invaders.gameobject.GameObject;
import invaders.rendering.Renderable;
import java.util.List;
import java.util.ArrayList;

public class Memento { // saves a state of the game after pressing 's' key
    private final List<Renderable> savedState;
    private final List<GameObject> savedObjects;
    private final GameTimer savedTime;
    private final GamePoints savedPoints;

    public Memento(List<Renderable> savedState,List<GameObject> savedObjects, GameTimer savedTime, GamePoints savedPoints){
        this.savedState = new ArrayList<>(savedState);
        this.savedObjects = new ArrayList<>(savedObjects);
        this.savedTime = savedTime.copy();
        this.savedPoints = savedPoints.copy();

    }

    

    public List<Renderable> getGameState(){
        return this.savedState;
    }
    public List<GameObject> getGameObjects(){
        return this.savedObjects;
    }

    public GameTimer getSavedTime(){
        return this.savedTime;
    }

    public GamePoints getSavedPoints(){
        return this.savedPoints;
    }


    
}
