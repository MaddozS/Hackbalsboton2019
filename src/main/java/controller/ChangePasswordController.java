/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JOptionPane;
import model.crud.UserCRUD;
import model.schemas.User;
import view.ForgotPassword;
import view.changePasswordView;

/**
 *
 * @author axel_
 */
public class ChangePasswordController {
    private User userToChange;

    public void setUserToChange(User userToChange) {
        this.userToChange = userToChange;
    }
    
    public boolean changePassword(changePasswordView view){
        UserCRUD model = new UserCRUD();
        boolean result = false;
        if(!(view.getNewPassword().equals("")) || !(view.getNewPasswordConfirm().equals(""))){
            if( view.getNewPassword().equals(view.getNewPasswordConfirm()) ){
                userToChange.setPassword(view.getNewPassword());
                
                try{
                    model.updateUser(userToChange);
                    result = true;
                }catch (Exception ex){
                    result = false;
                    UnexpectedError(ex, view);
                }
            }else{
                result = false;
                PasswordError(view);
            }
        }
        else{
            result = false;
            EmptyField(view);
        }
        
        return result;
    }
    
    public void PasswordError(changePasswordView view){
        JOptionPane.showMessageDialog(
                view, "The inputs doesn't match", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    public void UnexpectedError(Exception ex, changePasswordView view){
        JOptionPane.showMessageDialog(
                view, "An unexpected error happend", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    public void EmptyField(changePasswordView view){
        JOptionPane.showMessageDialog(
                view, "An input is empty", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
