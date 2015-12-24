/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.chofer;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sebastian
 */
@Named(value = "editarChoferTResultMB")
@RequestScoped
@ManagedBean
public class EditarChoferTResultMB {

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
    
    //Listado de  ediciones realizadas
    List<EdicionFormulario> ediciones;
    List<Traslado> traslados;
    
    static final Logger logger = Logger.getLogger(EditarChoferTResultMB.class.getName());
    
    public EditarChoferTResultMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "editarChoferTResultMB");
        /**/
        this.ediciones = new ArrayList();
        this.traslados = new ArrayList();
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
            logger.log(Level.FINEST, "Cuenta Usuario recibido {0}", this.usuarioS);
        }

        logger.exiting(this.getClass().getName(), "editarChoferTResultMB");
    }

    @PostConstruct
    public void loadDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadFormulario & loadUsuario ");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(usuarioS);
        this.traslados = formularioEJB.traslados(formulario);
        this.ediciones = formularioEJB.listaEdiciones(this.nue, this.usuarioSesion.getIdUsuario());
        logger.exiting(this.getClass().getName(), "loadFormulario & loadUsuario");
    }

    
    public String editarFormulario(){
    
        String response = formularioEJB.edicionFormulario(formulario, observacionEdicion, usuarioSesion);
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        if(response.equals("Exito")){
            return "/chofer/editarChoferTResult.xhtml?faces-redirect=true";
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

    public List<EdicionFormulario> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<EdicionFormulario> ediciones) {
        this.ediciones = ediciones;
    }

    public List<Traslado> getTraslados() {
        return traslados;
    }

    public void setTraslados(List<Traslado> traslados) {
        this.traslados = traslados;
    }
    
    
    
}
