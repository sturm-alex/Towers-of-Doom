package dac.weapon;

import dac.util.Timer;
import dac.util.configuration.Config;
import dac.util.configuration.NConfigWeapon;
import dac.util.configuration.NConfigWeaponGrenade;
import dac.util.configuration.NConfigWeaponLaser;
import dac.util.configuration.NConfigWeaponParticalBeam;

import java.util.HashMap;
import java.util.Map;


public abstract class Weapon {
    static Map<String,Weapon> weapons = new HashMap<>();

    static {
        NConfigWeaponGrenade grenadeConfig = new NConfigWeaponGrenade();
        grenadeConfig.damage = Config.granadeBaseDamage;
        grenadeConfig.cooldown = Config.granadeAktivationInterval;
        grenadeConfig.range = Config.grenadeRange;
        grenadeConfig.size = Config.granadeBaseDurchmesser;
        grenadeConfig.fallOffSizePercent = 0.75f;
        weapons.put("Bomb", new WeaponBomb( grenadeConfig ) );
        NConfigWeaponLaser laserConfig = new NConfigWeaponLaser();
        laserConfig.damage = Config.laserBaseDamage;
        laserConfig.cooldown = Config.laserCooldown;    
        laserConfig.range = Config.laserRange;
        laserConfig.size = Config.laserSize;
        laserConfig.fallOffRange = 200;
        weapons.put("Laser", new WeaponLaser( laserConfig ) );
        NConfigWeaponParticalBeam particalBeamConfig = new NConfigWeaponParticalBeam();
        particalBeamConfig.damage = Config.particalBeamBaseDamage;
        particalBeamConfig.cooldown = Config.particalBeamCooldown;    
        particalBeamConfig.range = Config.particalBeamRange;
        particalBeamConfig.size = Config.particalBeamSize;
        particalBeamConfig.fallOffRange = 200;
        weapons.put("Partical", new WeaponParticalBeam( particalBeamConfig ) );
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

    private NConfigWeapon config = null;


    public Weapon( NConfigWeapon c ) {
        this.config = c;
        this.reloadFromConfig();
    }


    private void reloadFromConfig() {
        this.damage = config.damage;
        this.cooldownRemaining = 0;
        this.cooldownMax = config.cooldown;
        this.range = config.range;
        this.size = config.size;
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
