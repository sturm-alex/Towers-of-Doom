package dac.util;

import java.util.List;

import dac.Game;
import processing.core.PApplet;
import processing.core.PImage;


public class SpriteManager {

    private final String pathToSprites;
    private List<String> spriteFiles;
    private PImage[] sprites;


    public SpriteManager( String pathToSprites ) {
        this.pathToSprites = pathToSprites;

        this.loadAllImages();
    }


    public void loadAllImages() {
        this.spriteFiles = List.of(
            "explosion_1to4.png",
            "enemy_1.png",
            "enemy_2.png",
            "player_ship.png"
        );

        this.sprites = new PImage[ spriteFiles.size() ];

        PApplet pA = Game.getInstance();
        for( int i = 0; i < sprites.length; i++ ) {
            sprites[ i ] = pA.loadImage( pathToSprites + spriteFiles.get( i ).toLowerCase() );
        }  
    }


    public PImage getSprite( String string ) {
        int idx = this.spriteFiles.indexOf( string );
        assert idx >= 0 && idx < this.sprites.length : "Sprite not found: " + string;
        return this.sprites[ idx ];
    }
}
