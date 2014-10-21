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


//http://bootstrapvalidator.com/
//https://github.com/Eonasdan/bootstrap-datetimepicker 

var viewForm = function(clase) {
    return {
        loadFormValues: function(valores, campos) {
            switch (clase) {
                case 'documento':
//                    $('#documento_form #titulo').val(valores['titulo']);
//                    $('#documento_form #contenido').val(valores['contenido']);
//                    $('#documento_form #alta').val(valores['alta']);
//                    $('#documento_form #cambio').val(valores['cambio']);
//                    $('#documento_form #hits').val(valores['hits']);
//                    $('#documento_form #id_usuario').val(valores['id_usuario']);
//                    $('#documento_form #etiquetas').val(valores['etiquetas']);
//                    $('#documento_form #publicado').val(valores['publicado']);
//                    $('#documento_form #portada').val(valores['portada']);

                    vista(clase).doFillForm(valores, campos);
                    break;
                default:
                    vista(clase).doFillForm(valores, campos);
                    break;
            }
        },
        getFormValues: function() {
            switch (clase) {
                case 'documento':
                    var valores = [];
//                    valores['titulo'] = $('#documento_form #titulo');
//                    valores['contenido'] = $('#documento_form #contenido');
//                    valores['alta'] = $('#documento_form #alta');
//                    valores['cambio'] = $('#documento_form #cambio');
//                    valores['hits'] = $('#documento_form #hits');
//                    valores['id_usuario'] = $('#documento_form #id_usuario');
//                    valores['etiquetas'] = $('#documento_form #etiquetas');
//                    valores['publicado'] = $('#documento_form #publicado');
//                    valores['portada'] = $('#documento_form #portada');

                    var disabled = $('#documentoForm').find(':input:disabled').removeAttr('disabled');
                    valores = $('#documentoForm').serializeObject();
                    disabled.attr('disabled', 'disabled');
                    return valores;
                    break;
                default:
                    var valores = [];
                    var disabled = $('#' + clase + 'Form').find(':input:disabled').removeAttr('disabled');
                    valores = $('#' + clase + 'Form').serializeObject();
                    disabled.attr('disabled', 'disabled');
                    return valores;
                    break;
            }
        },
        doEventsLoading: function() {
            switch (clase) {
                case 'documento':
                    $('#documentoForm #obj_usuario_button').unbind('click');
                    $("#documentoForm #obj_usuario_button").click(function() {
                        var oControl = control('documento');  //para probar dejar documento
                        //vista('usuario').cargaModalBuscarClaveAjena('#modal01', "documento");

                        $("#documentoForm").append(vista(clase).getEmptyModal());
                        util().loadForm('#modal01', vista('usuario').getFormHeader('Elección de usuario'), "", vista('usuario').getFormFooter(), true);

                        $('#documentoForm').append(vista('documento').getEmptyModal());

                        oControl.list('#modal01 #modal-body', param().defaultizeUrlObjectParameters({}), true);
                        oControl.modalListEventsLoading('#modal01 #modal-body', param().defaultizeUrlObjectParameters({}), function(id) {
                            $('#obj_usuario_id').val(id).change();
                            $('#obj_usuario_desc').text(decodeURIComponent(model('usuario').getMeAsAForeignKey(id)));
                            $('#modal01').modal('hide');

                        });
                        return false;
                    });
                    $('#documentoForm #obj_tipodocumento_button').unbind('click');
                    $("#documentoForm #obj_tipodocumento_button").click(function() {
                        var oControl = control('documento');

                        $("#documentoForm").append(vista(clase).getEmptyModal());
                        util().loadForm('#modal01', vista('tipodocumento').getFormHeader('Elección de tipo de documento'), "", vista('tipodocumento').getFormFooter(), true);

                        $('#documentoForm').append(vista('documento').getEmptyModal());

                        oControl.list('#modal01 #modal-body', param().defaultizeUrlObjectParameters({}), true);
                        oControl.modalListEventsLoading('#modal01 #modal-body', param().defaultizeUrlObjectParameters({}), function(id) {
                            $('#obj_tipodocumento_id').val(id).change();
                            $('#obj_tipodocumento_desc').text(decodeURIComponent(model('tipodocumento').getMeAsAForeignKey(id)));
                            $('#modal01').modal('hide');

                        });
                        return false;
                    });
                    $('#contenido_button').unbind('click');
                    $('#contenido_button').click(function() {
                        //cabecera = '<button id="full-width" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' + '<h3 id="myModalLabel">Edición de contenidos</h3>';
                        cabecera = vista('documento').getFormHeader('Edición de contenidos');
                        //pie = '<button type="button" class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cerrar</button>';                        
                        pie = '<a class="btn btn-primary" href="http://creoleparser.googlecode.com/svn/trunk/creoleparser/test_pages/CheatSheetPlus.html">Sintaxis</a>';
                        pie += vista('documento').getFormFooter();
                        contenido = '<div class="row"><div class="col-md-6">';
                        contenido += '<textarea type="text" id="contenidomodal" name="contenido" rows="20" cols="70" placeholder="contenido"></textarea>';
                        contenido += '</div><div class="col-md-6"><div id="textoparseado"></div></div>';
                        contenido += '</div>';

                        $('#documentoForm').append(vista('documento').getEmptyModal());

                        util().loadForm('#modal01', cabecera, contenido, pie, true);
                        var texto = $('#contenido').val();
                        $('#contenidomodal').val(texto);
                        util().creoleParse(texto, $('#textoparseado'));
                        $('#contenido').val($('#contenidomodal').val());
                        $('#contenidomodal').keyup(function() {
                            util().creoleParse($('#contenidomodal').val(), $('#textoparseado'));
                            $('#contenido').val($('#contenidomodal').val());
                        });
                        return false;
                    });
                    break;
                default:
                    break;
            }
        },
        okValidation: function(f) {
            switch (clase) {
                case 'documento':
                    $('#documentoForm').on('success.form.bv', f);
                    break;
                default:
                    $('#' + clase + 'Form').on('success.form.bv', f);
                    break;
            }
        }
    }
}
var viewList = function(clase) {
    return {
        loadButtons: function(id) {
            switch (clase) {
                case 'documento':
                    var botonera = "";
                    botonera += '<div class="btn-toolbar" role="toolbar"><div class="btn-group btn-group-xs">';
                    botonera += '<a class="btn btn-default view" id="' + id + '"  href="jsp#/' + model('documento').getName() + '/view/' + id + '"><i class="glyphicon glyphicon-eye-open"></i></a>';
                    botonera += '<a class="btn btn-default edit" id="' + id + '"  href="jsp#/' + model('documento').getName() + '/edit/' + id + '"><i class="glyphicon glyphicon-pencil"></i></a>';
                    botonera += '<a class="btn btn-default remove" id="' + id + '"  href="jsp#/' + model('documento').getName() + '/remove/' + id + '"><i class="glyphicon glyphicon-remove"></i></a>';
                    botonera += '</div></div>';
                    return botonera;
                    break;
                default:
                    var botonera = "";
                    botonera += '<div class="btn-toolbar" role="toolbar"><div class="btn-group btn-group-xs">';
                    botonera += '<a class="btn btn-default view" id="' + id + '"  href="jsp#/' + model(clase).getName() + '/view/' + id + '"><i class="glyphicon glyphicon-eye-open"></i></a>';
                    botonera += '<a class="btn btn-default edit" id="' + id + '"  href="jsp#/' + model(clase).getName() + '/edit/' + id + '"><i class="glyphicon glyphicon-pencil"></i></a>';
                    botonera += '<a class="btn btn-default remove" id="' + id + '"  href="jsp#/' + model(clase).getName() + '/remove/' + id + '"><i class="glyphicon glyphicon-remove"></i></a>';
                    botonera += '</div></div>';
                    return botonera;
                    break;
            }
        }
    }
}
