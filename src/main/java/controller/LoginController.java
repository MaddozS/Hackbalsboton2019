package controller;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import controller.exceptions.EmptyException;
import model.crud.UserCRUD;
import model.schemas.User;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import view.Login;

import javax.persistence.NoResultException;
import javax.swing.*;
import java.sql.SQLException;
import java.util.*;

public final class LoginController {
    //Validates the input and send it to the model

    public static void showLoginView(){
        Login login = new Login();
        login.setVisible(true);
    }

    public static void LoginUser(Login view){
        boolean result = false;
        User user;

        Map<String, String> data = new HashMap<>();
        data.put("email", view.getEmailText());
        data.put("password", view.getPasswordText());

        if (!validCompleteness(data)) {
            view.emptyField();
        }
        else {
            try{
                UserCRUD model = new UserCRUD();
                user = model.getUser(data.get("email"));
                if(user.getPassword().equals(data.get("password"))){
                    view.showPanelView();
                }else{
                    view.noValidInput();
                }
                model.closeCRUD();
            }
            catch (ExceptionInInitializerError | NoClassDefFoundError  ex){
                System.out.println(ex.getCause());
                view.connectionError();
            }
            catch(NoResultException ex){
                System.out.println(ex.getMessage());
                view.noValidInput();
            }
        }
        
    }

    //Validate that there is no empty information
    public static boolean validCompleteness(Map<String, String> data){
        boolean isComplete = false;
        Set<String> keys = data.keySet();
        for(String key: keys){
            if(!data.get(key).isEmpty()){ //Checks that there is no empty information
                isComplete = true;
            }
        }
        return isComplete;
    }
}


