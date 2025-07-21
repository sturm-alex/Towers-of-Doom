package dac.entities;
import java.util.List;

import dac.util.Timer;
import processing.core.PApplet;
import processing.core.PVector;


public class Spawner {
    private PApplet pA;
    private List<Enemy> enemies;
    private PVector baseCenter;
    private int spawnInterval;
    private int cooldown = 0;


    public Spawner( PApplet pA, List<Enemy> enemies, PVector baseCenter, int spawnInterval ) {
        this.pA = pA;
        this.enemies = enemies;
        this.baseCenter = baseCenter;
        this.spawnInterval = spawnInterval;
    }
    

    public void update() {
        cooldown += Timer.getInstance().getDeltaMillis();

        if ( cooldown >= spawnInterval ) {
            spawnEnemy();
            cooldown -= spawnInterval;
        }
    }


    private void spawnEnemy() {
        float dist = 0.5f * PApplet.sqrt( pA.width * pA.width + pA.height * pA.height );
        PVector t = new PVector( dist, 0 );
        t.rotate( pA.random( PApplet.TWO_PI ) ).add( pA.width * 0.5f, pA.height * 0.5f );
        enemies.add( new Enemy( t.x, t.y, baseCenter.x, baseCenter.y ) );
    }
}
