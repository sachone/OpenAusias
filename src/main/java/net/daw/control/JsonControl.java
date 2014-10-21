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
package net.daw.control;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.control.operation.generic.specific.implementation.DocumentoControlOperationGenSpImpl;
import net.daw.control.operation.generic.specific.implementation.TipodocumentoControlOperationGenSpImpl;
import net.daw.control.operation.generic.specific.implementation.UsuarioControlOperationGenSpImpl;
import net.daw.control.operation.specific.implementation.ArmaControlOperationSpImpl;
import net.daw.control.operation.specific.implementation.OrdenadorControlOperationSpImpl;
import net.daw.control.operation.specific.implementation.ProductoControlOperationSpImpl;
import net.daw.control.operation.specific.implementation.TipoproductoControlOperationSpImpl;
import net.daw.control.route.generic.specific.implementation.DocumentoControlRouteGenSpImpl;
import net.daw.control.route.generic.specific.implementation.TipodocumentoControlRouteGenSpImpl;
import net.daw.control.route.generic.specific.implementation.UsuarioControlRouteGenSpImpl;
import net.daw.control.route.specific.implementation.ArmaControlRouteSpImpl;
import net.daw.control.route.specific.implementation.OrdenadorControlRouteSpImpl;
import net.daw.control.route.specific.implementation.ProductoControlRouteSpImpl;
import net.daw.control.route.specific.implementation.TipoproductoControlRouteSpImpl;
import net.daw.helper.EstadoHelper;
import net.daw.helper.EstadoHelper.Tipo_estado;
import net.daw.helper.ExceptionBooster;
import net.daw.helper.parameterCooker;

public class JsonControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private void retardo(Integer iLast) {
        try {
            Thread.sleep(iLast);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception ex) {
                if (EstadoHelper.getTipo_estado() == Tipo_estado.Debug) {
                    Map<String, String> data = new HashMap<>();
                    data.put("status", "500");
                    data.put("message", "ERROR: " + ex.getMessage());
                    Gson gson = new Gson();
                    request.setAttribute("contenido", gson.toJson(data));
                    getServletContext().getRequestDispatcher("/jsp/messageAjax.jsp").forward(request, response);
                } else {
                    Map<String, String> data = new HashMap<>();
                    data.put("status", "500");
                    data.put("message", "Applications server error. Please, contact your administrator.");
                    Gson gson = new Gson();
                    request.setAttribute("contenido", gson.toJson(data));
                    getServletContext().getRequestDispatcher("/jsp/messageAjax.jsp").forward(request, response);
                }
                Logger.getLogger(JsonControl.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            //----------------------------------------------------------------------          
            retardo(0); //debug delay
            String jsonResult = "";
            if (request.getSession().getAttribute("usuarioBean") != null) {
                switch (parameterCooker.prepareObject(request)) {
                    case "documento":
                        DocumentoControlRouteGenSpImpl oDocumentoRoute = new DocumentoControlRouteGenSpImpl();
                        DocumentoControlOperationGenSpImpl oDocumentoControlOperation = new DocumentoControlOperationGenSpImpl(request);
                        jsonResult = oDocumentoRoute.execute(request, oDocumentoControlOperation);
                        break;
                    case "tipodocumento":
                        TipodocumentoControlRouteGenSpImpl oTipodocumentoRoute = new TipodocumentoControlRouteGenSpImpl();
                        TipodocumentoControlOperationGenSpImpl oTipodocumentoControlOperation = new TipodocumentoControlOperationGenSpImpl(request);
                        jsonResult = oTipodocumentoRoute.execute(request, oTipodocumentoControlOperation);
                        break;
                    case "usuario":
                        UsuarioControlRouteGenSpImpl oUsuarioRoute = new UsuarioControlRouteGenSpImpl();
                        UsuarioControlOperationGenSpImpl oUsuarioControlOperation = new UsuarioControlOperationGenSpImpl(request);
                        jsonResult = oUsuarioRoute.execute(request, oUsuarioControlOperation);
                        break;
                    case "producto":
                        ProductoControlRouteSpImpl oProductoRoute = new ProductoControlRouteSpImpl();
                        ProductoControlOperationSpImpl oProductoControlOperation = new ProductoControlOperationSpImpl(request);
                        jsonResult = oProductoRoute.execute(request, oProductoControlOperation);
                        break;
                    case "tipoproducto":
                        TipoproductoControlRouteSpImpl oTipoproductoRoute = new TipoproductoControlRouteSpImpl();
                        TipoproductoControlOperationSpImpl oTipoproductoControlOperation = new TipoproductoControlOperationSpImpl(request);
                        jsonResult = oTipoproductoRoute.execute(request, oTipoproductoControlOperation);
                        break;
                    case "ordenador":
                        OrdenadorControlRouteSpImpl oOrdenadorRoute = new OrdenadorControlRouteSpImpl();
                        OrdenadorControlOperationSpImpl oOrdenadorControlOperation = new OrdenadorControlOperationSpImpl(request);
                        jsonResult = oOrdenadorRoute.execute(request, oOrdenadorControlOperation);
                        break;
                    case "arma":
                        ArmaControlRouteSpImpl oArmaRoute = new ArmaControlRouteSpImpl();
                        ArmaControlOperationSpImpl oArmaControlOperation = new ArmaControlOperationSpImpl(request);
                        jsonResult = oArmaRoute.execute(request, oArmaControlOperation);
                        break;
                    default:
                        ExceptionBooster.boost(new Exception(this.getClass().getName() + ":processRequest ERROR: no such operation"));
                }
            } else {
                jsonResult = "{\"error\" : \"No active server session\"}";
            }
            request.setAttribute("contenido", jsonResult);
            getServletContext().getRequestDispatcher("/jsp/messageAjax.jsp").forward(request, response);
        } catch (Exception ex) {
            if (EstadoHelper.getTipo_estado() == Tipo_estado.Debug) {
                Map<String, String> data = new HashMap<>();
                data.put("status", "500");
                data.put("message", "ERROR: " + ex.getMessage());
                Gson gson = new Gson();
                request.setAttribute("contenido", gson.toJson(data));
                getServletContext().getRequestDispatcher("/jsp/messageAjax.jsp").forward(request, response);
            } else {
                Map<String, String> data = new HashMap<>();
                data.put("status", "500");
                data.put("message", "Applications server error. Please, contact your administrator.");
                Gson gson = new Gson();
                request.setAttribute("contenido", gson.toJson(data));
                getServletContext().getRequestDispatcher("/jsp/messageAjax.jsp").forward(request, response);
            }
            Logger.getLogger(JsonControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (Exception ex) {
            //Logger.getLogger(JsonControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (Exception ex) {
            //Logger.getLogger(JsonControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
