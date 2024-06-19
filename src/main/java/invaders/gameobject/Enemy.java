package invaders.gameobject;

import invaders.engine.GameEngine;
import invaders.factory.EnemyProjectile;
import invaders.factory.EnemyProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.prototype.Prototype;
import invaders.rendering.Renderable;
import invaders.factory.EnemyProjectile;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Enemy implements GameObject, Renderable , Prototype<Enemy>{
    private Vector2D position;
    private int lives = 1;
    private Image image;
    private int xVel = -1;

    private ArrayList<Projectile> enemyProjectile;
    private ArrayList<Projectile> pendingToDeleteEnemyProjectile;
    private ProjectileStrategy projectileStrategy;
    private ProjectileFactory projectileFactory;
    private Image projectileImage;
    private Random random = new Random();

    public Enemy(Vector2D position) {
        this.position = position;
        this.projectileFactory = new EnemyProjectileFactory();
        this.enemyProjectile = new ArrayList<>();
        this.pendingToDeleteEnemyProjectile = new ArrayList<>();
    }

    @Override
    public void start() {}

    @Override
    public void update(GameEngine engine) {
        if(enemyProjectile.size()<3){
            if(this.isAlive() &&  random.nextInt(120)==20){
                Projectile p = projectileFactory.createProjectile(new Vector2D(position.getX() + this.image.getWidth() / 2, position.getY() + image.getHeight() + 2),projectileStrategy, projectileImage);
                enemyProjectile.add(p);
                engine.getPendingToAddGameObject().add(p);
                engine.getPendingToAddRenderable().add(p);
            }
        }else{
            pendingToDeleteEnemyProjectile.clear();
            for(Projectile p : enemyProjectile){
                if(!p.isAlive()){
                    engine.getPendingToRemoveGameObject().add(p);
                    engine.getPendingToRemoveRenderable().add(p);
                    pendingToDeleteEnemyProjectile.add(p);
                }
            }

            for(Projectile p: pendingToDeleteEnemyProjectile){
                enemyProjectile.remove(p);
            }
        }

        if(this.position.getX()<=this.image.getWidth() || this.position.getX()>=(engine.getGameWidth()-this.image.getWidth()-1)){
            this.position.setY(this.position.getY()+25);
            xVel*=-1;
        }

        this.position.setX(this.position.getX() + xVel);

        if((this.position.getY()+this.image.getHeight())>=engine.getPlayer().getPosition().getY()){
            engine.getPlayer().takeDamage(Integer.MAX_VALUE);
        }

        /*
        Logic TBD
         */

    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return this.image.getWidth();
    }

    @Override
    public double getHeight() {
       return this.image.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setProjectileImage(Image projectileImage) {
        this.projectileImage = projectileImage;
    }

    @Override
    public void takeDamage(double amount) {
        this.lives-=1;
    }

    @Override
    public double getHealth() {
        return this.lives;
    }

    @Override
    public String getRenderableObjectName() {
        return "Enemy";
    }

    @Override
    public boolean isAlive() {
        return this.lives>0;
    }

    public void setProjectileStrategy(ProjectileStrategy projectileStrategy) {
        this.projectileStrategy = projectileStrategy;
    }

    public ProjectileStrategy getProjectileStrategy(){
        return projectileStrategy;
    }
    public ProjectileFactory getProjectileFactory(){
        return projectileFactory;
    }
    public ArrayList<Projectile> getEnemyProjectile(){
        return this.enemyProjectile;
    }
    public ArrayList<Projectile> getPending(){
        return this.pendingToDeleteEnemyProjectile;
    }

    private Enemy(Vector2D position, int lives, ProjectileFactory projectileFactory, ArrayList<Projectile> enemyProjectile, ArrayList<Projectile> pendingToDeleteEnemyProjectile,Image image) {
        this.position = new Vector2D(position.getX(), position.getY());
        this.lives = lives;
        this.projectileFactory = projectileFactory; 
        this.enemyProjectile = enemyProjectile;
        this.pendingToDeleteEnemyProjectile = pendingToDeleteEnemyProjectile;
        this.image = image;
    }
    
    // public Enemy copy() {
    //     return new Enemy(this.position, this.lives, this.projectileFactory, new ArrayList<>(this.enemyProjectile), new ArrayList<>(this.pendingToDeleteEnemyProjectile), this.image);
    // }

    @Override
    public Enemy copy() {
        ArrayList<Projectile> copiedEnemyProjectiles = new ArrayList<>();
        for (Projectile p : this.enemyProjectile) {
            if (p instanceof EnemyProjectile) {
                copiedEnemyProjectiles.add(((EnemyProjectile) p).copy());
            } 
            // You can add more else-if conditions here for other Projectile subtypes 
            // when they have their own copy methods.
        }
    
        ArrayList<Projectile> copiedPendingToDelete = new ArrayList<>();
        for (Projectile p : this.pendingToDeleteEnemyProjectile) {
            if (p instanceof EnemyProjectile) {
                copiedPendingToDelete.add(((EnemyProjectile) p).copy());
            } 
            // Similar to above, you can add conditions for other Projectile subtypes.
        }
    
        return new Enemy(this.position, this.lives, this.projectileFactory, copiedEnemyProjectiles, copiedPendingToDelete, this.image);
    }
    

}
