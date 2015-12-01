/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import entity.Formulario;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Aracelly
 */
//@Stateless
@ManagedBean
@RequestScoped
public class FormularioMB {
   
    static final Logger logger = Logger.getLogger(FormularioMB.class.getName());
    
    private Date fecha;  
    
    private Formulario formulario;
    
    public Formulario getFormulario() {
        return formulario;
    }
    
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public void crearFormulario(){
        logger.entering(this.getClass().getName(), "crearFormulario", formulario.toString());
        this.formulario.setFechaOcurrido(new Date(System.currentTimeMillis()));
        this.formulario.setDireccionFotografia("npi");
        
       
        logger.entering(this.getClass().getName(), "crearFormulario");
    }
    
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
    
}
