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
import static mb.CrearFormularioTecnicoMB.logger;

/**
 *
 * @author sebastian
 */
@Named(value = "busquedaTecnicoMB")
@RequestScoped
@ManagedBean
public class BuscadorTecnicoMB {

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

    public BuscadorTecnicoMB() {
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
    public void loadUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuarioTecnico");
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        logger.exiting(this.getClass().getName(), "loadUsuarioTecnico");
    }

    public String buscarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarBuscadorFormulario");
        System.out.println("NUE CAPTURADO:"+this.nue);
        Formulario formulario = formularioEJB.findFormularioByNue(this.nue);
        
        if (formulario != null) {
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            logger.exiting(this.getClass().getName(), "iniciarFormulario", "resultadoBuscadorTecnico");
            return "resultadoBuscadorTecnico?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "no existe", "Datos no válidos"));
        logger.exiting(this.getClass().getName(), "iniciarBuscarFormulario", "");
        System.out.println("no encontro el nue");
        return "";
    }

    public String salir() {
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

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    
}