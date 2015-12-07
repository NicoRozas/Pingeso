/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Aracelly
 */
@Named(value = "crearFormularioMB")
@RequestScoped
@ManagedBean
public class CrearFormularioMB {

    static final Logger logger = Logger.getLogger(CrearFormularioMB.class.getName());
    
    public CrearFormularioMB() {
        
    }
    
}
