package dac.entities;

import dac.Game;
import dac.util.SpriteAnimation;
import dac.util.collision.ColliderCircle;
import dac.util.configuration.Config;
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
    private SpriteAnimation spriteAnimation;


    public EffectExplosion(float damage, PVector position, float size) {
        super( position, Config.grenadeExplosionDuration );
        this.damage = damage;
        this.size = size;
        this.collider = new ColliderCircle( position, size * 0.5f );

        this.sprite = Game.getInstance().getSpriteManager().getSprite( "explosion_1to4.png", 0 );
        assert this.sprite != null : "Sprite for EffectExplosion not found!";
        
        this.spriteAnimation = Game.getInstance().getSpriteManager().getSpriteAnimation( "EffectExplosion" ).getNewInstance();
        assert this.spriteAnimation != null : "SpriteAnimation for EffectExplosion not found!";
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

        spriteAnimation.update();
    }


    @Override
    public void render( PGraphics pG ) {
        if( isExpired() )
            return;

        pG.imageMode( PGraphics.CENTER );
        pG.image( spriteAnimation.getCurrentSprite(), position.x, position.y, size*1.5f, size*1.5f );

        // Debugview
        pG.strokeWeight( 1f );
        pG.stroke( pG.color( 255 ) );
        pG.fill( pG.color( 255, 0, 0, 63 ) );
        pG.circle( position.x, position.y, size );
    }
}
