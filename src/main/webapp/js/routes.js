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


function do_routes() {

    Path.map("#/documento").to(function() {
        $('#indexContenidoJsp').spinner();
        control('documento').list($('#indexContenido'), param().defaultizeUrlObjectParameters({}), null);
        //documentoControl.modalListEventsLoading(documentoObject, documentoView, $('#indexContenido'), param().defaultizeUrlObjectParameters({}), null);        
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/documento/list/:url").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('documento').list($('#indexContenido'), paramsObject, null);
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/documento/view/:id").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('documento').view($('#indexContenido'), paramsObject['id']);
        $('#indexContenidoJsp').empty();

        return false;
    });

    Path.map("#/documento/edit/:id").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('documento').edit($('#indexContenido'), paramsObject['id']);
        $('#indexContenidoJsp').empty();
    });

    Path.map("#/documento/new").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('documento').new($('#indexContenido'));
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/documento/remove/:id").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('documento').remove($('#indexContenido'), paramsObject['id']);
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/arma").to(function() {
        $('#indexContenidoJsp').spinner();
        control('arma').list($('#indexContenido'), param().defaultizeUrlObjectParameters({}), null);
        //documentoControl.modalListEventsLoading(documentoObject, documentoView, $('#indexContenido'), param().defaultizeUrlObjectParameters({}), null);        
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/arma/list/:url").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('arma').list($('#indexContenido'), paramsObject, null);
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/arma/view/:id").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('arma').view($('#indexContenido'), paramsObject['id']);
        $('#indexContenidoJsp').empty();

        return false;
    });

    Path.map("#/arma/edit/:id").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('arma').edit($('#indexContenido'), paramsObject['id']);
        $('#indexContenidoJsp').empty();
    });

    Path.map("#/arma/new").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('arma').new($('#indexContenido'));
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/ordenador").to(function() {
        $('#indexContenidoJsp').spinner();
        control('ordenador').list($('#indexContenido'), param().defaultizeUrlObjectParameters({}), null);
        //documentoControl.modalListEventsLoading(documentoObject, documentoView, $('#indexContenido'), param().defaultizeUrlObjectParameters({}), null);        
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/ordenador/list/:url").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('ordenador').list($('#indexContenido'), paramsObject, null);
        $('#indexContenidoJsp').empty();
        return false;
    });

    Path.map("#/ordenador/view/:id").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('ordenador').view($('#indexContenido'), paramsObject['id']);
        $('#indexContenidoJsp').empty();

        return false;
    });

    Path.map("#/ordenador/edit/:id").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('ordenador').edit($('#indexContenido'), paramsObject['id']);
        $('#indexContenidoJsp').empty();
    });

    Path.map("#/ordenador/new").to(function() {
        $('#indexContenidoJsp').spinner();
        var paramsObject = param().defaultizeUrlObjectParameters(param().getUrlObjectFromUrlString(this.params['url']));
        control('ordenador').new($('#indexContenido'));
        $('#indexContenidoJsp').empty();
        return false;
    });

}
;
