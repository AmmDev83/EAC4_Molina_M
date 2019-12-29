package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyAMMGame;

import helpers.AssetManager;
import objects.ScrollHandler;
import utils.Methods;
import utils.Settings;

public class SplashScreen implements Screen {
    // Objectes necessaris
    private Stage stage;
    private MyAMMGame game;
    // TODO: Exercici 2.1 // Variables del exerici
    private Label.LabelStyle textStyle;
    private Label textLbl;

    private ScrollHandler scrollHandler;
    // TODO: Exercici 2.2 // Variables del exerici
    float posInicial;
    float posFinal;
    float pos;
    private float runTime;
    private Animation<TextureRegion> animRecta, animBaixant, animPujant;
    private SpriteBatch batch;

    private TextureRegion currentFrame;

    public SplashScreen(MyAMMGame game) {

        this.game = game;

        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera perquè
        // faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(false);


        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);

        scrollHandler = new ScrollHandler();
        scrollHandler.getStage();

        // Creem el fons
        Image fons = new Image(AssetManager.background);
        fons.setWidth(Settings.GAME_WIDTH);
        fons.setHeight(Settings.GAME_HEIGHT);
        stage.addActor(fons);

        // TODO: Exercici 2.1 // Instanciem els objectes
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Nit de Monstres", textStyle);
        // TODO: Exercici 2.1 // Creem un container i afegim el Label, posem tranform a true,
        //  el centrem al container i posicionem el container
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setPosition(Settings.GAME_WIDTH / 2 , Settings.GAME_HEIGHT / 2 - textLbl.getHeight() / 2);
        // TODO: Exercici 2.1 // Donem les propietats requerides del exercici a la sequencia de l'acció
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.parallel(Actions.alpha(1, 1),
                Actions.scaleTo(1.5f, 1.5f, 1)), Actions.parallel(Actions.alpha(0, 1),
                Actions.scaleTo(1, 1, 1)))));
        // TODO: Exercici 2.1 // Afegim el container al stage
        stage.addActor(container);
        currentFrame = new TextureRegion();
        // TODO: Exercici 2.2 // Iniciem posicio Inicial, Final i li passem a Pos, per fer les repeticions
        posInicial = Methods.randomFloat(0, Settings.GAME_HEIGHT / 2 + textLbl.getHeight());
        posFinal = Methods.randomFloat(0, Settings.GAME_HEIGHT / 2 + textLbl.getHeight());
        pos = posInicial;
        // TODO: Exercici 2.2 // Instanciem el batch on reproduirem l'animacio
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
        }
        // TODO: Exercici 2.2 // Iniciem el batch i el TextureRegion per les imatges de les animacions
        batch.begin();
        //currentFrame = new TextureRegion();
        // TODO: Exercici 2.2 // Controlem el temps per quina imatge s'ha de dibuixar
        runTime += Gdx.graphics.getDeltaTime();
        // TODO: Exercici 2.2 // Si el protagonista Puja, carreguem les seves animacions i anem canviant la posicio Inicial
        if(posInicial < posFinal){
            animPujant = AssetManager.personPujantAnim;
            currentFrame = animPujant.getKeyFrame(runTime, true);
            posInicial += Settings.PERSONATGE_VELOCITY * delta;
            // TODO: Exercici 2.2 // Si arriba a la posicio Final, tornem a començar
            if(posInicial >= posFinal){
                posInicial = pos;
            }
        }
        // TODO: Exercici 2.2 // Si el protagonista Baixa, carreguem les seves animacions i anem canviant la posicio Inicial
        if(posInicial > posFinal){
            animBaixant = AssetManager.personBaixantAnim;
            currentFrame = animBaixant.getKeyFrame(runTime, true);
            posInicial -= Settings.PERSONATGE_VELOCITY * delta;
            // TODO: Exercici 2.2 // Si arriba a la posicio Final, tornem a començar
            if(posInicial <= posFinal){
                posInicial = pos;
            }

        }
        // TODO: Exercici 2.2 // Si el protagonista va Recta, carreguem les seves animacions
        if(posInicial == posFinal){
            animRecta = AssetManager.personatgeAnim;
            currentFrame = animRecta.getKeyFrame(runTime, true);
        }
        // TODO: Exercici 2.2 // Anem dibuixant les animacions pertinents
        batch.draw((TextureRegion) currentFrame, 0 + Settings.PERSONATGE_WIDTH, posInicial, Settings.PERSONATGE_WIDTH * 8 , Settings.PERSONATGE_HEIGHT * 8);
        // TODO: Exercici 2.2 // Aturem el batch
        batch.end();

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
}
