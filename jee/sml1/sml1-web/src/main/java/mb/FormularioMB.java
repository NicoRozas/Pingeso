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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Aracelly
 */
@Named(value = "formularioJSFManagedBean")
@ManagedBean
@RequestScoped
public class FormularioMB {
    
    static final Logger logger = Logger.getLogger(FormularioMB.class.getName());
    
    @EJB
    private FormularioEJBLocal formularioEJBLocal;
    
    private Usuario usuarioIniciaFormulario;
    private Usuario usuarioEntrega;
    private Usuario usuarioRecibe;
    
    private Formulario formulario;
    
    private String horaFormulario;   
    private String fechaFormulario;
    private String horaTraslado;   
    private String fechaTraslado;
    private String cargoEntrega;
    private String cargoRecibe;
    private String descripcionEspecieCC;
    
    private String motivoTraslado;   
    
    private List<Traslado> traslados; //para cargar los traslados del formulario.
    
    public FormularioMB() {
        System.out.println("caca");
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "FormularioMB");        
        this.usuarioEntrega = new Usuario();
        this.usuarioRecibe = new Usuario();
        this.usuarioIniciaFormulario = new Usuario(); 
        this.formulario = new Formulario();
        this.traslados = new ArrayList<>();
        System.out.println("koko");
        logger.exiting(this.getClass().getName(), "FormularioMB");
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

    public String getHoraFormulario() {
        return horaFormulario;
    }

    public void setHoraFormulario(String horaFormulario) {
        this.horaFormulario = horaFormulario;
    }

    public String getFechaFormulario() {
        return fechaFormulario;
    }

    public void setFechaFormulario(String fechaFormulario) {
        this.fechaFormulario = fechaFormulario;
    }

    public String getHoraTraslado() {
        return horaTraslado;
    }

    public void setHoraTraslado(String horaTraslado) {
        this.horaTraslado = horaTraslado;
    }

    public String getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(String fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
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

    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }   
    
    public void crearFormulario(){
        System.out.println("hola1");
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearFormulario");
        System.out.println("motivo "+this.motivoTraslado);
        System.out.println("hora "+this.horaFormulario);
        System.out.println("nue "+this.formulario.getNue());
        
//        logger.info("hora -> "+horaFormulario);
//        logger.info("nue -> "+this.formulario.getNue());
//        logger.info("nombre del que inicia el formualrio -> "+this.usuarioIniciaFormulario.getNombreUsuario());
        logger.exiting(this.getClass().getName(), "crearFormulario");        
    }
    
    public void agregarTraslado(){
        System.out.println("hola2");
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "agregarTraslado");
        logger.info("motivo traslado -> "+motivoTraslado);
        logger.info("formulario nue -> "+this.formulario.getNue());
        //cosas para crear un traslado
        
        //this.traslados = this.formulario.getTrasladoList(); 
        logger.exiting(this.getClass().getName(), "agregarTraslado");             
    }
    
}
