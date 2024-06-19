package invaders.Singleton;
/**
 * Serves as an enum to hold which file will run after user chooses difficulty
 * 
 * 
 /* */
public enum Levels {

    EASY("src/main/resources/config_easy.json"),
    MEDIUM("src/main/resources/config_medium.json"),
    HARD("src/main/resources/config_hard.json");

    private final String filePath;

    Levels(String filePath){
        this.filePath = filePath;
    }

    public String getfilePath(){
        return filePath;
    }

    
}
