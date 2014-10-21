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
package net.daw.dao.generic.implementation;

import net.daw.dao.publicinterface.TableDaoInterface;
import net.daw.dao.publicinterface.ViewDaoInterface;
import net.daw.dao.publicinterface.MetaDaoInterface;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Locale;
import net.daw.helper.ExceptionBooster;

public abstract class TableDaoGenImpl<TIPO_OBJETO> extends ViewDaoGenImpl<TIPO_OBJETO> implements TableDaoInterface<TIPO_OBJETO>, ViewDaoInterface<TIPO_OBJETO>, MetaDaoInterface {

    protected final String strTabla;

    public TableDaoGenImpl(String view, Connection pooledConnection) throws Exception {
        super(view, pooledConnection);
        strTabla = view;
    }

    @Override
    public TIPO_OBJETO set(TIPO_OBJETO oBean) throws Exception {        
        Class<TIPO_OBJETO> tipo = (Class<TIPO_OBJETO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Method metodo_getId = tipo.getMethod("getId");
        Method metodo_setId = tipo.getMethod("setId", Integer.class);
        try {
            if ((Integer) metodo_getId.invoke(oBean) == 0) {
                metodo_setId.invoke(oBean, oMysql.insertOne(strTabla));
            }
            for (Method method : tipo.getMethods()) {
                if (!method.getName().substring(3).equalsIgnoreCase("id")) {
                    if (method.getName().substring(0, 3).equalsIgnoreCase("get")) {
                        if (!method.getName().substring(0, 6).equalsIgnoreCase("getObj")) {
                            if (!method.getName().equals("getClass")) {
                                final Class<?> classTipoDevueltoMetodoGet = method.getReturnType();
                                String value = method.invoke(oBean).toString();
                                switch (classTipoDevueltoMetodoGet.getName()) {
                                    case "java.util.Date":
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                        value = format.format(method.invoke(oBean));
                                        break;
                                    case "java.lang.Boolean":
                                        if ("true".equals(value)) {
                                            value = "1";
                                        } else {
                                            value = "0";
                                        }
                                        break;
                                }
                                String strCampo = method.getName().substring(3).toLowerCase(Locale.ENGLISH);
                                oMysql.updateOne((Integer) metodo_getId.invoke(oBean), strTabla, strCampo, value);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
        }
        return oBean;
    }

    @Override
    public int remove(TIPO_OBJETO oBean) throws Exception {
        int result = 0;
        Class<TIPO_OBJETO> tipo = (Class<TIPO_OBJETO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Method metodo_getId = tipo.getMethod("getId");
        try {
            result = oMysql.removeOne((Integer) metodo_getId.invoke(oBean), strTabla);
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
        }
        return result;
    }
}
