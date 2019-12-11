/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.deprecated;

import javax.swing.JOptionPane;

import controller.deprecated.AnswerQuestionController;
import model.crud.UserCRUD;
import model.schemas.User;
import view.ForgotPassword;

/**
 *
 * @author axel_
 */
public class ForgotPasswordController {
                
    public boolean checkUserExistence(ForgotPassword view){
        boolean result = false;
        UserCRUD model = new UserCRUD();
        
        try{
            User userToChange = model.getUser(view.getEmail());
            AnswerQuestionController answerController = new AnswerQuestionController();
            answerController.setUserToChange(userToChange);
            view.showQuestionWindow(userToChange.getQuestion());
            result = true;
        }
        catch(Exception ex){
            result = false;
            showErrorEmail(ex, view);
        }
        
        return result;
    }
    
    public void showErrorEmail(Exception ex, ForgotPassword view){
        JOptionPane.showMessageDialog(
                view, "Any user with this email", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    
}
