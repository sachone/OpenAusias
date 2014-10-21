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

var model = function(clase) {
    //contexto privado
    var urlJson = path + '/json?ob=' + clase;
    var urlJsp = path + '/jsp?ob=' + clase;
    //cache vars
    var cFieldNames = null;
    var cPrettyFieldNames = null;
    var cPrettyFieldNamesAcciones = null;
    var cCountFields = null;
    var cPage = null;
    var cPages = null;
    var cRegisters = null;
    var cOne = null;
    return {
        //contexto público (interface)
        getName: function() {
            return clase;
        },
        getPath: function() {
            return path;
        },
        getUrlJson: function() {
            return urlJson;
        },
        getUrlJsp: function() {
            return urlJsp;
        },
        //--------------------
        loadAggregateViewSome: function(objParams) {
            if (objParams.order) {
                orderParams = '&order=' + objParams.order + '&ordervalue=' + objParams.ordervalue;
            } else {
                orderParams = "";
            }
            if (objParams.filter) {
                filterParams = "&filter=" + objParams.filter + "&filteroperator=" + objParams.filteroperator + "&filtervalue=" + objParams.filtervalue;
            } else {
                filterParams = "";
            }
            if (objParams.systemfilter) {
                systemfilterParams = "&systemfilter=" + objParams.systemfilter + "&systemfilteroperator=" + objParams.systemfilteroperator + "&systemfiltervalue=" + objParams.systemfiltervalue;
            } else {
                systemfilterParams = "";
            }
            $.when(ajax().ajaxCallSync(urlJson + '&op=getaggregateviewsome' + filterParams + '&rpp=' + objParams.rpp + orderParams + '&page=' + objParams.page + systemfilterParams, 'GET', '')).done(function(data) {
                pagina_objs = data; //decodeURI htmlDecode
            });
            //return pagina_objs;
            cFieldNames = pagina_objs.data.columns;
            cCountFields = cFieldNames.length;
            cPrettyFieldNames = pagina_objs.data.prettyColumns;
            cPrettyFieldNamesAcciones = cPrettyFieldNames;
            cPrettyFieldNamesAcciones.push("acciones");
            cPage = pagina_objs.data.page.list;
            cPages = pagina_objs.data.pages.data;
            cRegisters = pagina_objs.data.registers.data;
        },
        loadAggregateViewOne: function(id1) {
            $.when(ajax().ajaxCallSync(urlJson + '&op=getaggregateviewone&id=' + id1, 'GET', '')).done(function(data) {
                pagina_objs = data;
            });
            cFieldNames = pagina_objs.data.columns;
            cCountFields = cFieldNames.length;
            cPrettyFieldNames = pagina_objs.data.prettyColumns;
            cOne = pagina_objs.data.data;
        },
        getCachedOne: function() {
            return cOne;
        },
        getCachedPrettyFieldNames: function() {
            return cPrettyFieldNames;
        },
        getCachedPrettyFieldNamesAcciones: function() {
            return cPrettyFieldNamesAcciones;
        },
        getCachedCountFields: function() {
            return cCountFields;
        },
        getCachedFieldNames: function() {
            return cFieldNames;
        },
        getCachedPage: function() {
            return cPage;
        },
        getCachedPages: function() {
            return cPages;
        },
        getCachedRegisters: function() {
            return cRegisters;
        },
        //--------------------------------------------------
        validateUser: function(user, password) {
            $.when(ajax().ajaxCallSync(urlJson + '&op=validate&user=' + user + "&password=" + password, 'GET', '')).done(function(data) {
                resultado = data['data'];
            });
            return resultado;
        },
        getGenericOperation: function(operation, id) {
            $.when(ajax().ajaxCallSync(urlJson + '&op=' + operation + "&id=" + id, 'GET', '')).done(function(data) {
                resultado = data['data'];
            });
            return resultado;
        },
        getPrettyFieldNames: function() {
            $.when(ajax().ajaxCallSync(urlJson + '&op=getprettycolumns', 'GET', '')).done(function(data) {
                prettyFieldNames = data;
                cPrettyFieldNames = data;
            });
            return prettyFieldNames;
        },
        getPrettyFieldNamesAcciones: function() {
            $.when(ajax().ajaxCallSync(urlJson + '&op=getprettycolumns', 'GET', '')).done(function(data) {
                prettyFieldNames = data;
                prettyFieldNames.push("acciones");
                cPrettyFieldNamesAcciones = prettyFieldNames;
                //prettyFieldNames = data['data'];
                //prettyFieldNames.push("acciones");
            });
            return prettyFieldNames;
        },
        getCountFields: function() {
            $.when(ajax().ajaxCallSync(urlJson + '&op=getcolumns', 'GET', '')).done(function(data) {
                //countFields = data['data'].length;
                countFields = data.length;
                cCountFields = countFields;
            });
            return countFields;
        },
        getFieldNames: function() {
            $.when(ajax().ajaxCallSync(urlJson + '&op=getcolumns', 'GET', '')).done(function(data) {
                //fieldNames = data['data'];
                dataFieldNames = data;
                cFieldNames = dataFieldNames;
            });
            return dataFieldNames;
        },
        getMeAsAForeignKey:function(id) {
            //returns the description of this object to be displayed as a foreign key
            return util().getForeign(this.getOne(id));
        },
        getForeignKey: function(id, campo) {
            registro = this.getOne(id);
            idAjena = registro[campo];
            var modelForeign = campo.split("_")[1].replace(/[0-9]*$/, "");
            var foreignObject = model(modelForeign, path);
            var registroForeign = foreignObject.getOne(idAjena);
            return registroForeign;
        },
        getPage: function(pagina, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
            if (order) {
                orderParams = '&order=' + order + '&ordervalue=' + ordervalue;
            } else {
                orderParams = "";
            }
            if (filter) {
                filterParams = "&filter=" + filter + "&filteroperator=" + filteroperator + "&filtervalue=" + filtervalue;
            } else {
                filterParams = "";
            }
            if (systemfilter) {
                systemfilterParams = "&systemfilter=" + systemfilter + "&systemfilteroperator=" + systemfilteroperator + "&systemfiltervalue=" + systemfiltervalue;
            } else {
                systemfilterParams = "";
            }
            $.when(ajax().ajaxCallSync(urlJson + '&op=getpage' + filterParams + '&rpp=' + rpp + orderParams + '&page=' + pagina + systemfilterParams, 'GET', '')).done(function(data) {
                pagina_objs = data.list; //decodeURI htmlDecode
                cPage = data.list;
            });
            return pagina_objs;
        },
        getPages: function(rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
            if (filter) {
                filterParams = "&filter=" + filter + "&filteroperator=" + filteroperator + "&filtervalue=" + filtervalue;
            } else {
                filterParams = "";
            }
            if (systemfilter) {
                systemfilterParams = "&systemfilter=" + systemfilter + "&systemfilteroperator=" + systemfilteroperator + "&systemfiltervalue=" + systemfiltervalue;
            } else {
                systemfilterParams = "";
            }
            $.when(ajax().ajaxCallSync(urlJson + '&op=getpages' + filterParams + '&rpp=' + rpp + systemfilterParams, 'GET', '')).done(function(data) {
                pages = data['data'];
                cPages = data['data'];
            });
            return pages;
        },
        getRegisters: function(filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {

            if (filter) {
                filterParams = "&filter=" + filter + "&filteroperator=" + filteroperator + "&filtervalue=" + filtervalue;
            } else {
                filterParams = "";
            }
            if (systemfilter) {
                systemfilterParams = "&systemfilter=" + systemfilter + "&systemfilteroperator=" + systemfilteroperator + "&systemfiltervalue=" + systemfiltervalue;
            } else {
                systemfilterParams = "";
            }
            $.when(ajax().ajaxCallSync(urlJson + '&op=getregisters' + filterParams + systemfilterParams, 'GET', '')).done(function(data) {
                regs = data['data'];
                cRegisters = data['data'];
            });

            return regs;
        },
        getAll: function() {
            $.when(ajax().ajaxCallSync(urlJson + '&op=getall', 'GET', '')).done(function(data) {
                one = data;
            });
            return one;
        },
        getOne: function(id1) {
            $.when(ajax().ajaxCallSync(urlJson + '&op=get&id=' + id1, 'GET', '')).done(function(data) {
                one = data;
            });
            return one;
        },
        saveOne: function(jsonfile) {
            $.when(ajax().ajaxCallSync(urlJson + '&op=save', 'GET', jsonfile)).done(function(data) {
                feedback = data;
            });
            return feedback;
        },
        removeOne: function(id) {
            $.when(ajax().ajaxCallSync(urlJson + '&op=remove&id=' + id, 'GET', '')).done(function(data) {
                feedback = data;
            });
            return feedback;
        }
    };
};
