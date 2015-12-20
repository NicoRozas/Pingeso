/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface FormularioEJBLocal {

    public Formulario findFormularioByNue(int nueAbuscar);

    public List<Traslado> traslados(Formulario formulario);

    public String crearTraslado(Formulario formulario, String usuarioEntrega, String usuarioEntregaUnidad, String usuarioEntregaCargo, String usuarioEntregaRut, String usuarioRecibe, String usuarioRecibeUnidad, String usuarioRecibeCargo, String usuarioRecibeRut, Date fechaT, String observaciones, String motivo);

    public String crearFormulario(String ruc, String rit, int nue, int nParte, String cargo, String delito, String direccionSS, String lugar, String unidad, String levantadoPor, String rut, Date fecha, String observacion, String descripcion, Usuario digitador);
}
