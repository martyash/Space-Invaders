package invaders.engine;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class KeyboardInputHandler {
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private boolean q = false;
    private boolean w = false;
    private boolean e = false;
    private boolean r = false;
    private boolean s = false;
    private boolean d = false;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    private Map<String, MediaPlayer> sounds = new HashMap<>();

    KeyboardInputHandler(GameEngine model) {
        this.model = model;

        // TODO (longGoneUser): Is there a better place for this code?
        URL mediaUrl = getClass().getResource("/shoot.wav");
        String jumpURL = mediaUrl.toExternalForm();

        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        sounds.put("shoot", mediaPlayer);
    }

    void handlePressed(KeyEvent keyEvent) {
        if (pressedKeys.contains(keyEvent.getCode())) {
            return;
        }
        pressedKeys.add(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            if (model.shootPressed()) {
                MediaPlayer shoot = sounds.get("shoot");
                shoot.stop();
                shoot.play();
            }
        }

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = true;
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            right = true;
        }
        if (keyEvent.getCode().equals(KeyCode.Q)){
            q = true;
            if(w||e|r){
                return; // prevent both cheats at once
            }
            
        }
        if(keyEvent.getCode().equals(KeyCode.W)){
            w = true;
            if(q || e || r){
                return; // prevent both cheats at once
            }
        }
        if(keyEvent.getCode().equals(KeyCode.E)){
            e = true;
            if(q || w || r){
                return;
            }
        }
          if(keyEvent.getCode().equals(KeyCode.R)){
            r = true;
            if(q || w || e){
                return;
            }
        }
        if(keyEvent.getCode().equals(KeyCode.S)){
            s = true;
        }
        if(keyEvent.getCode().equals(KeyCode.D)){
            d = true;
        }
       
        if (left) {
            model.leftPressed();
        }

        if(right){
            model.rightPressed();
        }
        if(q){
            model.qPressed();    
            }
        if(w){
            model.wPressed();
        }
        if(e){
            model.ePressed();
        }
        if(r){
            model.rPressed();
        }
        if(s){
            model.sPressed();
        }
        if(d){
            model.dPressed();
        }

        }
        

        
    

    void handleReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = false;
            model.leftReleased();
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            model.rightReleased();
            right = false;
        }

        if (keyEvent.getCode().equals(KeyCode.Q)){
            model.qReleased();
            q = false;
        }
        if (keyEvent.getCode().equals(KeyCode.W)){
            model.qReleased();
            w = false;
        }
        if (keyEvent.getCode().equals(KeyCode.E)){
            model.eReleased();
            e = false;
        }
        if (keyEvent.getCode().equals(KeyCode.R)){
            model.qReleased();
            r = false;
        }
        if(keyEvent.getCode().equals(KeyCode.S)){
            model.sReleased();
            s = false;
        }
        if(keyEvent.getCode().equals(KeyCode.D)){
            model.dReleased();
            d = false;
        }
    }
}
