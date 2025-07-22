package dac.entities;

import dac.util.Timer;
import dac.util.collision.Collider;
import dac.util.collision.ColliderCircle;
import dac.util.configuration.Config;
import processing.core.PGraphics;
import processing.core.PVector;



public class Enemy {
    private PVector position;
    private PVector target;
    private final float speed = Config.enemy1BaseSpeed;
    private final float radius = Config.enemy1BaseRadius;
    private int health = Config.enemy1BaseHP;
    private boolean alive = true;
    private ColliderCircle collider;


    public Enemy(float x, float y, float targetX, float targetY ) {
        this.position = new PVector(x, y);
        this.target = new PVector(targetX, targetY);
        this.collider = new ColliderCircle( this.position, this.radius );
    }


    public void takeDamage( int dmg )
    {
        health -= dmg;

        // death check is done in update() once per frame
    }

  
    public boolean isAlive()
    {
        return this.alive;
    }


    public boolean collidesWith( Collider collider )
    {
        return collider.collidesWith( this.collider );
    }


    public void update() {
        if( ! alive )
            return;

        this.position.add( PVector.sub(target, position).limit( speed * Timer.getInstance().getDeltaSeconds() ) );
    
        // player might have had 0 hp and got back to more than 0 and survive
        if( health <= 0 ) {
            alive = false;
        }
    }


    public void render( PGraphics pG ) {
        // pG.strokeWeight( 3f );
        // pG.stroke( pG.color( 255, 63, 63 ) );
        pG.noStroke();
        pG.fill( pG.color( 255, 191, 191 ) );
        pG.ellipse( position.x, position.y, 2f * radius, 2f * radius );
    }


    public PVector getPosition() {
        return position;
    }
}
