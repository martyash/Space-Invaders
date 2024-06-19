package invaders.Observer;

import java.util.ArrayList;

import invaders.prototype.Prototype;

public class GameTimer implements Prototype{
    private ArrayList<TimeObserver> observers = new ArrayList<>();
    private long timePassedInNano = 0;
    private boolean isGameOver = false;

    public GameTimer() {
        observers = new ArrayList<>();
        timePassedInNano = 0;
        isGameOver = false;
    }


    public void addObserver(TimeObserver o){
        observers.add(o);
    }

    public void removeObserver(TimeObserver o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for (TimeObserver o : observers) {
            o.update(timePassedInNano,isGameOver);
        }
    }

    public void incrementTime(long time) {
        
        timePassedInNano += time;
        notifyObservers();
    }

    public long getTime(){
        return timePassedInNano;
    }

    public void stopTimer() {
        this.isGameOver = true;
        notifyObservers();
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    private GameTimer(ArrayList<TimeObserver> observers ,long timePassedInNano,boolean isGameOver){
        this.observers = observers;
        this.timePassedInNano = timePassedInNano;
        this.isGameOver = isGameOver;
    }

    @Override
    public GameTimer copy(){
        ArrayList<TimeObserver> copiedObservers = new ArrayList<>(this.observers);
        return new GameTimer(copiedObservers, getTime(), isGameOver());
    }



    
}
