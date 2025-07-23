package dac.weapon;

import dac.Game;
import dac.entities.EffectParticalBeam;
import dac.util.configuration.NConfigWeaponParticalBeam;
import processing.core.PVector;

public class WeaponParticalBeam extends Weapon {

    private int fallOffRange;


    public WeaponParticalBeam( NConfigWeaponParticalBeam particalBeamConfig ) {
        super( particalBeamConfig );
        this.fallOffRange = particalBeamConfig.fallOffRange;
    }


    public void fire() {
        // Logic for firing the laser weapon
        System.out.println("Firing Partical Beam with damage: " + damage);

        PVector base = Game.getInstance().getBasePosition();
        PVector target = Game.getInstance().getMousePosition();

        PVector targetLimitedRange = PVector.add( base, PVector.sub( target, base ).limit( range ) );

        Game.getInstance().addEffect( new EffectParticalBeam( damage, targetLimitedRange, size, range, cooldownMax ) );

        cooldownRemaining = cooldownMax;
    }
}
