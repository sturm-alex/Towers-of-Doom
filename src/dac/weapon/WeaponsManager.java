package dac.weapon;

import java.util.HashMap;
import java.util.Map;

import dac.util.configuration.Config;
import dac.util.configuration.ConfigsManager;
import dac.util.configuration.NConfigWeaponLaser;

public class WeaponsManager {
    static Map<String,Weapon> weapons = new HashMap<>();


    public Weapon createWeapon( String name ) {
        System.out.println( "Creating weapon: " + name );
        Config config = ConfigsManager.getInstance().loadConfig( name );
        
        return new WeaponLaser( new NConfigWeaponLaser() );
    }
}
