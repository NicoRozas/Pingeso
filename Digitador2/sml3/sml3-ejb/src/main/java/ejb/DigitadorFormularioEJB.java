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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    //Función para verificar la existencia de un usuario en el sistema
    @Override
    public boolean verificarUser(String user, String pass) {

        //Busco al usuario la cuenta del usuario 
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(user);

        //Si lo encuentro verifico si la contraseña es igual a la que se ingreso
        if (foundUser != null) {
            if (foundUser.getPassUsuario().equals(pass)) {
                return true;
            }
        }
        return false;
    }

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

    //DJZack estuvo aquí
    //Función que verifica el ruc y el rit, solamente entrega true en los siguientes casos (513-21321) y ().
    private boolean checkRucOrRit(String rucOrRit) {

        if (rucOrRit.equals("")) {
            return true;
        }
        int largoTotal = rucOrRit.length();
        String lastGuion = "" + rucOrRit.charAt(largoTotal - 1);
        String[] numeros = rucOrRit.split("-");
        int largoN = numeros.length;
        int largoInterno = 0;
        System.out.println("Largo: " + largoN);

        if (lastGuion.equals("-")) {
            return false;
        }
        if (largoN == 2) {
            for (int i = 0; i < largoN; i++) {
                largoInterno = numeros[i].length();
                if (largoInterno == 0) {
                    return false;
                }
                for (int j = 0; j < largoInterno; j++) {
                    if (numeros[i].charAt(j) < 48 || numeros[i].charAt(j) > 57) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;

    }

    //DJZack estuvo aquí
    //Función que verifica el rut, entrega true solo con el siguiente formato (18486956k) sin puntos ni guión.
    private boolean val(String rut) {

        int contadorPuntos = 0;
        int contadorGuion = 0;

        int largoR = rut.length();

        //Verifico que no tenga puntos y que tenga 1 solo guion
        for (int i = 0; i < largoR; i++) {
            if (rut.charAt(i) == 46) {
                contadorPuntos++;
            }
            if (rut.charAt(i) == 45) {
                contadorGuion++;
            }

        }

        if (contadorPuntos > 0 || contadorGuion > 0) {
            return false;
        }

        try {
            rut = rut.toUpperCase();
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                return true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean crearFormulario(Formulario formulario, Usuario usuarioIniciaF) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearFormulario");
        if (formulario != null && usuarioIniciaF != null && formulario.getNue() > 0) {
            Formulario formularioP = formularioFacade.find(formulario.getNue()); //verificamos que no haya otro formulario con el mismo nue
            if (formularioP == null) { //significa que no hay otro formulario con el mismo nue
                Usuario usuarioIniciaP = null;
                //Verificando el rut
                if (val(usuarioIniciaF.getRutUsuario())) {
                    usuarioIniciaP = usuarioFacade.findByRUN(usuarioIniciaF.getRutUsuario()); //buscamos al usuario que ingresa el formulario
                }
                else{
                    return false;
                }

                if (usuarioIniciaP == null) { //significa que el usuario no existe en la bd.
                    usuarioIniciaP = crearExterno(usuarioIniciaF);
                }
                Formulario nuevoFormulario = formulario;
                
                /*Validar RUC Y RIT LA FUNCION ETA IMPLEMENTADA*/
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

    //
    private boolean compareFechas(Date fechaT, Date fechaFormulario) {

        Date dateTraslado = fechaT;
        Date dateFormulario = fechaFormulario;
        if (dateTraslado.equals(dateFormulario) || dateTraslado.after(dateFormulario)) {
            return true;
        }
        return false;
    }

    //OJO AGREGUE UN NUEVO PARAMETRO QUE ES LA FECHA DEL FORMULARIO PARA COMPARIR LA FECHA DEL TRASLADO CON LA DEL FORMULARIO  
    @Override
    public boolean crearTraslado(int nue, Usuario usuarioEntrega, Usuario usuarioRecibe, Date fechaT, Date fechaFormulario, String obs, String motivo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearTraslado");
        if (nue > 0 && usuarioEntrega != null && usuarioRecibe != null && motivo != null && compareFechas(fechaT, fechaFormulario)) {//verificando que los datos de entreada no sean nulos.
            //traer usuarios, motivo
            TipoMotivo motivoP = tipoMotivoLocal.findByTipoMotivo(motivo);
            if (motivoP == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Motivo de Traslado");
                return false;
            }

            Usuario usuarioEntregaP = null;
            Usuario usuarioRecibeP = null;
            //Validando rut de los usuarios ingresados
            if (val(usuarioEntrega.getRutUsuario()) && val(usuarioRecibe.getRutUsuario())) {
                usuarioEntregaP = usuarioFacade.findByRUN(usuarioEntrega.getRutUsuario());
                usuarioRecibeP = usuarioFacade.findByRUN(usuarioRecibe.getRutUsuario());
            } else {
                return false;
            }

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

}
