package dac.weapon;

import dac.util.Timer;
import dac.util.configuration.Config;
import dac.util.configuration.ConfigWeapon;

import java.util.HashMap;
import java.util.Map;


public abstract class Weapon {
    static Map<String,Weapon> weapons = new HashMap<>();

    static {
        weapons.put("Bomb", new WeaponBomb( new ConfigWeapon( Config.granadeBaseDamage, Config.granadeAktivationInterval, Config.grenadeRange, Config.granadeBaseDurchmesser ) ) );
        weapons.put("Laser", new WeaponLaser( new ConfigWeapon( Config.laserBaseDamage, Config.laserCooldown, Config.laserRange, Config.laserSize ) ) );
        // Add more weapons as needed
    }


    public static Weapon get( String name ) {
        if( weapons.containsKey(name) ) {
            return weapons.get(name);
        } else {
            throw new IllegalArgumentException( "Weapon not found: " + name );
        }
    }


    protected float damage = 0;
    protected long cooldownRemaining = Long.MAX_VALUE;
    protected long cooldownMax = Long.MAX_VALUE;
    protected float range = 0;
    protected float size = 0;


    Weapon( float damage, long cooldown, float range, float size ) {
        this.damage = damage;
        this.cooldownRemaining = 0l;
        this.cooldownMax = cooldown;
        this.range = range;
        this.size = size;
    }


    public Weapon( ConfigWeapon c ) {
        this( c.damage(), c.cooldownMax(), c.range(), c.size() );
    }


    abstract void fire();


    public void pullTrigger() {
        if( cooldownRemaining > 0 )
            return;

        fire();
    }


    public float getDamage() {
        return damage;
    }


    public void update() {
        if( cooldownRemaining > 0 )
            cooldownRemaining -= Timer.getInstance().getDeltaMillis();
    }
}
