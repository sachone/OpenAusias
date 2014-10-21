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

<form class="form-horizontal" action="#" id="formulario" name="formulario">
    <div class="control-group">
        <label class="control-label" for="inputId">Id:</label>
        <div class="controls">
            <input type="text" id="id" name="id" placeholder="id"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"  for="inputLogin">Login:</label>
        <div class="controls">
            <input type="text" id="login" name="login" size="15" placeholder="login" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"  for="inputPassword">Password:</label>
        <div class="controls">
            <input type="password" id="password" name="password" size="15" placeholder="password" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"  for="inputPasswordRepite">Repite Password:</label>
        <div class="controls">
            <input type="password" id="passwordRepite" name="passwordRepite" size="15" placeholder="repitePassword" />
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" id="submitForm" class="btn submitForm">Submit</button>
        </div>
    </div>
</form>

