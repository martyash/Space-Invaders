package invaders.strategy;

import invaders.factory.Projectile;


public interface ProjectileStrategy {
   public void update(Projectile p);

   default boolean isSlow(){
      return false;
   }
}


