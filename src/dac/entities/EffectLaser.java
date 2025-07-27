package dac.entities;

import dac.Game;
import dac.util.collision.ColliderRay;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;


public class EffectLaser extends Effect {
    private float damage;
    private PVector aimedAt;
    private boolean damageDealt = false;
    private float size;
    private float range;
    private float fallOffPercent;
    private ColliderRay collider;
    private PVector closestCollision;


    public EffectLaser( float damage, PVector position, PVector aimedAt, float size, float range, float fallOffPercent, long lifetime ) {
        super( position, lifetime );
        this.damage = damage;
        this.aimedAt = aimedAt;
        this.size = size;
        this.range = range;
        this.fallOffPercent = fallOffPercent;
        this.collider = new ColliderRay( new PVector(), new PVector( 1, 0 ) );
    }


    @Override
    public void update() {
        if( ! damageDealt )
        {
            collider.setOrigin( this.position );
            collider.setDirection( PVector.sub( aimedAt, collider.getOrigin() ) );
            // System.out.println( collider.getDirection());
            damageDealt = true;
            // check for collisions with enemies
            float closestDistance = range + 1f;
            closestCollision = null;
            Enemy closestEnemy = null;
            for( Enemy enemy : Game.getInstance().getEnemies() )
            {
                if( enemy.collidesWith( collider ) )
                {
                    // Using shortcut: instead of hit point on the circle, check the circle's
                    // center distance to the ray origin
                    float d = PVector.dist( collider.getOrigin(), enemy.getPosition() );
                    if( d < closestDistance )
                    {
                        closestDistance = d;
                        closestCollision = enemy.getPosition();
                        closestEnemy = enemy;
                    }
                }
            }
            if( closestCollision != null && closestDistance <= range )
            {
                    float dmg = PApplet.constrain( PApplet.map( closestDistance, fallOffPercent * range, range, damage, 0 ), 0, damage );
                    closestEnemy.takeDamage( PApplet.round( dmg ) );
                    // TODO: Create a hit effect
            }
        }
    }


    @Override
    public void render( PGraphics pG ) {
        if( isExpired() )
            return;

        // System.out.println( collider.getDirection() );
        PVector direction = collider.getDirection().copy().mult( range );
        PVector hitPoint = closestCollision;
        if( hitPoint == null )
            hitPoint = PVector.add( position, direction );
        pG.pushStyle();
        pG.strokeWeight( size );
        pG.stroke( pG.color( 0, 191, 191, 63 ) );
        pG.strokeCap( PGraphics.ROUND );
        pG.line( position.x, position.y, hitPoint.x, hitPoint.y );
        pG.popStyle();

        // Debug drawing
        pG.strokeCap( PGraphics.SQUARE );
        pG.strokeWeight( 1f );
        pG.stroke( pG.color( 0, 191, 191 ) );
        pG.fill( pG.color( 191, 255, 255, 63 ) );
        pG.circle( aimedAt.x, aimedAt.y, size );

        pG.stroke( pG.color( 255 ) );
        pG.strokeWeight( 2f );
        pG.line( position.x, position.y, position.x + fallOffPercent * direction.x, position.y + fallOffPercent * direction.y );
        pG.stroke( pG.color( 191, 191, 255 ) );
        pG.strokeWeight( 1f );
        pG.line( position.x + fallOffPercent * direction.x, position.y + fallOffPercent * direction.y, position.x + direction.x, position.y + direction.y );

        pG.stroke( pG.color( 255, 0, 0 ) );
        pG.noFill();
        pG.circle( hitPoint.x, hitPoint.y, size );
    }

}
