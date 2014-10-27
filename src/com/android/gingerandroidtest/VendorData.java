package com.android.gingerandroidtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class VendorData {

	private String name;
	private List<Date> pastEvents;

	public VendorData(String name){
		this.name = name;
		this.pastEvents = new ArrayList<Date>();
	}

	public String getName() {
		return name;
	}

	public List<Date> getPastEvents() {
		return pastEvents;
	}

	public void sortPastEvents(){
		Collections.sort(pastEvents);
	}

	public void setPastEvents(Date pastEvents) {
		this.pastEvents.add(pastEvents);
	}


}
