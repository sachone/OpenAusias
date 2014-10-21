<%-- 
    Document   : form
    Created on : 17-ene-2014, 11:57:50
    Author     : al037877
--%>

<form class="form-horizontal" action="#" id="formulario" name="formulario">
    <h2>Tipodocumento</h2>
    <div class="control-group">
        <label class="control-label" for="inputId">Id:</label>
        <div class="controls">
            <input type="text" id="id" name="id" placeholder="id" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"  for="inputNombre">Descripcion:</label>
        <div class="controls">
            <input type="text" id="descripcion" name="descripcion" size="15" placeholder="Descripcion" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="inputApe1" >Privado:</label>
        <div class="controls">
            <input type="checkbox" id="privado" name="privado" value="true" />
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" id="submitForm" class="btn submitForm">Submit</button>
        </div>
    </div>
</form>