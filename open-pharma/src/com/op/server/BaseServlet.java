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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

//Cette classe permet de partager des méthodes entre plusieurs classes filles
public class BaseServlet extends HttpServlet  {
	private static final long serialVersionUID = 1464654657498786L;
	protected static final String Domain_Appli="http://open-pharma.appspot.com";
	
	protected static final Logger  log = Logger.getLogger(BaseServlet.class.getName());	
	protected static DAO dao=new DAO();;
		
}

