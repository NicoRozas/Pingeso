/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import static mb.ForTrasladoTecnicoMB.logger;

/**
 *
 * @author Aracelly
 */
@Named(value = "todoMB")
@RequestScoped
@ManagedBean
public class TodoMB {

    @EJB
    private FormularioEJBLocal formularioEJB;

    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;

    
    private String usuarioEntrega;
    private String usuarioEntregaUnidad;
    private String usuarioEntregaCargo;
    private String usuarioEntregaRut;
    private String usuarioRecibe;
    private String usuarioRecibeUnidad;
    private String usuarioRecibeCargo;
    private String usuarioRecibeRut;
    private String motivo;
    private String observaciones;
    private Date fechaT;

    private int nue;

    private Formulario formulario;

    private List<Traslado> trasladosList;

    public TodoMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "TodoMB");
        this.trasladosList = new ArrayList<>();
        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("nueF") != null) {
            this.nue = (int) httpServletRequest.getSession().getAttribute("nueF");
            logger.log(Level.FINEST, "todo nue recibido {0}", this.nue);
        }
        logger.exiting(this.getClass().getName(), "TodoMB");
    }
/*
    public String agregarTraslado() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTraslado");
        logger.log(Level.FINEST, "rut usuario entrega {0}", this.usuarioEntrega.getRutUsuario());
        logger.log(Level.FINEST, "rut usuario recibe {0}", this.usuarioRecibe.getRutUsuario());
        logger.log(Level.FINEST, "rut motivo {0}", this.motivo);
        String resultado = formularioEJB.crearTraslado(this.nue, usuarioEntrega, usuarioRecibe, fechaT, formulario.getFechaOcurrido(), obs, motivo, cargoEntrega, cargoRecibe);
        if (resultado.equals("Exito")) {
            httpServletRequest.getSession().setAttribute("nueF", this.nue);
            logger.exiting(this.getClass().getName(), "agregarTraslado", "todoH11?faces-redirect=true");
            return "todoH11?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Uno o más datos inválidos"));
        logger.exiting(this.getClass().getName(), "agregarTraslado", "");
        return "";
    }
    */

    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarFormulario");
        this.formulario = formularioEJB.findFormularioByNue(this.nue);
        this.trasladosList = formularioEJB.traslados(this.formulario);
        //this.trasladosList = this.formulario.getTrasladoList();
        // this.trasladosList = formularioEJB.getTrasladoByNue(this.nue);
        logger.log(Level.INFO, "formulario ruc {0}", this.formulario.getRuc());
        logger.log(Level.FINEST, "todos cant traslados {0}", this.trasladosList.size());
        logger.exiting(this.getClass().getName(), "cargarFormulario");
    }



}
