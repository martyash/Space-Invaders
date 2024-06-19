package invaders.memento;

public class Caretaker { //caretaker
    /*
 * SaveState saves the state after pressing 's' and when 'd' is pressed
 * we 'undo the state' by setting the memento to null after performing neccessary operations
 * 
*/

    private Memento memento = null;

    public void saveState(GameOriginator gameState){ // saves game state
        if(memento == null){
            memento = gameState.save();
        }

    }

    public void undoState(GameOriginator gameState){
        if(memento != null){
            gameState.setMemento(memento);
            memento = null;

        }
    }



    
}
