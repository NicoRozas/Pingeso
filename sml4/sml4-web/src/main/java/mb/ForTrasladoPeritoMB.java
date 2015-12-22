/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import entity.Formulario;
import entity.Usuario;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sebastian
 */
@Named(value = "forTrasladoPeritoMB")
@RequestScoped
@ManagedBean
public class ForTrasladoPeritoMB {

   @EJB
    private FormularioEJBLocal formularioEJB;
    
    static final Logger logger = Logger.getLogger(ForTrasladoMB.class.getName());
    
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    
    private String usuarioEntrega;
    private String usuarioEntregaUnidad;
    private String usuarioEntregaCargo;
    private String usuarioEntregaRut;
    private String usuarioRecibe;
    private String usuarioRecibeUnidad;
    private String usuarioRecibeCargo;
    private String usuarioRecibeRut;
    private String motivo;
    private String observaciones;
    private Date fechaT;
   
    private String usuarioS;
    private Usuario usuarioSesion;
    
    private int nue;
    
    private Formulario formulario;
    
    
    public ForTrasladoPeritoMB() {
       logger.setLevel(Level.ALL);
       logger.entering(this.getClass().getName(), "ForTrasladoPerito");
       facesContext = FacesContext.getCurrentInstance();
       httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
       if(httpServletRequest.getSession().getAttribute("nueF") != null){
           this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
           logger.log(Level.FINEST, "nue recibido {0}", this.nue);
       }
       logger.exiting(this.getClass().getName(), "ForTrasladoPerito");
    }             
    
    public String agregarTraslado(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTrasladoPerito");
        logger.log(Level.FINEST, "rut usuario entrega {0}", this.usuarioEntrega);
        logger.log(Level.FINEST, "rut usuario recibe {0}", this.usuarioRecibe);
        logger.log(Level.FINEST, "rut motivo {0}", this.motivo);
        String resultado = formularioEJB.crearTraslado(formulario, usuarioEntrega, usuarioEntregaUnidad, usuarioEntregaCargo, usuarioEntregaRut, usuarioRecibe, usuarioRecibeUnidad, usuarioRecibeCargo, usuarioRecibeRut, fechaT, observaciones, motivo, usuarioSesion);
        if(resultado.equals("Exito")){
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            logger.exiting(this.getClass().getName(), "agregarTrasladoTecnico", "todoTecnico?faces-redirect=true");
            return "todoTecnico?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Uno o más datos inválidos"));
        logger.exiting(this.getClass().getName(), "agregarTrasladoPerito", "");
        return "";
    }
    
    @PostConstruct
    public void cargarFormulario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarFormularioPerito");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        logger.exiting(this.getClass().getName(), "cargarFormularioPerito");
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

    public String getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(String usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public String getUsuarioEntregaUnidad() {
        return usuarioEntregaUnidad;
    }

    public void setUsuarioEntregaUnidad(String usuarioEntregaUnidad) {
        this.usuarioEntregaUnidad = usuarioEntregaUnidad;
    }

    public String getUsuarioEntregaCargo() {
        return usuarioEntregaCargo;
    }

    public void setUsuarioEntregaCargo(String usuarioEntregaCargo) {
        this.usuarioEntregaCargo = usuarioEntregaCargo;
    }

    public String getUsuarioEntregaRut() {
        return usuarioEntregaRut;
    }

    public void setUsuarioEntregaRut(String usuarioEntregaRut) {
        this.usuarioEntregaRut = usuarioEntregaRut;
    }

    public String getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(String usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getUsuarioRecibeUnidad() {
        return usuarioRecibeUnidad;
    }

    public void setUsuarioRecibeUnidad(String usuarioRecibeUnidad) {
        this.usuarioRecibeUnidad = usuarioRecibeUnidad;
    }

    public String getUsuarioRecibeCargo() {
        return usuarioRecibeCargo;
    }

    public void setUsuarioRecibeCargo(String usuarioRecibeCargo) {
        this.usuarioRecibeCargo = usuarioRecibeCargo;
    }

    public String getUsuarioRecibeRut() {
        return usuarioRecibeRut;
    }

    public void setUsuarioRecibeRut(String usuarioRecibeRut) {
        this.usuarioRecibeRut = usuarioRecibeRut;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaT() {
        return fechaT;
    }

    public void setFechaT(Date fechaT) {
        this.fechaT = fechaT;
    }
    
}
