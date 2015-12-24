/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.tecnico;

import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Formulario;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import static mb.tecnico.BuscadorTecnicoMB.logger;

/**
 *
 * @author sebastian
 */
@Named(value = "resultadoBuscadorTecnico")
@RequestScoped
@ManagedBean
public class ResultadoBuscadorTecnico {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    
    @EJB
    private FormularioEJBLocal formularioEJB;
    
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
    private int nue;
    private Formulario formulario;
    
    
    public ResultadoBuscadorTecnico() {
        
         logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "BusquedaTecnicoMB");
        /**/
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();

        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest1.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.nue);
        }

        logger.exiting(this.getClass().getName(), "BusquedaTecnicoMB");
    }
    
    
    @PostConstruct
    public void loadFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadFormulario");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
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
