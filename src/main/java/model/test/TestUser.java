package model.test;

import model.crud.HouseCRUD;
import model.crud.UserCRUD;
import model.schemas.House;
import model.schemas.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestUser {

    public static void main(String[] args){
        User user_add = new User("Jorge", "jorge@hotmail.com", "holamundo", "What is the name of my dog?", "lucho");
        UserCRUD uc = new UserCRUD();
        User user = uc.getUser("axel@axel.com");

        System.out.println(user.toString());
        uc.createUser(user_add);
    }
    private static EntityManagerFactory emf;
    private static EntityManager manager;

    public static void test() {
        emf = Persistence.createEntityManagerFactory("DomoticaModel");
        manager = emf.createEntityManager();


        List<User> userList = manager.createQuery("from User").getResultList();
        for (User u:userList) {
            System.out.println(u.toString());
        }

    }
}
