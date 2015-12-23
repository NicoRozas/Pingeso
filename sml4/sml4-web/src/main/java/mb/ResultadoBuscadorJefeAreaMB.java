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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import static mb.BuscadorTecnicoMB.logger;

/**
 *
 * @author sebastian
 */
@Named(value = "resultadoBuscadorJefeAreaMB")
@RequestScoped
public class ResultadoBuscadorJefeAreaMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;

    @EJB
    private FormularioEJBLocal formularioEJB;

    //Capturando nue
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
    private int nue;
    private Formulario formulario;

    //Capturando usuario
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    private String usuarioS;

    private Usuario usuarioSesion;

    public ResultadoBuscadorJefeAreaMB() {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "ResultadoBuscadorJefeAreaMB");
        /**/
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest1.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.nue);
        }

        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioS);
        }

        logger.exiting(this.getClass().getName(), "ResultadoBuscadorJefeAreaMB");
    }

    @PostConstruct
    public void loadFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadFormulario");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.usuarioSesion = usuarioEJB.findUsuarioSesionByCuenta(this.usuarioS);
        logger.exiting(this.getClass().getName(), "loadFormulario");
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

}
