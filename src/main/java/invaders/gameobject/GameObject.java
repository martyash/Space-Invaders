package invaders.gameobject;

import invaders.engine.GameEngine;
import invaders.prototype.Prototype;

// contains basic methods that all GameObjects must implement
public interface GameObject  {

    public void start();
    public void update(GameEngine model);
   

}
