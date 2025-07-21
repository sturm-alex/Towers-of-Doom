package dac.util.collision;

import processing.core.PVector;


public class ColliderRay implements Collider {
    private PVector origin;
    private PVector direction;


    public PVector getOrigin() {
        return origin;
    }
    public PVector getDirection() {
        return direction;
    }


    public void setDirection(PVector direction) {
        this.direction.set( direction ).normalize();
    }
    public void setOrigin(PVector origin) {
        this.origin.set( origin );
    }


    public ColliderRay(PVector origin, PVector direction) {
        this.origin = origin.copy();
        this.direction = direction.copy().normalize();
    }


    @Override
    public boolean collidesWith( ColliderCircle circle ) {
        return CollisionCheck.collide( circle, this );
    }


    @Override
    public boolean collidesWith(ColliderRay ray) {
        return CollisionCheck.collide( this, ray);
    }

}
