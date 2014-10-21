/*
 * Copyright (C) July 2014 Rafael Aznar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.daw.control.operation.publicinterface;

import javax.servlet.http.HttpServletRequest;

public interface ControlOperationInterface {

    public String get(HttpServletRequest request) throws Exception;

    public String getaggregateviewone(HttpServletRequest request) throws Exception;

    public String getprettycolumns(HttpServletRequest request) throws Exception;

    public String getcolumns(HttpServletRequest request) throws Exception;

    public String getpage(HttpServletRequest request) throws Exception;

    public String getpages(HttpServletRequest request) throws Exception;

    public String getregisters(HttpServletRequest request) throws Exception;

    public String getaggregateviewsome(HttpServletRequest request) throws Exception;

    public String remove(HttpServletRequest request) throws Exception;

    public String set(HttpServletRequest request) throws Exception;

}
