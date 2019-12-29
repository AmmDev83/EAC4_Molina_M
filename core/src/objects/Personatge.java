package objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import helpers.AssetManager;
import utils.Settings;

// TODO: Exercici 1.2 // Objecte Personatge
public class Personatge extends Actor {
    // TODO: Exercici 1.2 // Variables necessaries
    // Distintes posicions del personatge, recta, pujant i baixant
    public static final int PERSONATGE_RECTA = 0;
    public static final int PERSONATGE_PUJANT = 1;
    public static final int PERSONATGE_BAIXANT = 2;

    // Paràmetres del personatge
    private Vector2 position;
    private int width, height;
    private int direction;

    private float runTime;

    private Rectangle collisionRect;
    // TODO: Exercici 3.1 // Variable necessaria
    private int puntuacio;
    private int ultimaPuntuacio;


    // TODO: Exercici 1.2 // Metode que crea l'objecte Personatge
    public Personatge(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem el personatge a l'estat normal
        direction = PERSONATGE_RECTA;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);


    }

    // TODO: Exercici 1.2 // Metode que dibuixa les propietats del objecte
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) getPersonatgeTexture().getKeyFrame(runTime), position.x, position.y, width , height );

    }

    // TODO: Exercici 1.2 // Metode que realitza les accions del objecte.
    public void act(float delta) {
        super.act(delta);
        runTime += delta;

        // Movem el personatge depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case PERSONATGE_BAIXANT:
                if (this.position.y - Settings.PERSONATGE_VELOCITY * delta >= 0) {
                    this.position.y -= Settings.PERSONATGE_VELOCITY * delta;
                }
                break;
            case PERSONATGE_PUJANT:
                if (this.position.y + height + Settings.PERSONATGE_VELOCITY * delta <= (Settings.GAME_HEIGHT / 2) - 5 + Settings.PERSONATGE_HEIGHT) {
                    this.position.y += Settings.PERSONATGE_VELOCITY * delta;
                }
                break;
            case PERSONATGE_RECTA:
                break;
        }
        collisionRect.set(position.x, position.y, Settings.PERSONATGE_WIDTH, Settings.PERSONATGE_HEIGHT);
        setBounds(position.x, position.y, width, height);


    }

    // TODO: Exercici 1.2 // Metode que reinicia les posicions del objecte.
    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.PERSONATGE_STARTX;
        position.y = Settings.PERSONATGE_STARTY;
        direction = PERSONATGE_RECTA;
        collisionRect = new Rectangle();
        this.setPuntuacio(0);
        // this.ultimaPuntuacio();
    }

    // TODO: Exercici 1.2 // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció del personatge: Puja
    public void goUp() {
        direction = PERSONATGE_PUJANT;
    }

    // Canviem la direcció del personatge: Baixa
    public void goDown() {
        direction = PERSONATGE_BAIXANT;
    }

    // Posem el personatge al seu estat original
    public void goStraight() {
        direction = PERSONATGE_RECTA;
    }

    public float getRunTime() {
        return runTime;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    // TODO Exercici 3.1 // Getter i Setter per la puntuacio
    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public int getUltimaPuntuacio() {
        return ultimaPuntuacio;
    }

    public void setSumarPunts(int puntuacio){
        this.setPuntuacio(this.getPuntuacio() + puntuacio);
    }

    public void ultimaPuntuacio(){
        this.ultimaPuntuacio = this.getPuntuacio();
        this.setPuntuacio(0);
    }

    // TODO: Exercici 1.2 // Obtenim el TextureRegion depenent de la posició del objecte
    public Animation getPersonatgeTexture() {

        switch (direction) {

            case PERSONATGE_RECTA:
                return AssetManager.personatgeAnim;
            case PERSONATGE_PUJANT:
                return AssetManager.personPujantAnim;
            case PERSONATGE_BAIXANT:
                return AssetManager.personBaixantAnim;
            default:
                return AssetManager.personatgeAnim;
        }
    }
}
