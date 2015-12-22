/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sebastian
 */
@Embeddable
public class EdicionFormularioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private int usuarioidUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Formulario_NUE")
    private int formularioNUE;

    public EdicionFormularioPK() {
    }

    public EdicionFormularioPK(int usuarioidUsuario, int formularioNUE) {
        this.usuarioidUsuario = usuarioidUsuario;
        this.formularioNUE = formularioNUE;
    }

    public int getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(int usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getFormularioNUE() {
        return formularioNUE;
    }

    public void setFormularioNUE(int formularioNUE) {
        this.formularioNUE = formularioNUE;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioidUsuario;
        hash += (int) formularioNUE;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EdicionFormularioPK)) {
            return false;
        }
        EdicionFormularioPK other = (EdicionFormularioPK) object;
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        if (this.formularioNUE != other.formularioNUE) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EdicionFormularioPK[ usuarioidUsuario=" + usuarioidUsuario + ", formularioNUE=" + formularioNUE + " ]";
    }
    
}
