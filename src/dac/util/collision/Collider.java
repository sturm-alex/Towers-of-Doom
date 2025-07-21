package dac.util.collision;


public interface Collider {
    boolean collidesWith(ColliderCircle colliderCircle);
    boolean collidesWith(ColliderRay colliderRay);
}