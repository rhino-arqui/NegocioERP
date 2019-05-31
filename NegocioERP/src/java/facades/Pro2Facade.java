/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Pro2;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mariana
 */
@Stateless
public class Pro2Facade extends AbstractFacade<Pro2> {

    @PersistenceContext(unitName = "NegocioERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Pro2Facade() {
        super(Pro2.class);
    }
    
}
