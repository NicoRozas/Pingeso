package entity;

import entity.Evidencia;
import entity.Formulario;
import entity.FormularioEvidenciaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-07T04:05:08")
@StaticMetamodel(FormularioEvidencia.class)
public class FormularioEvidencia_ { 

    public static volatile SingularAttribute<FormularioEvidencia, FormularioEvidenciaPK> formularioEvidenciaPK;
    public static volatile SingularAttribute<FormularioEvidencia, Formulario> formulario;
    public static volatile SingularAttribute<FormularioEvidencia, Integer> cantidad;
    public static volatile SingularAttribute<FormularioEvidencia, Evidencia> evidencia;

}