package dac.weapon;

import dac.Game;
import dac.entities.EffectExplosion;
import dac.util.configuration.NConfigWeaponGrenade;
import processing.core.PVector;


public class WeaponBomb extends Weapon {
    float fallOffSizePercent;
    
    public WeaponBomb( NConfigWeaponGrenade c ) {
        super( c );
        this.fallOffSizePercent = c.fallOffSizePercent;
    }


    @Override
    void fire()
    {
        System.out.println("Firing bomb with damage: " + damage);

        PVector base = Game.getInstance().getBasePosition();
        PVector target = Game.getInstance().getMousePosition();

        PVector targetLimitedRange = PVector.add( base, PVector.sub( target, base ).limit( range ) );

        Game.getInstance().addEffect( new EffectExplosion( damage, targetLimitedRange, size ) );

        cooldownRemaining = cooldownMax;
    }
}
