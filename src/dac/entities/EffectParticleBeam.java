package dac.entities;

import static processing.core.PApplet.println;

import dac.Game;
import dac.util.SpriteAnimation;
import dac.util.Timer;
import dac.util.collision.ColliderRay;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;


public class EffectParticleBeam extends Effect {
    private float damage;
    private boolean aimed = false;
    private float size;
    private float range;
    private ColliderRay collider;

    private SpriteAnimation spriteAnimation;


    public EffectParticleBeam( float damage, PVector position, float size, float range, long lifetime ) {
        super( position, lifetime );
        this.damage = damage;
        this.size = size;
        this.range = range;
        this.collider = new ColliderRay( new PVector(), new PVector( 1, 0 ) );

        this.spriteAnimation = Game.getInstance().getSpriteManager().getSpriteAnimation( "EffectParticalBeam" ).getNewInstance();
        assert this.spriteAnimation != null : "SpriteAnimation for EffectParticalBeam not found!";
    }


    @Override
    public void update() {
        if( ! aimed )
        {
            collider.setOrigin( Game.getInstance().getBasePosition() );
            collider.setDirection( PVector.sub( Game.getInstance().getMousePosition(), collider.getOrigin() ) );
            aimed = true;
        }
        // System.out.println( collider.getDirection());
        // check for collisions with enemies
        for( Enemy enemy : Game.getInstance().getEnemies() )
        {
            if( enemy.collidesWith( collider ) )
            {
                // Using shortcut: instead of hit point on the circle, check the circle's
                // center distance to the ray origin
                float d = PVector.dist( collider.getOrigin(), enemy.getPosition() );
                if( d > range )
                    continue; // out of range
                float partialTime = (float) Timer.getInstance().getDeltaMillis() / lifetime;
                float continuousDamage = damage * partialTime;
                println("Particle Beam hit enemy: " + enemy + " with damage: " + continuousDamage);
                enemy.takeDamage( PApplet.round( continuousDamage ) );
                
            }
        }
        spriteAnimation.update();
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
        
        pG.pushMatrix();
        pG.imageMode( PGraphics.CENTER );
        pG.translate( base.x, base.y );
        pG.rotate( direction.heading() + PGraphics.HALF_PI );
        for( int i = 0; i < range / (size*2f); i++ )
            pG.image( spriteAnimation.getCurrentSprite(), 0, -i*size*2f-size*2f*0.5f, size*2f, size*2f );
        pG.popMatrix();
    }

}
