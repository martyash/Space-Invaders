package invaders.state;
import invaders.gameobject.Bunker;
import javafx.scene.image.Image;
import java.io.File;

public class RedState implements BunkerState {
    private Bunker bunker;

    public RedState(Bunker bunker){
        this.bunker = bunker;
    }

    @Override
    public void takeDamage() {
        // bunker.

    }

    private static RedState createRedState(Bunker bunkerCopy){
        RedState red = new RedState(bunkerCopy);
        return red;
    }

   // @Override
  //  public RedState copy(){
  //      Bunker bunkerCopy = this.bunker.copy();  
  //      return RedState.createRedState(bunkerCopy);
  //  }

  public BunkerState copy(){
    // Return a new GreenState associated with the same bunker.
    return new RedState(bunker);
}
}
