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
package net.daw.helper;

public class EstadoHelper {

    public static enum Tipo_estado {

        Debug,
        Production
    };

    public static Tipo_estado getTipo_estado() {
        return Tipo_estado.Debug;
    }

    public static String getVersion() {
        return "03";
    }

    public static String getFecha() {
        return "15/10/2014";
    }

    public static String getAnyo() {
        return "2014";
    }

    public static String getCurso() {
        return "2014-2015";
    }

    public static String getAutor() {
        return "Rafael Aznar";
    }

    public static String getMailAutor() {
        return "rafaaznar{at}gmail{dot}com";
    }

    public static String getLicenciaLink() {
        return "<a href=\"http://www.gnu.org/licenses/gpl-2.0.html\">GNU General Public License, version 2</a>";
    }
}
