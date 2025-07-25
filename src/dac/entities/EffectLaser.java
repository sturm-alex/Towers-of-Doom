package dac.entities;

import dac.Game;
import dac.util.collision.ColliderRay;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;


public class EffectLaser extends Effect {
    private float damage;
    private boolean damageDealt = false;
    private float size;
    private float range;
    private ColliderRay collider;


    public EffectLaser( float damage, PVector position, float size, float range, long lifetime ) {
        super( position, lifetime );
        this.damage = damage;
        this.size = size;
        this.range = range;
        this.collider = new ColliderRay( new PVector(), new PVector( 1, 0 ) );
    }


    @Override
    public void update() {
        if( ! damageDealt )
        {
            collider.setOrigin( Game.getInstance().getBasePosition() );
            collider.setDirection( PVector.sub( Game.getInstance().getMousePosition(), collider.getOrigin() ) );
            // System.out.println( collider.getDirection());
            damageDealt = true;
            // check for collisions with enemies
            for( Enemy enemy : Game.getInstance().getEnemies() )
            {
                if( enemy.collidesWith( collider ) )
                {
                    // Using shortcut: instead of hit point on the circle, check the circle's
                    // center distance to the ray origin
                    float d = PVector.dist( collider.getOrigin(), enemy.getPosition() );
                    float dmg = PApplet.constrain( PApplet.map( d, range, range * 2, damage, 0 ), 0, damage );
                    enemy.takeDamage( PApplet.round( dmg ) );
                }
            }
        }
    }


    @Override
    public void render( PGraphics pG ) {
        if( isExpired() )
            return;

        PVector base = Game.getInstance().getBasePosition();
        // System.out.println( collider.getDirection() );
        PVector direction = collider.getDirection().copy().mult( range );
        pG.pushStyle();
        pG.strokeWeight( size );
        pG.stroke( pG.color( 0, 191, 191, 63 ) );
        pG.strokeCap( PGraphics.ROUND );
        pG.line( base.x, base.y, base.x + direction.x, base.y + direction.y );
        pG.strokeWeight( size * 0.5f );
        pG.line( base.x + direction.x, base.y + direction.y, base.x + 2f * direction.x, base.y + 2f * direction.y );
        pG.popStyle();

        pG.strokeWeight( 3f );
        pG.stroke( pG.color( 0, 191, 191 ) );
        pG.fill( pG.color( 191, 255, 255 ) );
        pG.circle( position.x, position.y, size );
    }

}
