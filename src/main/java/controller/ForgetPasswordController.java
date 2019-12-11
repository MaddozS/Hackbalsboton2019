package controller;

import model.crud.UserCRUD;
import model.schemas.User;
import view.ForgotPassword;
import view.answerSecurityQuestion;
import view.changePasswordView;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

public final class ForgetPasswordController {
    private static User userToChange;

    public static void checkUserExistence(ForgotPassword view){
        try{
            UserCRUD model = new UserCRUD();
            userToChange = model.getUser(view.getEmail());
            view.showQuestionWindow(userToChange.getQuestion());
            model.closeCRUD();
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
            else if (cause instanceof NoResultException){
                view.noUserFound();
            }
            else if (cause instanceof PersistenceException){
                view.connectionError();
            }
            else{
                System.out.println(ex.getCause());
                view.connectionError();
            }
        }
    }

    public static void checkAnswer(answerSecurityQuestion view){
        if(view.getAnswer().equals(userToChange.getAnswer()) ){
            view.showChangePasswordView();
        }
        else{
            view.showErrorAnswer();
        }
    }

    public static void changePassword(changePasswordView view){

        if(!(view.getNewPassword().equals("")) || !(view.getNewPasswordConfirm().equals(""))){
            if( view.getNewPassword().equals(view.getNewPasswordConfirm()) ){
                userToChange.setPassword(view.getNewPassword());
                try{
                    UserCRUD model = new UserCRUD();
                    model.updateUser(userToChange);
                    model.closeCRUD();
                    view.showLoginView();
                }
                catch (PersistenceException ex){
                    view.showError(ex);
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
                    else{
                        System.out.println(ex.getCause());
                        view.showError(ex);
                    }
                }
            }
            else{
                view.noMatch();
            }
        }
        else{
            view.emptyField();
        }
    }
}
