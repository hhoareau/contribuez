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


import java.io.IOException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;


/**
 * Cette classe regroupe l'ensemble des méthodes utilisées
 * par les servlet du serveur pour manipuler la base de données de google
 * @see http://code.google.com/p/objectify-appengine/wiki/BestPractices#Utilisez_un_DAO
 *  
 * @author Hervé Hoareau
 */
public class DAO extends DAOBase {	
	private static final Logger log = Logger.getLogger(DAO.class.getName());
	
	static 	{
			ObjectifyService.register(Drug.class);
			}

	
	public void capture(String url) {
		
		log.warning(url+" loading ...");
		
		Document doc =null;
		try {doc=Jsoup.connect(url).get();} 
		catch (IOException e) {log.severe(e.getMessage());}
		
		if(doc!=null){
			String title=doc.title();
			if(title!=null && title.contains("DrugBank:") && title.contains("(DB")){
				//log.warning("analyse de "+title);
				String nom=title.split(":")[1].split("\\(DB")[0];
				Drug d=new Drug(nom);
				int ind=doc.getElementById("drug-actions").getElementsByClass("standard").size();
				log.warning("index="+ind);
				//d.CASnumber=doc.getElementsByIndexEquals(ind).val();
				ofy().put(d);
			}
		}
	}

	

	
	
}
