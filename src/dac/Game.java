package dac;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dac.entities.Base;
import dac.entities.Effect;
import dac.entities.Enemy;
import dac.entities.Spawner;
import dac.util.Timer;
import dac.util.configuration.Config;
import dac.util.configuration.ConfigGame;
import dac.weapon.Weapon;
import processing.core.PApplet;
import processing.core.PVector;


public class Game extends PApplet {
    static Game game = null;

    private ConfigGame config;
    private Base base;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Enemy> enemiesRemovalList = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();
    private List<Effect> effectsRemovalList = new ArrayList<>();
    private Spawner spawner;
    private Weapon activeWeapon;
    private List<Weapon> weaponsInventory = new ArrayList<>();
    
    
    public Game() {
        super();
        this.config = new ConfigGame( Config.screenSizeWidth, Config.screenSizeHeight );
        game = this;
    }


    public void settings() {
        size( config.screenWidth(), config.screenHeight() );
    }


    public void setup() {
        surface.setResizable( false );
        surface.setAlwaysOnTop( true );
        surface.setLocation( 100, 100 );
        surface.setTitle( "Save Haven from Destruction by Evil Forces!" );
  
        // Setze die Default-Locale auf US (z. B. für nf() → Dezimalpunkt)
        Locale.setDefault(Locale.US);
        
        Timer.createSingleton( this );

        base = new Base( width * 0.5f, height * 0.5f, 100, 100 );

        enemies = new ArrayList<>();

        spawner = new Spawner( this, enemies, base.getPosition(), 500 );

        activeWeapon = Weapon.get( "Bomb" );
        System.out.println("Weapon selected: Bomb" );
        weaponsInventory.add( Weapon.get( "Laser" ) );
    }


    public void draw() {
        if( frameCount % 60 == 0 )
            println( Timer.getInstance() );

            
        
        /* ******************
         * *** USER INPUT ***
         * ****************** */

            
        /* **************
         * *** UPDATE ***
         * ************** */
        for( Effect effect : effects )
        {
            effect.updateLifetime();
            if( effect.isExpired() )
                effectsRemovalList.add( effect );
        }
        effects.removeAll( effectsRemovalList );
        effectsRemovalList.clear();
        
        if( activeWeapon != null )
        {
            activeWeapon.update();
            activeWeapon.pullTrigger();
        }

        base.update();

        for( Enemy enemy : enemies )
        {
            enemy.update();
            if( ! enemy.isAlive() )
                enemiesRemovalList.add( enemy );
        }
        enemies.removeAll( enemiesRemovalList );
        enemiesRemovalList.clear();

        for( Effect effect : effects )
            effect.update();

        for( Enemy enemy : enemies )
        {
            if( enemy.collidesWith( base.getCollider() ) )
            {
                enemy.takeDamage( 100 );
                base.takeDamage( 1 );
            }
        }

        spawner.update();
        
        /* **************
         * *** RENDER ***
         * ************** */
        background( color( 31, 63, 63 ) );

        base.render( this.g );

        for( Enemy enemy : enemies )
            enemy.render( this.g );

        for( Effect effect : effects )
            effect.render( this.g );
    }


    public static Game getInstance() {
        if( game == null ) {
            throw new IllegalStateException("Game instance not initialized. Call setup() first.");
        }   
        return game;
    }


    public PVector getBasePosition() {
        return base.getPosition();
    }


    public List<Enemy> getEnemies() {
        if( enemies != null )
            return enemies;

        throw new IllegalStateException("Enemy List is null!" );
    }


    public PVector getMousePosition() {
        return new PVector( mouseX, mouseY );
    }


    int getScreenWidthPixels() {
        return width;
    }


    int getScreenHeightPixels() {
        return height;
    }


    public void addEffect( Effect effect ) {
        effects.add( effect );
    }


    // public void mousePressed() {
    //     if( activeWeapon != null )
    //         activeWeapon.pullTrigger();
    // }


    public void keyReleased() {
        if( keyCode == UP ) {
            weaponsInventory.addLast( activeWeapon );
            activeWeapon = weaponsInventory.getFirst();
        } else if( keyCode == DOWN ) {
            weaponsInventory.addFirst( activeWeapon );
            activeWeapon = weaponsInventory.getLast();
        }
    }
}