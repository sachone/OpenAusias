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
package net.daw.dao.generic.specific.implementation;

import net.daw.dao.generic.implementation.TableDaoGenImpl;
import java.sql.Connection;
import net.daw.bean.generic.specific.implementation.UsuarioBeanGenSpImpl;

public class UsuarioDaoGenSpImpl extends TableDaoGenImpl<UsuarioBeanGenSpImpl> {

    public UsuarioDaoGenSpImpl(Connection pooledConnection) throws Exception {
        super("usuario", pooledConnection);
    }

    public UsuarioBeanGenSpImpl getFromLogin(UsuarioBeanGenSpImpl oUsuario) throws Exception {
        try {

            String strId = oMysql.getId("usuario", "login", oUsuario.getLogin());
            if (strId == null) {
                oUsuario.setId(0);
            } else {
                Integer intId = Integer.parseInt(strId);
                oUsuario.setId(intId);
                String pass = oUsuario.getPassword();
                oUsuario.setPassword(oMysql.getOne("usuario", "password", oUsuario.getId()));
                if (!pass.equals(oUsuario.getPassword())) {
                    oUsuario.setId(0);
                }
            }

            return oUsuario;
        } catch (Exception e) {
            throw new Exception("UsuarioDao.getFromLogin: Error: " + e.getMessage());
        }
    }

//    public UsuarioBean type(UsuarioBean oUsuarioBean) throws Exception {
//
//        try {
//            AlumnoDao oAlumnoDao = new AlumnoDao();
//            AlumnoBean oAlumnoBean = oAlumnoDao.getFromId_usuario(oUsuarioBean);
//            oUsuarioBean.setTipoUsuario(Enum.TipoUsuario.Alumno);
//        } catch (Exception e1) {
//            try {
//                EmpresaDao oEmpresaDao = new EmpresaDao();
//                EmpresaBean oEmpresaBean = oEmpresaDao.getFromId_usuario(oUsuarioBean);
//                oUsuarioBean.setTipoUsuario(Enum.TipoUsuario.Empresa);
//            } catch (Exception e2) {
//                try {
//                    ProfesorDao oProfesorDao = new ProfesorDao();
//                    ProfesorBean oProfesorBean = oProfesorDao.getFromId_usuario(oUsuarioBean);
//                    oUsuarioBean.setTipoUsuario(Enum.TipoUsuario.Profesor);
//                } catch (Exception e3) {
//                    throw new Exception("UsuarioDao.type: Error: " + e3.getMessage());
//                }
//            }
//        } finally {
//            oMysql.desconexion();
//        }
//        return oUsuarioBean;
//    }
//    @Override
//    public UsuarioBean get(UsuarioBean oUsuarioBean) throws Exception {
//        if (oUsuarioBean.getId() > 0) {
//            try {
//                oMysql.conexion(enumTipoConexion);
//                if (!oMysql.existsOne("usuario", oUsuarioBean.getId())) {
//                    oUsuarioBean.setId(0);
//                } else {
//                    oUsuarioBean.setLogin(oMysql.getOne("usuario", "login", oUsuarioBean.getId()));
//                    oUsuarioBean.setPassword(oMysql.getOne("usuario", "password", oUsuarioBean.getId()));
//                }
//            } catch (Exception e) {
//                throw new Exception("UsuarioDao.getUsuario: Error: " + e.getMessage());
//            } finally {
//                oMysql.desconexion();
//            }
//        } else {
//            oUsuarioBean.setId(0);
//        }
//        return oUsuarioBean;
//    }
//
//
//    @Override
//    public String getDescription(int id) throws Exception {
//        UsuarioBean oUsuarioBean = new UsuarioBean();
//        oUsuarioBean.setId(id);
//        oUsuarioBean = this.get(oUsuarioBean);
//        String description;
//        if (oUsuarioBean.getLogin().length() > 20) {
//            description = oUsuarioBean.getLogin().substring(0, 19) + "...";
//        } else {
//            description = oUsuarioBean.getLogin();
//        }
//        return description;
//    }
}
