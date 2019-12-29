package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {
    // TODO: Exercici 1.2 // Variables necessaries
    // Sprite Sheet
    public static Texture sheet;

    // Personatge
    public static TextureRegion[] personatge;
    public static Animation<TextureRegion> personatgeAnim;

    public static TextureRegion[] personatgePujant;
    public static Animation<TextureRegion> personPujantAnim;

    public static TextureRegion[] personatgeBaixant;
    public static Animation<TextureRegion> personBaixantAnim;

    public static TextureRegion background;

    // Monstre
    public static TextureRegion[] monstre;
    public static Animation<TextureRegion> monstreAnim;

    // Explosió
    public static TextureRegion[] explosio;
    public static Animation<TextureRegion> explosioAnim;

    // Sons
    public static Sound explosioSo;
    public static Music music;

    // Font
    public static BitmapFont font;
    // TODO: Exercici 3.1 // Variables necessaries
    // Bonus
    public static TextureRegion bonus, bonusEspecial;

    // TODO: Exercici 3.2 // Variable necessaria
    public static Sound bonusMusic;
    // TODO: Exercici 4.1 // Variable necessaria
    public static TextureRegion botoPausa;

    // TODO: Exercici 1.2 // Metode per carregar les imatges noves.
    public static void load() {
        // TODO: Exercici 1.2 // Carreguem el nou sprite sheet
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("sprite_molina_m.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        // TODO: Exercici 1.2 // Carregem les imatges del personatge en estat normal
        personatge = new TextureRegion[4];
        for (int i = 0; i < personatge.length; i++) {
            // Com cada estat te una amplada diferent, ho pasem per un comprovador i li donem la seva amplada
            if(i == 0){
                personatge[i] = new TextureRegion(sheet, 0, 40, 20, 40);
            }else if (i == 1){
                personatge[i] = new TextureRegion(sheet, 19, 40, 20, 40);
            } else if (i == 2){
                personatge[i] = new TextureRegion(sheet, 63, 40, 20, 40);
            } else if (i == 3){
                personatge[i] = new TextureRegion(sheet, 85, 40, 20, 40);
            }

        }
        // TODO: Exercici 1.2 // Creem l'animacio del personatge en estat normal
        personatgeAnim = new Animation<>(0.2f, personatge);
        personatgeAnim.setPlayMode(Animation.PlayMode.LOOP);
        // TODO: Exercici 1.2 // Carreguem les imatges del personatge en estat pujant
        personatgePujant = new TextureRegion[4];
        for (int i = 0; i < personatgePujant.length; i++){
            // Com cada estat te una amplada diferent, ho pasem per un comprovador i li donem la seva amplada
            if(i == 0){
                personatgePujant[i] = new TextureRegion(sheet, 0, 80, 20, 40);
            }else if (i == 1){
                personatgePujant[i] = new TextureRegion(sheet, 21, 80, 20, 40);
            } else if (i == 2){
                personatgePujant[i] = new TextureRegion(sheet, 66, 80, 20, 40);
            } else if (i == 3){
                personatgePujant[i] = new TextureRegion(sheet, 89, 80, 20, 40);
            }
        }
        // TODO: Exercici 1.2 // Creem l'animacio del personatge en estat pujant
        personPujantAnim = new Animation<>(0.2f, personatgePujant);
        personPujantAnim.setPlayMode(Animation.PlayMode.LOOP);
        // TODO: Exercici 1.2 // Carreguem les imatges del personatge en estat baixant
        personatgeBaixant = new TextureRegion[5];
        for (int i = 0; i < personatgeBaixant.length; i++){
            // Com cada estat te una amplada diferent, ho pasem per un comprovador i li donem la seva amplada
            if(i == 0){
                personatgeBaixant[i] = new TextureRegion(sheet, 0, 0, 18, 38);
            }else if (i == 1){
                personatgeBaixant[i] = new TextureRegion(sheet, 21, 0, 18, 38);
            } else if (i == 2){
                personatgeBaixant[i] = new TextureRegion(sheet, 41, 0, 18, 38);
            } else if (i == 3){
                personatgeBaixant[i] = new TextureRegion(sheet, 62, 0, 18, 38);
            } else if (i == 4){
                personatgeBaixant[i] = new TextureRegion(sheet, 82, 0, 18, 38);
            }
        }
        // TODO: Exercici 1.2 // Creem l'animacio del personatge en estat baixant
        personBaixantAnim = new Animation<>(0.2f, personatgeBaixant);
        personBaixantAnim.setPlayMode(Animation.PlayMode.LOOP);
        // TODO: Exercici 1.2 // Carreguem les imatges del monstre
        monstre = new TextureRegion[3];
        for (int i = 0; i < monstre.length; i++) {
            if(i == 0){
                monstre[i] = new TextureRegion(sheet, 0, 125, 36, 45);
            } else if (i == 1){
                monstre[i] = new TextureRegion(sheet, 38, 125, 36, 45);
            } else if (i == 2){
                monstre[i] = new TextureRegion(sheet, 82, 125, 36, 45);
            }
        }
        // TODO: Exercici 1.2 // Creem l'animacio del monstre
        monstreAnim = new Animation<>(0.2f, monstre);
        monstreAnim.setPlayMode(Animation.PlayMode.LOOP);

        // TODO: Exercici 1.2 // Carreguem les imatges de l'explosio
        explosio = new TextureRegion[4];
        for (int i = 0; i < explosio.length; i++){
            if(i == 0){
                explosio[i] = new TextureRegion(sheet, 189, 166, 32, 26);
            } else if (i == 1){
                explosio[i] = new TextureRegion(sheet, 187, 4, 34, 38);
            } else if (i == 2){
                explosio[i] = new TextureRegion(sheet, 178, 46, 50, 46);
            } else if (i == 3){
                explosio[i] = new TextureRegion(sheet, 180, 102, 50, 50);
            }

        }
        // TODO: Exercici 1.2 // Creem l'animacio de l'explosio
        explosioAnim = new Animation<>(0.4f, explosio);
        explosioAnim.setPlayMode(Animation.PlayMode.LOOP);

        // TODO: Exercici 1.2 // Fons de pantalla
        background = new TextureRegion(sheet, 1, 212, 199, 135);


        /******************************* Sounds *************************************/
        // TODO: Exercici 1.2 // Só explosió
        explosioSo = Gdx.audio.newSound(Gdx.files.internal("sounds/explosio.ogg"));

        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music-amm.ogg"));
        music.setVolume(0.5f);
        music.setLooping(true);

        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/joc_ioc.fnt");
        font = new BitmapFont(fontFile, false);
        font.getData().setScale(0.4f);
        // TODO: Exercici 3.1 // Inicialitzem les imatges dels bonus
        /****************************** Bonus **************************************/
        // Imatges del bonus
        bonus = new TextureRegion(sheet, 33, 182, 16, 16);
        bonusEspecial = new TextureRegion(sheet, 7, 182, 16, 16);
        // TODO: Exercici 3.2 // Carreguem la musica del bonus
        bonusMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/cash.mp3"));
        music.setVolume(0.5f);
        music.setLooping(false);
        // TODO: Exercici 4.1 // Carreguem la imatge del boto
        botoPausa = new TextureRegion(sheet, 75, 175, 32, 32);

    }

    // TODO: Exercici 1.2 // Metode per refrescar els sprites i el só
    public static void dispose() {
        sheet.dispose();
        explosioSo.dispose();
        music.dispose();
        bonusMusic.dispose();
    }
}
