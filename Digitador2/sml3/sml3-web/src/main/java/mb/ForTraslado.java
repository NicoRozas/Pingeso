/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import entity.Formulario;
import entity.Usuario;
import facade.FormularioFacadeLocal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aracelly
 */
@Named(value = "forTrasladoMB")
@RequestScoped
public class ForTraslado {
    
    @EJB
    private FormularioFacadeLocal formularioFacade;
    
    @EJB
    private FormularioEJBLocal digitadorFormularioEJB;
    
    static final Logger logger = Logger.getLogger(ForTraslado.class.getName());
    
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    
    private Usuario usuarioEntrega;
    private Usuario usuarioRecibe;
    private String motivo;
    private String obs;
    private Date fechaT;
    
    private int nue;
    
    private Formulario formulario;
    
    
    public ForTraslado() {
       logger.setLevel(Level.ALL);
       logger.entering(this.getClass().getName(), "ForTraslado");
       this.usuarioEntrega = new Usuario();
       this.usuarioRecibe = new Usuario();
       facesContext = FacesContext.getCurrentInstance();
       httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
       if(httpServletRequest.getSession().getAttribute("nueF") != null){
           this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
           logger.log(Level.FINEST, "nue recibido {0}", this.nue);
       }
       logger.exiting(this.getClass().getName(), "ForTraslado");
    }             
    
    public String agregarTraslado(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTraslado");
        logger.log(Level.FINEST, "rut usuario entrega {0}", this.usuarioEntrega.getRutUsuario());
        logger.log(Level.FINEST, "rut usuario recibe {0}", this.usuarioRecibe.getRutUsuario());
        logger.log(Level.FINEST, "rut motivo {0}", this.motivo);
        digitadorFormularioEJB.crearTraslado(this.nue, usuarioEntrega, usuarioRecibe, fechaT, formulario.getFechaOcurrido(), obs, motivo);
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        logger.exiting(this.getClass().getName(), "agregarTraslado", "todo?faces-redirect=true");
        return "todo?faces-redirect=true";
    }
    
    @PostConstruct
    public void cargarFormulario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarFormulario");
        this.formulario = formularioFacade.find(nue);
        logger.exiting(this.getClass().getName(), "cargarFormulario");
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

    public Usuario getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(Usuario usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public Usuario getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(Usuario usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getFechaT() {
        return fechaT;
    }

    public void setFechaT(Date fechaT) {
        this.fechaT = fechaT;
    }
    
}
