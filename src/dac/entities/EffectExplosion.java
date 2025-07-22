package dac.entities;

import dac.Game;
import dac.util.collision.ColliderCircle;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;


public class EffectExplosion extends Effect {
    private float damage;
    private boolean damageDealt = false;
    private float size;
    private ColliderCircle collider;

    private PImage sprite;


    public EffectExplosion(float damage, PVector position, float size) {
        super( position, 150 );
        this.damage = damage;
        this.size = size;
        this.collider = new ColliderCircle( position, size * 0.5f );

        // this.sprite = ( (PApplet) Game.getInstance() ).loadImage( "assets/graphics/sprites/explosion_1to4.png" );
        this.sprite = Game.getInstance().getSpriteManager().getSprite( "explosion_1to4.png" );
        assert this.sprite != null : "Sprite for EffectExplosion not found!";
    }


    @Override
    public void update() {
        if( ! damageDealt )
        {
            damageDealt = true;
            // check for collisions with enemies
            for( Enemy enemy : Game.getInstance().getEnemies() )
            {
                if( enemy.collidesWith( collider ) )
                    enemy.takeDamage( PApplet.round( damage ) );
            }
        }
    }


    @Override
    public void render( PGraphics pG ) {
        if( isExpired() )
            return;

        pG.imageMode( PGraphics.CENTER );
        pG.image( sprite, position.x, position.y, size, size );

        // Debugview
        pG.strokeWeight( 1f );
        pG.stroke( pG.color( 255 ) );
        pG.fill( pG.color( 255, 0, 0, 63 ) );
        pG.circle( position.x, position.y, size );
    }

}
