package dac.entities;
import dac.util.collision.ColliderCircle;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;


public class Base {
  private PVector position;
  private float radius;
  private int hp;
  private boolean dead = false;
  private ColliderCircle collider;


  public PVector getPosition() {
    return position.copy();
  }
  public ColliderCircle getCollider() {
    return collider;
  }


  public Base( float x, float y, float diameter, int hp )
  {
    this.position = new PVector( x, y );
    this.radius = diameter * 0.5f;
    this.hp = hp;
    this.collider = new ColliderCircle( this.position, this.radius );
  }


  public void update()
  {
    if( hp <= 0 )
      dead = true;
  }
  
  
  
  public void render( PGraphics p )
  {
    p.strokeWeight( 10f );
    p.stroke( p.color( 191, 255, 191 ) );
    p.fill( 63, 255, 191 );
    p.circle( position.x, position.y, radius * 2 );

    p.fill( p.color( 31, 63, 31 ) );
    p.textSize( 30 );
    p.textAlign( PApplet.CENTER, PApplet.CENTER );
    p.text( "Basis\n" + hp, position.x, position.y );
  }

  

  public void takeDamage( int dmg )
  {
    hp -= dmg;
  }

  

  boolean isDestroyed()
  {
    return dead;
  }
}
