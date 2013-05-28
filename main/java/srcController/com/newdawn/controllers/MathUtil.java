/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.controllers;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class MathUtil {

	public static double calcutatePercent(double value, double total) {

		double percent = 100 * value / total;
		return percent;
	}

	public static double calculatePercentValue(double percent, double total) {
		double value = percent * total / 100;
		return value;
	}
}
