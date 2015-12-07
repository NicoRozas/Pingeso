/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import entity.Formulario;
import entity.TipoMotivo;
import entity.Traslado;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Aracelly
 */
@Named(value = "formularioMB")
@ManagedBean
@RequestScoped
public class FormularioMB {
    
    static final Logger logger = Logger.getLogger(FormularioMB.class.getName());
    
    private String nombreUserSesion = "pepito";
    
    @EJB
    private FormularioEJBLocal formularioEJBLocal;
        
    private Formulario formulario;
    private Usuario usuarioIniciaFormulario;
    private String cargoInicia;
    
    private String motivoSeleccionado;
    private Usuario usuarioEntrega;
    private Usuario usuarioRecibe;    
    private String cargoEntrega;
    private String cargoRecibe;
    private String descripcionEspecieCC;    
    private String observacionesT;        
    private Date fechaT;  
    
    
    public FormularioMB() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "FormularioMB");        
        this.usuarioEntrega = new Usuario();
        this.usuarioRecibe = new Usuario();
        this.usuarioIniciaFormulario = new Usuario(); 
        this.formulario = new Formulario();   
//        this.motivosTraslado = new ArrayList<>();       
        logger.exiting(this.getClass().getName(), "FormularioMB");
    }       

//    @PostConstruct
//    public void cargarMotivos(){
//        logger.setLevel(Level.ALL);
//        logger.entering(this.getClass().getName(), "cargarMotivos");        
//  //      this.motivosTraslado = formularioEJBLocal.findAllMotivos();
//        logger.exiting(this.getClass().getName(), "cargarMotivos");        
//    }
    
    public String getNombreUserSesion() {
        return nombreUserSesion;
    }

    public void setNombreUserSesion(String nombreUserSesion) {
        this.nombreUserSesion = nombreUserSesion;
    }
  
    public String getObservacionesT() {
        return observacionesT;
    }

    public void setObservacionesT(String observacionesT) {
        this.observacionesT = observacionesT;
    }

    public String getCargoInicia() {
        return cargoInicia;
    }

    public void setCargoInicia(String cargoInicia) {
        this.cargoInicia = cargoInicia;
    }

    public String getDescripcionEspecieCC() {
        return descripcionEspecieCC;
    }

    public void setDescripcionEspecieCC(String descripcionEspecieCC) {
        this.descripcionEspecieCC = descripcionEspecieCC;
    }

    public Usuario getUsuarioIniciaFormulario() {
        return usuarioIniciaFormulario;
    }

    public void setUsuarioIniciaFormulario(Usuario usuarioIniciaFormulario) {
        this.usuarioIniciaFormulario = usuarioIniciaFormulario;
    }

    public Usuario getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(Usuario usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public Usuario getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(Usuario usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String getCargoEntrega() {
        return cargoEntrega;
    }

    public void setCargoEntrega(String cargoEntrega) {
        this.cargoEntrega = cargoEntrega;
    }

    public String getCargoRecibe() {
        return cargoRecibe;
    }

    public void setCargoRecibe(String cargoRecibe) {
        this.cargoRecibe = cargoRecibe;
    }

    public String getMotivoSeleccionado() {
        return motivoSeleccionado;
    }

    public void setMotivoSeleccionado(String motivoSeleccionado) {
        this.motivoSeleccionado = motivoSeleccionado;
    }

    public Date getFechaT() {
        return fechaT;
    }

    public void setFechaT(Date fechaT) {
        this.fechaT = fechaT;
    }    

   
//    private void buscarMotivo(){
//        logger.setLevel(Level.ALL);
//        logger.entering(this.getClass().getName(), "buscarMotivo");
//        
//        for(int i =0; i < motivosTraslado.size(); i++){
//            if(motivoSeleccionado.equals(motivosTraslado.get(i).getTipoMotivo())){
//                tipoMotivo = motivosTraslado.get(i);
//                break;
//            }
//        }
//    }
//    
    private void crearFormulario(){        
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearFormulario");
        logger.info("fecha ingreso "+this.formulario.getFechaOcurrido());
        logger.info("lugar levantamiento "+this.formulario.getLugarLevantamiento());
        logger.info("nue "+this.formulario.getNue());
        logger.info("cargo "+this.cargoInicia);
        
        boolean isExito = formularioEJBLocal.crearFormulario(formulario, usuarioIniciaFormulario, cargoInicia);
        
        logger.exiting(this.getClass().getName(), "crearFormulario", isExito);                
    }
    
    public void agregarTraslado(){     
        System.out.println("agregar");
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTraslado");
        crearFormulario();
//        buscarMotivo();
        logger.info("motivo traslado -> "+motivoSeleccionado);        
        boolean isExito = formularioEJBLocal.crearTraslado(usuarioEntrega, usuarioRecibe, fechaT, motivoSeleccionado, observacionesT, descripcionEspecieCC, cargoEntrega, cargoRecibe, this.formulario.getNue());     
        logger.exiting(this.getClass().getName(), "agregarTraslado "+isExito);  
    }
    
    public void salir(){
        System.out.println("boton salir");
    }
}
