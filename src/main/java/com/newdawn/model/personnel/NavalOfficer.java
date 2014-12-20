package com.newdawn.model.personnel;

import com.newdawn.model.personnel.ranks.NavalRank;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class NavalOfficer extends Official {

	public void setRank(NavalRank navalRank) {
		rankProperty.setValue(navalRank);
	}
}
