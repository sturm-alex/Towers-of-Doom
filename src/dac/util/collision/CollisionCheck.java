package dac.util.collision;

import processing.core.PVector;

public class CollisionCheck {


    static public boolean collide( ColliderCircle circle1, ColliderCircle circle2 ) {
        float distance = PVector.dist( circle1.getCenter(), circle2.getCenter() );
        return distance <= ( circle1.getRadius() + circle2.getRadius() );
    }


    static public boolean collide( ColliderCircle circle, ColliderRay ray ) {
        // Assume r.getDirection() is normalized
        PVector toCenter = PVector.sub(circle.getCenter(), ray.getOrigin());
        float t = toCenter.dot(ray.getDirection()); // projection length onto ray

        // Only consider intersection in front of the ray origin
        if (t < 0) return false;

        // Closest point on ray to circle center
        PVector closest = PVector.add(ray.getOrigin(), PVector.mult(ray.getDirection(), t));
        float distToCenter = PVector.dist(closest, circle.getCenter());

        return distToCenter <= circle.getRadius();
    }


    static public boolean collide( ColliderRay r1, ColliderRay r2 ) {
        // Implement collision detection logic between two rays
        // This is a placeholder implementation
        return false; // Placeholder return value
    }
}
