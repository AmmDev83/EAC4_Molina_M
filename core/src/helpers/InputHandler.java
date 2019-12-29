package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import objects.Personatge;
import screens.GameScreen;

public class InputHandler implements InputProcessor {
    // Objectes necessaris
    private Personatge personatge;
    private GameScreen screen;
    private Vector2 stageCoord;
    private Stage stage;

    // Enter per a la gestió del moviment d'arrossegament
    int previousY = 0;

    public InputHandler(GameScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        personatge = screen.getPersonatge();
        stage = screen.getStage();

    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //previousY = screenY;
        //return true;
        switch (screen.getCurrentState()) {
            case READY:

                // Si fem clic comencem el joc
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
            case RUNNING:
                previousY = screenY;

                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);
                if (actorHit != null) {
                    Gdx.app.log("HIT", actorHit.getName());
                    // TODO: Exercici 4.1 // Si premem el boto, canvi d'estat
                    if(actorHit.getName().equals("Pausa")){
                        screen.setCurrentState(GameScreen.GameState.PAUSE);
                    }
                }
                break;
            // Si l'estat és GameOver tornem a iniciar el joc
            case GAMEOVER:
                screen.reset();
                break;
            // TODO: Exercici 4.1 // Si tornem a premer el boto, conviem d'estat
            case PAUSE:
                // Si fem clic comencem el joc
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Quan deixem anar el dit acabem un moviment
        // i posem el personatge a l'estat normal
        personatge.goStraight();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Posem un llindar per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)

            // Si la Y és major que la que tenim
            // guardada és que va cap avall
            if (previousY < screenY) {
                personatge.goDown();
            } else {
            // En cas contrari cap amunt
                personatge.goUp();
            }
        // Guardem la posició de la Y
        previousY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
