package invaders.Singleton;


public class LevelSingleton {
    private static LevelSingleton uniqueInstance;
    private Levels difficulty;

   
    private LevelSingleton(){
        this.difficulty = Levels.EASY;
    }


    public static LevelSingleton getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new LevelSingleton();
        }
        return uniqueInstance;
    }

    public void setDifficulty(Levels newLevel){
        this.difficulty = newLevel;
    }

    public Levels getDifficulty(){
        return this.difficulty;
    }

    public String getCurrentFilePath(){
        return difficulty.getfilePath();
    }


}
