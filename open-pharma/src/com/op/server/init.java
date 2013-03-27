/*
 * Copyright (C) 2012 SFR API - Hervé Hoareau

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.op.server;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

//Servlet permettant de vérifier un lot de numéros de téléphone
//compatible avec le dépot de message
@SuppressWarnings("serial")
public class init extends BaseServlet {
	String rc="";
	
	public void doGet(HttpServletRequest req, final HttpServletResponse resp) throws IOException {				

		//voir http://jena.apache.org/documentation/ontology/	
		OntModel model = ModelFactory.createOntologyModel();
		
		ServletContext context = getServletContext();
		model.read(context.getResourceAsStream("/WEB-INF/openpharma.xml"),"RDF/XML");		
		
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model );
		OntClass medicament=model.getOntClass("Medicament");
		Individual i=model.createIndividual("Med1",medicament);
		
		for(int k=945;k<950;k++)
			dao.capture("http://www.drugbank.ca/drugs/DB"+String.format("%05d", k));
			
		resp.getWriter().write("ok");
		resp.setContentType("text/plain");
	}
}
