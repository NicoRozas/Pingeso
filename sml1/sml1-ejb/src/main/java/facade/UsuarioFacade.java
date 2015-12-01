/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_sml1-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public Usuario findByRUN(String run) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findByRUN", run);
        Usuario retorno = null;
        try{
            Query q = em.createNamedQuery("Usuario.findByRutUsuario", Usuario.class).setParameter("rutUsuario", run);
            //retorno = new Usuario();
            retorno = (Usuario) q.getSingleResult();
        }catch(IllegalArgumentException e){
            logger.severe("UsuarioFacade: el nombre o el parametro de la Query no existe -> "+e);
            retorno = null;
        }
        catch(NoResultException e){
            logger.severe("UsuarioFacade: No hay resultados -> "+e);
            retorno = null;
        }
        catch(NonUniqueResultException e){
            logger.severe("UsuarioFacade: hay mas de un resulado -> "+e);
            retorno = null;
        }
        catch(IllegalStateException e){
            logger.severe("UsuarioFacade: ocurrio un problema con la consulta -> "+e);
            retorno = null;
        }
        catch(QueryTimeoutException e){
            logger.severe("UsuarioFacade: ocurrio un problema con la consulta -> "+e);
            retorno = null;
        }
        catch(TransactionRequiredException e){
            logger.severe("UsuarioFacade: ocurrio un problema con la consulta -> "+e);
            retorno = null;
        }
        catch(PessimisticLockException e){
            logger.severe("UsuarioFacade: ocurrio un problema con la consulta -> "+e);
            retorno = null;
        }
        catch(LockTimeoutException e){
            logger.severe("UsuarioFacade: ocurrio un problema con la consulta -> "+e);
            retorno = null;
        }
        catch(PersistenceException e){
            logger.severe("UsuarioFacade: ocurrio un problema con la consulta -> "+e);
            retorno = null;
        }
        if(retorno == null){
            logger.exiting(this.getClass().getName(), "findByRUN", null);
            return null;
        }else{
            logger.exiting(this.getClass().getName(), "findByRUN", retorno.toString()+retorno.getCargoidCargo().getNombreCargo());
            return retorno;
        }
}
}