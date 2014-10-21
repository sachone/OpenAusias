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

function inicializacion() {
    creole = new Parse.Simple.Creole({
        forIE: document.all,
        interwiki: {
            WikiCreole: 'http://www.wikicreole.org/wiki/',
            Wikipedia: 'http://en.wikipedia.org/wiki/'
        },
        linkFormat: ''
    });
    $(document).on('show', '.modal', function() {
        $(document).off('focusin.modal');
    });
    $.when(ajax().ajaxCallSync('images/ajax-loading.gif', 'GET', '')).done(function() {
        util().setSpinner('<img src="images/ajax-loading.gif" alt="cargando..." />');
    })
    $.fn.serializeObject = function()
    {
        // http://jsfiddle.net/davidhong/gP9bh/
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = encodeURIComponent(this.value) || '';
            }
        });
        return o;
    };
    $.fn.populateSelectBox = function(values, captions) {
        var combo = $(this);
        if (combo.is("select")) {
            $.each(values, function(index) {
                if (typeof captions === "undefined")
                    combo.append($("<option />").val(this).text(this));
                else
                    combo.append($("<option />").val(this).text(captions[index]));
            });
        }
    };
    //load spinner
//    var img = $('<img alt="cargando..." / >').attr('src', "images/ajax-loading.gif").load(function() {
//        if (!this.complete || typeof this.naturalWidth == "undefined" || this.naturalWidth == 0) {
//            spinner = '<h3>Loading...</h3>';
//        } else {
//            spinner = '<img src="images/ajax-loading.gif" alt="cargando..." />';
//        }
//    });

    //load spinner

    $.fn.spinner = function() {
        $(this).html(util().getSpinner());
    };
}

