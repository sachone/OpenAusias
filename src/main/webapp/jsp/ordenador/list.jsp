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
<%@page import="net.daw.dao.generic.specific.implementation.DocumentoDaoGenSpImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%
    ArrayList<String> alColumnsNames;
    Iterator<String> oIterador;
    String strNombreMantenimiento = "ordenador";
    Connection connection = (Connection) request.getAttribute("connection");
    DocumentoDaoGenSpImpl oDocumentoDao = new DocumentoDaoGenSpImpl(connection);
%>
<div class="row">
    <div class="col-md-12">
        <div class="tabbable">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#pane1" data-toggle="tab">Paginación</a></li>                
                <li><a href="#pane2" data-toggle="tab">Filtro</a></li>
                <li><a href="#pane3" data-toggle="tab">Nuevo</a></li>
            </ul>
            <div class="tab-content">
                <br />

                <div class="row">
                    <div class="col-md-4">
                        <div id="registers"></div>
                    </div>
                    <div class="col-md-4">
                        <div id="order"></div> 
                    </div>
                    <div class="col-md-4">
                        <div id="filter"></div>   
                    </div>
                </div>

                <div id="pane1" class="tab-pane active">
                    <div class="row">
                        <div class="col-md-7">
                            <div class="panel panel-default">
                                <!-- Default panel contents -->
                                <div class="panel-heading">Paginación</div>
                                <div class="panel-body">
                                    <div class="text-center">
                                        <div id="pagination"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="panel panel-default">
                                <!-- Default panel contents -->
                                <div class="panel-heading">Campos visibles</div>
                                <div class="panel-body">     
                                    <form class="navbar-form navbar-right" role="form" action="Controller" method="post" id="visibleFieldsForm">
                                        <select id="selectVisibleFields" class="form-control" name="filter" width="80" style="width: 70px">
                                        </select>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="panel panel-default">
                                <!-- Default panel contents -->
                                <div class="panel-heading">Registros por página</div>
                                <div class="panel-body">
                                    <div class="text-center">
                                        <div id="nrpp"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div id="pane2" class="tab-pane">                    
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-default">
                                <!-- Default panel contents -->
                                <div class="panel-heading">Filtro</div>
                                <div class="panel-body">
                                    <form class="navbar-form navbar-right" role="form" action="Controller" method="post" id="empresaForm">
                                        <select id="selectFilter" class="form-control" name="filter" width="80" style="width: 160px">
                                        </select>
                                        <select id="selectFilteroperator" class="form-control" name="filteroperator" width="80" style="width: 200px">
                                            <option value="like">contiene</option>
                                            <option value="notlike">no contiene</option>
                                            <option value="equals">es igual a</option>
                                            <option value="notequalto">es distinto de</option>
                                            <option value="less">es menor que</option>
                                            <option value="lessorequal">es menor o igual que</option>
                                            <option value="greater">es mayor que</option>
                                            <option value="greaterorequal">es mayor o igual que</option> 
                                        </select>
                                        <input id="inputFiltervalue" class="form-control" name="filtervalue" type="text" size="20" maxlength="50" value=""  width="100" style="width: 140px" placeholder="Valor"/>
                                        <input type="submit" class="btn" id="btnFiltrar" name="btnFiltrar" value="Filtrar " />
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="pane3" class="tab-pane">                    
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-default">                                 
                                <div class="panel-heading">Nuevo</div>
                                <div class="panel-body">
                                    <br />
                                    <div class="text-center">
                                        <a class="btn btn-primary" href='jsp#/<%=strNombreMantenimiento%>/new'>Crear un nuevo <%=strNombreMantenimiento%></a> 
                                    </div>
                                    <br />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>
</div>
<div class="row">
    <div class="col-md-12" id="menuLateralList">
        <table class="table table-responsive table-hover table-striped table-condensed">
            <thead id="tableHeaders"></thead>
            <tbody id="tableBody"></tbody>
        </table>
        <div id="datos"></div>
        <div id="datos2"></div>
    </div> 
</div> 
<!-- Modals -->
<div id="modal01" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header" id="modal-header"></div>
            <div class="modal-body" id="modal-body"></div>
            <div class="modal-footer" id="modal-footer"></div>
        </div>
    </div>
</div>
<div id="modal02" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header"></div>
            <div class="modal-body"></div>
            <div class="modal-footer"></div>
        </div>                
    </div>
</div>

