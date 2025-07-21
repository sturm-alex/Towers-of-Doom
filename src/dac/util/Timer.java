package dac.util;

import java.util.concurrent.TimeUnit;

import processing.core.PApplet;

import static processing.core.PApplet.nf;



/**
 * Timer class to measure time intervals in Processing sketches.
 * It uses nanoseconds for high precision and provides methods to get
 * the delta time in seconds and milliseconds.
 */
public class Timer
{
  private static Timer instance = null;

  private long lastTime;
  private long deltaNanos;


  private Timer( PApplet applet )
  {
    lastTime = System.nanoTime();
    deltaNanos = 0;

    // Registrierung in der pre()-Phase von Processing
    applet.registerMethod( "pre", this );
  }


  public static void createSingleton( PApplet pA )
  {
    if( instance == null )
      instance = new Timer( pA );
  }
  
  
  public static Timer getInstance()
  {
    return instance;
  }


  public void pre()
  {
    update();
  }


  private void update()
  {
    long currentTime = System.nanoTime();
    deltaNanos = currentTime - lastTime;
    lastTime = currentTime;
  }


  public float getDeltaSeconds()
  {
    return deltaNanos * 0.00_000_000_1f;
  }


  public long getDeltaMillis()
  {
    return TimeUnit.NANOSECONDS.toMillis( deltaNanos );
  }
  
  
  @Override
  public String toString()
  {
    return "Timer: " + nf( (float)getDeltaSeconds(), 0, 3 ) + "s";
  }
}
