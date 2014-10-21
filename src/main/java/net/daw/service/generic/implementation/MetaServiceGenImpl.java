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
package net.daw.service.generic.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.ArrayList;
import net.daw.dao.generic.implementation.TableDaoGenImpl;
import net.daw.helper.ExceptionBooster;
import net.daw.service.publicinterface.MetaServiceInterface;

public class MetaServiceGenImpl implements MetaServiceInterface {

    protected Connection oConnection = null;
    protected String strObjectName = null;

    public MetaServiceGenImpl(String ob, Connection con) {
        strObjectName = Character.toUpperCase(ob.charAt(0)) + ob.substring(1);
        oConnection = con;
    }

    @Override
    public String getPrettyColumns() throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            Constructor c = Class.forName("net.daw.dao.generic.specific.implementation." + strObjectName + "DaoGenSpImpl").getConstructor(Connection.class);
            TableDaoGenImpl oDao = (TableDaoGenImpl) c.newInstance(oConnection);
            ArrayList<String> alColumns = null;
            alColumns = oDao.getPrettyColumnsNames();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            data = gson.toJson(alColumns);
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPrettyColumns ERROR: " + ex.getMessage()));
        } finally {
            oConnection.commit();
        }
        return data;
    }

    @Override
    public String getColumns() throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            Constructor c = Class.forName("net.daw.dao.generic.specific.implementation." + strObjectName + "DaoGenSpImpl").getConstructor(Connection.class);
            TableDaoGenImpl oDao = (TableDaoGenImpl) c.newInstance(oConnection);
            ArrayList<String> alColumns = null;
            alColumns = oDao.getColumnsNames();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            data = gson.toJson(alColumns);
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getColumns ERROR: " + ex.getMessage()));
        } finally {
            oConnection.commit();
        }
        return data;
    }
}
