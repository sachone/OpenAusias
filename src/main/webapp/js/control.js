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



var control = function(clase) {


    return {
        getClassName: function() {
            return clase;
        },
        new : function(place) {
            $(place).empty();
            $(place).append(vista(clase).getPanel("Alta de " + model(clase).getName(), vista(clase).getEmptyForm()));
            //id must not be enabled
            $('#id').val('0').attr("disabled", true);
            viewForm(clase).doEventsLoading();
            $('#submitForm').unbind('click');
            $('#submitForm').click(function() {
                viewForm(clase).okValidation(function(e) {
                    resultado = model(clase).saveOne({json: JSON.stringify(viewForm(clase).getFormValues())});
                    vista(clase).doResultOperationNotifyToUser(place, resultado["status"], "Se ha creado el registro con id=" + resultado["message"], resultado["message"], true);
                    e.preventDefault();
                    return false;
                });
            });
        },
        view: function(place, id) {
            $(place).empty();
            var oDocumentoModel = model(clase);
            oDocumentoModel.loadAggregateViewOne(id);
            $(place).append(vista(clase).getPanel("Detalle de " + model(clase).getName(), vista(clase).getObjectTable(oDocumentoModel.getCachedPrettyFieldNames(), oDocumentoModel.getCachedOne(), oDocumentoModel.getCachedFieldNames())));
            $(place).append('<a class="btn btn-primary" href="jsp#/' + model(clase).getName() + '/edit/' + id + '">Editar</a>');
            $(place).append('<a class="btn btn-primary" href="jsp#/' + model(clase).getName() + '/remove/' + id + '">Borrar</a>');
            $(place).append('<a class="btn btn-primary" href="jsp#/' + model(clase).getName() + '/list/' + id + '">Listar</a>');
        },
        edit: function(place, id) {
            $(place).empty();
            $(place).append(vista(clase).getPanel("Edición de " + model(clase).getName(), vista(clase).getEmptyForm()));
            var oDocumentoModel = model(clase);
            oDocumentoModel.loadAggregateViewOne(id);
            viewForm(clase).loadFormValues(oDocumentoModel.getCachedOne(), oDocumentoModel.getCachedFieldNames());
            $('#id').attr("disabled", true);
            viewForm(clase).doEventsLoading();
            $('#submitForm').unbind('click');
            $('#submitForm').click(function() {
                viewForm(clase).okValidation(function(e) {
                    resultado = model(clase).saveOne({json: JSON.stringify(viewForm(clase).getFormValues())});
                    vista(clase).doResultOperationNotifyToUser(place, resultado["status"], "Se ha actualizado el registro con id=" + resultado["message"], resultado["message"], true);
                    e.preventDefault();
                    return false;
                });
            });
        },
        remove: function(place, id) {
            $(place).empty();
            var oDocumentoModel = model(clase);
            oDocumentoModel.loadAggregateViewOne(id);
            $(place).append(vista(clase).getPanel("Borrado de " + model(clase).getName(), vista(clase).getObjectTable(oDocumentoModel.getCachedPrettyFieldNames(), oDocumentoModel.getCachedOne(), oDocumentoModel.getCachedFieldNames())));
            $(place).append('<div id=\"result\">¿Seguro que desea borrar el registro?</div>');
            $(place).append('<a class="btn btn-danger" id="btnBorrarSi" href="#">Sí, borrar</a>');
            $('#btnBorrarSi').unbind('click');
            $('#btnBorrarSi').click(function(event) {
                resultado = model(clase).removeOne(id);
                vista(clase).doResultOperationNotifyToUser(place, resultado["status"], resultado["message"], resultado["message"], false);
                return false;
            });
        },
        list: function(place, objParams, callback) {
            objParams = param().validateUrlObjectParameters(objParams);
            //get all data from server in one http call and store it in cache
            var oDocumentoModel = model(clase);
            oDocumentoModel.loadAggregateViewSome(objParams);
            //get html template from server and show it
            if (callback) {
                $(place).empty().append(vista(clase).getSpinner()).html(vista(clase).getEmptyList());
            } else {
                $(place).empty().append(vista(clase).getSpinner()).html(vista(clase).getPanel("Listado de " + model(clase).getName(), vista(clase).getEmptyList()));
            }
            //show page links pad
            var strUrlFromParamsWithoutPage = param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ["page"]));
            var url = 'jsp#/' + model(clase).getName() + '/list/' + strUrlFromParamsWithoutPage;

            //visible fields select population, setting & event
            $('#selectVisibleFields').empty().populateSelectBox(util().getIntegerArray(1, oDocumentoModel.getCachedCountFields()));
            $('#selectVisibleFields').unbind('change');
            $("#selectVisibleFields").change(function() {
                window.location.href = "jsp#/" + model(clase).getName() + "/list/" + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['vf'])) + "&vf=" + $("#selectVisibleFields option:selected").val();
                return false;
            });
            //show the table
            var fieldNames = oDocumentoModel.getCachedFieldNames();
            if (fieldNames.length < objParams["vf"]) {
                objParams["vf"] = fieldNames.length;
            }
            $("#selectVisibleFields").val(objParams["vf"]);
            var prettyFieldNames = oDocumentoModel.getCachedPrettyFieldNames();
            var strUrlFromParamsWithoutOrder = param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ["order", "ordervalue"]));
            var page = oDocumentoModel.getCachedPage();
            if (parseInt(objParams["page"]) > parseInt(oDocumentoModel.getCachedPages())){
                objParams["page"] = parseInt(oDocumentoModel.getCachedPages());
            }
            $("#pagination").empty().append(vista(clase).getSpinner()).html(vista(clase).getPageLinks(url, parseInt(objParams["page"]), parseInt(oDocumentoModel.getCachedPages()), 2));

            $("#tableHeaders").empty().append(vista(clase).getSpinner()).html(vista(clase).getHeaderPageTable(prettyFieldNames, fieldNames, parseInt(objParams["vf"]), strUrlFromParamsWithoutOrder));
            $("#tableBody").empty().append(vista(clase).getSpinner()).html(function() {
                return vista(clase).getBodyPageTable(page, fieldNames, parseInt(objParams["vf"]), function(id) {
                    if (callback) {
                        var botonera = "";
                        botonera += '<div class="btn-toolbar" role="toolbar"><div class="btn-group btn-group-xs">';
                        botonera += '<a class="btn btn-default selector_button" id="' + id + '"  href="#"><i class="glyphicon glyphicon-ok"></i></a>';
                        botonera += '</div></div>';
                        return botonera;
                    } else {
                        return viewList(clase).loadButtons(id);
                    }
                    //mejor pasar documento como parametro y crear un repo global de código personalizado
                });
            });
            //show information about the query
            $("#registers").empty().append(vista(clase).getSpinner()).html(vista(clase).getRegistersInfo(oDocumentoModel.getCachedRegisters()));
            $("#order").empty().append(vista(clase).getSpinner()).html(vista(clase).getOrderInfo(objParams));
            $("#filter").empty().append(vista(clase).getSpinner()).html(vista(clase).getFilterInfo(objParams));
            //regs per page links
            $('#nrpp').empty().append(vista(clase).getRppLinks(objParams));
            //filter population & event
            $('#selectFilter').empty().populateSelectBox(fieldNames, prettyFieldNames);
            $('#btnFiltrar').unbind('click');
            $("#btnFiltrar").click(function(event) {
                filter = $("#selectFilter option:selected").val();
                filteroperator = $("#selectFilteroperator option:selected").val();
                filtervalue = $("#inputFiltervalue").val();
                window.location.href = 'jsp#/' + model(clase).getName() + '/list/' + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['filter', 'filteroperator', 'filtervalue'])) + "&filter=" + filter + "&filteroperator=" + filteroperator + "&filtervalue=" + filtervalue;
                return false;
            });
        },
        modalListEventsLoading: function(place, objParams, callbackFunction) {
            var thisObject = this;
            $('.pagination_link').unbind('click');
            $('.pagination_link').click(function(event) {
                //rpp = $( "#rpp option:selected").text();
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["page"]);
                objParams["page"] = parseInt($(this).attr('id'));
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                return false;
            });
            //visible fields select population, setting & event
            $('#selectVisibleFields').unbind('change');
            $("#selectVisibleFields").change(function() {
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["vf"]);
                objParams["vf"] = $("#selectVisibleFields option:selected").val();
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                //window.location.href = "jsp#/" + model('documento').getName() + "/list/" + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['vf'])) + "&vf=" + $("#selectVisibleFields option:selected").val();
                return false;
            });
            $('.rpp_link').unbind('click');
            $('.rpp_link').on('click', function(event) {
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["rpp"]);
                objParams["rpp"] = parseInt($(this).attr('id'));
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                //window.location.href = "jsp#/" + model('documento').getName() + "/list/" + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['vf'])) + "&vf=" + $("#selectVisibleFields option:selected").val();
                return false;
                //thisObject.inicia(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, callback, systemfilter, systemfilteroperator, systemfiltervalue);
            });
            //list events
            if (callbackFunction) {
                $('.btn.btn-default.selector_button').unbind('click');
                $('.btn.btn-default.selector_button').click(function(event) {
                    callbackFunction(parseInt($(this).attr('id')))
                });
            } else {
//                $('.btn.btn-default.action01').unbind('click');
//                $('.btn.btn-default.action01').click(function() {
//                    var oDocumentoModel = model('documento');
//                    oDocumentoModel.loadAggregateViewOne($(this).attr('id'));
//                    var content = vista('documento').getObjectTable(oDocumentoModel.getCachedPrettyFieldNames(), oDocumentoModel.getCachedOne(), oDocumentoModel.getCachedFieldNames());
//                    loadModalView('#modal01', $(this).attr('id'), model('documento').getName(), content);
//                    return false;
//                });
//                $('.btn.btn-default.action02').unbind('click');
//                $('.btn.btn-default.action02').click(function() {
//                    //edit
//                    return false;
//                });
//
//                $('.btn.btn-default.action03').unbind('click');
//                $('.btn.btn-default.action03').click(function() {
//                    //delete
//                    return false;
//                });

            }

            $('#btnFiltrar').unbind('click');
            $("#btnFiltrar").click(function(event) {
//                filter = $("#selectFilter option:selected").val();
//                filteroperator = $("#selectFilteroperator option:selected").val();
//                filtervalue = $("#inputFiltervalue").val();
//                window.location.href = 'jsp#/' + model('documento').getName() + '/list/' + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['filter', 'filteroperator', 'filtervalue'])) + "&filter=" + filter + "&filteroperator=" + filteroperator + "&filtervalue=" + filtervalue;
//                return false;
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["filter", "filteroperator", "filtervalue"]);
                objParams["filter"] = $("#selectFilter option:selected").val();
                objParams["filteroperator"] = $("#selectFilteroperator option:selected").val();
                objParams["filtervalue"] = $("#inputFiltervalue").val();
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                //window.location.href = "jsp#/" + model('documento').getName() + "/list/" + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['vf'])) + "&vf=" + $("#selectVisibleFields option:selected").val();
                return false;
            });

            $('.orderAsc').unbind('click');
            $('.orderAsc').on('click', function(event) {
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["order", "ordervalue"]);
                objParams["order"] = $(this).attr('id');
                objParams["ordervalue"] = "asc";
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                //window.location.href = "jsp#/" + model('documento').getName() + "/list/" + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['vf'])) + "&vf=" + $("#selectVisibleFields option:selected").val();
                return false;
                //thisObject.inicia(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, callback, systemfilter, systemfilteroperator, systemfiltervalue);
            });

            $('.orderDesc').unbind('click');
            $('.orderDesc').on('click', function(event) {
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["order", "ordervalue"]);
                objParams["order"] = $(this).attr('id');
                objParams["ordervalue"] = "desc";
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                //window.location.href = "jsp#/" + model('documento').getName() + "/list/" + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ['vf'])) + "&vf=" + $("#selectVisibleFields option:selected").val();
                return false;
                //thisObject.inicia(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, callback, systemfilter, systemfilteroperator, systemfiltervalue);
            });
            $('#linkQuitarOrden').unbind('click');
            $('#linkQuitarOrden').click(function() {
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["order", "ordervalue"]);
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                return false;
            });
            $('#linkQuitarFiltro').unbind('click');
            $('#linkQuitarFiltro').click(function() {
                objParams = param().getUrlObjectFromParamsWithoutParamArray(objParams, ["filter", "filteroperator", "filtervalue"]);
                thisObject.list(place, objParams, callbackFunction);
                thisObject.modalListEventsLoading(place, objParams, callbackFunction);
                return false;
            });
        }
    };
};
