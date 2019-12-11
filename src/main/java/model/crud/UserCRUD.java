/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import model.bootstraper.EMFBootstrapper;
import model.schemas.User;


/**
 *
 * @author joses
 */
public class UserCRUD {
    private EntityManager manager = EMFBootstrapper.openEntityManager();

    public void createUser(User user) throws PersistenceException{
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(user);
        transaction.commit();

        System.out.printf("se ha añadido con exito");
    }

    public User getUser(String email) throws PersistenceException{
        User user;
        user = (User) manager.createQuery("from User u where u.Email='" + email + "'").getSingleResult();
        return user;
    }
    
    public void deleteUser(User user) throws PersistenceException{
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        manager.remove(user);
        transaction.commit();
        System.out.printf("se ha eliminado con exito");

    }
    
    public void updateUser(User user) throws PersistenceException{
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.merge(user);
        transaction.commit();
        System.out.printf("se ha cambiado con exito");

    }

    public void closeCRUD(){
        manager.close();
    }
}
