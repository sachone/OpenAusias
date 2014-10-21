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
package net.daw.control.operation.generic.implementation;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.control.operation.publicinterface.ControlOperationInterface;
import net.daw.service.generic.implementation.TableServiceGenImpl;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.helper.ExceptionBooster;
import net.daw.helper.FilterBeanHelper;
import net.daw.helper.parameterCooker;

public class ControlOperationGenImpl implements ControlOperationInterface {

    private ConnectionInterface DataConnectionSource = null;
    private Connection connection = null;
    private String strObject = null;
    private TableServiceGenImpl process = null;

    public ControlOperationGenImpl(HttpServletRequest request) throws Exception {
        try {
            DataConnectionSource = new BoneConnectionPoolImpl();
            connection = DataConnectionSource.newConnection();
            strObject = parameterCooker.prepareObject(request);
            Constructor oConstructor = Class.forName("net.daw.service.generic.specific.implementation." + parameterCooker.prepareCamelCaseObject(request) + "ServiceGenSpImpl").getConstructor(String.class, Connection.class);
            process = (TableServiceGenImpl) oConstructor.newInstance(strObject, connection);
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":ControlOperationGenImpl ERROR: " + ex.getMessage()));
        }
    }

    @Override
    public String get(HttpServletRequest request) throws Exception {
        String result = process.get(parameterCooker.prepareId(request));
        closeDB();
        return result;
    }

    @Override
    public String getaggregateviewone(HttpServletRequest request) throws Exception {
        String result = process.getAggregateViewOne(parameterCooker.prepareId(request));
        closeDB();
        return result;
    }

    @Override
    public String getprettycolumns(HttpServletRequest request) throws Exception {
        String result = process.getPrettyColumns();
        closeDB();
        return result;
    }

    @Override
    public String getcolumns(HttpServletRequest request) throws Exception {
        String result = process.getColumns();
        closeDB();
        return result;
    }

    @Override
    public String getpage(HttpServletRequest request) throws Exception {
        Integer intRegsPerPag = parameterCooker.prepareRpp(request);
        Integer intPage = parameterCooker.preparePage(request);
        ArrayList<FilterBeanHelper> alFilter = parameterCooker.prepareFilter(request);
        HashMap<String, String> hmOrder = parameterCooker.prepareOrder(request);
        String result = process.getPage(intRegsPerPag, intPage, alFilter, hmOrder);
        closeDB();
        return result;
    }

    @Override
    public String getpages(HttpServletRequest request) throws Exception {
        Integer intRegsPerPag = parameterCooker.prepareRpp(request);
        ArrayList<FilterBeanHelper> alFilter = parameterCooker.prepareFilter(request);
        String result = process.getPages(intRegsPerPag, alFilter);
        closeDB();
        return result;
    }

    @Override
    public String getregisters(HttpServletRequest request) throws Exception {
        ArrayList<FilterBeanHelper> alFilter = parameterCooker.prepareFilter(request);
        String result = process.getCount(alFilter);
        closeDB();
        return result;
    }

    @Override
    public String getaggregateviewsome(HttpServletRequest request) throws Exception {
        Integer intRegsPerPag = parameterCooker.prepareRpp(request);
        Integer intPage = parameterCooker.preparePage(request);
        ArrayList<FilterBeanHelper> alFilter = parameterCooker.prepareFilter(request);
        HashMap<String, String> hmOrder = parameterCooker.prepareOrder(request);
        String result = process.getAggregateViewSome(intRegsPerPag, intPage, alFilter, hmOrder);
        closeDB();
        return result;
    }

    @Override
    public String remove(HttpServletRequest request) throws Exception {
        String result = process.remove(parameterCooker.prepareId(request));
        closeDB();
        return result;
    }

    @Override
    public String set(HttpServletRequest request) throws Exception {
        String result = process.set(parameterCooker.prepareJson(request));
        closeDB();
        return result;
    }

    private void closeDB() throws SQLException, Exception {
        if (connection != null) {
            connection.close();
        }
        DataConnectionSource.disposeConnection();
    }
}
