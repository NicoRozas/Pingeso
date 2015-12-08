/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Area;
import entity.Cargo;
import entity.Formulario;
import entity.TipoMotivo;
import entity.TipoUsuario;
import entity.Traslado;
import entity.Usuario;
import facade.AreaFacadeLocal;
import facade.CargoFacadeLocal;
import facade.FormularioFacadeLocal;
import facade.TipoMotivoFacadeLocal;
import facade.TipoUsuarioFacadeLocal;
import facade.TrasladoFacadeLocal;
import facade.UsuarioFacadeLocal;
import java.util.ArrayList;
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
public class DigitadorFormularioEJB implements FormularioEJBLocal {

    @EJB
    private TipoMotivoFacadeLocal tipoMotivoLocal;

    @EJB
    private TipoUsuarioFacadeLocal tipoUsuarioFacade;

    @EJB
    private AreaFacadeLocal areaFacade;

    @EJB
    private TrasladoFacadeLocal trasladoFacade;

    @EJB
    private CargoFacadeLocal cargoFacade;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    @EJB
    private FormularioFacadeLocal formularioFacade;

    static final Logger logger = Logger.getLogger(DigitadorFormularioEJB.class.getName());

    /*
     retorna la lista de motivos que se encuentran en la bd
     */
    @Override
    public List<TipoMotivo> findAllMotivos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findAllMotivos");
        List<TipoMotivo> motivos = tipoMotivoLocal.findAll();
        if (motivos != null && !motivos.isEmpty()) {
            logger.exiting(this.getClass().getName(), "findAllMotivos" + motivos.size());
            return motivos;
        }
        logger.exiting(this.getClass().getName(), "findAllMotivos" + null);
        return null;
    }

    @Override
    public List<Traslado> traslados(int nue) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "traslados", nue);
        List<Traslado> retorno = formularioFacade.find(nue).getTrasladoList();
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "traslados", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "traslados", retorno.size());
            return retorno;
        }
    }

    @Override
    public List<Traslado> getTrasladoByNue(int nue) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "getTrasladoByNue");
        List<Traslado> allTraslados = trasladoFacade.findAll();
        if (allTraslados == null) {
            logger.exiting(this.getClass().getName(), "getTrasladoByNue", "Error al buscar todos");
            return null;
        } else {
            List<Traslado> trasladoNue = new ArrayList<>();
            for (int i = 0; i < allTraslados.size(); i++) {
                if (allTraslados.get(i).getFormularioNUE().getNue().equals(nue)) {
                    trasladoNue.add(allTraslados.get(i));
                }
            }
            logger.exiting(this.getClass().getName(), "getTrasladoByNue", trasladoNue.size());
            return trasladoNue;
        }       
    }

    private Usuario crearExterno(Usuario usuario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearExterno");
        if (usuario != null) {
            Usuario nuevoExterno = usuario;
            Area areaExterno = areaFacade.findByArea("Otro");
            TipoUsuario tue = tipoUsuarioFacade.findByTipo("Externo");
            Cargo cargoExterno = cargoFacade.findByCargo("Externo");
            nuevoExterno.setAreaidArea(areaExterno);
            nuevoExterno.setCargoidCargo(cargoExterno);
            nuevoExterno.setTipoUsuarioidTipoUsuario(tue);
            nuevoExterno.setEstadoUsuario(Boolean.TRUE);
            nuevoExterno.setMailUsuario("na");
            nuevoExterno.setPassUsuario("na");
            logger.finest("se inicia la persistencia del nuevo usuario externo");
            usuarioFacade.create(nuevoExterno);
            logger.finest("se finaliza la persistencia del nuevo usuario externo");

            nuevoExterno = usuarioFacade.findByRUN(usuario.getRutUsuario());
            if (nuevoExterno != null) {
                logger.exiting(this.getClass().getName(), "crearExterno", nuevoExterno.toString());
                return nuevoExterno;
            }
        }
        logger.exiting(this.getClass().getName(), "crearExterno", null);
        return null;
    }

    @Override
    public boolean crearFormulario(Formulario formulario, Usuario usuarioIniciaF) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearFormulario");
        if (formulario != null && usuarioIniciaF != null && formulario.getNue() > 0) {
            Formulario formularioP = formularioFacade.find(formulario.getNue()); //verificamos que no haya otro formulario con el mismo nue
            if (formularioP == null) { //significa que no hay otro formulario con el mismo nue
                Usuario usuarioIniciaP = usuarioFacade.findByRUN(usuarioIniciaF.getRutUsuario()); //buscamos al usuario que ingresa el formulario
                if (usuarioIniciaP == null) { //significa que el usuario no existe en la bd.
                    usuarioIniciaP = crearExterno(usuarioIniciaF);
                }
                Formulario nuevoFormulario = formulario;
                nuevoFormulario.setDireccionFotografia("C:");
                nuevoFormulario.setFechaIngreso(new Date(System.currentTimeMillis()));
                nuevoFormulario.setUltimaEdicion(nuevoFormulario.getFechaIngreso());
                nuevoFormulario.setUsuarioidUsuario(usuarioIniciaP);
                nuevoFormulario.setUsuarioidUsuario1(usuarioIniciaP);

                logger.finest("se inicia la persistencia del nuevo formulario");
                formularioFacade.create(nuevoFormulario);
                logger.finest("se finaliza la persistencia del nuevo formulario");
                logger.exiting(this.getClass().getName(), "crearFormulario", true);
                return true;
            } else { //ya existe un forulario con el mismo nue.
                logger.exiting(this.getClass().getName(), "crearFormulario", false);
                return false;
            }
        } else {
            logger.exiting(this.getClass().getName(), "crearFormulario", false);
        }
        return false;
    }

    /*
     Recibe un Formulario, el Usuario que lo inicia y un String con el cargo de ese usuario 
     Retorna true cuando el formulario fue creado con exito.
     1.verifica que no exista otro formulario con el mismo nue.
     2.verifica que el nue sea positivo mayor que cero.
     3.verifica que el usuario ingresado no sea nulo.
     4.usamos TraerUsuario
     5.se agrega el usuario al formulario.
     6.se realiza la persistencia del formulario.
     */
    @Override
    public boolean crearFormulario(Formulario formulario, Usuario usuarioFormulario, String cargo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearFormulario ", formulario.toString());
        if (formulario.getNue() > 0) {
            Formulario f = formularioFacade.find(formulario.getNue());
            if (f == null) {//si no existe un formulario con el mismo nue, entonces podemos crearlo.
                formulario.setFechaIngreso(new Date(System.currentTimeMillis()));
                formulario.setUltimaEdicion(formulario.getFechaIngreso());
                formulario.setDireccionFotografia("C:");

                Usuario nuevo = traerUsuario(usuarioFormulario, cargo);
                if (nuevo != null) { //verifica que se tenga al usuario.                    
                    formulario.setUsuarioidUsuario1(nuevo);
                    formulario.setUsuarioidUsuario(nuevo);
                } else {  //el usuario recibido no es valido.
                    logger.exiting(this.getClass().getName(), "crearFormulario ", false + ", se requiere un usuario valido");
                    return false;
                }
                logger.info("se inicia inserción del nuevo formulario");
                formularioFacade.create(formulario);
                logger.info("formulario insertado");
                logger.exiting(this.getClass().getName(), "crearFormulario ", true);
                return true;
            } else {
                logger.exiting(this.getClass().getName(), "crearFormulario ", false + ", nue ya existe");
                return false;
            }
        } else {
            logger.exiting(this.getClass().getName(), "crearFormulario ", false + ", se requiere nue");
            return false;
        }
    }

    @Override
    public boolean crearTraslado(int nue, Usuario usuarioEntrega, Usuario usuarioRecibe, Date fechaT, String obs, String motivo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearTraslado");
        if (nue > 0 && usuarioEntrega != null && usuarioRecibe != null && motivo != null) {//verificando que los datos de entreada no sean nulos.
            //traer usuarios, motivo
            TipoMotivo motivoP = tipoMotivoLocal.findByTipoMotivo(motivo);
            if (motivoP == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Motivo de Traslado");
                return false;
            }
            Usuario usuarioEntregaP = usuarioFacade.findByRUN(usuarioEntrega.getRutUsuario());
            Usuario usuarioRecibeP = usuarioFacade.findByRUN(usuarioRecibe.getRutUsuario());

            if (usuarioEntregaP == null) { //el usuario no esta en la bd
                usuarioEntregaP = crearExterno(usuarioEntrega);
                if (usuarioEntregaP == null) {
                    logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Usuario Entrega");
                    return false;
                }
            }
            if (usuarioRecibeP == null) {
                usuarioRecibeP = crearExterno(usuarioRecibe);
                if (usuarioEntregaP == null) {
                    logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Usuario Recibe");
                    return false;
                }
            }
            Formulario formulario = formularioFacade.find(nue);
            if (formulario == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Formulario");
                return false;
            }
            Traslado nuevoTraslado = new Traslado();
            nuevoTraslado.setFechaEntrega(fechaT);
            nuevoTraslado.setFormularioNUE(formulario);
            nuevoTraslado.setObservaciones(obs);
            nuevoTraslado.setTipoMotivoidMotivo(motivoP);
            nuevoTraslado.setUsuarioidUsuario(usuarioRecibeP);
            nuevoTraslado.setUsuarioidUsuario1(usuarioEntregaP);
            logger.info("se inicia insercion del nuevo traslado");
            trasladoFacade.create(nuevoTraslado);
            logger.info("se finaliza insercion del nuevo traslado");
            logger.exiting(this.getClass().getName(), "crearTraslado", true);
            return true;
        }
        logger.exiting(this.getClass().getName(), "crearTraslado", false);
        return false;
    }

//    private TipoMotivo traerMotivo(String motivo){
//        logger.setLevel(Level.ALL);
//        logger.entering(this.getClass().getName(), "traerMotivo", motivo);
//        TipoMotivo retorno = tipoMotivoLocal.findByTipoMotivo(motivo);
//        if(retorno != null){
//            logger.exiting(this.getClass().getName(), "traerMotivo", retorno.toString());
//            return retorno;
//        }
//        logger.exiting(this.getClass().getName(), "traerMotivo", null);
//        return null;
//    }
    @Override
    public boolean crearTraslado(Usuario usuarioEntrega, Usuario usuarioRecibe, Date fecha, String motivo, String observacionesT, String descripcionEspecie, String cargoEntrega, String cargoRecibe, int nue) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearTraslado");
        usuarioEntrega = traerUsuario(usuarioEntrega, cargoEntrega);
        usuarioRecibe = traerUsuario(usuarioRecibe, cargoRecibe);
        Traslado nuevoTraslado = new Traslado();
        if (usuarioEntrega != null && usuarioRecibe != null && nue != 0) {
            nuevoTraslado.setFechaEntrega(fecha);
            nuevoTraslado.setObservaciones(observacionesT);

            nuevoTraslado.setTipoMotivoidMotivo(tipoMotivoLocal.find(1));
            nuevoTraslado.setUsuarioidUsuario1(usuarioEntrega);
            nuevoTraslado.setUsuarioidUsuario(usuarioRecibe);
            nuevoTraslado.setFormularioNUE(formularioFacade.find(nue));

            //nuevoTraslado.setIdInvolucrado(Integer.MIN_VALUE);
            trasladoFacade.create(nuevoTraslado);
            logger.exiting(this.getClass().getName(), "crearTraslado", true);
            return true;
        }
        logger.exiting(this.getClass().getName(), "crearTraslado", false);
        return false;
    }

    private Cargo traerCargo(String nombreCargo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "traerCargo", nombreCargo);
        Cargo retorno = cargoFacade.findByCargo(nombreCargo);
        if (retorno == null) {//el cargo no existe, entocnes lo creamos.
            Cargo cNuevo = new Cargo();
            cNuevo.setNombreCargo(nombreCargo);
            //cNuevo.setIdCargo(Integer.MIN_VALUE);
            logger.info("se procede a crear cargo");
            cargoFacade.create(cNuevo);
            logger.info("nuevo cargo insertado");
            retorno = cargoFacade.findByCargo(nombreCargo);//lo buscamos de nuevo para traerlo con su correspondiente id.
            logger.exiting(this.getClass().getName(), "traerCargo", retorno.toString());
            return retorno;
        }
        logger.exiting(this.getClass().getName(), "traerCargo", null);
        return retorno;
    }

    private Usuario traerUsuario(Usuario usuario, String cargo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "traerUsuario", usuario + " " + cargo);
        if (usuario != null && usuario.getRutUsuario() != null) {
            Usuario retorno = usuarioFacade.findByRUN(usuario.getRutUsuario());
            if (retorno == null) { //el usuario no se encuentra en la BD, se agrega como usuario Externo.
                retorno = usuario;

                retorno.setEstadoUsuario(true);
                retorno.setIdUsuario(Integer.MIN_VALUE);
                //buscar si existe el cargo que se le adjudica
                Cargo c = traerCargo(cargo);
                //ahora que el cargo se encuentra en la bd, lo asignamos al usuario                   
                retorno.setCargoidCargo(c);

                retorno.setAreaidArea(areaFacade.find(1));
                retorno.setTipoUsuarioidTipoUsuario(tipoUsuarioFacade.find(1));
                //persistir usuario
                logger.info("se inicia inserción del nuevo usuario");
                usuarioFacade.create(retorno);
                logger.info("finaliza inserción del nuevo usuario");
                retorno = usuarioFacade.findByRUN(usuario.getRutUsuario()); //traemos al usuario desde la db con su correspondiente id asignado.
            }
            logger.exiting(this.getClass().getName(), "traerUsuario", retorno.toString());
            return retorno;
        } else {
            logger.exiting(this.getClass().getName(), "traerUsuario", null);
            return null;
        }
    }
}
