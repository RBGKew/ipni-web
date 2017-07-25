package org.ipni.view;

import org.ipni.model.Name;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class NameHelper {
	public CharSequence showName(Name taxon) {
		if(taxon.getName() != null) {
			return taxon.getName();
		} else {
			return taxon.getFamily();
		}
	}
}
