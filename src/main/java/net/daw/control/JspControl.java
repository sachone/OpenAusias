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

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.bean.generic.specific.implementation.UsuarioBeanGenSpImpl;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.generic.specific.implementation.UsuarioDaoGenSpImpl;
import net.daw.helper.EstadoHelper;
import net.daw.helper.EstadoHelper.Tipo_estado;
import net.daw.helper.parameterCooker;

public class JspControl extends HttpServlet {

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
        retardo(0); //debug delay
        ConnectionInterface DataConnectionSource = null;
        Connection connection = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            //HTTP headers
            response.setHeader("page language", "java");
            response.setHeader("contentType", "text/html");
            response.setHeader("pageEncoding", "UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            //parameter loading
            String ob = parameterCooker.prepareObject(request);
            String op = parameterCooker.prepareOperation(request);
            String mode = parameterCooker.prepareMode(request);
            //security check
            if (request.getSession().getAttribute("usuarioBean") == null) {
                ob = "usuario";
                if (!op.equals("inicio") && !op.equals("login02")) {
                    op = "login01";
                    mode = "wrappered";
                }
            }
            //login & logout management
            if (ob.equalsIgnoreCase("usuario")) {
                if (op.equalsIgnoreCase("login02")) {
                    UsuarioBeanGenSpImpl oUsuario = new UsuarioBeanGenSpImpl();
                    String login = request.getParameter("login");
                    String pass = request.getParameter("password");
                    if (!login.equals("") && !pass.equals("")) {
                        try {
                            DataConnectionSource = new BoneConnectionPoolImpl();
                            connection = DataConnectionSource.newConnection();
                            oUsuario.setLogin(login);
                            oUsuario.setPassword(pass);
                            UsuarioDaoGenSpImpl oUsuarioDao = new UsuarioDaoGenSpImpl(connection);
                            oUsuario = oUsuarioDao.getFromLogin(oUsuario);
                            if (oUsuario.getId() != 0) {
                                //oUsuario = oUsuarioDao.type(oUsuario); //fill user level -> pending
                                request.getSession().setAttribute("usuarioBean", oUsuario);
                            }
                        } catch (Exception ex) {
                            if (EstadoHelper.getTipo_estado() == Tipo_estado.Debug) {
                                request.setAttribute("title", "Application server error (debug mode)");
                                request.setAttribute("message", "<pre>ERROR: " + ex.getMessage() + "</pre>");
                                request.setAttribute("contenido", "/jsp/message.jsp");
                                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                            } else {
                                request.setAttribute("title", "Application server error.");
                                request.setAttribute("message", "Please, contact your server administrator.");
                                request.setAttribute("contenido", "/jsp/message.jsp");
                                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                            }
                            Logger.getLogger(JsonControl.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            if (connection != null) {
                                connection.close();
                            }
                            if (DataConnectionSource != null) {
                                DataConnectionSource.disposeConnection();
                            }
                        }
                    }
                }
                if (op.equalsIgnoreCase("logout")) {
                    request.getSession().invalidate();
                }
            }
            //delivering jsp page
            if ("wrappered".equals(mode)) {
                request.setAttribute("contenido", "jsp/" + ob + "/" + op + ".jsp");
                request.setAttribute("connection", connection);
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                response.setContentType("text/html; charset=UTF-8");
                request.setAttribute("connection", connection);
                getServletContext().getRequestDispatcher("/jsp/" + ob + "/" + op + ".jsp").forward(request, response);
            }
        } catch (Exception ex) {
            if (EstadoHelper.getTipo_estado() == Tipo_estado.Debug) {
                request.setAttribute("title", "Application server error (debug mode)");
                request.setAttribute("message", "<pre>ERROR: " + ex.getMessage() + "</pre>");
                request.setAttribute("contenido", "/jsp/message.jsp");
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                request.setAttribute("title", "Application server error");
                request.setAttribute("message", "Please, contact your server administrator.");
                request.setAttribute("contenido", "/jsp/message.jsp");
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
            Logger.getLogger(JsonControl.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
//            if (EstadoHelper.getTipo_estado() == Tipo_estado.Debug) {
//                PrintWriter out = response.getWriter();
//                out.println("<html><body><h1>Application error</h1><strong>Message:</strong> " + ex + "<body></html>");
//
//            } else {
//                Logger.getLogger(JspControl.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
//            if (EstadoHelper.getTipo_estado() == Tipo_estado.Debug) {
//                PrintWriter out = response.getWriter();
//                out.println("<html><body><h1>Application error</h1><strong>Message:</strong> " + ex + "<body></html>");
//
//            } else {
//                Logger.getLogger(JspControl.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "jsp controller";
    }
// </editor-fold>
}
