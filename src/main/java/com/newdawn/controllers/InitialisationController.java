/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import viewerfx.AsteroidBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
@Component
public class InitialisationController {

	@Autowired
	private GameData gameData;
	@Autowired
	private Config config;
	@Autowired
	private SystemHandler systemHandler;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public GameData getGameData() {
		return gameData;
	}

	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}

	public StellarSystem createSystemFromFile(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {
		FileInputStream inputStream = new FileInputStream(xmlFile);
		return createSystem(inputStream);
	}

	public StellarSystem createSystem(InputStream inputStream)
			throws SAXException, IOException, ParserConfigurationException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(inputStream, systemHandler);
		return systemHandler.getCreatedSystem();
	}

	public Star addStarToSystem(StellarSystem system, String name,
			Star.SpectralClass spectralClass, long diameter) {
		Star star = new Star(system, spectralClass, diameter);
		star.setName(name);
		system.getStars().add(star);
		return star;
	}

	public Planet addNewPlanetToStar(Star orbitCenter, String name,
			Planet.PlanetaryClass planetaryClass, long orbitalRadius,
			long diameter) {
		return this.addNewPlanetToStar(orbitCenter, name, planetaryClass,
				orbitalRadius, diameter, null);
	}

	public Planet addNewPlanetToStar(Star orbitCenter, String name,
			Planet.PlanetaryClass planetaryClass, long orbitalRadius,
			long diameter, Long orbitalPeriod) {
		return this.addNewPlanetToStar(orbitCenter, name, planetaryClass,
				orbitalRadius, diameter, orbitalPeriod, 0.0);
	}

	public Planet addNewPlanetToStar(Star orbitCenter, String name,
			Planet.PlanetaryClass planetaryClass, long orbitalRadius,
			long diameter, Long orbitalPeriod, double delta) {
		Orbit orbit = new Orbit(orbitCenter, orbitalRadius);
		Planet newPlanet = new Planet(name, planetaryClass, orbit, diameter);
		newPlanet.setDelta(delta);
		newPlanet.setOrbitalPeriod(orbitalPeriod);
		orbitCenter.getStellarSystem().getPlanets().add(newPlanet);
		return newPlanet;
	}

	public Satellite addNewSatelliteToPlanet(Planet orbitCenter, String name,
			long orbitalRadius, long diameter) {
		return this.addNewSatelliteToPlanet(orbitCenter, name, orbitalRadius,
				diameter, null);
	}

	public Satellite addNewSatelliteToPlanet(Planet orbitCenter, String name,
			long orbitalRadius, long diameter, Long orbitalPeriod) {

		Orbit orbit = new Orbit(orbitCenter, orbitalRadius);
		Satellite satellite = new Satellite(name, orbit, diameter);
		satellite.setOrbitalPeriod(orbitalPeriod);
		orbitCenter.getSatellites().add(satellite);
		return satellite;
	}

	public Asteroid addNewAsteroidToStar(CelestialBody orbitCenter,
			long orbitalRadius, long diameter, Long orbitalPeriod, double delta) {
		Orbit orbit = new Orbit(orbitCenter, orbitalRadius);
		Asteroid satellite = new Asteroid(orbit, diameter);
		satellite.setOrbitalPeriod(orbitalPeriod);
		orbitCenter.getStellarSystem().getAsteroids().add(satellite);
		return satellite;
	}

	public void addAsteroidBeltToStar(Star currentStar, long number,
			long orbitalRadius, long width, boolean useGaussianRepartition) {

		AsteroidBuilder asteroidBuilder = new AsteroidBuilder();
		asteroidBuilder.setMinimumDiameter(10);
		asteroidBuilder.setMaximumDiameter(15);
		asteroidBuilder.setCentralOrbitalValue(orbitalRadius);
		asteroidBuilder.setWidth(width);
		asteroidBuilder.setOrbitCenter(currentStar);
		for (int i = 0; i < number; i++) {
			currentStar.getStellarSystem().getAsteroids()
					.add(asteroidBuilder.getAsteroid());
		}

	}
}
