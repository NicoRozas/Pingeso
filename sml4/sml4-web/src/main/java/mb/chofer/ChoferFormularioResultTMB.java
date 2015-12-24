/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.chofer;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sebastian
 */
@Named(value = "choferFormularioResultTMB")
@RequestScoped
public class ChoferFormularioResultTMB {

    @EJB
    private FormularioEJBLocal formularioEJB;
    @EJB
    private UsuarioEJBLocal usuarioEJB;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;

    private String usuarioS;
    private Usuario usuarioSesion;

    private int nue;

    private Formulario formulario;

    private List<Traslado> trasladosList;

    static final Logger logger = Logger.getLogger(ChoferFormularioResultTMB.class.getName());

    public ChoferFormularioResultTMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "ChoferFormularioResultTMB");
        this.trasladosList = new ArrayList<>();

        facesContext1 = FacesContext.getCurrentInstance();
        httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "todo nue recibido {0}", this.nue);
        }

        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioS = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioS);
        }
        logger.exiting(this.getClass().getName(), "choferFormularioResultTMB");
    }

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CargarDatosChoferFormularioResultT");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.usuarioS);
        this.trasladosList = formularioEJB.traslados(this.formulario);
        logger.log(Level.INFO, "formulario ruc {0}", this.formulario.getRuc());
        logger.log(Level.FINEST, "todos cant traslados {0}", this.trasladosList.size());
        logger.exiting(this.getClass().getName(), "CargarDatosChoferFormularioResultT");
    }

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirJefeArea");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "salirJefeArea", "indexListo");
        return "indexListo?faces-redirect=true";
    }

    public String editar() {
        //Enviando nue
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        //Enviando usuario
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edicion", "Ir a edicion"));
        return "/chofer/editarChofer?faces-redirect=true";

    }

    public String nuevaCadena() {

        //Enviando usuario
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioS);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva Cadena", "Ir a nuevo formulario"));
        return "/chofer/choferFormulario?faces-redirect=true";

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

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
}
