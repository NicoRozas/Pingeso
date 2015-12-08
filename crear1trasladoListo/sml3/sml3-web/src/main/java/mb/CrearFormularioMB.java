/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import entity.Formulario;
import entity.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aracelly
 */
@Named(value = "crearFormularioMB")
@RequestScoped
@ManagedBean
public class CrearFormularioMB {
    @EJB
    private FormularioEJBLocal digitadorFormularioEJB;   

    static final Logger logger = Logger.getLogger(CrearFormularioMB.class.getName());
    
    private Formulario formulario;
    private Usuario usuarioInicia;
        
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    
    public CrearFormularioMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearFormularioMB");
        this.formulario = new Formulario();
        this.usuarioInicia = new Usuario();
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        logger.exiting(this.getClass().getName(), "CrearFormularioMB");
    }
    
    public String iniciarFormulario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarFormulario");
        logger.log(Level.FINEST, "formulario nue {0}", this.formulario.getNue());
        logger.log(Level.FINEST, "usuario rut {0}", this.usuarioInicia.getRutUsuario());
        httpServletRequest.getSession().setAttribute("nueF", this.formulario.getNue());
        digitadorFormularioEJB.crearFormulario(formulario, usuarioInicia);
        logger.log(Level.FINEST, "formulario fecha {0}", this.formulario.getFechaOcurrido());        
        logger.exiting(this.getClass().getName(), "iniciarFormulario", "addTaslado");
        return "addTaslado";
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Usuario getUsuarioInicia() {
        return usuarioInicia;
    }

    public void setUsuarioInicia(Usuario usuarioInicia) {
        this.usuarioInicia = usuarioInicia;
    }    
}
