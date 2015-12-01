/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Formulario;
import entity.Usuario;
import javax.ejb.Local;

/**
 *
 * @author Aracelly
 */
@Local
public interface FormularioEJBLocal {
    public boolean crearFormulario(Formulario formulario, Usuario usuarioFormulario, String cargo);
}
