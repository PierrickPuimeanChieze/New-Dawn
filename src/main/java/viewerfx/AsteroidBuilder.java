package viewerfx;

import com.newdawn.model.system.Asteroid;
import com.newdawn.model.system.CelestialBody;
import com.newdawn.model.system.Orbit;

import java.util.Random;

public class AsteroidBuilder {

	private CelestialBody orbitCenter;

	private Random orbitRandom = new Random();
	private long centralOrbitalValue;
	private long width;

	private double minimumDiameter;
	private double maximumDiameter;
	private Random diameterRandom = new Random();
	
	public void setOrbitCenter(CelestialBody orbitCenter) {
		this.orbitCenter = orbitCenter;
	}
	public void setCentralOrbitalValue(long centralOrbitalValue) {
		this.centralOrbitalValue = centralOrbitalValue;
	}


	public void setWidth(long width) {
		this.width = width;
	}


	public void setMinimumDiameter(long minimumDiameter) {
		this.minimumDiameter = minimumDiameter;
	}


	public void setMaximumDiameter(long maximumDiameter) {
		this.maximumDiameter = maximumDiameter;
	}



	public Asteroid getAsteroid() {
		double orbitalValue = centralOrbitalValue - width / 2;
		orbitalValue += orbitRandom.nextGaussian() * width;
		Orbit orbit = new Orbit(orbitCenter, Math.round(orbitalValue));
		
		double diameterWidth = (maximumDiameter-minimumDiameter)*2;
		double diameter = minimumDiameter + diameterWidth*diameterRandom.nextGaussian();
		Asteroid asteroid = new Asteroid(orbit, diameter);
		return asteroid;
	}

}
