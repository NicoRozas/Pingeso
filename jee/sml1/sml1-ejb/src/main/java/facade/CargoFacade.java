/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Cargo;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

/**
 *
 * @author Aracelly
 */
@Stateless
public class CargoFacade extends AbstractFacade<Cargo> implements CargoFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_sml1-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoFacade() {
        super(Cargo.class);
    }
    
    @Override
    public Cargo findByCargo(String cargo) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByCargo", cargo);
        Cargo retorno = null;
        try{
            Query q = em.createNamedQuery("Cargo.findByNombreCargo", CargoFacade.class).setParameter("nombreCargo", cargo);
            retorno = (Cargo) q.getSingleResult();
        }catch(IllegalArgumentException e){
            logger.severe("CargoFacade: el nombre o el parametro de la Query no existe -> "+e);
        }
        catch(NoResultException e){
            logger.severe("No hay resultados -> "+e);
        }
        catch(NonUniqueResultException e){
            logger.severe("hay mas de un resulado -> "+e);
        }
        catch(IllegalStateException e){
            logger.severe("CargoFacade: ocurrio un problema con la consulta -> "+e);
        }
        catch(QueryTimeoutException e){
            logger.severe("CargoFacade: ocurrio un problema con la consulta -> "+e);
        }
        catch(TransactionRequiredException e){
            logger.severe("CargoFacade: ocurrio un problema con la consulta -> "+e);
        }
        catch(PessimisticLockException e){
            logger.severe("CargoFacade: ocurrio un problema con la consulta -> "+e);
        }
        catch(LockTimeoutException e){
            logger.severe("CargoFacade: ocurrio un problema con la consulta -> "+e);
        }
        catch(PersistenceException e){
            logger.severe("CargoFacade: ocurrio un problema con la consulta -> "+e);
        }
        if(retorno == null){
            logger.exiting(this.getClass().getName(), "findByCargo", null);
            return null;
        }else{
            logger.exiting(this.getClass().getName(), "findByCargo", retorno.toString());
            return retorno;
        }
}
}
