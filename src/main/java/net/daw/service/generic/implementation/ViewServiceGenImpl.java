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

import net.daw.service.publicinterface.ViewServiceInterface;
import net.daw.service.publicinterface.MetaServiceInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.daw.bean.generic.implementation.BeanGenImpl;
import net.daw.bean.publicinterface.BeanInterface;
import net.daw.dao.generic.implementation.TableDaoGenImpl;
import net.daw.helper.ExceptionBooster;
import net.daw.helper.FilterBeanHelper;

public abstract class ViewServiceGenImpl extends MetaServiceGenImpl implements ViewServiceInterface, MetaServiceInterface {

    public ViewServiceGenImpl(String ob, Connection con) {
        super(ob, con);
    }

    @Override
    public String get(Integer id) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            BeanGenImpl oGenericBean = (BeanGenImpl) Class.forName("net.daw.bean.generic.specific.implementation." + strObjectName + "BeanGenSpImpl").newInstance();
            Constructor c = Class.forName("net.daw.dao.generic.specific.implementation." + strObjectName + "DaoGenSpImpl").getConstructor(Connection.class);
            TableDaoGenImpl oGenericDao = (TableDaoGenImpl) c.newInstance(oConnection);
            oGenericBean.setId(id);
            oGenericBean = (BeanGenImpl) (BeanInterface) oGenericDao.get(oGenericBean);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy");
            Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
            data = gson.toJson(oGenericBean);
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        } finally {
            oConnection.commit();
        }
        return data;
    }

    @Override
    public String getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            BeanGenImpl oGenericBean = (BeanGenImpl) Class.forName("net.daw.bean.generic.specific.implementation." + strObjectName + "BeanGenSpImpl").newInstance();
            Constructor c = Class.forName("net.daw.dao.generic.specific.implementation." + strObjectName + "DaoGenSpImpl").getConstructor(Connection.class);
            TableDaoGenImpl oGenericDao = (TableDaoGenImpl) c.newInstance(oConnection);
            List<BeanInterface> loGenericBean = oGenericDao.getPage(intRegsPerPag, intPage, alFilter, hmOrder);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy");
            Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
            data = gson.toJson(loGenericBean);
            data = "{\"list\":" + data + "}";
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        } finally {
            oConnection.commit();
        }
        return data;
    }

    @Override
    public String getPages(int intRegsPerPag, ArrayList<FilterBeanHelper> alFilter) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            Constructor c = Class.forName("net.daw.dao.generic.specific.implementation." + strObjectName + "DaoGenSpImpl").getConstructor(Connection.class);
            TableDaoGenImpl oGenericDao = (TableDaoGenImpl) c.newInstance(oConnection);
            int pages = oGenericDao.getPages(intRegsPerPag, alFilter);
            data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        } finally {
            oConnection.commit();
        }
        return data;
    }

    @Override
    public String getCount(ArrayList<FilterBeanHelper> alFilter) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            Constructor c = Class.forName("net.daw.dao.generic.specific.implementation." + strObjectName + "DaoGenSpImpl").getConstructor(Connection.class);
            TableDaoGenImpl oGenericDao = (TableDaoGenImpl) c.newInstance(oConnection);
            int registers = oGenericDao.getCount(alFilter);
            data = "{\"data\":\"" + Integer.toString(registers) + "\"}";
            return data;
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        } finally {
            oConnection.commit();
        }
        return data;
    }

    @Override
    //no se utiliza por ahora
    public String getAggregateViewSome(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {
        String data = null;
        try {
            //falta controlar la transacción a esta altura
            String columns = this.getColumns();
            String prettyColumns = this.getPrettyColumns();
            //String types = this.getTypes();
            String page = this.getPage(intRegsPerPag, intPage, alFilter, hmOrder);
            String pages = this.getPages(intRegsPerPag, alFilter);
            String registers = this.getCount(alFilter);
            data = "{\"data\":{"
                    + "\"columns\":" + columns
                    + ",\"prettyColumns\":" + prettyColumns
                    // + ",\"types\":" + types
                    + ",\"page\":" + page
                    + ",\"pages\":" + pages
                    + ",\"registers\":" + registers
                    + "}}";
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getAggregateViewOne(Integer id) throws Exception {
        String data = null;
        try {
            //falta controlar la transacción a esta altura
            String columns = this.getColumns();
            String prettyColumns = this.getPrettyColumns();
            //String types = this.getTypes();
            String one = this.get(id);
            data = "{\"data\":{"
                    + "\"columns\":" + columns
                    + ",\"prettyColumns\":" + prettyColumns
                    // + ",\"types\":" + types
                    + ",\"data\":" + one
                    + "}}";
            return data;
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        }
        return data;
    }
}
