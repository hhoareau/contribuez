/*
 * Copyright (C) 2012 SFR API - Hervï¿½ Hoareau

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


import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;


/**
 * Cette classe regroupe l'ensemble des mï¿½thodes utilisï¿½es
 * par les servlet du serveur pour manipuler la base de donnï¿½es de google
 * @see http://code.google.com/p/objectify-appengine/wiki/BestPractices#Utilisez_un_DAO
 *  
 * @author Herve Hoareau
 */
public class DAO extends DAOBase {	
	private static final Logger log = Logger.getLogger(DAO.class.getName());
	private static final String NS = "http://www.owl-ontologies.com/OntologyBase.owl#";
	
	//voir http://jena.apache.org/documentation/ontology/	
	OntModel model = ModelFactory.createOntologyModel();
	OntModel inf =null;
	
	static 	{
			ObjectifyService.register(Drug.class);
			ObjectifyService.register(Pharmacie.class);
			}

	
	public DAO(InputStream is){
		model.read(is,"RDF/XML");		
		inf= ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);
	}
	
	public Document HTMLParser(String url){
		Document doc =null;
		try {doc=Jsoup.connect(url).get();} 
		catch (IOException e) {log.severe(e.getMessage());}
		return doc;
	}
	
	
	//Capture d'une page de type 
	public void captureDrugs(String url) {		
		Document doc=HTMLParser(url);
		if(doc!=null){
			String title=doc.title();
			if(title!=null && title.contains("DrugBank:") && title.contains("(DB")){
				String nom=title.split(":")[1].split("\\(DB")[0];
				Drug d=new Drug(nom);
				Element table=doc.select("table[class=standard]").first();
				Iterator<Element> ite=table.select("td").iterator();
				String rc="";
				while(ite.hasNext()){
					String col=ite.next().text().toLowerCase();
					if(col.equals("name"))d.name=ite.next().text();
					if(col.equals("indication"))d.indication=ite.next().text();
					if(col.equals("cas number"))d.CASnumber=ite.next().text();
				}
				ofy().put(d);
			}
		}
	}
	
	//Capture de la page http://en.wikipedia.org/wiki/List_of_medical_symptoms
	public void captureSymptom(){
		Document doc=HTMLParser("http://en.wikipedia.org/wiki/List_of_medical_symptoms");
		if(doc!=null){
			Iterator<Element> symptome=doc.select("href").iterator();
			while(symptome.hasNext()){
				Symptom s=new Symptom();
				s.name=symptome.next().text();
				ofy().put(s);
			}
		}
	}
	
	
	public void capturePharmacie(String url) {
		Document doc=HTMLParser(url);

		Element table=doc.select("table[border=0]").first();
		Iterator<Element> ite=table.select("td[aligne=left]").iterator();
		
		String rc="";
		while(ite.hasNext()){
			Pharmacie p=new Pharmacie();
			p.name=ite.next().text();
			p.CodePostal=Integer.parseInt(ite.next().text());
			ofy().put(p);
		}
	}
	
	
	//création des instances en fonction des tables
	//voir http://jena.sourceforge.net/tutorial/RDF_API/#ch-Introduction
	
	public void makeInstance(String ClasseOntologique){
		OntClass ontclass=model.getOntClass(ClasseOntologique);
		Property p=model.createProperty(NS+"MedicamentNom");
		
		QueryResultIterator<Drug> ite=ofy().query(Drug.class).iterator();
		while(ite.hasNext()){
			Individual i=model.createIndividual(ite.next().name,ontclass);
			i.addLiteral(p, ite.next().name);
		}
		
	}
	
	
	
	
}
