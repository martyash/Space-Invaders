package invaders.Facade;

import java.util.List;

import invaders.Observer.GamePoints;
import invaders.factory.EnemyProjectile;
import invaders.gameobject.Enemy;
import invaders.rendering.Renderable;


public class Facade {
  
    private List<Renderable> renderables;
    private GamePoints gamePoints;


    public Facade( List<Renderable> renderables,GamePoints gamePoints){
        
        this.renderables = renderables;
        this.gamePoints = gamePoints;

    }
    


     public void removeAllSlowProjectiles(){    
        // loop through all renderables 

        for(int i = 0 ; i < renderables.size();i++){
 
           //System.out.println("Full class name: " + renderables.get(i).getClass().getCanonicalName());
            // remove all the projectiles that are of type slow
            if(renderables.get(i).isAlive()){

            if(renderables.get(i) instanceof EnemyProjectile ){         
                EnemyProjectile projectile = (EnemyProjectile) renderables.get(i);
                // utilise polymorphism
                if(projectile.getProjectileStrategy().isSlow()){
                    renderables.get(i).takeDamage(1);
                     // add points via gamepoints.incrementPoints
                     gamePoints.incrementPoints(renderables.get(i));
                }               
            }
            }

        }
        
    }


    public void removeAllFastProjectiles(){

          // loop through all renderables 

        for(int i = 0 ; i < renderables.size();i++){
            
            if(renderables.get(i).isAlive()){
           //System.out.println("Full class name: " + renderables.get(i).getClass().getCanonicalName());
            // remove all the projectiles that are of type slow
            if(renderables.get(i) instanceof EnemyProjectile ){         
                EnemyProjectile projectile = (EnemyProjectile) renderables.get(i);
                // utilise polymorphism
                if(!projectile.getProjectileStrategy().isSlow()){
                    renderables.get(i).takeDamage(1);
                     // add points via gamepoints.incrementPoints
                     gamePoints.incrementPoints(renderables.get(i));
                }               
            }
            }

        }      
    }

    public void removeAllSlowAliens(){

        for(int i = 0; i < renderables.size(); i++){
            if(renderables.get(i).isAlive()){
            if(renderables.get(i) instanceof Enemy){
                Enemy enemy = (Enemy) renderables.get(i);
                //polymorphism
                if(enemy.getProjectileStrategy().isSlow()){
                    enemy.takeDamage(1); // kill to remove aliens
                    // add points via gamepoints.incrementPoints
                     gamePoints.incrementPoints(renderables.get(i));
                }
            }
            }
        }
    }

    public void removeAllFastAliens(){
        for(int i = 0; i < renderables.size(); i++){
            if(renderables.get(i).isAlive()){
            if(renderables.get(i) instanceof Enemy){
                Enemy enemy = (Enemy) renderables.get(i);
                //polymorphism
                if(!enemy.getProjectileStrategy().isSlow()){
                    enemy.takeDamage(1); // kill to remove aliens
                    // add points via gamepoints.incrementPoints
                     gamePoints.incrementPoints(renderables.get(i));
                }
            }
            }
        }


        
        

    }

    

   
}
