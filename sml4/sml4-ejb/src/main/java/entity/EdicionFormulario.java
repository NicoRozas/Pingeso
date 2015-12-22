/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sebastian
 */
@Entity
@Table(name = "Edicion_Formulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EdicionFormulario.findAll", query = "SELECT e FROM EdicionFormulario e"),
    @NamedQuery(name = "EdicionFormulario.findByUsuarioidUsuario", query = "SELECT e FROM EdicionFormulario e WHERE e.edicionFormularioPK.usuarioidUsuario = :usuarioidUsuario"),
    @NamedQuery(name = "EdicionFormulario.findByFormularioNUE", query = "SELECT e FROM EdicionFormulario e WHERE e.edicionFormularioPK.formularioNUE = :formularioNUE"),
    @NamedQuery(name = "EdicionFormulario.findByFechaEdicion", query = "SELECT e FROM EdicionFormulario e WHERE e.fechaEdicion = :fechaEdicion"),
    @NamedQuery(name = "EdicionFormulario.findByObservaciones", query = "SELECT e FROM EdicionFormulario e WHERE e.observaciones = :observaciones")})
public class EdicionFormulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EdicionFormularioPK edicionFormularioPK;
    @Column(name = "fechaEdicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 400)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "Formulario_NUE", referencedColumnName = "NUE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Formulario formulario;

    public EdicionFormulario() {
    }

    public EdicionFormulario(EdicionFormularioPK edicionFormularioPK) {
        this.edicionFormularioPK = edicionFormularioPK;
    }

    public EdicionFormulario(int usuarioidUsuario, int formularioNUE) {
        this.edicionFormularioPK = new EdicionFormularioPK(usuarioidUsuario, formularioNUE);
    }

    public EdicionFormularioPK getEdicionFormularioPK() {
        return edicionFormularioPK;
    }

    public void setEdicionFormularioPK(EdicionFormularioPK edicionFormularioPK) {
        this.edicionFormularioPK = edicionFormularioPK;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (edicionFormularioPK != null ? edicionFormularioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EdicionFormulario)) {
            return false;
        }
        EdicionFormulario other = (EdicionFormulario) object;
        if ((this.edicionFormularioPK == null && other.edicionFormularioPK != null) || (this.edicionFormularioPK != null && !this.edicionFormularioPK.equals(other.edicionFormularioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EdicionFormulario[ edicionFormularioPK=" + edicionFormularioPK + " ]";
    }
    
}
