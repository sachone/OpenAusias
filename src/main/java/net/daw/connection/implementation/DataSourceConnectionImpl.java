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
package net.daw.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.helper.ConnectionClassHelper;

public class DataSourceConnectionImpl implements ConnectionInterface {

    InitialContext initialContext;

    @Override
    public Connection newConnection() throws Exception {

        try {
            initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/" + ConnectionClassHelper.getDatabaseName());
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (NamingException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void disposeConnection() throws Exception {
        try {
            if (initialContext != null) {
                initialContext.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(DataSourceConnectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
