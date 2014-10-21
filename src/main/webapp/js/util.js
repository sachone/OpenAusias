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

var util = function() {
    var spinner = "";
    return {
        setSpinner: function(htmlSpinner) {
            spinner = htmlSpinner;
        },
        getSpinner: function() {
            return '<img src="images/ajax-loading.gif" alt="cargando..." />';
        },
//        getSpinner2: function() {
//            return '<img src="images/ajax-loading.gif" alt="cargando..." />';
//        },
        getForeign: function(objForeign) {
            //falta organizar con metadatos para mostrar sólo los campos relevantes
            var numKeys = Object.keys(objForeign).length;
            var strResult = "";
            for (counter = 0; counter < numKeys - 1; counter++) {
                valor = objForeign[Object.keys(objForeign)[counter]];
                if (valor != true && valor != false)
                    strResult += " " + valor;
            }
            //if (typeof fieldContent == "string") {
            if (strResult.length > 50) //don't show too long fields
                strResult = strResult.substr(0, 20) + " ...";
            return strResult;
        },
        loadForm: function(modalName, headerData, bodyData, footerData, keyb) {
            $(modalName + ' .modal-header').empty().append(headerData);
            $(modalName + ' .modal-body').empty().append(bodyData);
            $(modalName + ' .modal-footer').empty().append(footerData);
            $(modalName).modal({
                keyboard: keyb
            })
        },
        getIntegerArray: function(min, max) {
            var iArray = [];
            for (var counter = min; counter <= max; counter++) {
                iArray.push(counter);
            }
            return iArray;
        },
        htmlEncode: function(value) {
//create a in-memory div, set it's inner text(which jQuery automatically encodes)
//then grab the encoded contents back out.  The div never exists on the page.
            return $('<div/>').text(value).html();
        },
        htmlDecode: function(value) {
            return $('<div/>').html(value).text();
        },
        linkBack: function() {
            history.back();
            return false;
        },
        createGhostDiv: function(id, data) {
            var divContainer = $('<div>').attr({
                id: id
            });
            $('body').append(divContainer);
            $('#' + id).append(data);
        },
        replaceAll: function(str, search, rpl) {
            return str.split(search).join(rpl);
        },
        creoleParse: function(content, lugar) {
            var div = document.createElement('div');
            div.innerHTML = "";
            creole.parse(div, content);
            //var tablas = div.getElementsByTagName('table');
            //for (var i = 0; i < tablas.length; i++) {
            //    tablas[i].className = 'table table-bordered';
            //}                    

            lugar.empty().html(div);
            var codigo = lugar.html();
            codigo = codigo.replace("<table>", '<table class="table table-bordered"><tbody>');
            codigo = codigo.replace("</table>", '</tbody></table>');
            lugar.empty().append(codigo);
        }
    }
}

