package invaders.factory;

import invaders.engine.GameEngine;

import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

public class EnemyProjectile extends Projectile {
    private ProjectileStrategy strategy;

    public EnemyProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        super(position,image);
        this.strategy = strategy;
    }

    @Override
    public void update(GameEngine model) {
        strategy.update(this);
        if(this.getPosition().getY()>= model.getGameHeight() - this.getImage().getHeight()){
            this.takeDamage(1);
        }

    }
    @Override
    public String getRenderableObjectName() {
        return "EnemyProjectile";
    }

    public ProjectileStrategy getProjectileStrategy(){
        return this.strategy;
    }

    public boolean isSlow(){
        return strategy.isSlow();
    }

    public ProjectileStrategy getStrategy(){
        return this.strategy;
    }
    // cannot create a duplicate constructor so have to make a static method
    private static EnemyProjectile createEP(Vector2D position, ProjectileStrategy strategy, Image image) {
        EnemyProjectile copiedProjectile = new EnemyProjectile(position, strategy, image);
        return copiedProjectile;
    }

    @Override
    public EnemyProjectile copy(){
        return EnemyProjectile.createEP(getPosition(), getStrategy(), getImage());
    }

    
}
