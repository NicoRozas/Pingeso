/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cargo;
import entity.Formulario;
import entity.Usuario;
import facade.CargoFacadeLocal;
import facade.FormularioFacadeLocal;
import facade.TipoMotivoFacadeLocal;
import facade.UsuarioFacadeLocal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aracelly
 */
@Stateless
public class FormularioEJB implements FormularioEJBLocal {
    @EJB
    private CargoFacadeLocal cargoFacade;
        
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    
    @EJB
    private TipoMotivoFacadeLocal tipoMotivoFacade;   
    
    @EJB
    private FormularioFacadeLocal formularioFacade;   
   
    
    
    static final Logger logger = Logger.getLogger(FormularioEJB.class.getName());
    
    
    
//    public Formulario findByNue(int nue){
//        logger.entering(this.getClass().getName(), "findByNue", nue);
//        Formulario f = formularioFacade.find(nue);
//        if(f != null ){
//            logger.exiting(this.getClass().getName(), "findByNue", "encontrado");
//            return f;
//        }else{
//            logger.entering(this.getClass().getName(), "findByNue", "no existe");
//            return null;
//        }
//    }
    
    @Override
    public boolean crearFormulario(Formulario formulario, Usuario usuarioFormulario, String cargo){
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearFormulario", formulario.toString());
        Formulario f = formularioFacade.find(formulario.getNue());
        boolean retorno = false;
        if(f == null ){//si no existe un formualrio con el mismo nue.
            formulario.setFechaIngreso(new Date(System.currentTimeMillis()));
            formulario.setUltimaEdicion(formulario.getFechaIngreso());
            formulario.setDireccionFotografia("C:");
            
            if(usuarioFormulario != null){ //verifica que se haya enviado un Usuario no null desde el MB.                
                Usuario usuario = usuarioFacade.findByRUN(usuarioFormulario.getRutUsuario()); 
                if(usuario == null){ //el usuario no se encuentra en la BD
                   usuarioFormulario.setEstadoUsuario(true);
                   
                   //buscar si existe el cargo que se le adjudica
                   Cargo c = cargoFacade.findByCargo(cargo);
                   if(c == null){//el cargo no existe, entocnes lo creamos.
                       Cargo cNuevo = new Cargo();
                       cNuevo.setNombreCargo(cargo);
                       cargoFacade.create(c);
                       c = cargoFacade.findByCargo(cargo);//lo buscamos de nuevo para traerlo con su correspondiente id.
                   }
                   //ahora que el cargo se encuentra en la bd, lo asignamos al usuario                   
                   usuarioFormulario.setCargoidCargo(c);
                   
                   //persistir usuario
                   usuarioFacade.create(usuarioFormulario);              
                   usuario = usuarioFacade.findByRUN(usuarioFormulario.getRutUsuario()); //traemos al usuario desde la db con su correspondiente id asignado.
                }
                //asignamos este usuario al formulario en cuestion
                formulario.setUsuarioidUsuario1(usuario);            
                
            }else{  //el usuario recibido no es valido.
                logger.entering(this.getClass().getName(), "crearFormulario", retorno+", usuario no valido");
                return retorno;
            }
            
            formularioFacade.create(formulario);
            logger.exiting(this.getClass().getName(), "crearFormulario", retorno);
            return retorno;
        }else{
            logger.entering(this.getClass().getName(), "crearFormulario", retorno+", formulario no valido");
            return retorno;
        }
    }
    
    public boolean traslado(Usuario usuarioEntrega, Usuario usuarioRecibe, String fecha, String hora, String motivo, String descripcionEspecie){
    return false;
    }
 
}
