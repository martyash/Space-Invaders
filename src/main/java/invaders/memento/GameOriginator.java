package invaders.memento;

import java.util.ArrayList;
import java.util.List;

import invaders.Observer.GamePoints;
import invaders.Observer.GameTimer;
import invaders.gameobject.GameObject;
import invaders.prototype.Prototype;
import invaders.rendering.Renderable;
import invaders.entities.Player;
import invaders.factory.EnemyProjectile;
import invaders.gameobject.Enemy;
import invaders.gameobject.Bunker;
import invaders.factory.PlayerProjectile;

public class GameOriginator { // originator

    private List<Renderable> currentState;
    private List<GameObject> currentObjects;
    private GameTimer currentTime;
    private GamePoints currentPoints;

    public GameOriginator(List<Renderable> currentState,List<GameObject> currentObjects, GameTimer currentTime,GamePoints currentPoints){
        this.currentState = currentState;
        this.currentObjects = currentObjects;
        this.currentTime = currentTime;
        this.currentPoints = currentPoints;
    }

    public List<Renderable> getState(){
        return this.currentState;
    }

    public List<GameObject> getGameObjects(){
        return this.currentObjects;
    }
    
    public GameTimer getSavedTime(){
        return this.currentTime;
    }

    public GamePoints getSavedPoints(){
        return this.currentPoints;
    }

    public Memento save(){ // save the state by creating a memento

         List<Renderable> stateCopy = new ArrayList<>();
         List<GameObject> gameCopy = new ArrayList<>();
       
        for(Renderable renderable : currentState) {
            if (renderable instanceof Prototype) {
                if(renderable instanceof Enemy){
                    Enemy e = (Enemy) renderable;
                    Renderable r = (Renderable) e.copy();
                    stateCopy.add(r);
                }
                if(renderable instanceof Player){
                    Player p = (Player) renderable;
                    Renderable r = (Renderable) p.copy();
                    stateCopy.add(r);
                }
                if(renderable instanceof Bunker){
                    Bunker b = (Bunker) renderable;
                    Renderable r = (Renderable) b.copy();
                    stateCopy.add(r);
                }
                if(renderable instanceof EnemyProjectile){
                    EnemyProjectile pr = (EnemyProjectile) renderable;
                    Renderable r = (Renderable) pr.copy();
                    stateCopy.add(r);
                }
                if(renderable instanceof PlayerProjectile){
                    PlayerProjectile pr = (PlayerProjectile) renderable;
                    Renderable r = (Renderable) pr.copy();
                    stateCopy.add(r);
                }
            }
        }

         for(GameObject g : currentObjects) {
            if (g instanceof Prototype) {
                if(g instanceof Enemy){
                    Enemy e = (Enemy) g;
                    GameObject r = (GameObject) e.copy();
                    gameCopy.add(r);
                }
                if(g instanceof Bunker){
                    Bunker b = (Bunker) g;
                    GameObject r = (GameObject) b.copy();
                    gameCopy.add(r);
                }
                if(g instanceof EnemyProjectile){
                    EnemyProjectile ep = (EnemyProjectile) g;
                    GameObject r = (GameObject) ep.copy();
                    gameCopy.add(r);
                }
                if(g instanceof PlayerProjectile){
                    PlayerProjectile pp = (PlayerProjectile) g;
                    GameObject r = (GameObject) pp.copy();
                    gameCopy.add(r);
                } 
            }
        }

        GameTimer timerCopy = currentTime.copy();  
        GamePoints pointsCopy = currentPoints.copy(); 
        return new Memento(stateCopy,gameCopy, timerCopy, pointsCopy);
    }

    public void setMemento(Memento memento){
        this.currentState = memento.getGameState();
        this.currentTime = memento.getSavedTime();
        this.currentPoints = memento.getSavedPoints();
        this.currentObjects = memento.getGameObjects();

    }
}
