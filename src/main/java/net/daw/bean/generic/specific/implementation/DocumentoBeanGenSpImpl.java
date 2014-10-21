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
package net.daw.bean.generic.specific.implementation;

import net.daw.bean.generic.implementation.BeanGenImpl;
import net.daw.bean.publicinterface.BeanInterface;
import com.google.gson.annotations.Expose;
import java.util.Date;

public class DocumentoBeanGenSpImpl extends BeanGenImpl implements BeanInterface {

    public DocumentoBeanGenSpImpl() {
    }

    public DocumentoBeanGenSpImpl(Integer id) {
        super(id);
    }

    @Expose
    private String titulo = "";
    //private String presentacion = "";
    @Expose
    private String contenido = "";
    @Expose
    private Date alta = new Date();
    @Expose
    private Date cambio = new Date();
    @Expose
    private Integer hits = 0;
    @Expose(serialize = false)
    private Integer id_usuario = 0; //importante inicializar a 0 las claves ajenas
    @Expose(deserialize = false)
    private UsuarioBeanGenSpImpl obj_usuario = null;
    @Expose(serialize = false)
    private Integer id_tipodocumento = 0; //importante inicializar a 0 las claves ajenas
    @Expose(deserialize = false)
    private TipodocumentoBeanGenSpImpl obj_tipodocumento = null;
    @Expose
    private String etiquetas = "";
    @Expose
    private Boolean publicado = false;
    @Expose
    private Boolean portada = false;
    @Expose
    private Boolean destacado = false;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) throws Exception {
        this.contenido = contenido;
    }

//     public String getPresentacion() {
//        return presentacion;
//    }
//
//    public void setPresentacion(String contenidoParseado) {
//        this.presentacion = contenidoParseado;
//    }
    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date fecha) {
        this.alta = fecha;
    }

    public Date getCambio() {
        return cambio;
    }

    public void setCambio(Date fecha) {
        this.cambio = fecha;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_tipodocumento() {
        return id_tipodocumento;
    }

    public void setId_tipodocumento(Integer id_tipodocumento) {
        this.id_tipodocumento = id_tipodocumento;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    public Boolean getPortada() {
        return portada;
    }

    public void setPortada(Boolean portada) {
        this.portada = portada;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public UsuarioBeanGenSpImpl getObj_usuario() {
        return obj_usuario;
    }

    public void setObj_usuario(UsuarioBeanGenSpImpl obj_usuario) {
        this.obj_usuario = obj_usuario;
    }

    public TipodocumentoBeanGenSpImpl getObj_tipodocumento() {
        return obj_tipodocumento;
    }

    public void setObj_tipodocumento(TipodocumentoBeanGenSpImpl obj_tipodocumento) {
        this.obj_tipodocumento = obj_tipodocumento;
    }

}
