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
package net.daw.service.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.daw.bean.generic.specific.implementation.ProductoBeanGenSpImpl;
import net.daw.dao.specific.implementation.ProductoDaoSpcImpl;
import net.daw.helper.EncodingUtilHelper;
import net.daw.helper.ExceptionBooster;
import net.daw.helper.FilterBeanHelper;
import net.daw.service.publicinterface.MetaServiceInterface;
import net.daw.service.publicinterface.TableServiceInterface;
import net.daw.service.publicinterface.ViewServiceInterface;

public class ProductoServiceSpImpl implements TableServiceInterface, ViewServiceInterface, MetaServiceInterface {

    protected Connection oConnection = null;
    protected String strObjectName = null;

    public ProductoServiceSpImpl(String strObject, Connection con) {
        strObjectName = strObject;
        oConnection = con;
    }

    @Override
    public String remove(Integer id) throws Exception {
        String resultado = null;
        try {
            oConnection.setAutoCommit(false);
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            ProductoBeanGenSpImpl oProducto = new ProductoBeanGenSpImpl(id);
            Map<String, String> data = new HashMap<>();
            oProductoDAO.remove(oProducto);
            data.put("status", "200");
            data.put("message", "se ha eliminado el registro con id=" + oProducto.getId());
            Gson gson = new Gson();
            resultado = gson.toJson(data);
            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        }
        return resultado;
    }

    @Override
    public String set(String jason) throws Exception {
        String resultado = null;
        try {
            oConnection.setAutoCommit(false);
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            ProductoBeanGenSpImpl oProducto = new ProductoBeanGenSpImpl();
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            jason = EncodingUtilHelper.decodeURIComponent(jason);
            oProducto = gson.fromJson(jason, oProducto.getClass());
            oProducto = oProductoDAO.set(oProducto);
            Map<String, String> data = new HashMap<>();
            data.put("status", "200");
            data.put("message", Integer.toString(oProducto.getId()));
            resultado = gson.toJson(data);
            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
        }
        return resultado;
    }

    @Override
    public String get(Integer id) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            ProductoBeanGenSpImpl oProducto = new ProductoBeanGenSpImpl(id);
            oProducto = oProductoDAO.get(oProducto);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy");
            Gson gson = gsonBuilder.create();
            data = gson.toJson(oProducto);
            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":get ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            List<ProductoBeanGenSpImpl> oProductos = oProductoDAO.getPage(intRegsPerPag, intPage, alFilter, hmOrder);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy");
            Gson gson = gsonBuilder.create();
            data = gson.toJson(oProductos);
            data = "{\"list\":" + data + "}";
            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPage ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getPages(int intRegsPerPag, ArrayList<FilterBeanHelper> alFilter) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            int pages = oProductoDAO.getPages(intRegsPerPag, alFilter);
            data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPages ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getCount(ArrayList<FilterBeanHelper> alFilter) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            int registers = oProductoDAO.getCount(alFilter);
            data = "{\"data\":\"" + Integer.toString(registers) + "\"}";
            oConnection.commit();

        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCount ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getPrettyColumns() throws Exception {
        String data = null;
        ArrayList<String> alColumns = null;
        try {
            oConnection.setAutoCommit(false);
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            alColumns = oProductoDAO.getPrettyColumnsNames();
            data = new Gson().toJson(alColumns);
            //data = "{\"data\":" + data + "}";
            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPrettyColumns ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getColumns() throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
            ArrayList<String> alColumns = null;
            ProductoDaoSpcImpl oProductoDAO = new ProductoDaoSpcImpl(strObjectName, oConnection);
            alColumns = oProductoDAO.getColumnsNames();
            data = new Gson().toJson(alColumns);
            //data = "{\"data\":" + data + "}";
            oConnection.commit();

        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getColumns ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getAggregateViewOne(Integer id) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
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
            oConnection.commit();

        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAggregateViewOne ERROR: " + ex.getMessage()));
        }
        return data;
    }

    @Override
    public String getAggregateViewSome(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {
        String data = null;
        try {
            oConnection.setAutoCommit(false);
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
            oConnection.commit();

        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAggregateViewSome ERROR: " + ex.getMessage()));
        }
        return data;
    }
}
