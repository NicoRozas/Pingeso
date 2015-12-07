/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import facade.FormularioFacadeLocal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Aracelly
 */
@Named(value = "verFormularioMB")
@ManagedBean
@RequestScoped
public class VerFormularioMB {
    
    static final Logger logger = Logger.getLogger(VerFormularioMB.class.getName());
    
    @ManagedProperty(value = "#{formularioMB}")
    private FormularioMB formularioMB;
            
    @EJB
    private FormularioFacadeLocal formularioFacade;
    
    private int nue;
   
    private Formulario formulario;
    
    private Usuario usuarioIniciaFormulario;
    private Traslado traslado;
    
    private String horaFormulario;   
    private String fechaFormulario;
    private String horaTraslado;   
    private String fechaTraslado;
    private String cargoEntrega;
    private String cargoRecibe;
    private String descripcionEspecieCC;
    private String cargoInicia;
    private String observacionesT;
    
    private String motivoTraslado;   
    
    private Date fechaT;
    
    public VerFormularioMB() {
        this.traslado = new Traslado();        
    }

    @PostConstruct
    public void cargarDatos() {
        this.nue = formularioMB.getFormulario().getNue();
        this.formulario = formularioFacade.find(nue);
        this.usuarioIniciaFormulario = this.formulario.getUsuarioidUsuario();
    }   

    public Traslado getTraslado() {
        return traslado;
    }

    public void setTraslado(Traslado traslado) {
        this.traslado = traslado;
    }

    public String getHoraFormulario() {
        return horaFormulario;
    }

    public void setHoraFormulario(String horaFormulario) {
        this.horaFormulario = horaFormulario;
    }

    public String getFechaFormulario() {
        return fechaFormulario;
    }

    public void setFechaFormulario(String fechaFormulario) {
        this.fechaFormulario = fechaFormulario;
    }

    public String getHoraTraslado() {
        return horaTraslado;
    }

    public void setHoraTraslado(String horaTraslado) {
        this.horaTraslado = horaTraslado;
    }

    public String getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(String fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public String getCargoEntrega() {
        return cargoEntrega;
    }

    public void setCargoEntrega(String cargoEntrega) {
        this.cargoEntrega = cargoEntrega;
    }

    public String getCargoRecibe() {
        return cargoRecibe;
    }

    public void setCargoRecibe(String cargoRecibe) {
        this.cargoRecibe = cargoRecibe;
    }

    public String getDescripcionEspecieCC() {
        return descripcionEspecieCC;
    }

    public void setDescripcionEspecieCC(String descripcionEspecieCC) {
        this.descripcionEspecieCC = descripcionEspecieCC;
    }

    public String getCargoInicia() {
        return cargoInicia;
    }

    public void setCargoInicia(String cargoInicia) {
        this.cargoInicia = cargoInicia;
    }

    public String getObservacionesT() {
        return observacionesT;
    }

    public void setObservacionesT(String observacionesT) {
        this.observacionesT = observacionesT;
    }

    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }

    public Date getFechaT() {
        return fechaT;
    }

    public void setFechaT(Date fechaT) {
        this.fechaT = fechaT;
    }   
    
    public Usuario getUsuarioIniciaFormulario() {
        return usuarioIniciaFormulario;
    }

    public void setUsuarioIniciaFormulario(Usuario usuarioIniciaFormulario) {
        this.usuarioIniciaFormulario = usuarioIniciaFormulario;
    }

    public FormularioMB getFormularioMB() {
        return formularioMB;
    }

    public void setFormularioMB(FormularioMB formularioMB) {
        this.formularioMB = formularioMB;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }
    
    public void agregarTraslado(){        
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTraslado");
        //logger.info("motivo traslado -> "+motivoTraslado);
        logger.info("formulario nue -> "+this.formulario.getNue());
        //cosas para crear un traslado
        //boolean isExito = formularioEJBLocal.crearTraslado(usuarioEntrega, usuarioRecibe, fechaTraslado, horaTraslado, motivoTraslado, observacionesT, descripcionEspecieCC, cargoEntrega, cargoRecibe, this.formulario.getNue());     
        //this.traslados = formularioEJBLocal.traslados(this.formulario.getNue());
        //logger.exiting(this.getClass().getName(), "agregarTraslado",isExito);    
         logger.exiting(this.getClass().getName(), "agregarTraslado");    
        //return "welcomePrimefaces_1.xhtml";
    }
    
}
