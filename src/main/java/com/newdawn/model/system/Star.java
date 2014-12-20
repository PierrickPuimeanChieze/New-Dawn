/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Star extends CelestialBody {

	/**
	 * See <a href="en.wikipedia.org/wiki/Stellar_classification">Stellar
	 * classification</a>
	 */
	public static enum SpectralClass {

		O, B, A, F, G, K, M
	}

	private SpectralClass spectralClass;

	/**
	 * Create a star at the center of the system
	 * 
	 * @param spectralClass
	 *            the spectral class of the star
	 * @param diameter
	 *            the diameter of the star
	 */
	public Star(StellarSystem stellarSystem, SpectralClass spectralClass,
			long diameter) {
		this(stellarSystem, spectralClass, 0L, 0L, diameter);
	}

	/**
	 * Create a star at a given position
	 * 
	 * @param spectralClass
	 *            the spectral class of the star
	 * 
	 * @param diameter
	 *            the diameter of the star
	 */
	public Star(StellarSystem stellarSystem, SpectralClass spectralClass,
			long positionX, long positionY, long diameter) {
		super(stellarSystem, diameter);
		setPositionX(positionX);
		setPositionY(positionY);
		this.spectralClass = spectralClass;
	}
}
