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

<%@page import="net.daw.helper.AppInformationHelper"%>
<%@page import="net.daw.helper.EstadoHelper"%>
<div class="jumbotron">
    <h1>Bienvenidos a <%=AppInformationHelper.getAppName()%> <%=EstadoHelper.getAnyo()%></h1>
    <h3>Versi�n (v.<%=EstadoHelper.getVersion()%>) de <%=EstadoHelper.getFecha()%></h3>
    <h5>Desarrollo de aplicaciones web. CPIFP Ausi�s March. Curso <%=EstadoHelper.getCurso()%></h5>   
    <%
        if (EstadoHelper.getTipo_estado() == EstadoHelper.getTipo_estado().Debug) {
            out.print("<h5>Modo debug</h5>");
        }
    %>
</div>
<div class="row">
    <div class="col-md-8">
        <div class="row">
            <div class="col-md-6">
                <h3>�Qu� es <%=AppInformationHelper.getAppName()%>?</h3>
                <p>Es una aplicaci�n did�ctica para aprender a organizar y desarrollar 
                    aplicaciones AJAX. Est� pensado para soportar mantenimientos 
                    CRUDL (create remove update delete list).</p>
            </div>
            <div class="col-md-6">
                <h3>�Para qu� sirve?</h3>
                <p>Se trata de una aplicaci�n de car�cter did�ctico. 
                    Se ha estructurado siguiendo un modelo MVC que permita a los alumnos 
                    conocer un referente para dise�ar en un futuro sus proyectos.                    
                </p>
            </div>    
        </div>
        <div class="row">    
            <div class="col-md-6">
                <h3>�Qui�n la ha desarrollado?</h3>
                <p>La ha desarrollado <%=EstadoHelper.getAutor()%>,
                    profesor de DAW en el CPIFP Ausi�s March de Valencia (Spain).
                    Puedes contactar en <%=EstadoHelper.getMailAutor()%>.</p>
            </div> 
            <div class="col-md-6">
                <h3>�Qu� licencia tiene?</h3>
                <p>La he liberado con licencia <%=EstadoHelper.getLicenciaLink()%></p>
            </div>

        </div>
    </div>
    <div class="col-md-4">
        <h3>�Cu�les son sus caracter�sticas?</h3>
        <ul>
            <li>Es un desarrollo did�ctico</li>
            <li>Estructura de servidor MVC por niveles sin frameworks</li> 
            <li>Servidor escrito en Java (servlets y jsp)</li>  
            <li>Dos controladores, uno para servir jsp y otro para servir json</li>  
            <li>Cliente AJAX con Javascript y JQuery</li>              
            <li>Cliente desarrollado sobre Bootstrap responsivo</li>
            <li>Acceso a datos (base de datos MySQL) mediante clases de negocio y DAO</li>
            <li>Mantenimientos paginados</li>
            <li>Ajax Stock soporta Relaciones 1:M</li>
            <li>Ajax Stock soporta Relaciones M:M</li>
        </ul>
    </div>
</div>
