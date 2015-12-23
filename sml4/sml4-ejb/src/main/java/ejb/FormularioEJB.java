/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Area;
import entity.Cargo;
import entity.EdicionFormulario;
import entity.Formulario;
import entity.TipoMotivo;
import entity.TipoUsuario;
import entity.Traslado;
import entity.Usuario;
import facade.AreaFacadeLocal;
import facade.CargoFacadeLocal;
import facade.EdicionFormularioFacadeLocal;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private TipoUsuarioFacadeLocal tipoUsuarioFacade;
    @EJB
    private AreaFacadeLocal areaFacade;
    @EJB
    private TrasladoFacadeLocal trasladoFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private TipoMotivoFacadeLocal tipoMotivoFacade;
    @EJB
    private FormularioFacadeLocal formularioFacade;
    @EJB
    private EdicionFormularioFacadeLocal edicionFormularioFacade;

    static final Logger logger = Logger.getLogger(FormularioEJB.class.getName());

    @Override
    public List<EdicionFormulario> listaEdiciones(int nue, int idUser){
        List<EdicionFormulario> lista = new ArrayList();
        List<EdicionFormulario> response = new ArrayList();
        lista = edicionFormularioFacade.findAll();
        
        for(int i = 0; i < lista.size() ; i++){
            
            if(lista.get(i).getUsuarioidUsuario().getIdUsuario() == idUser && lista.get(i).getFormularioNUE().getNue() == nue){
                response.add(lista.get(i));
            }
        }
        
        if(response.isEmpty()){
            response = null;
        }
        
        return response;    
    }
    
    @Override
    public Formulario findFormularioByNue(int nueAbuscar) {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findFormularioByNue", nueAbuscar);

        Formulario formulario = formularioFacade.findByNue(nueAbuscar);
        if (formulario != null) {
            logger.exiting(this.getClass().getName(), "findFormularioByNue", formulario.toString());
            return formulario;
        }
        logger.exiting(this.getClass().getName(), "findFormularioByNue", "Error con formulario");
        return null;
    }

    @Override
    public String crearTraslado(Formulario formulario, String usuarioEntrega, String usuarioEntregaUnidad, String usuarioEntregaCargo, String usuarioEntregaRut, String usuarioRecibe, String usuarioRecibeUnidad, String usuarioRecibeCargo, String usuarioRecibeRut, Date fechaT, String observaciones, String motivo, Usuario uSesion) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearTraslado");

        if (usuarioEntrega == null || usuarioEntregaUnidad == null || usuarioEntregaCargo == null || usuarioEntregaRut == null || usuarioRecibe == null || usuarioRecibeUnidad == null || usuarioRecibeCargo == null || usuarioRecibeRut == null || motivo == null) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Campos null");
            return "Faltan campos";
        }

        //Validando usuario que entrega
        if (!val(usuarioEntregaRut) || !soloCaracteres(usuarioEntrega) || !soloCaracteres(usuarioEntregaUnidad) || !soloCaracteres(usuarioEntregaCargo)) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error verificacion datos usuario entrega");
            return "Error datos usuario entrega";
        }

        //Validando usuario que recibe
        if (!val(usuarioRecibeRut) || !soloCaracteres(usuarioRecibe) || !soloCaracteres(usuarioRecibeUnidad) || !soloCaracteres(usuarioRecibeCargo)) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error verificacion datos usuario recibe");
            return "Error datos usuario recibe";
        }

        //Busco todo slos traslados del formulario
        List<Traslado> trasladoList = traslados(formulario);

        //Comparando fechas entre traslados
        if (!trasladoList.isEmpty() && !compareFechas(fechaT, trasladoList.get(trasladoList.size() - 1).getFechaEntrega())) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Fecha");
            return "Error, la fecha del nuevo traslado debe ser igual o posterior a la ultima fecha de traslado.";
        }

        //Comparando fecha entre traslado y formulario
        if (!compareFechas(fechaT, formulario.getFechaOcurrido())) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Fecha");
            return "Error, la fecha de traslado debe ser igual o posterior a la fecha del formulario.";
        }

        //traer usuarios, motivo
        TipoMotivo motivoP = tipoMotivoFacade.findByTipoMotivo(motivo);
        if (motivoP == null) {
            logger.exiting(this.getClass().getName(), "crearTraslado", "Error con Motivo de Traslado");
            return "Error, se requiere especificar Motivo del traslado.";
        }

        Usuario usuarioEntregaP = null;
        Usuario usuarioRecibeP = null;

        //Verificando usuario Entrega
        usuarioEntregaP = usuarioFacade.findByRUN(usuarioEntregaRut);

        if (usuarioEntregaP == null) {
            usuarioEntregaP = crearExterno1(usuarioEntregaCargo, usuarioEntregaUnidad, usuarioEntrega, usuarioEntregaRut);
            if (usuarioEntregaP == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con creacion Usuario Entrega");
                return "Error con datos de la persona que entrega.";
            }
        } else {
            if (!usuarioEntregaP.getNombreUsuario().equals(usuarioEntrega) || !usuarioEntregaP.getUnidad().equals(usuarioEntregaUnidad) || !usuarioEntregaP.getCargoidCargo().getNombreCargo().equals(usuarioEntregaCargo)) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con verificacion Usuario Entrega");
                return "Datos no coinciden con el rut ingresado";
            }

        }
        //Verificando usuario Recibe
        usuarioRecibeP = usuarioFacade.findByRUN(usuarioRecibeRut);
        if (usuarioRecibeP == null) {
            usuarioRecibeP = crearExterno1(usuarioRecibeCargo, usuarioRecibeUnidad, usuarioRecibe, usuarioRecibeRut);
            if (usuarioRecibeP == null) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con creacion usuario Recibe");
                return "Error con datos de la persona que recibe.";
            }
        } else {
            if (!usuarioRecibeP.getNombreUsuario().equals(usuarioRecibe) || !usuarioRecibeP.getUnidad().equals(usuarioRecibeUnidad) || !usuarioRecibeP.getCargoidCargo().getNombreCargo().equals(usuarioRecibeCargo)) {
                logger.exiting(this.getClass().getName(), "crearTraslado", "Error con verificacion usuario Recibe");
                return "Datos no corresponden al rut";
            }

        }

        //Creando traslado
        Traslado nuevoTraslado = new Traslado();
        nuevoTraslado.setFechaEntrega(fechaT);
        nuevoTraslado.setFormularioNUE(formulario);
        nuevoTraslado.setObservaciones(observaciones);
        nuevoTraslado.setTipoMotivoidMotivo(motivoP);
        nuevoTraslado.setUsuarioidUsuario(usuarioRecibeP);
        nuevoTraslado.setUsuarioidUsuario1(usuarioEntregaP);

        if (nuevoTraslado.getTipoMotivoidMotivo().getTipoMotivo().equals("Peritaje")) {

            if (uSesion.getCargoidCargo().getNombreCargo().equals("Tecnico") || uSesion.getCargoidCargo().getNombreCargo().equals("Perito")) {
                logger.info("se inicia insercion del nuevo traslado y fin de la cadena");
                trasladoFacade.create(nuevoTraslado);
                logger.info("se finaliza insercion del nuevo traslado y fin de la cadena");
                logger.exiting(this.getClass().getName(), "crearTraslado", "Exito");
                return "Fin";
            }
        }

        logger.info("se inicia insercion del nuevo traslado");
        trasladoFacade.create(nuevoTraslado);
        logger.info("se finaliza insercion del nuevo traslado");
        logger.exiting(this.getClass().getName(), "crearTraslado", "Exito");
        return "Exito";

    }

    private boolean compareFechas(Date fechaT, Date fechaFormulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "compareFechas");
        if (fechaT != null && fechaFormulario != null) {
            Date dateTraslado = fechaT;
            Date dateFormulario = fechaFormulario;
            if (dateTraslado.equals(dateFormulario) || dateTraslado.after(dateFormulario)) {
                logger.exiting(this.getClass().getName(), "compareFechas", true);
                return true;
            }
        } else {
            logger.severe("Error con fechas");
        }
        logger.exiting(this.getClass().getName(), "compareFechas", false);
        return false;
    }

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

    private boolean soloCaracteres(String palabra) {

        Pattern patron = Pattern.compile("[^A-Za-z ]");
        Matcher encaja = patron.matcher(palabra);

        if (!encaja.find()) {
            System.out.println("solo tiene letras y espacio!");
            return true;
        } else {
            System.out.println("tiene otra cosa");
            return false;
        }

    }

    private Usuario crearExterno(Usuario usuario, String cargo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearExterno");

        if (usuario != null) {
            Usuario nuevoExterno = usuario;
            Area areaExterno = areaFacade.findByArea("Otro");
            TipoUsuario tue = tipoUsuarioFacade.findByTipo("Externo");
            Cargo cargoExterno = cargoFacade.findByCargo(cargo);
            if (cargoExterno == null) {
                Cargo nuevo = new Cargo();
                nuevo.setNombreCargo(cargo);
                cargoFacade.create(nuevo);
                cargoExterno = cargoFacade.findByCargo(cargo);
            }

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

    //ZACK
    private Usuario crearExterno1(String cargo, String unidad, String nombre, String rut) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "crearExterno");

        Usuario nuevoExterno = new Usuario();
        Area areaExterno = areaFacade.findByArea("Otro");
        TipoUsuario tue = tipoUsuarioFacade.findByTipo("Externo");
        //buscando cargo, en el caso que no exista se crea
        Cargo cargoExterno = cargoFacade.findByCargo(cargo);
        if (cargoExterno == null) {
            Cargo nuevo = new Cargo();
            nuevo.setNombreCargo(cargo);
            cargoFacade.create(nuevo);
            cargoExterno = cargoFacade.findByCargo(cargo);
        }

        //APELLIDO ?
        nuevoExterno.setUnidad(unidad);
        nuevoExterno.setNombreUsuario(nombre);
        nuevoExterno.setRutUsuario(rut);
        nuevoExterno.setAreaidArea(areaExterno);
        nuevoExterno.setCargoidCargo(cargoExterno);
        nuevoExterno.setTipoUsuarioidTipoUsuario(tue);
        nuevoExterno.setEstadoUsuario(Boolean.TRUE);
        nuevoExterno.setMailUsuario("na");
        nuevoExterno.setPassUsuario("na");
        logger.finest("se inicia la persistencia del nuevo usuario externo");
        usuarioFacade.create(nuevoExterno);
        logger.finest("se finaliza la persistencia del nuevo usuario externo");

        nuevoExterno = usuarioFacade.findByRUN(rut);
        if (nuevoExterno != null) {
            logger.exiting(this.getClass().getName(), "crearExterno", nuevoExterno.toString());
            return nuevoExterno;
        }
        logger.exiting(this.getClass().getName(), "crearExterno", null);
        return null;
    }

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

    @Override
    public List<Traslado> traslados(Formulario formulario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "traslados", formulario.toString());
        List<Traslado> retorno = trasladoFacade.findByNue(formulario);
        if (retorno == null) {
            logger.exiting(this.getClass().getName(), "traslados", null);
            return null;
        } else {
            logger.exiting(this.getClass().getName(), "traslados", retorno.size());
            return retorno;
        }
    }

    //ZACK
    //Función que crea el formulario 
    @Override
    public String crearFormulario(String ruc, String rit, int nue, int nParte, String cargo, String delito, String direccionSS, String lugar, String unidad, String levantadoPor, String rut, Date fecha, String observacion, String descripcion, Usuario digitador) {

        //Verificando nue
        if (nue > 0) {
            //Verificando si existe un formulario con ese nue
            Formulario verificar = formularioFacade.findByNue(nue);
            if (verificar != null) {
                return "Formulario existente";
            }
        } else {
            return "Nue inválido";
        }

        //Verificando rit y ruc
        //checkeo ruc y rit
        if (!checkRucOrRit(ruc) || !checkRucOrRit(rit)) {
            logger.exiting(this.getClass().getName(), "crearFormulario", "Error con RUC o RIT");
            return "Error con RUC o RIT.";
        }

        //Verificando que los campos sean string
        if (!soloCaracteres(cargo) || !soloCaracteres(delito) || !soloCaracteres(unidad) || !soloCaracteres(levantadoPor) || !val(rut)) {

            return "Campos erroneos";
        }

        //ruc - rit- nparte - obs y descripcion no son obligatorios
        Usuario usuarioIngresar = new Usuario();
        //Verificando en la base de datos si existe el usuario con ese rut
        usuarioIngresar = usuarioFacade.findByRUN(rut);

        if (usuarioIngresar == null) {
            //quiero decir que no existe
            usuarioIngresar = crearExterno1(cargo, unidad, levantadoPor, rut);

            if (usuarioIngresar == null) {
                return "No se pudo crear el nuevo usuario";
            }
        } else {
            //Existe, y hay que verificar que los datos ingresador concuerdan con los que hay en la base de datos
            if (!usuarioIngresar.getCargoidCargo().getNombreCargo().equals(cargo) || !usuarioIngresar.getUnidad().equals(unidad) || !usuarioIngresar.getNombreUsuario().equals(levantadoPor)) {
                return "Datos nos coinciden con el rut";
            }
        }

        Formulario nuevoFormulario = new Formulario();

        nuevoFormulario.setDireccionFotografia("C:");
        nuevoFormulario.setFechaIngreso(new Date(System.currentTimeMillis()));
        nuevoFormulario.setFechaOcurrido(fecha);
        nuevoFormulario.setUltimaEdicion(nuevoFormulario.getFechaIngreso());
        nuevoFormulario.setUsuarioidUsuario(digitador); // Usuario digitador
        nuevoFormulario.setUsuarioidUsuario1(usuarioIngresar); //Usuario inicia
        nuevoFormulario.setDescripcionEspecieFormulario(descripcion);
        nuevoFormulario.setObservaciones(observacion);
        nuevoFormulario.setDelitoRef(delito);
        nuevoFormulario.setNue(nue);
        nuevoFormulario.setNumeroParte(nParte);
        nuevoFormulario.setDelitoRef(delito);
        nuevoFormulario.setRuc(ruc);
        nuevoFormulario.setRit(rit);
        nuevoFormulario.setLugarLevantamiento(lugar);
        nuevoFormulario.setDireccionSS(direccionSS);

        logger.finest("se inicia la persistencia del nuevo formulario");
        formularioFacade.create(nuevoFormulario);
        logger.finest("se finaliza la persistencia del nuevo formulario");
        logger.exiting(this.getClass().getName(), "crearFormulario", true);
        return "Exito";

    }
    
    @Override
    public String edicionFormulario(Formulario formulario, String obsEdicion, Usuario usuarioSesion){
    
        if(obsEdicion == null){
            return "Se requiere la observación";
        }

        //Creando el objeto edicion
        
        EdicionFormulario edF = new EdicionFormulario();
        
        edF.setFormularioNUE(formulario);
        edF.setUsuarioidUsuario(usuarioSesion);
        edF.setObservaciones(obsEdicion);
        edF.setFechaEdicion(new Date(System.currentTimeMillis()));
        
        //Actualizando ultima edicion formulario
        formulario.setUltimaEdicion(edF.getFechaEdicion());
        
        edicionFormularioFacade.edit(edF);
        formularioFacade.edit(formulario);
       
        return "Exito";
    }

}