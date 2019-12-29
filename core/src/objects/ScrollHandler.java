package objects;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;

import screens.GameScreen;
import utils.Methods;
import utils.Settings;

public class ScrollHandler extends Group {
    // TODO: Exercici 1.2 // Variables necessaries
    // Fons de pantalla
    Background bg, bg_back;

    // Monstres
    int numMonstres;
    ArrayList<Monstre> monstres;
    // TODO: Exercici 3.1 // Variables necessaries
    // Bonus
    int numMonedes = 5;
    ArrayList<Moneda> monedes;

    // Objecte random
    Random r;



    // TODO: Exercici 1.2 // Constructor de la rotacio de la pantalla que anira afegint actors
    public ScrollHandler() {
        //Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        //Afegim els fons (actors) al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 2 monstres
        numMonstres = 2;

        // Creem l'ArrayList
        monstres = new ArrayList<Monstre>();

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_HEIGHT, Settings.MAX_HEIGHT) ;

        // Afegim el primer monstre a l'array i al grup
        Monstre monstre = new Monstre(Settings.GAME_WIDTH, newSize, Settings.MONSTRE_WIDTH,
                Settings.MOSNTRE_HEIGHT, Settings.MONSTRE_VELOCITAT);
        monstres.add(monstre);
        addActor(monstre);

        // Des del segon fins l'últim monstre
        for (int i = 1; i < numMonstres; i++) {
            // Creem la posicio y aleatòria
            newSize = Methods.randomFloat(Settings.MIN_HEIGHT, Settings.MAX_HEIGHT);
            // Afegim el monstre
            monstre = new Monstre(Settings.GAME_WIDTH, newSize, Settings.MONSTRE_WIDTH,
                    Settings.MOSNTRE_HEIGHT, Settings.MONSTRE_VELOCITAT);
            // Afegim el monstre a l'ArrayList
            monstres.add(monstre);
            // Afegim el monstre al grup d'actors
            addActor(monstre);
        }
        // TODO: Exercici 3.1 // Inicialitzem array per introduir les monedes i creem una moneda
        monedes = new ArrayList<Moneda>();
        float nouY = Methods.randomFloat(Settings.MIN_HEIGHT, Settings.MAX_HEIGHT) ;
        // Començem amb un bonus normal
        Moneda moneda = new Moneda(Settings.GAME_WIDTH, nouY, Settings.MONEDA_WIDTH,
                Settings.MONEDA_HEIGHT, Settings.VELOCITAT_BONUS, 0);
        // TODO: Exercici 3.1 // Afegim la moneda al array i al stage
        monedes.add(moneda);
        addActor(moneda);
        // TODO: Exercici 3.1 // Recorrem l'aray per introduir mes monedes
        // Des de la segona fins l'última moneda
        for (int i = 1; i < numMonedes ; i++) {
            float nouBonus = Methods.randomFloat(0f, 100f);
            // Creem la posicio y aleatòria
            nouY = Methods.randomFloat(Settings.MIN_HEIGHT, Settings.MAX_HEIGHT - moneda.getHeight());
            if(nouBonus < 10 ){
                moneda = new Moneda(Settings.GAME_WIDTH + Settings.MONSTRE_GAP, nouY, Settings.MONEDA_WIDTH,
                        Settings.MONEDA_HEIGHT, Settings.VELOCITAT_BONUS_ESPECIAL, 1);

            }else if (nouBonus < 90){
                moneda = new Moneda(Settings.GAME_WIDTH + Settings.MONSTRE_GAP, nouY, Settings.MONEDA_WIDTH,
                        Settings.MONEDA_HEIGHT, Settings.VELOCITAT_BONUS, 0);
            }
            // Afegim la moneda a l'ArrayList
            monedes.add(moneda);
            // Afegim la moneda al grup d'actors
            addActor(moneda);
        }

    }
    // TODO: Exercici 1.2 // Metode que va actualitzant el ScrollHandler
    @Override
    public void act(float delta) {
        super.act(delta);

        // Si algun element es troba fora de la pantalla, fem un reset de l'element
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < monstres.size(); i++) {

            Monstre monstre = monstres.get(i);
            if (monstre.isLeftOfScreen()) {
                if (i == 0) {
                    monstre.reset(Settings.GAME_WIDTH);
                } else {
                    monstre.reset(Settings.GAME_WIDTH);
                }
            }
        }
        // TODO: Exercici 3.1 // Actualitzem l'estat de les monedes
        for (int i = 0; i < monedes.size(); i++) {

            Moneda moneda = monedes.get(i);
            if (moneda.isLeftOfScreen()) {
                if (i == 0) {
                    moneda.reset(Settings.GAME_WIDTH);
                } else {
                    moneda.reset(Settings.GAME_WIDTH);
                }
            }
        }
    }
    // TODO: Exercici 1.2 // Metode que comprovara si han colisionat
    public boolean collides(Personatge mPersonatge) {

        // Comprovem les col·lisions entre cada monstre i el personatge
        for (Monstre monstre  : monstres) {
            if (monstre.collides(mPersonatge)) {
                return true;
            }
        }
        return false;
    }
    // TODO: Exercici 3.1 // Metode que gestiona les colisions de monedes
    public boolean collidesBonus(Personatge mPersonatge){
        // Comprovem les col·lisions entre cada monstre i el personatge
        for (Moneda moneda  : monedes) {
            if (moneda.collides(mPersonatge)) {
                // TODO: Exercici 3.1 // Si la moneda es normal
                if(moneda.getTipus() == Moneda.NORMAL){
                    mPersonatge.setSumarPunts(10);
                    moneda.reset(Settings.GAME_WIDTH);
                    // TODO: Exercici 3.1 // Si la moneda es especial
                }else if (moneda.getTipus() == Moneda.ESPECIAL){
                    mPersonatge.setSumarPunts(50);
                    moneda.reset(Settings.GAME_WIDTH);
                }
                return true;
            }
        }
        return false;

    }
    // TODO: Exercici 1.2 // Metode que reinicia les posicions
    public void reset() {

        // Posem el primer monstre fora de la pantalla per la dreta
        monstres.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta de monstres
        for (int i = 1; i < monstres.size(); i++) {

            monstres.get(i).reset(Settings.GAME_WIDTH - Settings.MONSTRE_GAP);

        }
        // TODO: Exercici 3.1 // Si sorten les monedes del escenari
        // Posem la primera moneda fora de la pantalla per la dreta
        monedes.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta de monedes
        for (int i = 1; i < monedes.size(); i++) {

            monedes.get(i).reset(Settings.GAME_WIDTH);

        }
    }

    public ArrayList<Monstre> getMonstres() {
        return monstres;
    }

    public ArrayList<Moneda> getMonedes(){
        return monedes;
    }

}
