/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.TipoMotivo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aracelly
 */
@Stateless
public class TipoMotivoFacade extends AbstractFacade<TipoMotivo> implements TipoMotivoFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_sml1-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoMotivoFacade() {
        super(TipoMotivo.class);
    }
    
}
