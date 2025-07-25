package dac.weapon;

import dac.Game;
import dac.entities.EffectParticleBeam;
import dac.util.configuration.NConfigWeaponParticleBeam;
import processing.core.PVector;

public class WeaponParticleBeam extends Weapon {

    private int fallOffRange;


    public WeaponParticleBeam( NConfigWeaponParticleBeam particalBeamConfig ) {
        super( particalBeamConfig );
        this.fallOffRange = particalBeamConfig.fallOffRange;
    }


    public void fire() {
        // Logic for firing the laser weapon
        System.out.println("Firing Partical Beam with damage: " + damage);

        PVector base = Game.getInstance().getBasePosition();
        PVector target = Game.getInstance().getMousePosition();

        PVector targetLimitedRange = PVector.add( base, PVector.sub( target, base ).limit( range ) );

        Game.getInstance().addEffect( new EffectParticleBeam( damage, targetLimitedRange, size, range, (long) ( 0.9 * cooldownMax ) ) );

        cooldownRemaining = cooldownMax;
    }
}
