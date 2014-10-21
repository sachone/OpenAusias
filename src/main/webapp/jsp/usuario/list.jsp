<%-- 
 Copyright (C) July 2014 Rafael Aznar

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
--%>

<%@page import="java.sql.Connection"%>
<%@page import="net.daw.dao.implementation.UsuarioDaoImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%
    Connection connection = (Connection) request.getAttribute("connection");
    UsuarioDaoImpl oUsuarioDao = new UsuarioDaoImpl(connection);
    ArrayList<String> alColumnsNames = (ArrayList<String>) oUsuarioDao.getColumnsNames();
    Iterator<String> oIterador = alColumnsNames.listIterator();
    String strNombreMantenimiento = "usuario";
%>
<div id="<%=strNombreMantenimiento%>_list">
    <div>
        <div class="row">
            <div class="col-md-7">       
                <h2><%=strNombreMantenimiento%></h2>
                <div id="order"></div>
                <div id="filter"></div>            
                <div id="registers"></div>
                <div id="pagination"></div>
            </div>
            <div class="col-md-5">
                <div class="text-right">
                    <legend>Filtro</legend> 
                    <form class="navbar-form navbar-right" role="form" action="Controller" method="post" id="filterForm">

                        <select id="selectFilter" name="filter" width="80" style="width: 100px">
                            <%
                                while (oIterador.hasNext()) {
                                    String strNombreColumna = oIterador.next();
                                    String strNombreColumnaPretty = strNombreColumna.charAt(0) + strNombreColumna.substring(1);
                            %>
                            <option value="<%=strNombreColumna%>"><%=strNombreColumnaPretty%></option>
                            <% }%>
                        </select>

                        <select id="selectFilteroperator" name="filteroperator" width="80" style="width: 100px">
                            <option>like</option>
                            <option>notlike</option>
                            <option>equals</option>
                            <option>notequalto</option>
                            <option>less</option>
                            <option>lessorequal</option>
                            <option>greater</option>
                            <option>greaterorequal</option> 
                        </select>
                        <input id="inputFiltervalue" name="filtervalue" type="text" size="20" maxlength="50" value=""  width="100" style="width: 90px"/>

                        <input type="submit" class="btn" id="btnFiltrar" name="btnFiltrar" value="Filtrar <%=strNombreMantenimiento%>" />


                    </form>
                </div>
                <div class="text-right">
                    <legend>Registros por página</legend> 
                    <form class="navbar-form navbar-right" role = "form" action="Controller" method="post" id="nrrpForm" >
                        <fieldset>                                               
                            <span>
                                <select  id="rpp" name="nrpp" value="select" style="width: 80px">                        
                                    <option>5</option>
                                    <option selected>10</option>
                                    <option>20</option>
                                    <option>50</option>
                                    <option>100</option>
                                </select>  
                            </span>                   
                        </fieldset>
                    </form>                
                </div> 
                <button class="btn" id="crear">Crear <%=strNombreMantenimiento%></button>
            </div>
        </div>
        <br>
        <div id="datos"></div>
        <div id="datos2"></div>
    </div>
    <!-- Modales -->
    <div id="modal01" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header"></div>
        <div class="modal-body"></div>
        <div class="modal-footer"></div>
    </div>
    <div id="modal02" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header"></div>
        <div class="modal-body"></div>
        <div class="modal-footer"></div>
    </div>
</div>

