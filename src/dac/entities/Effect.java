package dac.entities;

import dac.util.Timer;

import processing.core.PGraphics;
import processing.core.PVector;


public abstract class Effect {
    PVector position;
    long lifetime;
    boolean expired = false;


    public Effect( PVector position, long lifetime ) {
        this.position = position;
        this.lifetime = lifetime;
    }


    final public void updateLifetime() {
        lifetime -= Timer.getInstance().getDeltaMillis();

        if( lifetime <= 0 )
            expired = true;
    }


    public abstract void update();
    public abstract void render( PGraphics pG );


    public boolean isExpired() {
        return expired;
    }
}
