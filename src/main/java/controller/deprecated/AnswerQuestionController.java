/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.deprecated;

import javax.swing.JOptionPane;

import model.schemas.User;
import view.answerSecurityQuestion;

/**
 *
 * @author axel_
 */
public class AnswerQuestionController {
    private User userToChange;
    
    public void setUserToChange(User userToChange){
        this.userToChange = userToChange;
    }
    
    public void checkAnswer(answerSecurityQuestion view){
        if( view.getAnswer().equals(userToChange.getAnswer()) ){
            ChangePasswordController changePassController = new ChangePasswordController();
            changePassController.setUserToChange(userToChange);
            
            view.showChangePasswordView();
        }
        else{
            showErrorAnswer(view);
        }
    }
    
    public void showErrorAnswer(answerSecurityQuestion view){
        JOptionPane.showMessageDialog(
                view, "This is not the answer to your question, try again", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
}
