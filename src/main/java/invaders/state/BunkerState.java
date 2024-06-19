package invaders.state;

import invaders.prototype.Prototype;

public interface BunkerState {
    public void takeDamage();

    public BunkerState copy();
}
