package objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import java.util.Random;

import helpers.AssetManager;
import utils.Methods;
import utils.Settings;
// TODO: Exercici 3.1 // Classe Moneda
public class Moneda extends Scrollable {
    // TODO: Exercici 3.1 // Variables necessaries
    public static final int NORMAL = 0;
    public static final int ESPECIAL = 1;
    private float runTime;

    private int tipus = 0;  // Per defecte NORMAL

    private Circle collisionCercle;

    Random r;

    int assetMonedes;


    public Moneda(float x, float y, float width, float height, float velocity, int tipus) {
        super(x, y, width, height, velocity);

        runTime = Methods.randomFloat(0,1);

        this.tipus = tipus;

        // Creem el rectangle de col·lisions
        collisionCercle = new Circle();

        /* Accions */
        r = new Random();
        assetMonedes = r.nextInt(12);

        // Rotacio
        RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(-90f);
        rotateAction.setDuration(0.2f);

        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);

        // Equivalent:
        // this.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(-90f, 0.2f)));

        this.addAction(repeat);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        runTime += delta;

        collisionCercle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);

    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Personatge personatge) {

        if (position.x <= personatge.getX() + personatge.getWidth()) {
            // Comprovem si han col·lisionat sempre que la moneda es trobi a la mateixa alçada que el personatge
            return (Intersector.overlaps(collisionCercle, personatge.getCollisionRect()));
        }

        return false;
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);

        float nouY = Methods.randomFloat(Settings.MIN_HEIGHT, Settings.MAX_HEIGHT) ;

        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  nouY;

        assetMonedes = r.nextInt(12);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // TODO: Exercici 3.1 // Bucle if per diferenciar del normal amb l'especial
        if(tipus == NORMAL){
            batch.draw(AssetManager.bonus, position.x, position.y, width, height);
        } else if(tipus == ESPECIAL){
            batch.draw(AssetManager.bonusEspecial, position.x, position.y, width, height);
        }


    }
    // TODO: Exercici 3.1 // Getter i setter per al tipus de moneda
    public int getTipus() {
        return tipus;
    }

    public void setTipus(int tipus) {
        this.tipus = tipus;
    }
}
