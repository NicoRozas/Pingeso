/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Formulario;
import entity.Traslado;
import entity.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface FormularioEJBLocal {
    public boolean crearFormulario(Formulario formulario, Usuario usuarioFormulario, String cargo);
    public boolean crearTraslado(Usuario usuarioEntrega, Usuario usuarioRecibe, String fecha, String hora, String motivo, String observacionesT , String descripcionEspecie, String cargoEntrega, String cargoRecibe, int nue);
    public List<Traslado> traslados(int nue);
}
