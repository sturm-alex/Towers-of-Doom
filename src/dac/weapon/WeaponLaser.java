package dac.weapon;

import dac.Game;
import dac.entities.EffectLaser;
import dac.util.configuration.ConfigWeapon;

import processing.core.PVector;

public class WeaponLaser extends Weapon {

    
    public WeaponLaser( ConfigWeapon c ) {
        super( c );
    }


    public void fire() {
        // Logic for firing the laser weapon
        System.out.println("Firing laser with damage: " + damage);

        PVector base = Game.getInstance().getBasePosition();
        PVector target = Game.getInstance().getMousePosition();

        PVector targetLimitedRange = PVector.add( base, PVector.sub( target, base ).limit( range ) );

        Game.getInstance().addEffect( new EffectLaser( damage, targetLimitedRange, size, range, cooldownMax ) );

        cooldownRemaining = cooldownMax;
    }
}
