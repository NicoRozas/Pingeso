/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aracelly
 */
@Entity
@Table(name = "formulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulario.findAll", query = "SELECT f FROM Formulario f"),
    @NamedQuery(name = "Formulario.findByNue", query = "SELECT f FROM Formulario f WHERE f.nue = :nue"),
    @NamedQuery(name = "Formulario.findByFechaIngreso", query = "SELECT f FROM Formulario f WHERE f.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Formulario.findByRuc", query = "SELECT f FROM Formulario f WHERE f.ruc = :ruc"),
    @NamedQuery(name = "Formulario.findByRit", query = "SELECT f FROM Formulario f WHERE f.rit = :rit"),
    @NamedQuery(name = "Formulario.findByDireccionFotografia", query = "SELECT f FROM Formulario f WHERE f.direccionFotografia = :direccionFotografia"),
    @NamedQuery(name = "Formulario.findByFechaOcurrido", query = "SELECT f FROM Formulario f WHERE f.fechaOcurrido = :fechaOcurrido"),
    @NamedQuery(name = "Formulario.findByLugarLevantamiento", query = "SELECT f FROM Formulario f WHERE f.lugarLevantamiento = :lugarLevantamiento"),
    @NamedQuery(name = "Formulario.findByNumeroParte", query = "SELECT f FROM Formulario f WHERE f.numeroParte = :numeroParte"),
    @NamedQuery(name = "Formulario.findByUnidadPolicial", query = "SELECT f FROM Formulario f WHERE f.unidadPolicial = :unidadPolicial"),
    @NamedQuery(name = "Formulario.findByObservaciones", query = "SELECT f FROM Formulario f WHERE f.observaciones = :observaciones"),
    @NamedQuery(name = "Formulario.findByDireccionSS", query = "SELECT f FROM Formulario f WHERE f.direccionSS = :direccionSS")})
public class Formulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nue")
    private Integer nue;
    @Column(name = "fechaIngreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Size(max = 45)
    @Column(name = "RUC")
    private String ruc;
    @Size(max = 45)
    @Column(name = "RIT")
    private String rit;
    @Size(max = 45)
    @Column(name = "direccionFotografia")
    private String direccionFotografia;
    @Column(name = "fechaOcurrido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOcurrido;
    @Size(max = 45)
    @Column(name = "lugarLevantamiento")
    private String lugarLevantamiento;
    @Size(max = 45)
    @Column(name = "numeroParte")
    private String numeroParte;
    @Size(max = 45)
    @Column(name = "unidadPolicial")
    private String unidadPolicial;
    @Size(max = 300)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 45)
    @Column(name = "direccionSS")
    private String direccionSS;

    public Formulario() {
    }

    public Formulario(Integer nue) {
        this.nue = nue;
    }

    public Integer getNue() {
        return nue;
    }

    public void setNue(Integer nue) {
        this.nue = nue;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRit() {
        return rit;
    }

    public void setRit(String rit) {
        this.rit = rit;
    }

    public String getDireccionFotografia() {
        return direccionFotografia;
    }

    public void setDireccionFotografia(String direccionFotografia) {
        this.direccionFotografia = direccionFotografia;
    }

    public Date getFechaOcurrido() {
        return fechaOcurrido;
    }

    public void setFechaOcurrido(Date fechaOcurrido) {
        this.fechaOcurrido = fechaOcurrido;
    }

    public String getLugarLevantamiento() {
        return lugarLevantamiento;
    }

    public void setLugarLevantamiento(String lugarLevantamiento) {
        this.lugarLevantamiento = lugarLevantamiento;
    }

    public String getNumeroParte() {
        return numeroParte;
    }

    public void setNumeroParte(String numeroParte) {
        this.numeroParte = numeroParte;
    }

    public String getUnidadPolicial() {
        return unidadPolicial;
    }

    public void setUnidadPolicial(String unidadPolicial) {
        this.unidadPolicial = unidadPolicial;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDireccionSS() {
        return direccionSS;
    }

    public void setDireccionSS(String direccionSS) {
        this.direccionSS = direccionSS;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nue != null ? nue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formulario)) {
            return false;
        }
        Formulario other = (Formulario) object;
        if ((this.nue == null && other.nue != null) || (this.nue != null && !this.nue.equals(other.nue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Formulario[ nue=" + nue + " ]";
    }
    
}
