/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Formulario;
import entity.Usuario;
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
import static mb.BuscadorTecnicoMB.logger;

/**
 *
 * @author sebastian
 */
@Named(value = "editarChoferMB")
@RequestScoped
@ManagedBean
public class EditarChoferMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;

    @EJB
    private FormularioEJBLocal formularioEJB;

    //Para obtener nue
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
    private int nue;
    private Formulario formulario;

    //Para obtener el usuario
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String usuarioS;
    private Usuario usuarioSesion;

    private String observacionEdicion;
    
    
    
    public EditarChoferMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "EditarChoferMB");
        /**/
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        if (httpServletRequest1.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest1.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "Nue recibido {0}", this.nue);
        }

        if (httpServletRequest.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioS = (String) httpServletRequest.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Cuenta Usuario recibido {0}", this.nue);
        }

        logger.exiting(this.getClass().getName(), "EditarChoferMB");
    }

    @PostConstruct
    public void loadDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadFormulario & loadUsuario ");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioS);
        logger.exiting(this.getClass().getName(), "loadFormulario & loadUsuario");
    }

    
    public String editarFormulario(){
    
        String response = formularioEJB.edicionFormulario(formulario, observacionEdicion, usuarioSesion);
    
        if(response.equals("Exito")){
            return "Edición realizada correctamente";
        }
        
        return "";
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

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public String getObservacionEdicion() {
        return observacionEdicion;
    }

    public void setObservacionEdicion(String observacionEdicion) {
        this.observacionEdicion = observacionEdicion;
    }
    
}
