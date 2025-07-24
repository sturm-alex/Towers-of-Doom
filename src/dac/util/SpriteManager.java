package dac.util;

import java.util.List;
import java.util.Map;

import dac.Game;
import processing.core.PApplet;
import processing.core.PImage;


public class SpriteManager {

    private final String pathToSprites;
    private List<String> spriteFiles;
    private PImage[][] sprites;
    private Map<String,SpriteAnimation> spriteAnimations;


    public SpriteManager( String pathToSprites ) {
        this.pathToSprites = pathToSprites;

        this.loadAllImages();
        this.loadAllAnimations();
    }


    public void loadAllImages() {
        this.spriteFiles = List.of(
            "explosion_1to4.png",
            "explosion_1to4_22.png",
            "explosion_1to4_big.png",
            "enemy_1.png",
            "enemy_2.png",
            "partical_beam.png",
            "player_ship.png"
        );

        this.sprites = new PImage[ spriteFiles.size() ][];

        PApplet pA = Game.getInstance();
        for( int i = 0; i < sprites.length; i++ )
        {
            PImage s = pA.loadImage( pathToSprites + spriteFiles.get( i ).toLowerCase() );
            int a = s.height;
            int count = (int) s.width / a; // Assuming each sprite is square
            if( count <= 1 )
                sprites[ i ] = new PImage[] { s };
            else
            {
                sprites[ i ] = new PImage[ count ];
                for( int j = 0; j < count; j++ )
                    sprites[ i ][ j ] = s.get( j * a, 0, a, a );
            }
        }  
    }


    private void loadAllAnimations() {
        this.spriteAnimations = Map.of(
            "EffectExplosion", new SpriteAnimation(
                new PImage[] {
                    getSprite( "explosion_1to4_big.png", 0 ),
                    getSprite( "explosion_1to4_big.png", 1 ),
                    getSprite( "explosion_1to4_big.png", 2 ),
                    getSprite( "explosion_1to4_big.png", 3 )
                },
                new float[] { 150, 250, 350, 500 }
            ),
            "EffectParticalBeam", new SpriteAnimation(
                new PImage[] {
                    getSprite( "partical_beam.png", 0 ),
                    getSprite( "partical_beam.png", 1 ),
                    getSprite( "partical_beam.png", 2 ),
                    getSprite( "partical_beam.png", 3 ),
                    getSprite( "partical_beam.png", 4 ),
                    getSprite( "partical_beam.png", 5 )
                },
                new float[] { 4, 8, 12, 16, 20, 24 }
            )
        );
    }


    public PImage getSprite( String name ) {
        return this.getSprite( name, 0 );
    }


    public PImage getSprite( String name, int index ) {
        int idx = this.spriteFiles.indexOf( name );
        assert idx >= 0 && idx < this.sprites.length : "Sprite-Atlas not found: " + name;
        assert index >= 0 && index < this.sprites[ idx ].length : "Sprite index out of bounds: " + index + " for atlas: " + name;
        return this.sprites[ idx ][ index ];
    }


    public SpriteAnimation getSpriteAnimation( String name ) {
        return this.spriteAnimations.get( name );
    }
}
