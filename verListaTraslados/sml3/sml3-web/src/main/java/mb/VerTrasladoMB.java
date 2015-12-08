/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import entity.Traslado;
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
 * @author Aracelly
 */
@Named(value = "verTrasladoMB")
@RequestScoped
@ManagedBean
public class VerTrasladoMB {
    
    @EJB
    private FormularioEJBLocal digitadorFormularioEJB;
    
    static final Logger logger = Logger.getLogger(VerTrasladoMB.class.getName());
    
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private int nue;
    
    private List<Traslado> trasladosList;
    
    public VerTrasladoMB() {
       logger.setLevel(Level.ALL);
       logger.entering(this.getClass().getName(), "VerTrasladoMB");
       trasladosList = new ArrayList<>();       
       facesContext = FacesContext.getCurrentInstance();
       httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
       if(httpServletRequest.getSession().getAttribute("nueF") != null){
           this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
           logger.log(Level.FINEST, "ver traslado nue recibido {0}", this.nue);
       }       
       logger.exiting(this.getClass().getName(), "VerTrasladoMB");
    }
    
    @PostConstruct
    public void cargarListaTraslados(){
       this.trasladosList = digitadorFormularioEJB.traslados(this.nue);
       logger.log(Level.FINEST, "cant traslados {0}", this.trasladosList.size());
    }

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public List<Traslado> getTrasladosList() {
        return trasladosList;
    }

    public void setTrasladosList(List<Traslado> trasladosList) {
        this.trasladosList = trasladosList;
    }
    
}
