package controller;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import controller.exceptions.EmptyException;
import model.crud.UserCRUD;
import model.schemas.User;
import org.hibernate.exception.ConstraintViolationException;
import view.Register;

import javax.persistence.PersistenceException;
import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;

public class RegisterController {
    public static void addUser(Register view){
        User nuser = new User();
        nuser.setName(view.getNombre());
        nuser.setEmail(view.getCorreo());
        nuser.setPassword(view.getContrasena());
        nuser.setQuestion(view.getSecurityQuestion());
        nuser.setAnswer(view.getSecurityAnswer());

        Map<String, String> data = new HashMap<>();
        data.put("nombre", view.getNombre());
        data.put("correo", view.getCorreo());
        data.put("contrasena", view.getContrasena());
        data.put("conf_contrasena", view.getConfcontrasena());

        int campos = 0; //Verificacion campos
        int correo = 0; //Verificacion correo
        int contra = 0; //Verificacion contraseña
        int debounce = 0; //Mostrar solo un mensaje
        //Aunque no muestre el mensaje aun asi retorna la excepcion

        //confirmacion de todos los espacios fueron rellenados
        try {
            Set<String> llave = data.keySet();
            for (String key : llave) {
                if(data.get(key).isEmpty()) {
                    throw new NullPointerException();
                }
                else {
                    campos = 1;
                }
            }
        }
        catch(NullPointerException ex) {
            if (debounce == 0) {
                debounce = 1;
                view.emptyField();
            }
        }
        //verificacion de que es un correo
            int verif = 0;
            verif = data.get("correo").indexOf("@");

            if (verif != -1) {
                correo = 1;
            }
            else {
                if (debounce == 0) {
                    debounce = 1;
                    view.noValidEmail();
                }
            }

        //confirmacion de contrase?a
        if (data.get("contrasena").equals(data.get("conf_contrasena"))) {
            contra = 1;
        }
        else {
            if (debounce == 0) {
                debounce = 1;
                view.noMatch();
            }
        }

        //envia la informacion si todo es correcto
        if(campos == 1 && correo == 1 && contra == 1 && debounce == 0){
            try{
                UserCRUD crudnu = new UserCRUD();
                crudnu.createUser(nuser);
                crudnu.closeCRUD();
            }
            catch(Exception ex){
                Throwable cause = ex.getCause();
                if (cause instanceof ExceptionInInitializerError) {
                    System.out.println(cause.getMessage());
                    view.connectionError();
                }
                else if (cause instanceof NoClassDefFoundError) {
                    System.out.println(cause.getMessage());
                    view.connectionError();
                }
                else if (cause instanceof ConstraintViolationException) {
                    System.out.println(cause.getMessage());
                    view.alreadyExist();
                }
                else if(cause instanceof CommunicationsException){
                    System.out.println(ex.getCause());
                    view.connectionError();
                }
                else{
                    System.out.println(ex.getCause());
                    view.showError(ex);
                }
            }
        }
    }
}
