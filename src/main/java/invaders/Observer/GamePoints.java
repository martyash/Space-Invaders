package invaders.Observer;

import invaders.factory.EnemyProjectile;
import invaders.gameobject.Enemy;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.ProjectileStrategy;
import invaders.prototype.Prototype;

import java.util.ArrayList;


public class GamePoints implements Prototype {
    private ArrayList<PointObserver> pointObservers = new ArrayList<>();
    private int points = 0;
    private boolean isGameOver = false; 

    public GamePoints() {
        this.pointObservers = new ArrayList<>();
        this.points = 0;
    }

    public void addObserver(PointObserver o){
        pointObservers.add(o);
    }

    public void removeObserver(PointObserver o){
        pointObservers.remove(o);
    }

    public void incrementPoints(Object o) {
        // if alien 

        if (isGameOver) {
            return;
        }

        if(o instanceof Enemy){
            Enemy e = (Enemy)o;
            ProjectileStrategy strategy = e.getProjectileStrategy();
            if(strategy instanceof FastProjectileStrategy){
                points += 4;
                notifyObservers();
            }else{
                points += 3;
                notifyObservers();
            }
        }

        // if projectile
        if (o instanceof EnemyProjectile){
            EnemyProjectile ep = (EnemyProjectile)o;
            ProjectileStrategy type = ep.getProjectileStrategy();
            if(type instanceof FastProjectileStrategy){
                points += 2;
                notifyObservers();
            }else{
                points += 1;
                notifyObservers();
            }

        }
             
        
    }

    public void notifyObservers(){ // notifies observers of any changes when called
        for(PointObserver s: pointObservers){
            s.updateScore(points);
        }
    }

    public int getPoints(){
        return this.points;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    
    private GamePoints(int points,ArrayList<PointObserver> observers){
        this.points = points;
        this.pointObservers = observers;
    }

    @Override
    public GamePoints copy(){
        ArrayList<PointObserver> copiedObservers = new ArrayList<>(this.pointObservers);
        return new GamePoints(getPoints(), copiedObservers);
        
    }

   
    
}
