package dac.entities;

import dac.Game;
import dac.util.collision.ColliderCircle;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;


public class EffectExplosion extends Effect {
    private float damage;
    private boolean damageDealt = false;
    private float size;
    private ColliderCircle collider;


    public EffectExplosion(float damage, PVector position, float size) {
        super( position, 150 );
        this.damage = damage;
        this.size = size;
        this.collider = new ColliderCircle( position, size * 0.5f );
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

        pG.strokeWeight( 15f );
        pG.stroke( pG.color( 191, 127, 0 ) );
        pG.fill( pG.color( 255, 255, 255 ) );
        pG.circle( position.x, position.y, size );
    }

}
