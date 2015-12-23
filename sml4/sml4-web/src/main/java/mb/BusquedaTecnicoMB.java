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

/**
 *
 * @author sebastian
 */
@Named(value = "busquedaTecnicoMB")
@RequestScoped
@ManagedBean
public class BusquedaTecnicoMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(CrearFormularioMB.class.getName());

    private Usuario usuarioSesion;
 
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
 
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
 
    private String usuarioSis;
    private int nue;
 
    public BusquedaTecnicoMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "BusquedaTecnicoMB");
        /**/
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
       
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
 
        logger.exiting(this.getClass().getName(), "BusquedaTecnicoMB"); 
    }
   
    @PostConstruct
    public void loadUsuario(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuarioTecnico");       
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis); 
        logger.exiting(this.getClass().getName(), "loadUsuarioTecnico");   
    }
 
    public String busquedaFormulario(){
        Formulario formulario = formularioEJB.findFormularioByNue(nue);
        if(formulario != null){
             httpServletRequest.getSession().setAttribute("nueF", formulario.getNue());
            return "forAddTTecnico.xhtml?faces-redirect=true";
        }
        
        return "Nue no encontrado";
    }
    
    public String salir(){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirTecnico");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "salirTecnico", "indexListo");
        return "indexListo?faces-redirect=true";
    }
    
    public String getUsuarioSis() {
        return usuarioSis;
    }
 
    public void setUsuarioSis(String usuarioSis) {
        this.usuarioSis = usuarioSis;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
}