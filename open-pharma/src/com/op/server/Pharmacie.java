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
import java.util.logging.Logger;
import javax.persistence.Id;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * Cette classe permet de stocker le résultat du parsing HTML
 * @see 
 * @author Hervé Hoareau
 */
@Unindexed
public class Pharmacie {
	protected static final Logger log = Logger.getLogger(Pharmacie.class.getName());
	
	@Id public Long Id; 					//Id interne des messages	
	@Indexed public String name=null;
	@Indexed public Integer CodePostal=null;
	public String url=null;
	public String number=null;
	
	public Pharmacie(String name){this.name=name;}
	public Pharmacie(){}

}
