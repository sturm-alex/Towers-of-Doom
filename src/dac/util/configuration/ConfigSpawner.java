package dac.util.configuration;

import dac.entities.Enemy;


public record ConfigSpawner( Enemy prototype, int intervalMilliseconds ) {}
