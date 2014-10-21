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


<%@page import="net.daw.helper.EstadoHelper"%>
<%@page import="net.daw.bean.generic.specific.implementation.UsuarioBeanGenSpImpl"%>
<%@page import="net.daw.helper.AppInformationHelper"%>
<%UsuarioBeanGenSpImpl user = (UsuarioBeanGenSpImpl) request.getSession().getAttribute("usuarioBean");%>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
    <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Ajax Yield</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/main.css">
        <!-- <link rel="stylesheet" href="css/jquery-ui.css"> -->
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="css/bootstrapValidator.min.css">
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css"  />
    </head>
    <body>
        <!--[if lt IE 7]>
        <p class="chromeframe">You are using an outdated browser. <a href="http://browsehappy.com/">Upgrade your browser today</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to better experience this site.</p>
        <![endif]-->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" id="menuSuperior">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="jsp"><%=AppInformationHelper.getAppName()%></a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <%if (user != null) {%>
                    <jsp:include page="jsp/menuSuperior.jsp" /> 
                    <% }%>
                    <ul class="nav navbar-nav navbar-right">
                        <jsp:include page="jsp/usuario/infologin.jsp" />
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container -->
        </nav>

        <% if (user != null) {%>

        <div class="container">
            <div class="row">
                <div class="col-md-2" id="menuLateral">
                    <jsp:include page="jsp/menuLateral.jsp" />
                </div>
                <div class="col-md-10">
                    <div id="indexContenido"></div>
                    <div id="indexContenidoJsp">
                        <jsp:include page='<%=(String) request.getAttribute("contenido")%>' />                
                    </div>

                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="contenidoParseado"></div>   
            </div>
            <div class="row">
                <div class="col-md-12"><hr><footer><p>&copy; <%=EstadoHelper.getAutor()%>: <%=EstadoHelper.getMailAutor()%> (<%=EstadoHelper.getAnyo()%>) - <%=EstadoHelper.getLicenciaLink()%></p></footer></div> 
            </div>
        </div>            

        <% } else {%>

        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div id="indexContenidoJsp">
                        <jsp:include page='<%=(String) request.getAttribute("contenido")%>' />                
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="contenidoParseado"></div>   
            </div>
            <div class="row">
                <div class="col-md-12"><hr><footer><p>&copy; <%=EstadoHelper.getAutor()%>: <%=EstadoHelper.getMailAutor()%> (<%=EstadoHelper.getAnyo()%>) - <%=EstadoHelper.getLicenciaLink()%></p></footer></div>   
            </div>
        </div>  


        <% }%>

        <!-- carga de javascript   -->

        <script type="text/javascript"  src="./js/vendor/jquery-1.11.1.min.js"></script>
        <script type="text/javascript"  src="./js/vendor/bootstrap.min.js"></script>
        <script type="text/javascript"  src="./js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>

        <script type="text/javascript"  src="./js/vendor/moment.js"></script>
        <script type="text/javascript"  src="./js/vendor/moment.locale.es.js"></script>
        <script type="text/javascript"  src="./js/vendor/bootstrap-datetimepicker.min.js"></script>

        <script type="text/javascript"  src="./js/vendor/path.min.js"></script> 
        <script type="text/javascript"  src="./js/vendor/bootstrapValidator.min.js"></script>
        <script type="text/javascript"  src="./js/vendor/language/es_ES.js"></script>
        <script type="text/javascript"  src="./js/vendor/creole-parser.js"></script>

        <!--
        <script type="text/javascript"  src="./js/vendor/jquery-ui.js"></script>
        <script src="./js/vendor/jquery.validate.min.js"></script>
        
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.10.2.min.js"><\/script>')</script>
        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
        <script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
        -->


        <script src="js/util.js" charset="UTF-8"></script>
        <script src="js/model.js" charset="UTF-8"></script>
        <script src="js/view.js" charset="UTF-8"></script>
        <script src="js/viewSpecific.js" charset="UTF-8"></script>      
        <script src="js/routes.js" charset="UTF-8"></script>
        <script src="js/param.js" charset="UTF-8"></script>
        <script src="js/ajax.js" charset="UTF-8"></script>
        <script src="js/control.js" charset="UTF-8"></script> 
        <script src="js/initialization.js" charset="UTF-8"></script>


        <script type="text/javascript">

            var path = '<%=request.getContextPath()%>';

            $(document).ready(function() {

                //$('#indexContenidoJsp').addClass('animated slideInDown');
                //$('#menuSuperior').addClass('animated slideInLeft');
                //$('#menuLateral').addClass('animated slideInRight');

                inicializacion();
                do_routes();
                Path.listen();

            });

        </script>
    </body>
</html>

