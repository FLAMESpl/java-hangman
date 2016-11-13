package pl.wikihangman.controllers;

import pl.wikihangman.models.User;
import pl.wikihangman.views.ExceptionLogger;
import pl.wikihangman.views.UserOptionReader;
import pl.wikihangman.views.enums.GameMenuEnum;
import pl.wikihangman.views.enums.LoginMenuEnum;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class MasterController {
   
   private final static String USERS_DB_PATH = ".\\db.txt";
   private final static String MENU_MESSAGE = "Available actions:";
   
   private final ExceptionLogger logger; 
   private User user;
    
   public MasterController() {
       logger = new ExceptionLogger(System.err);
       user = null;
   }
   
   public void runLoginScreen(String[] applicationArguments) {
       
       int selectedOptionIndex;
       boolean exit = false;
       LoginMenuEnum selectedOption;
       AuthenticationController authenticationController = new AuthenticationController(USERS_DB_PATH);
       UserOptionReader optionReader = new UserOptionReader(LoginMenuEnum.toStringArray(), MENU_MESSAGE);
       
       user = authenticationController.authenticate(applicationArguments);
       
       while(!exit) {
           if (user != null) {
               runGameScreen();
           }
           
           selectedOptionIndex = optionReader.read();
           try {
               selectedOption = LoginMenuEnum.values()[selectedOptionIndex];
           } catch (IndexOutOfBoundsException ex) {
               continue;
           }
           
           switch (selectedOption) {
               case EXIT:
                   exit = true;
                   break;
               case LOGIN:
                   user = authenticationController.authenticate();
                   break;
               case SIGNUP:
                   authenticationController.register();
                   break;
           }
       }
   }
   
   public void runGameScreen() {
       
       GameController gameController = new GameController(user);
       int selectedOptionIndex;
       boolean exit = false;
       GameMenuEnum selectedOption;
       UserOptionReader optionReader = new UserOptionReader(GameMenuEnum.toStringArray(), MENU_MESSAGE);
       
       while(!exit) {
           selectedOptionIndex = optionReader.read();
           try {
               selectedOption = GameMenuEnum.values()[selectedOptionIndex];
           } catch(IndexOutOfBoundsException ex) {
               continue;
           }
           
           switch(selectedOption) {
               case EXIT:
                   exit = true;
                   break;
               case LOGOUT:
                   exit = true;
                   user = null;
                   break;
               case SCORE:
                   gameController.requestScore(USERS_DB_PATH);
                   break;
           }
       }
   }
}
