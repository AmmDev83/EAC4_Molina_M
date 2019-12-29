package objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import helpers.AssetManager;
import screens.GameScreen;
import utils.Methods;
import utils.Settings;

// TODO: Exercici 1.2 // Objecte Monstre
public class Monstre extends Scrollable {

    private float runTime;

    private Rectangle collisionRect;

    Random r;

    int assetMonstre;


    // TODO: Exercici 1.2 // Metode que crea l'objecte Monstre.
    public Monstre(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        runTime = Methods.randomFloat(0,1);

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        /* Accions */
        r = new Random();
        assetMonstre = r.nextInt(8);


    }

    // TODO: Exercici 1.2 // Metode que realitza les accions del objecte
    @Override
    public void act(float delta) {
        super.act(delta);
        runTime += delta;

        collisionRect.set(position.x, position.y, Settings.MONSTRE_WIDTH, Settings.MOSNTRE_HEIGHT);

    }

    // TODO: Exercici 1.2 // Metode que reinicia l'objecte monstre.
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número aleatori entre MIN i MAX
        float newPosition = Methods.randomFloat(Settings.MIN_HEIGHT, Settings.MAX_HEIGHT);
        // Modificarem l'alçada i l'amplada segons l'aleatori anterior
        width = Settings.MONSTRE_WIDTH;
        height = Settings.MOSNTRE_HEIGHT;

        position.y = newPosition;
        assetMonstre = r.nextInt(8);


    }

    // TODO: Exercici 1.2 // Metode que gestiona les collides
    // Retorna true si hi ha col·lisió
    public boolean collides(Personatge personatge) {

        if (position.x <= personatge.getX() + personatge.getWidth()) {
        // Comprovem si han col·lisionat sempre que el monstre es trobi a la mateixa alçada que el personatge
            return (Intersector.overlaps(collisionRect, personatge.getCollisionRect()));
        }
        if(position.y == personatge.getY() - personatge.getHeight()){
            return (Intersector.overlaps(collisionRect, personatge.getCollisionRect()));
        }
        return false;
    }


    // Getter pel runTime
    public float getRunTime() {

        return runTime;
    }

    // TODO: Exercici 1.2 // Metode que dibuixa l'objecte
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.monstreAnim.getKeyFrame(runTime), position.x, position.y, width, height);

    }
}
