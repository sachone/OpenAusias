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
package net.daw.control.route.specific.implementation;

import javax.servlet.http.HttpServletRequest;
import net.daw.control.operation.publicinterface.ControlOperationInterface;
import net.daw.control.operation.specific.implementation.TipoproductoControlOperationSpImpl;
import net.daw.control.route.publicinterface.ControlRouteInterface;
import net.daw.helper.ExceptionBooster;
import net.daw.helper.parameterCooker;

public class TipoproductoControlRouteSpImpl implements ControlRouteInterface {

    @Override
    public String execute(HttpServletRequest request, ControlOperationInterface oControl) throws Exception {
        String operation = parameterCooker.prepareOperation(request);
        TipoproductoControlOperationSpImpl oControlOperation = new TipoproductoControlOperationSpImpl(request);
        String jsonResult = "";
        try {
            switch (operation) {
                case "get":
                    jsonResult = oControl.get(request);
                    break;
                case "getaggregateviewone":
                    jsonResult = oControl.getaggregateviewone(request);
                    break;
                case "getprettycolumns":
                    jsonResult = oControl.getprettycolumns(request);
                    break;
                case "getcolumns":
                    jsonResult = oControl.getcolumns(request);
                    break;
                case "getpage":
                    jsonResult = oControl.getpage(request);
                    break;
                case "getpages":
                    jsonResult = oControl.getpages(request);
                    break;
                case "getregisters":
                    jsonResult = oControl.getregisters(request);
                    break;
                case "getaggregateviewsome":
                    jsonResult = oControl.getaggregateviewsome(request);
                    break;
                case "remove":
                    jsonResult = oControl.remove(request);
                    break;
                case "save":
                    jsonResult = oControl.set(request);
                    break;
                default:
                    ExceptionBooster.boost(new Exception(this.getClass().getName() + ":execute ERROR: error de operación: la operación no existe"));
                    break;
            }
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":execute ERROR: " + ex.getMessage()));
        }
        return jsonResult;
    }

}
