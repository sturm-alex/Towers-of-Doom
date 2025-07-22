package dac.util;

import processing.core.PImage;

public class SpriteAnimation {
    private final PImage[] frames;
    private final float[] frameEndTimes;
    private float currentTime;
    private int currentFrame;
    private EndBehavior endBehavior;


    public SpriteAnimation( PImage[] frames, float[] frameEndTimes ) {
        assert frames.length > 0 : "[SpriteAnimation] At least one frame is required";
        assert frames.length == frameEndTimes.length : "[SpriteAnimation] Frames and frame durations must have the same length";

        this.frames = frames;
        this.frameEndTimes = frameEndTimes;
        this.endBehavior = EndBehavior.SHOW_NOTHING;
        this.restart();
    }


    public void update() {
        currentTime += Timer.getInstance().getDeltaMillis();
        for( int i = currentFrame; i < frameEndTimes.length; i++ ) {
            if( currentTime >= frameEndTimes[i] ) {
                ++currentFrame;
            }
        }

        if( currentFrame >= frames.length ) {
            switch( endBehavior ) {
                case LOOP:
                    currentTime %= frameEndTimes[ frames.length - 1 ];
                    currentFrame = findCurrentFrame( currentTime);
                    break;
                case STOP_AT_LAST_FRAME, SHOW_NOTHING:
                    currentFrame = frames.length - 1;
                    break;
                case REVERSE:
                    throw new UnsupportedOperationException("Reverse animation not implemented yet");
            }
        }

        System.out.println("Current Frame: " + currentFrame + ", Time: " + currentTime);
    }


    private int findCurrentFrame(float currentTime ) {
        int i = 0;
        for( ; i < frameEndTimes.length; i++ ) {
            if( currentTime < frameEndTimes[i] )
                break;
        }
        return i;
    }

    public PImage getCurrentSprite() {
        return frames[currentFrame];
    }


    public enum EndBehavior {
        LOOP,
        STOP_AT_LAST_FRAME,
        SHOW_NOTHING,
        REVERSE
    }


    public void restart() {
        this.currentFrame = 0;
        this.currentTime = 0f;
    }


    public SpriteAnimation getNewInstance() {
        SpriteAnimation newInstance = new SpriteAnimation( frames, frameEndTimes );
        newInstance.endBehavior = this.endBehavior;
        return newInstance;
    }
}
