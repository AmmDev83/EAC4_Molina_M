package utils;

public class Settings {

    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 240;
    public static final int GAME_HEIGHT = 135;

    // TODO: Exercici 1
    // Propietats del personatge
    public static final float PERSONATGE_VELOCITY = 50;
    public static final int PERSONATGE_WIDTH = 15;
    public static final int PERSONATGE_HEIGHT = 32;
    public static final float PERSONATGE_STARTX = 20;
    public static final float PERSONATGE_STARTY = GAME_HEIGHT/2 - PERSONATGE_HEIGHT/2;

    // Rang de valors per canviar la mida de l'asteroide
    public static final float MAX_HEIGHT = GAME_HEIGHT/2;
    public static final float MIN_HEIGHT = 0.0f;

    public static final int MONSTRE_WIDTH = 16;
    public static final int MOSNTRE_HEIGHT = 23;
    public static final float VOLUM_MUSICA = 0.5f;


    // Configuració scrollable
    public static final int MONSTRE_VELOCITAT = -150;
    public static final int MONSTRE_GAP = 75;
    public static final int BG_SPEED = -100;
    // TODO: Exercici 3.2 // Constants necessaries Bonus
    // Bonus
    public static final int MONEDA_WIDTH = 16;
    public static final int MONEDA_HEIGHT = 16;

    public static final int VELOCITAT_BONUS = -175;
    public static final int VELOCITAT_BONUS_ESPECIAL = -250;
    // TODO: Exercici 4.1 // Constants necessaries
    public static final int BOTO_PAUSA_WIDTH = 16;
    public static final int BOTO_PAUSA_HEIGHT = 16;
    // TODO: Exercici 5.1 // Constants necessaries
    public static final String NOOB = "You’re a n00b!";
    public static final String DONE = "Well done!";
    public static final String YEAH = "Oh yeah!! You’re a pro!";
}
