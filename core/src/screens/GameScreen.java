package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import helpers.AssetManager;
import helpers.InputHandler;
import objects.Monstre;
import objects.Personatge;
import objects.ScrollHandler;
import utils.Settings;

public class GameScreen implements Screen {
    // Els estats del joc
    // TODO: Exercici 4 // Estats del joc PAUSE, RESUME
    public enum GameState {
        READY, RUNNING, GAMEOVER, PAUSE, RESUME
    }

    private GameState currentState;

    // Variables del joc
    private Stage stage;
    private Personatge personatge;
    private ScrollHandler scrollHandler;
    // Representació de figures geomètriques
    private ShapeRenderer shapeRenderer;
    // Per obtenir el batch de l'stage
    private Batch batch;
    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;
    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;
    // TODO: Exercici 3.1 // Variables necessaries
    private Label.LabelStyle puntuacioStyle;
    private Label puntuacioLbl;
    private Container conatinerPuntuacio;
    // TODO: Exercici 4 // Variables necessaries
    private Image pause;
    // TODO: Exercici 5.1 // Variable necessaria
    private GlyphLayout missatgeLayout;

    // TODO: Exercici 5.3 // Variable Preferences
    private Preferences prefs;
    private int puntuacio = 0;

    public GameScreen(Batch prevBatch, Viewport prevViewport) {
        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem el personatge i la resta d'objectes
        personatge = new Personatge(Settings.PERSONATGE_STARTX, Settings.PERSONATGE_STARTY, Settings.PERSONATGE_WIDTH, Settings.PERSONATGE_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(personatge);
        // Donem nom a l'Actor
        personatge.setName("Molina_Abraham");
        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready?");

        // TODO: Exercici 2.1 // Instanciem els objectes
        puntuacioStyle = new Label.LabelStyle(AssetManager.font, null);
        puntuacioLbl = new Label("Score: "+ 0, puntuacioStyle);
        // TODO: Exercici 2.1 // Creem un container i afegim el Label, posem tranform a true,
        //  el centrem al container i posicionem el container
        conatinerPuntuacio = new Container(puntuacioLbl);
        conatinerPuntuacio.setTransform(true);
        conatinerPuntuacio.center();
        conatinerPuntuacio.setPosition(5 + puntuacioLbl.getWidth() / 2, Settings.GAME_HEIGHT - puntuacioLbl.getHeight() / 2);
        stage.addActor(conatinerPuntuacio);

        // Pasem l'estat del joc
        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));
        // TODO: Exercici 4 // Inicialitzem el boto, li donem nom, el posicionem a la pantalla i li donem mides
        pause = new Image(AssetManager.botoPausa);
        pause.setName("Pausa");
        pause.setPosition((Settings.GAME_WIDTH) - Settings.BOTO_PAUSA_WIDTH - 5, Settings.GAME_HEIGHT - Settings.BOTO_PAUSA_HEIGHT);
        pause.setSize(Settings.BOTO_PAUSA_WIDTH, Settings.BOTO_PAUSA_HEIGHT);
        // TODO: Exercici 4 // Afegim el boto al stage
        stage.addActor(pause);
        // TODO: Exercici 5.1 // Inicialitzem el missatgeLayout
        missatgeLayout = new GlyphLayout();

        // TODO: Exercici 5.3 // Inicialitzem les preferencies
        prefs = Gdx.app.getPreferences("prefs");

    }

    private void drawElements(){

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));
        // Pintem el personatge
        shapeRenderer.rect(personatge.getX(), personatge.getY(), personatge.getWidth(), personatge.getHeight());

        // Recollim tots els Monstres
        ArrayList<Monstre> monstres = scrollHandler.getMonstres();
        Monstre monstre;

        for (int i = 0; i < monstres.size(); i++) {

            monstre = monstres.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1,0,0,1);
                    break;
                case 1:
                    shapeRenderer.setColor(0,0,1,1);
                    break;
                case 2:
                    shapeRenderer.setColor(1,1,0,1);
                    break;
                default:
                    shapeRenderer.setColor(1,1,1,1);
                    break;
            }
            shapeRenderer.circle(monstre.getX() + monstre.getWidth()/2, monstre.getY() + monstre.getWidth()/2, monstre.getWidth()/2);
        }
        shapeRenderer.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Dibuixem i actualitzem tots els actors de l'stage
        stage.draw();

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {

            case GAMEOVER:
                updateGameOver(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                updateReady();
                break;
            // TODO: Exercici 4 // Afegim els nous estats al render
            case PAUSE:
                updatePause();
                break;
            case RESUME:
                updateRunning(delta);
                break;
        }

        //drawElements();
    }
    // TODO: Exercici 5.2 // Metode a modificar per l'exercici
    private void updateReady() {
        // Dibuixem el text al centre de la pantalla
        batch.begin();
        puntuacioLbl.setVisible(false);
        puntuacioLbl.setText("Score: "+ 0);
        pause.setVisible(false);
        // TODO: Exercici 5.3 //  Recuperem la puntuacio de les preferencies
        int score = prefs.getInteger("score");
        // TODO: Exercici 5.2 //  Recuperem l'String del nivell
        String nivell = retornaNivell(score);
        // TODO: Exercici 5.2 //  Afegim al Layout el missatge HighScore mes la ultima puntuacio i el nivell
        // TODO: Exercici 5.3 // Afegim score al missatge
        missatgeLayout.setText(AssetManager.font, "HighScore :"+score+"\n     "+nivell);
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT - 75) );
        // TODO: Exercici 5.2 //  Afegim el Layout al draw
        AssetManager.font.draw(batch, missatgeLayout, (Settings.GAME_WIDTH / 2) - missatgeLayout.width / 2, (Settings.GAME_HEIGHT - 20)  );
        batch.end();
    }

    private void updateRunning(float delta) {
        stage.act(delta);
        puntuacioLbl.setVisible(true);
        pause.setVisible(true);
        AssetManager.music.setVolume(Settings.VOLUM_MUSICA);
        AssetManager.music.play();

        if (scrollHandler.collides(personatge)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            AssetManager.explosioSo.play();
            stage.getRoot().findActor("Molina_Abraham").remove();
            textLayout.setText(AssetManager.font, "Game Over :'(");
            currentState = GameState.GAMEOVER;
        }
        // TODO: Exercici 3.1 // Si el personatge colisiona amb la moneda
        if (scrollHandler.collidesBonus(personatge)) {
            puntuacioLbl.setText("Score : " + personatge.getPuntuacio());
            // TODO: Exercici 3.2 // Reproduim musica i executem animacio al label score
            AssetManager.bonusMusic.play();
            conatinerPuntuacio.addAction(Actions.sequence(Actions.scaleTo(1.5f, 1.5f,0.5f),
                    Actions.scaleTo(0.75f, 0.75f, 0.5f)));
        }
    }

    private void updateGameOver(float delta) {
        stage.act(delta);
        pause.setVisible(false);
        batch.begin();
        AssetManager.font.draw(batch, textLayout, Settings.GAME_WIDTH  / 2 - textLayout.width / 2,
                Settings.GAME_HEIGHT  / 2);
        // TODO: Exercici 5.1 // Creem variable per emmagatzemar el missatge
        String missatge = retornaMissatge();

        // TODO: Exercici 5.1 // Afegim el text i li pasem la font, i el pintem al batch
        missatgeLayout.setText(AssetManager.font, missatge);
        AssetManager.font.draw(batch, missatgeLayout, Settings.GAME_WIDTH  / 2 - missatgeLayout.width / 2,
                (Settings.GAME_HEIGHT) / 2  - textLayout.height * 2);
        // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
        batch.draw(AssetManager.explosioAnim.getKeyFrame(explosionTime, false), (personatge.getX() +
                personatge.getWidth() / 2) - 32, personatge.getY() + personatge.getHeight() / 2 - 32, 64, 64);
        batch.end();
        // TODO: Exercici 5.3 // Si la puntuacio actual es mes gran que la ultima, afegim a preferencies la puntuacio
        int score = prefs.getInteger("score", -1);
        // TODO: Exercici 5.3 // Comprovem si hi ha algun registre, si no hi ha,
        //  ho pasem directament a preferencies
        if(score == -1){
            prefs.putInteger("score", personatge.getPuntuacio());
        } else{
            // TODO: Exercici 5.3 // Si hi ha algun registre, comprovem si l'actual es mes gran, per emmagatzemar-lo
            if(personatge.getPuntuacio() > score){
                prefs.putInteger("score", personatge.getPuntuacio());
            }
        }
        prefs.flush();

        explosionTime += delta;

    }
    // TODO: Exercici 4 // Metode que pausa el joc, amaga el boto pausa i el score, i baixa el volum de la musica
    private void updatePause(){
        stage.act(0);
        puntuacioLbl.setVisible(false);
        pause.setVisible(false);
        AssetManager.music.setVolume(0.2f);
        AssetManager.music.play();
    }
    // TODO: Exercici 5.2 //  Metode que retorna l'String del nivell segons la puntuacio
    private String retornaNivell(int puntuacio){
        String retorn = null;
        // TODO: Exercici 5.2 // Segons la puntuacio, mostrem un o un altre missatge
        if (puntuacio < 100){
            retorn = "N00b";
        } else if (puntuacio < 150 || personatge.getPuntuacio() >= 100){
            retorn = "Person";
        } else if (puntuacio >= 150 ){
            retorn = "Pro";
        }

        return retorn;
    }

    private String retornaMissatge(){
        String retorn = null;
        // TODO: Exercici 5.1 // Segons la puntuacio, mostrem un o un altre missatge
        if (personatge.getPuntuacio() < 100){
            retorn = Settings.NOOB;
        } else if (personatge.getPuntuacio() < 150 && personatge.getPuntuacio() >= 100){
            retorn = Settings.DONE;
        } else if (personatge.getPuntuacio() >= 150 ){
            retorn = Settings.YEAH;
        }

        return retorn;
    }

    public void reset() {

        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Are you\nready?");

        // Cridem als restart dels elements
        personatge.reset();
        scrollHandler.reset();

        // Posem l'estat a READY
        currentState = GameState.READY;

        // Afegim el personatge a l'stage
        stage.addActor(personatge);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Stage getStage() {
        return stage;
    }

    public Personatge getPersonatge() {
        return personatge;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }
}

