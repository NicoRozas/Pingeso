/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import ejb.FormularioEJBLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Aracelly
 */
@Named(value = "inicioSesion")
@RequestScoped
@ManagedBean
public class InicioSesion {

    /**
     * Creates a new instance of InicioSesion
     */
    @EJB
    FormularioEJBLocal formularioLocal;
    
    private String user;
    private String pass;
    
    public InicioSesion() {
    }

    public String login(){
        
        boolean response = formularioLocal.verificarUser(user, pass);
        
        if(response){
            return "digitadorFormulario.xhtml?faces-redirect=true";
        }
        
        return "Usuario o contraseña inválido";
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
}
