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
package net.daw.data.specific.implementation;

import net.daw.data.publicinterface.DataInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.daw.helper.ExceptionBooster;
import net.daw.helper.FilterBeanHelper;

public class MysqlDataSpImpl implements DataInterface {

    Connection connection = null;

    public MysqlDataSpImpl(Connection pooledConnection) {
        connection = pooledConnection;
    }

    @Override
    public int removeOne(int intId, String strTabla) throws Exception {
        PreparedStatement oPreparedStatement = null;
        int intResult = 0;
        try {
            String strSQL = "DELETE FROM " + strTabla + " WHERE id = ?";
            oPreparedStatement = (PreparedStatement) connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, intId);
            intResult = oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":removeOne ERROR removing register: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public int insertOne(String strTabla) throws Exception {

        ResultSet oResultSet;
        java.sql.PreparedStatement oPreparedStatement = null;
        int id = 0;
        try {
            String strSQL = "INSERT INTO " + strTabla + " (id) VALUES (null) ";
            oPreparedStatement = connection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            int returnLastInsertId = oPreparedStatement.executeUpdate();
            if (returnLastInsertId != -1) {
                oResultSet = oPreparedStatement.getGeneratedKeys();
                oResultSet.next();
                id = oResultSet.getInt(1);
            } else {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":insertOne ERROR inserting register"));
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":insertOne ERROR inserting register: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return id;
    }

    @Override
    public int setNull(int intId, String strTabla, String strCampo) throws Exception {
        PreparedStatement oPreparedStatement = null;
        int intResult = 0;
        try {
            String strSQL = "UPDATE " + strTabla + " SET " + strCampo + " = null WHERE id = ?";
            oPreparedStatement = (PreparedStatement) connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, intId);
            intResult = oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":setNull ERROR updating register: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public int updateOne(int intId, String strTabla, String strCampo, String strValor) throws Exception {
        int intResult = 0;
        PreparedStatement oPreparedStatement = null;
        try {
            String strSQL = "UPDATE " + strTabla + " SET " + strCampo + " = '" + strValor + "' WHERE id = ?";
            oPreparedStatement = (PreparedStatement) connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, intId);
            intResult = oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":updateOne ERROR updating register: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public String getId(String strTabla, String strCampo, String strValor) throws Exception {
        String strResult = null;
        Statement oStatement = null;
        ResultSet oResultSet;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT id FROM " + strTabla + " WHERE " + strCampo + "='" + strValor + "'";
            oResultSet = oStatement.executeQuery(strSQL);
            if (oResultSet.next()) {
                strResult = oResultSet.getString("id");
            } else {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getId ERROR: ID not exists"));
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getId ERROR: Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return strResult;
    }

    @Override
    public String getOne(String strTabla, String strCampo, int id) throws Exception {
        String strResult = null;
        PreparedStatement oPreparedStatement = null;
        ResultSet oResultSet;
        try {
            String strSQL = "SELECT " + strCampo + " FROM " + strTabla + " WHERE id=?";
            oPreparedStatement = connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                strResult = oResultSet.getString(strCampo);
            } else {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: ID not exists: " + id));
            }
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: Can't process query: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return strResult;
    }

//    @Override
//    public String getOneFromSql(String strSQL) throws Exception {
//        Statement oStatement = null;
//        ResultSet oResultSet;
//        try {
//            oResultSet = oStatement.executeQuery(strSQL);
//            if (oResultSet.next()) {
//                return oResultSet.getString("strvalue");
//            } else {
//                return null;
//            }
//        } catch (SQLException ex) {
//            throw new Exception("mysql.getOne: No se ha podido realizar la consulta: " + ex.getMessage());
//        } finally {
//            if (oStatement != null) {
//                oStatement.close();
//            }
//
//        }
//    }
    @Override
    public Boolean existsOne(String strTabla, int id) throws Exception {
        int result = 0;
        Statement oStatement = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT COUNT(*) FROM " + strTabla + " WHERE 1=1";
            ResultSet rs = oStatement.executeQuery(strSQL);
            if (rs.next()) {
                result = rs.getInt("COUNT(*)");
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":existsOne ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }

        }
        return (result > 0);
    }

    @Override
    public int getPages(String strTabla, int intRegsPerPage, ArrayList<FilterBeanHelper> alFilter) throws Exception {

        int intResult = 0;
        Statement oStatement = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT count(*) FROM " + strTabla + " WHERE 1=1";
            if (alFilter != null) {
                Iterator iterator = alFilter.iterator();
                while (iterator.hasNext()) {
                    FilterBeanHelper oFilterBean = (FilterBeanHelper) iterator.next();
                    switch (oFilterBean.getFilterOperator()) {
                        case "like":
                            strSQL += " AND " + oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'";
                            break;
                        case "notlike":
                            strSQL += " AND " + oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'";
                            break;
                        case "equals":
                            strSQL += " AND " + oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'";
                            break;
                        case "notequalto":
                            strSQL += " AND " + oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'";
                            break;
                        case "less":
                            strSQL += " AND " + oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue() + "";
                            break;
                        case "lessorequal":
                            strSQL += " AND " + oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue() + "";
                            break;
                        case "greater":
                            strSQL += " AND " + oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue() + "";
                            break;
                        case "greaterorequal":
                            strSQL += " AND " + oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue() + "";
                            break;
                    }

                }
            }
            ResultSet oResultSet = oStatement.executeQuery(strSQL);
            while (oResultSet.next()) {
                intResult = oResultSet.getInt("COUNT(*)") / intRegsPerPage;
                if ((oResultSet.getInt("COUNT(*)") % intRegsPerPage) > 0) {
                    intResult++;
                }
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPages ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public int getCount(String strTabla, ArrayList<FilterBeanHelper> alFilter) throws Exception {

        int intResult = 0;
        Statement oStatement = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT count(*) FROM " + strTabla + " WHERE 1=1";
            if (alFilter != null) {
                Iterator iterator = alFilter.iterator();
                while (iterator.hasNext()) {
                    FilterBeanHelper oFilterBean = (FilterBeanHelper) iterator.next();
                    switch (oFilterBean.getFilterOperator()) {
                        case "like":
                            strSQL += " AND " + oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'";
                            break;
                        case "notlike":
                            strSQL += " AND " + oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'";
                            break;
                        case "equals":
                            strSQL += " AND " + oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'";
                            break;
                        case "notequalto":
                            strSQL += " AND " + oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'";
                            break;
                        case "less":
                            strSQL += " AND " + oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue() + "";
                            break;
                        case "lessorequal":
                            strSQL += " AND " + oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue() + "";
                            break;
                        case "greater":
                            strSQL += " AND " + oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue() + "";
                            break;
                        case "greaterorequal":
                            strSQL += " AND " + oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue() + "";
                            break;
                    }
                }
            }
            ResultSet oResultSet = oStatement.executeQuery(strSQL);
            while (oResultSet.next()) {
                intResult = oResultSet.getInt("COUNT(*)");
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCount ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public ArrayList<String> getColumnsName(String strTabla) throws Exception {
        ArrayList<String> vector = null;
        Statement oStatement = null;
        try {
            vector = new ArrayList<>();
            oStatement = connection.createStatement();
            String strSQL = "SHOW FULL COLUMNS FROM " + strTabla;
            ResultSet oResultSet = oStatement.executeQuery(strSQL);
            while (oResultSet.next()) {
                if (oResultSet.getString("Field").length() >= 4) {
                    if (oResultSet.getString("Field").substring(0, 3).equalsIgnoreCase("id_")) {
                        vector.add("obj_" + oResultSet.getString("Field").substring(3));
                    } else {
                        vector.add(oResultSet.getString("Field"));
                    }
                } else {
                    vector.add(oResultSet.getString("Field"));
                }
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getColumnsName ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }

        }
        return vector;
    }

    @Override
    public ArrayList<String> getPrettyColumns(String strTabla) throws Exception {
        ArrayList<String> vector = null;
        Statement oStatement = null;
        try {
            vector = new ArrayList<>();
            oStatement = connection.createStatement();
            //String strSQL = "SELECT id FROM " + strTabla + " WHERE 1=1 ";
            String strSQL = "SHOW FULL COLUMNS FROM " + strTabla;
            ResultSet oResultSet = oStatement.executeQuery(strSQL);
            while (oResultSet.next()) {
                vector.add(oResultSet.getString("Comment")); //COLUMNS.Comment COLUMN_COMMENT
//                if (desc) {
//                    if (oResultSet.getString("COLUMN_NAME").length() >= 4) {
//                        if (oResultSet.getString("COLUMN_NAME").substring(0, 3).equalsIgnoreCase("id_")) {
//                            vector.add("desc_" + oResultSet.getString("COLUMN_NAME").substring(3));
//                        }
//                    }
//                }
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPrettyColumns ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }

        }
        return vector;
    }

//    public ArrayList<Integer> getPageFromSQL(String strSql) throws Exception {
////pte:currando..
//        Statement oStatement = null;
//        try {
//            ArrayList<Integer> vector = new ArrayList<>();
//            int intOffset;
//
//            oStatement = (Statement) connection.createStatement();
//
//            ResultSet oResultSet = oStatement.executeQuery(strSql);
//
//            ResultSetMetaData metaData = resultSet.getMetaData();
//
//            int count = metaData.getColumnCount();
//            for (int i = 1; i <= count; i++) {
//                if (metaData.getColumnName(i).equals(desiredColumnName)) {
//                    // Whatever you want to do here.
//                }
//            }
//
//            while (oResultSet.next()) {
//                vector.add(oResultSet.getInt("id"));
//            }
//            return vector;
//        } catch (SQLException e) {
//            throw new Exception("mysql.getPageFromSQL: Error en la consulta: " + e.getMessage());
//        } finally {
//            if (oStatement != null) {
//                oStatement.close();
//            }
//
//        }
//    }
    @Override
    public ArrayList<Integer> getPage(String strTabla, int intRegsPerPage, int intPagina, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {
        ArrayList<Integer> vector = null;
        Statement oStatement = null;
        try {
            vector = new ArrayList<>();
            int intOffset;
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT id FROM " + strTabla + " WHERE 1=1 ";
            String strSQLcount = "SELECT COUNT(*) FROM " + strTabla + " WHERE 1=1 ";
            if (alFilter != null) {
                String strSQLFilter = "";
                Iterator iterator = alFilter.iterator();
                while (iterator.hasNext()) {
                    FilterBeanHelper oFilterBean = (FilterBeanHelper) iterator.next();
                    switch (oFilterBean.getFilterOperator()) {
                        case "like":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'";
                            break;
                        case "notlike":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'";
                            break;
                        case "equals":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'";
                            break;
                        case "notequalto":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'";
                            break;
                        case "less":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue() + "";
                            break;
                        case "lessorequal":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue() + "";
                            break;
                        case "greater":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue() + "";
                            break;
                        case "greaterorequal":
                            strSQLFilter += " AND " + oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue() + "";
                            break;
                    }
                }
                strSQL += strSQLFilter;
                strSQLcount += strSQLFilter;
            }            
            //when limit of pages exceed, show last page
            ResultSet oResultSet = oStatement.executeQuery(strSQLcount);
            int intCuenta=0;
            if (oResultSet.next()) {
                intCuenta = oResultSet.getInt("COUNT(*)");
            }
            int maxPaginas=new Double(intCuenta / intRegsPerPage).intValue(); 
            intPagina=Math.min(intPagina - 1, maxPaginas)+1;
            intOffset = Math.max(((intPagina - 1) * intRegsPerPage), 0);
            //--                        
            if (hmOrder != null) {
                strSQL += " ORDER BY";
                for (Map.Entry oPar : hmOrder.entrySet()) {
                    strSQL += " " + oPar.getKey() + " " + oPar.getValue() + ",";
                }
                strSQL = strSQL.substring(0, strSQL.length() - 1);
            }
            strSQL += " LIMIT " + intOffset + " , " + intRegsPerPage;
            oResultSet = oStatement.executeQuery(strSQL);
            while (oResultSet.next()) {
                vector.add(oResultSet.getInt("id"));
            }

        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPage ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return vector;
    }

    @Override
    public void removeSomeId(String strTabla, ArrayList<Integer> Ids) throws SQLException {
        Statement oStatement = null;
        try {
            Iterator<Integer> iterador = Ids.listIterator();
            while (iterador.hasNext()) {
                oStatement = (Statement) connection.createStatement();
                String strSQL = "DELETE FROM " + strTabla + " WHERE id = " + iterador.next();
                oStatement.executeUpdate(strSQL);
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":removeSomeId ERROR: En la consulta: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
    }

    @Override
    public void removeSomeCondition(String strTabla, String campo, String valor) throws Exception {
        Statement oStatement = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "DELETE FROM " + strTabla + " WHERE " + campo + " like " + valor;
            oStatement.executeUpdate(strSQL);
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":deleteOne ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
    }

//    @Override
//    public ArrayList<String> getColumnsName(String strTabla) throws Exception {
//        Connection conexion = ConnectionSource.getDataSource().getConnection();
//        Statement oStatement = null;
//        try {
//            ArrayList<String> vector = new ArrayList<>();
//
//           
//            oStatement = conexion.createStatement();
//            //String strSQL = "SELECT id FROM " + strTabla + " WHERE 1=1 ";
//            String strSQL = "SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` ";
//            strSQL += "WHERE `TABLE_SCHEMA`='" + ConnectionClass.getDatabaseName() + "' ";
//            strSQL += "AND `TABLE_NAME`='" + strTabla + "' ";
//            ResultSet oResultSet = oStatement.executeQuery(strSQL);
//            while (oResultSet.next()) {
//                if (oResultSet.getString("COLUMN_NAME").length() >= 4) {
//                    if (oResultSet.getString("COLUMN_NAME").substring(0, 3).equalsIgnoreCase("id_")) {
//                        vector.add("obj_" + oResultSet.getString("COLUMN_NAME").substring(3));
//                    } else {
//                        vector.add(oResultSet.getString("COLUMN_NAME"));
//                    }
//                } else {
//                    vector.add(oResultSet.getString("COLUMN_NAME"));
//                }
//            }
//            return vector;
//        } catch (SQLException e) {
//            throw new Exception("mysql.getColumnsName: Error en la consulta: " + e.getMessage());
//        } finally {
//            oStatement.close();
//            conexion.close();
//        }
//    }
}
