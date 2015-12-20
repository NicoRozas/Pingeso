/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author sebastian
 */
@Named(value = "testMB")
@RequestScoped
@ManagedBean
public class testMB {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;
    }

    public String getSayWelcome() {

        if ("".equals(name) || name == null) {
            return "";
        } else {

            return "Ajax message : Welcome " + name;

        }

    }
}
