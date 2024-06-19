package invaders.state;

import invaders.gameobject.Bunker;
import invaders.prototype.Prototype;
import javafx.scene.image.Image;
import java.io.File;

public class GreenState implements BunkerState {
    private Bunker bunker;

    public GreenState(Bunker bunker){
        this.bunker = bunker;
    }

    @Override
    public void takeDamage() {
        bunker.setImage(new Image(new File("src/main/resources/bunkerYellow.png").toURI().toString()));
        bunker.setState(new YellowState(bunker));
    }

    
    private static GreenState createGreenState(Bunker bunkerCopy){
        GreenState green = new GreenState(bunkerCopy);
        return green;
    }

   // @Override
  //  public GreenState copy(){
  //      Bunker bunkerCopy = this.bunker.copy();
  //      return GreenState.createGreenState(bunkerCopy);
  //  }

  @Override
  public BunkerState copy(){
    // Return a new GreenState associated with the same bunker.
    return new GreenState(bunker);
}
}
