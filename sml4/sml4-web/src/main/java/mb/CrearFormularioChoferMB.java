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
import java.util.Date;
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
@Named(value = "crearFormularioChoferMB")
@RequestScoped
@ManagedBean
public class CrearFormularioChoferMB {

    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;

    static final Logger logger = Logger.getLogger(CrearFormularioMB.class.getName());

    //Guardamos el usuario que inicia sesion
    private Usuario uSesion;
    
    //Atributos del formulario
    private String ruc;
    private String rit;
    private int nue;
    private String cargo;
    private String delito;
    private String direccionSS;
    private String lugar;
    private String unidad;
    private String levantadaPor;
    private String rut;
    private Date fecha;
    private String observacion;
    private String descripcion;
    private int parte;
    
    //Guardamos la cuenta del usuario que entrego la vista del login
    private String usuarioSis;
    
    //Captura al usuario proveniente del inicio de sesión
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;


    public CrearFormularioChoferMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearFormularioChoferMB");
        this.uSesion = new Usuario();
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }

        logger.exiting(this.getClass().getName(), "CrearFormularioChoferMB");
    }

    @PostConstruct
    public void loadUsuario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "loadUsuarioChofer");
        this.uSesion = (Usuario)usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
        logger.exiting(this.getClass().getName(), "loadUsuarioChofer");
    }
    

   public void iniciarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarFormularioChofer");
        logger.log(Level.FINEST, "formulario nue {0}", this.nue);
        logger.log(Level.FINEST, "usuario inicia rut {0}", this.rut);
        logger.log(Level.FINEST, "formulario fecha {0}", this.fecha);
        logger.log(Level.FINEST, "usuario inicia cargo {0}", this.cargo);
        String resultado = formularioEJB.crearFormulario(ruc, rit, nue, parte, cargo, delito, direccionSS, lugar, unidad, levantadaPor, rut, fecha, observacion, descripcion, uSesion);
        
        if (resultado.equals("Exito")) {
            logger.exiting(this.getClass().getName(), "iniciarFormularioChofer", "crearFormularioHU7");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, resultado, "Datos exitosos"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Datos no válidos"));
            logger.exiting(this.getClass().getName(), "iniciarFormularioChofer", "");
        }
    }
    

    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salir Chofer");
        logger.log(Level.FINEST, "usuario saliente {0}", this.uSesion.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "salir Chofer", "indexListo");
        return "indexListo?faces-redirect=true";
    }

    public Usuario getuSesion() {
        return uSesion;
    }

    public void setuSesion(Usuario uSesion) {
        this.uSesion = uSesion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRit() {
        return rit;
    }

    public void setRit(String rit) {
        this.rit = rit;
    }

    public int getNue() {
        return nue;
    }

    public void setNue(int nue) {
        this.nue = nue;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDelito() {
        return delito;
    }

    public void setDelito(String delito) {
        this.delito = delito;
    }

    public String getDireccionSS() {
        return direccionSS;
    }

    public void setDireccionSS(String direccionSS) {
        this.direccionSS = direccionSS;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getLevantadaPor() {
        return levantadaPor;
    }

    public void setLevantadaPor(String levantadaPor) {
        this.levantadaPor = levantadaPor;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioSis() {
        return usuarioSis;
    }

    public void setUsuarioSis(String usuarioSis) {
        this.usuarioSis = usuarioSis;
    }

    public int getParte() {
        return parte;
    }

    public void setParte(int parte) {
        this.parte = parte;
    }
    
    
}
