/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.mineral.*;
import com.newdawn.model.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
@Component
public class SystemHandler extends DefaultHandler2 {

	public StellarSystem createdSystem;
	private Planet currentPlanet;
	private MinerallyExploitableBody currentMinerallyExploitableBody;
	private MinerallyExploitableBodyModel currentMinerallyExploitableBodyModel;
	private MineralModel currentMineralModel;
	private MineralDeposit currentMineralDeposit;
	private List<MineralDeposit> depositsForCurrentMineralModel = new ArrayList<>();
	private Satellite currentSatellite;
	private Star currentStar;
	private Asteroid currentAsteroid;
	@Autowired
	private InitialisationController initialisationController;
	@Autowired
	private Config config;
	@Autowired(required=true)
	private Map<String, Mineral> minerals;
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("document Started");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		switch (qName) {
		case "system":
			initSystem(attributes);
			break;
		case "star":
			initCurrentStar(attributes);
			break;
		case "planet":
			initCurrentPlanet(attributes);
			break;
		case "satellite":
			initCurrentSatellite(attributes);
			break;
		case "mineralBodyModel":
			initCurrentBodyModel(attributes);
			break;
		case "mineralModel" :
			initCurrentMineralModel(attributes);
			break;
		case "deposit" :
			initCurrentDeposit(attributes);
			break;
		case "asteroid":
			initCurrentAsteroid(attributes);
			break;
		case "asteroidBelt":
			initCurrentAsteroidBelt(attributes);
			break;
		default:
			throw new AssertionError("Unknow Element : " + qName);
		}
		
	}

	private void initCurrentAsteroidBelt(Attributes attributes) {
		long orbitalRadius = Long.parseLong(attributes
				.getValue("orbitalRadius"));
		long width = Long.parseLong(attributes.getValue("width"));
		long number = Long.parseLong(attributes.getValue("number"));
		boolean useGaussianRepartition = Boolean.parseBoolean(attributes.getValue("gaussianRepartition")); 
//		String name = attributes.getValue("name");
//		Long orbitalPeriod = null;
//		String orbitalPeriodStr = attributes.getValue("orbitalPeriod");
//		if (orbitalPeriodStr != null) {
//			orbitalPeriod = Long.parseLong(orbitalPeriodStr);
//		}
		// TODO handling the illegal argument exception from parseDelta
		double delta = 0.0;
//		delta = parseDelta(attributes.getValue("delta"));
		initialisationController.addAsteroidBeltToStar(
				currentStar, number, orbitalRadius, width, useGaussianRepartition);
		
	}

	private void initCurrentAsteroid(Attributes attributes) {
		long orbitalRadius = Long.parseLong(attributes
				.getValue("orbitalRadius"));
		long diameter = Long.parseLong(attributes.getValue("diameter"));
//		String name = attributes.getValue("name");
		Long orbitalPeriod = null;
		String orbitalPeriodStr = attributes.getValue("orbitalPeriod");
		if (orbitalPeriodStr != null) {
			orbitalPeriod = Long.parseLong(orbitalPeriodStr);
		}
		// TODO handling the illegal argument exception from parseDelta
		double delta = 0.0;
//		delta = parseDelta(attributes.getValue("delta"));
		currentAsteroid = initialisationController.addNewAsteroidToStar(
				currentStar, orbitalRadius, diameter,
				orbitalPeriod, delta);
		initCurrentMinerralyExploitableBody(currentAsteroid);
		
	}

	private void initCurrentDeposit(Attributes attributes) {
		int skillLevelToDiscover = Integer.parseInt(attributes.getValue("skillLevelToDiscover"));
		int neededPointToDiscover= Integer.parseInt(attributes.getValue("neededPointToDiscover"));
		int quantity= Integer.parseInt(attributes.getValue("quantity"));
		currentMineralDeposit = new MineralDeposit(skillLevelToDiscover, neededPointToDiscover, quantity);
		
	}

	private void initCurrentMineralModel(Attributes attributes) {
		currentMineralModel = new MineralModel(minerals.get(attributes.getValue("mineral")), 12);
		depositsForCurrentMineralModel.clear();
	}

	private void initCurrentBodyModel(Attributes attributes) {
		int initialNeededPoints = Integer.parseInt(attributes
				.getValue("initialNeededPoints"));
		int finalisationSkill = Integer.parseInt(attributes
				.getValue("finalisationSkill"));
		int pointsNeededForFinalization = Integer.parseInt(attributes
				.getValue("pointsNeededForFinalization"));
		currentMinerallyExploitableBodyModel = new MinerallyExploitableBodyModel(
				initialNeededPoints, finalisationSkill,
				pointsNeededForFinalization);

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		switch (qName) {
		case "system":

			break;
		case "star":
			currentStar = null;
			break;
		case "planet":
			currentPlanet = null;
			currentMinerallyExploitableBody = null;
			break;
		case "satellite":
			currentSatellite = null;
			currentMinerallyExploitableBody = null;
			break;
		case "mineralBodyModel":
			currentMinerallyExploitableBody
					.setMinerallyExploitableBodyModel(currentMinerallyExploitableBodyModel);
			currentMinerallyExploitableBodyModel = null;
			break;
		case "mineralModel":
			currentMineralModel.setMineralDeposits(depositsForCurrentMineralModel);
			currentMinerallyExploitableBodyModel.getMineralModels().put(currentMineralModel.getMineral(), currentMineralModel);
			currentMineralModel = null;
			break;
		case "deposit":
			depositsForCurrentMineralModel.add(currentMineralDeposit);
			currentMineralDeposit = null;
			break;
		case "asteroid":
			currentAsteroid = null;
			currentMinerallyExploitableBody = null;
			break;
		case "asteroidBelt":
			break;
		default:
			throw new AssertionError("Unknow Element : " + qName);
		}
	}

	public StellarSystem getCreatedSystem() {
		return createdSystem;
	}

	private void initSystem(Attributes attributes) {
		createdSystem = new StellarSystem();
		createdSystem.setName(attributes.getValue("name"));
	}

	private void initCurrentStar(Attributes attributes) {
		Star.SpectralClass spectralClass = Star.SpectralClass
				.valueOf(attributes.getValue("spectralClass").toUpperCase());
		long diameter = Long.parseLong(attributes.getValue("diameter"));
		String name = attributes.getValue("name");
		currentStar = initialisationController.addStarToSystem(createdSystem,
				name, spectralClass, diameter);
	}

	private void initCurrentPlanet(Attributes attributes) {
		Planet.PlanetaryClass planetaryClass = Planet.PlanetaryClass
				.valueOf(attributes.getValue("planetaryClass").toUpperCase());
		long orbitalRadius = Long.parseLong(attributes
				.getValue("orbitalRadius"));
		long diameter = Long.parseLong(attributes.getValue("diameter"));
		String name = attributes.getValue("name");
		Long orbitalPeriod = null;
		String orbitalPeriodStr = attributes.getValue("orbitalPeriod");
		if (orbitalPeriodStr != null) {
			orbitalPeriod = Long.parseLong(orbitalPeriodStr);
		}
		// TODO handling the illegal argument exception from parseDelta
		double delta = 0.0;
		delta = parseDelta(attributes.getValue("delta"));
		currentPlanet = initialisationController.addNewPlanetToStar(
				currentStar, name, planetaryClass, orbitalRadius, diameter,
				orbitalPeriod, delta);
		initCurrentMinerralyExploitableBody(currentPlanet);
	}

	private void initCurrentMinerralyExploitableBody(
			MinerallyExploitableBody minerallyExploitableBody) {
		this.currentMinerallyExploitableBody = minerallyExploitableBody;
	}

	private void initCurrentSatellite(Attributes attributes) {
		long orbitalRadius = Long.parseLong(attributes
				.getValue("orbitalRadius"));
		long diameter = Long.parseLong(attributes.getValue("diameter"));
		String name = attributes.getValue("name");
		Long orbitalPeriod = null;
		String orbitalPeriodStr = attributes.getValue("orbitalPeriod");
		if (orbitalPeriodStr != null) {
			orbitalPeriod = Long.parseLong(orbitalPeriodStr);
		}
		currentSatellite = initialisationController.addNewSatelliteToPlanet(
				currentPlanet, name, orbitalRadius, diameter, orbitalPeriod);
		initCurrentMinerralyExploitableBody(currentSatellite);
	}

	private double parseDelta(String deltaStr) {
		// If the delta String is null
		if (deltaStr == null) {
			// We fall back on the default value
			deltaStr = config.getInitDefaultDeltaValue();
		}

		Matcher piPatternMatcher = Pattern.compile("(\\d+(\\.\\d+)?)?π")
				.matcher(deltaStr);

		// Check if a random value is asked and return it
		if ("random".equalsIgnoreCase(deltaStr)) {
			return Math.random() * 2 * Math.PI;
		}
		// Check and parse if the delta asked is in the form "Xπ" and return the
		// correct value
		if (piPatternMatcher.matches()) {
			double piCoef = 1;
			String piCoefStr = piPatternMatcher.group(1);
			if (piCoefStr != null && !piCoefStr.isEmpty()) {
				piCoef = Double.parseDouble(piCoefStr);
			}
			return piCoef * Math.PI;
		}
		// Check and parse if the delta asked is in numerical form and return
		// the parsed value
		Matcher doublePatternMatcher = Pattern.compile("\\d+(\\.\\d+)?")
				.matcher(deltaStr);
		if (doublePatternMatcher.matches()) {
			return Double.parseDouble(deltaStr);
		}

		throw new IllegalArgumentException(
				"Incorrect String format for a Delta");
	}
}
