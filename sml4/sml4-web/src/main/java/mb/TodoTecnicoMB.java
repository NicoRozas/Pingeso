/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import static mb.ForTrasladoTecnicoMB.logger;

/**
 *
 * @author sebastian
 */
@Named(value = "todoTecnicoMB")
@RequestScoped
public class TodoTecnicoMB {

    @EJB
    private FormularioEJBLocal formularioEJB;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    
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

    private int nue;

    private Formulario formulario;

    private List<Traslado> trasladosList;

    public TodoTecnicoMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "TodoMB");
        this.trasladosList = new ArrayList<>();  
        
        
        facesContext1 = FacesContext.getCurrentInstance();
        httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        
        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "todo nue recibido {0}", this.nue);
        }
        logger.exiting(this.getClass().getName(), "TodoMB");
    }

    
   
     public String agregarTraslado() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTrasladoTecnico");
        logger.log(Level.FINEST, "rut usuario entrega {0}", this.usuarioEntrega);
        logger.log(Level.FINEST, "rut usuario recibe {0}", this.usuarioRecibe);
        logger.log(Level.FINEST, "rut motivo {0}", this.motivo);
        String resultado = formularioEJB.crearTraslado(formulario, usuarioEntrega, usuarioEntregaUnidad, usuarioEntregaCargo, usuarioEntregaRut, usuarioRecibe, usuarioRecibeUnidad, usuarioRecibeCargo, usuarioRecibeRut, fechaT, observaciones, motivo);
        if (resultado.equals("Exito")) {
            httpServletRequest1.getSession().setAttribute("nueF", this.nue);
            logger.exiting(this.getClass().getName(), "agregarTrasladoTecnico", "todoTecnico?faces-redirect=true");
            System.out.println("se crea traslado");
            return "todoTecnico?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Uno o más datos inválidos"));
        logger.exiting(this.getClass().getName(), "agregarTrasladoTecnico", "NO SE CREA TRASLADO");
         System.out.println("no se crea traslado? ");
        return "";
    }
    
   
    

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarFormularioTecnico");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.trasladosList = formularioEJB.traslados(this.formulario);
        logger.log(Level.INFO, "formulario ruc {0}", this.formulario.getRuc());
        logger.log(Level.FINEST, "todos cant traslados {0}", this.trasladosList.size());
        logger.exiting(this.getClass().getName(), "cargarFormularioTecnico");
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

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public List<Traslado> getTrasladosList() {
        return trasladosList;
    }

    public void setTrasladosList(List<Traslado> trasladosList) {
        this.trasladosList = trasladosList;
    }

    public Date getFechaT() {
        return fechaT;
    }

    public void setFechaT(Date fechaT) {
        this.fechaT = fechaT;
    }

    
    

}
