package dac.util.configuration;

import java.util.Map;

public abstract class Config {
    public final static int screenSizeWidth = 1600;
    public final static int screenSizeHeight = 900;

    public final static int playerBaseHP = 100;
    public final static int playerBasePosX = screenSizeWidth  / 2;
    public final static int playerBasePosY = screenSizeHeight / 2;
    public final static int baseDiameter = 150;
    public final static int textsize = 15;

    public final static int granadeBaseDurchmesser = 250;
    public final static int granadeBaseRadius = granadeBaseDurchmesser/2;
    public final static int granadeBaseDamage = 50;
    public final static int granadeBaseDamageMult = 1;
    public final static int grenadeExplosionDuration = 500;
    public final static int granadeAktivationInterval= 1000;//milliseconds
    public final static int grenadeRange = 500;

    public final static int laserBaseDamage = 15;
    public final static int laserCooldown = 25;// milliseconds
    public final static int laserRange = 250;
    public final static int laserSize = 25;

    public final static int enemySpawnPerSecond = 10;
    public final static int enemySpawnInterval = 1000 / enemySpawnPerSecond;//milliseconds
    public final static int enemyBaseDamageMult = 1;
    public final static int enemy1BaseDamage = 10;
    public final static int enemy1BaseDurchmesser = 10;
    public final static int enemy1BaseRadius = enemy1BaseDurchmesser/2;
    public final static int enemy1BaseHP = 100;
    public final static int enemy1BaseSpeed = 50;

    public final static int particalBeamBaseDamage = 50000;
    public final static int particalBeamBaseDuration = 250;
    public final static int particalBeamCooldown= 2500;//milliseconds
    public final static int particalBeamRange = 400;
    public final static int particalBeamSize= 50;

    
    public static Map<String, Class<? extends Config>> getConfigClasses() {
        return Map.of(
            "NConfigWeapon", NConfigWeapon.class,
            "NConfigWeaponLaser", NConfigWeaponLaser.class,
            "NConfigWeaponParticalBeam", NConfigWeaponParticalBeam.class,
            "NConfigWeaponGrenade", NConfigWeaponGrenade.class
        );
    }
}
