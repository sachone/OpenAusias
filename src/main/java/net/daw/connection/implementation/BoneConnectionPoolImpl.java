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

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.helper.ConnectionClassHelper;
import net.daw.helper.ExceptionBooster;

public class BoneConnectionPoolImpl implements ConnectionInterface {

    private BoneCP connectionPool = null;

    @Override
    public Connection newConnection() throws Exception {
        Connection c = null;
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(ConnectionClassHelper.getConnectionChain());
        config.setUsername(ConnectionClassHelper.getDatabaseLogin());
        config.setPassword(ConnectionClassHelper.getDatabasePassword());
        config.setMinConnectionsPerPartition(1);
        config.setMaxConnectionsPerPartition(2);
        config.setPartitionCount(1);
        try {
            connectionPool = new BoneCP(config); // setup the connection pool
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":newConnection ERROR al crear la clase BoneCP: " + ex.getMessage()));
        }
        try {
            c = connectionPool.getConnection();
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":newConnection ERROR al conectarse: " + ex.getMessage()));
        }
        return c;
    }

    @Override
    public void disposeConnection() throws Exception {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }
}
