package dac.weapon;

import dac.Game;
import dac.entities.EffectLaser;
import dac.util.configuration.NConfigWeaponLaser;
import processing.core.PVector;

public class WeaponLaser extends Weapon {

    private float fallOffPercent;


    public WeaponLaser( NConfigWeaponLaser laserConfig ) {
        super( laserConfig );
        this.fallOffPercent = laserConfig.fallOffPercent;
    }


    public void fire() {
        // Logic for firing the laser weapon
        System.out.println("Firing laser with damage: " + damage);

        PVector base = Game.getInstance().getBasePosition();
        PVector target = Game.getInstance().getMousePosition();

        PVector targetLimitedRange = PVector.add( base, PVector.sub( target, base ).limit( range ) );

        Game.getInstance().addEffect( new EffectLaser( damage, base, targetLimitedRange, size, range, fallOffPercent, cooldownMax ) );

        cooldownRemaining = cooldownMax;
    }
}
