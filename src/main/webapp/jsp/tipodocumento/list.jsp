<%-- 
    Document   : list.jsp
    Created on : Jan 16, 2013, 12:57:09 PM
    Author     : Alvaro
--%>
<%@page import="net.daw.helper.Conexion"%>
<%@page import="net.daw.dao.DocumentoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%
    DocumentoDao oDocumentoDao_Mysql = new DocumentoDao();
    ArrayList<String> alColumnsNames = (ArrayList<String>) oDocumentoDao_Mysql.getColumnsNames(false);
    Iterator<String> oIterador = alColumnsNames.listIterator();
    String strNombreMantenimiento = "tipodocumento";
%>
<div id="<%=strNombreMantenimiento%>_list">
    <div>
        <div class="row">
            <div class="col-md-6">       
                <h2><%=strNombreMantenimiento%></h2>
                <div id="order"></div>
                <div id="filter"></div>            
                <div id="registers"></div>
                <div id="pagination"></div>
            </div>
            <div class="col-md-6">
                <div class="text-right">
                    <legend>Filtro</legend> 
                    <form class="navbar-form navbar-right" role="form" action="Controller" method="post" id="filterForm">
                        <select id="selectFilter" class="form-control" name="filter" width="80" style="width: 100px">
                            <%
                                while (oIterador.hasNext()) {
                                    String strNombreColumna = oIterador.next();
                                    String strNombreColumnaPretty = strNombreColumna.charAt(0) + strNombreColumna.substring(1);
                            %>
                            <option value="<%=strNombreColumna%>"><%=strNombreColumnaPretty%></option>
                            <% }%>
                        </select>
                        <select id="selectFilteroperator" class="form-control" name="filteroperator" width="80" style="width: 100px">
                            <option>like</option>
                            <option>notlike</option>
                            <option>equals</option>
                            <option>notequalto</option>
                            <option>less</option>
                            <option>lessorequal</option>
                            <option>greater</option>
                            <option>greaterorequal</option> 
                        </select>
                        <input id="inputFiltervalue" class="form-control" name="filtervalue" type="text" size="20" maxlength="50" value=""  width="100" style="width: 90px" placeholder="Valor"/>
                        <input type="submit" class="btn" id="btnFiltrar" name="btnFiltrar" value="Filtrar " />
                    </form>
                </div>
                <div class="text-right">
                    <legend>Registros por página</legend> 
                    <form class="navbar-form navbar-right" role = "form" action="Controller" method="post" id="nrrpForm" >
                        <select  id="rpp" class="form-control" name="nrpp" value="select" style="width: 80px">                        
                            <option>5</option>
                            <option selected>10</option>
                            <option>20</option>
                            <option>50</option>
                            <option>100</option>
                        </select>  
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

    <div id="modal01" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header"></div>
                <div class="modal-body"></div>
                <div class="modal-footer"></div>
            </div>
        </div>
    </div>
    <div id="modal02" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header"></div>
                <div class="modal-body"></div>
                <div class="modal-footer"></div>
            </div>                
        </div>
    </div>
</div>
