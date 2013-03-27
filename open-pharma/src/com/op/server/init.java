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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Servlet permettant de vérifier un lot de numéros de téléphone
//compatible avec le dépot de message
@SuppressWarnings("serial")
public class init extends BaseServlet {
	String rc="";
	
	public void doGet(HttpServletRequest req, final HttpServletResponse resp) throws IOException {				
				
		//Drugs
		for(int k=1;k<950;k++)
			dao.captureDrugs("http://www.drugbank.ca/drugs/DB"+String.format("%05d", k));
		
		//Pharmacie
		for(int k=1;k<95;k++)
			dao.capturePharmacie("http://www.keskeces.com/pharmacie/liste-"+k+".html");		
			
		resp.getWriter().write("ok");
		resp.setContentType("text/plain");
	}
}
