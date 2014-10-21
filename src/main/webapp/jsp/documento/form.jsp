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

<form class="form-horizontal" role="form" action="#" id="documentoForm" name="formulario">
    <div class="form-group">
        <label class="col-sm-2 control-label" for="id">Id:</label>
        <div class="col-sm-2">
            <input type="text" id="id" class="form-control"  name="id" placeholder="id" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"  for="titulo">Titulo:</label>
        <div class="col-sm-6">
            <input type="text" id="titulo" class="form-control"  name="titulo" size="15" placeholder="titulo" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label" for="contenido">Contenido:</label>
        <div class="col-sm-9">
            <textarea type="text"  class="form-control pull-left"  id="contenido" name="contenido" size="15" placeholder="contenido"></textarea>
        </div>
        <div class="col-sm-1">
            <a class="btn btn-primary btn-sm" id="contenido_button" href="#"><i class="glyphicon glyphicon-pencil"></i></a>
        </div>
    </div>

    
    <div class="form-group">
        <label class="col-sm-2 control-label" for="alta_group">Alta:</label> 
        <div class="col-sm-3">           
            <div class='input-group date' id='alta_group'>
                <input type='text' class="form-control" id='alta' name="alta_group" placeholder="Fecha de alta" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label" for="cambio_group">Cambio:</label> 
        <div class="col-sm-3">      
            <div class='input-group date' id='cambio_group'>
                <input type='text' class="form-control" id='cambio' name="cambio_group" placeholder="Fecha de cambio" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label"  for="hits">Hits:</label>
        <div class="col-sm-2">
            <input type="text"  class="form-control"  id="hits" name="hits" size="15" placeholder="hits" />
        </div>
    </div>



    <div class="form-group">
        <label class="col-sm-2 control-label" for="obj_usuario_id">Usuario: </label> 
        <div class="col-sm-2">              
            <input readonly="true"  class="form-control"  id="obj_usuario_id" class="input-mini" name="id_usuario" type="text" size="5" maxlength="5" />  
        </div>
        <div class="col-sm-1">              
            <a class="btn btn-primary btn-sm" id="obj_usuario_button" href="#"><i class="glyphicon glyphicon-search"></i></a>
        </div>        
        <label class="col-sm-7" for="obj_usuario_desc" id="obj_usuario_desc"></label>                     
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label" for="obj_tipodocumento_id">Tipo de documento: </label> 
        <div class="col-sm-2">              
            <input readonly="true"  class="form-control"  id="obj_tipodocumento_id" class="input-mini" name="id_tipodocumento" type="text" size="5" maxlength="5" />  
        </div>
        <div class="col-sm-1">              
            <a class="btn btn-primary btn-sm" id="obj_tipodocumento_button" href="#"><i class="glyphicon glyphicon-search"></i></a>
        </div>        
        <label class="col-sm-7" for="obj_usuario_desc" id="obj_tipodocumento_desc"></label>                     
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label"  for="etiquetas">Etiquetas:</label>
        <div class="col-sm-10">
            <input type="text"  class="form-control"  id="etiquetas" name="etiquetas" size="15" placeholder="etiquetas" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label"  for="publicado">Publicado:</label>
        <div class="col-sm-1">
            <input type="checkbox" id="publicado" name="publicado" value="true" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"  for="portada">Portada:</label>
        <div class="col-sm-1">
            <input type="checkbox" id="portada" name="portada" value="true" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"  for="destacado">Destacado:</label>
        <div class="col-sm-1">
            <input type="checkbox" id="portada" name="destacado" value="true" />
        </div>
    </div> 

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div id="messages"></div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-primary" id="submitForm">Guardar</button>
        </div>
    </div>

</form>
        

<script type="text/javascript">

    $(document).ready(function() {
        $('#alta_group').datetimepicker({
            pickTime: false,
            language: 'es',
            showToday: true
        });
        $('#cambio_group').datetimepicker({
            pickTime: false,
            language: 'es',
            showToday: true
        });
        //http://jqueryvalidation.org/documentation/
        $('#documentoForm')
                .bootstrapValidator({
                    container: '#messages',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        titulo: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe introducir un título'
                                },
                                stringLength: {
                                    max: 255,
                                    message: 'El título debe tener como máximo 255 caracteres'
                                }
                            }
                        },
                        contenido: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe introducir contenido'
                                }
                            }
                        },
                        alta_group: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe introducir una fecha de alta'
                                },
                                date: {
                                    format: 'DD/MM/YYYY',
                                    message: 'La fecha de alta no tiene formato DD/MM/YYYY'
                                }
                            }
                        },
                        cambio_group: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe introducir una fecha de cambio'
                                },
                                date: {
                                    format: 'DD/MM/YYYY',
                                    message: 'La fecha de cambio no tiene formato DD/MM/YYYY'
                                }
                            }
                        },
                        hits: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe introducir un número de hits'
                                },
                                integer: {
                                    message: 'El valor de hits debe ser un entero'
                                },
                                between: {
                                    min: -0,
                                    max: 99999999,
                                    message: 'El número de hits debe ser un entero entre 0 y 99999999'
                                }
                            }

                        },
                        id_usuario: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe elegir un usuario'
                                },
                                integer: {
                                    message: 'El identificador de usuario debe ser un entero'
                                }
                            }
                        },
                        id_tipodocumento: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe elegir un tipo de documento'
                                },
                                integer: {
                                    message: 'El identificador de tipo de documento debe ser un entero'
                                }
                            }
                        },
                        etiquetas: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe introducir una etiqueta'
                                },
                                stringLength: {
                                    max: 100,
                                    message: 'La longitud de las etiquetas debe ser de 100 caracteres como mucho'
                                }
                            }
                        }

                    }
                })
                .on('change', '[name="id_usuario"]', function() {
                    $('#documentoForm').bootstrapValidator('revalidateField', 'id_usuario');
                })

                .on('change', '[name="id_tipodocumento"]', function() {
                    $('#documentoForm').bootstrapValidator('revalidateField', 'id_tipodocumento');
                })
                ;
        $('#alta_group').on('dp.change dp.show', function(e) {
// Revalidate the date when user change it
            $('#documentoForm').bootstrapValidator('revalidateField', 'alta_group');
        });
        $('#cambio_group').on('dp.change dp.show', function(e) {
// Revalidate the date when user change it
            $('#documentoForm').bootstrapValidator('revalidateField', 'cambio_group');
        });
    });       

    
    
</script>
     