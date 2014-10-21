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
package net.daw.service.generic.specific.implementation;

import net.daw.service.generic.implementation.TableServiceGenImpl;
import java.sql.Connection;
import javax.servlet.ServletException;
import net.daw.bean.generic.specific.implementation.DocumentoBeanGenSpImpl;
import net.daw.dao.generic.specific.implementation.DocumentoDaoGenSpImpl;

public class DocumentoServiceGenSpImpl extends TableServiceGenImpl {

    public DocumentoServiceGenSpImpl(String strObject, Connection con) {
        super(strObject, con);
    }

    public String getContenido(Integer id) throws Exception {
        String data;
        try {
            DocumentoBeanGenSpImpl oDocumentoBean = new DocumentoBeanGenSpImpl();
            oDocumentoBean.setId(id);
            DocumentoDaoGenSpImpl oDocumentoDao = new DocumentoDaoGenSpImpl(oConnection);
            oDocumentoBean = oDocumentoDao.get(oDocumentoBean);
            return "{\"data\":\"" + oDocumentoBean.getContenido() + "\"}";
        } catch (Exception e) {
            throw new ServletException("GetContenido: View Error: " + e.getMessage());
        }
    }
}
