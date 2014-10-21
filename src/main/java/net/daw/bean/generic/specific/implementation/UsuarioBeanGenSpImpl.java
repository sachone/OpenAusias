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
package net.daw.bean.generic.specific.implementation;

import net.daw.bean.generic.implementation.BeanGenImpl;
import net.daw.bean.publicinterface.BeanInterface;
import com.google.gson.annotations.Expose;

public class UsuarioBeanGenSpImpl extends BeanGenImpl implements BeanInterface {

    @Expose
    private String login = "";
    @Expose
    private String password = "";
//@Expose
//    private Enum tipoUsuario = null;

//    /**
//     * @return the tipoUsuario
//     */
//    public Enum getTipoUsuario() {
//        return tipoUsuario;
//    }
//
//    /**
//     * @param tipoUsuario the tipoUsuario to set
//     */
//    public void setTipoUsuario(Enum tipoUsuario) {
//        this.tipoUsuario = tipoUsuario;
//    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
