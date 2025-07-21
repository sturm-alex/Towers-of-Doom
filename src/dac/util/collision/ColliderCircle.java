package dac.util.collision;

import processing.core.PVector;


public class ColliderCircle implements Collider
{
    private PVector center;
    private float radius;


    public PVector getCenter() {
        return center;
    }
    public float getRadius() {
        return radius;
    }


    public ColliderCircle( PVector center, float radius ) {
        this.center = center;
        this.radius = radius;
    }


    @Override
    public boolean collidesWith( ColliderCircle circle ) {
        return CollisionCheck.collide( this, circle );
    }


    @Override
    public boolean collidesWith(ColliderRay ray) {
        return CollisionCheck.collide( this, ray );
    }

}
