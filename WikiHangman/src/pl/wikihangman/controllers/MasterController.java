package pl.wikihangman.controllers;

import pl.wikihangman.models.User;
import pl.wikihangman.views.ExceptionLogger;
import pl.wikihangman.views.UserOptionReader;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class MasterController {
   
   private enum MenuOptionEnum {
       EXIT("exit"),
       LOGOUT("logout"),
       SCORE("score"),
       START("start");
       
       private final String value;
       
       private MenuOptionEnum(String value) {
           this.value = value;
       }
       
       public String getValue() {
           return value;
       }
       
       public static String[] toStringArray() {
           MenuOptionEnum[] values = values();
           int arrayLength = values.length;
           String[] result = new String[arrayLength];
           for (int i = 0; i < arrayLength; i++) {
               result[i] = values[i].getValue();
           }
           return result;
       }
   } 
    
   private final static String USERS_DB_PATH = ".\\db.txt";
   private final static String MENU_MESSAGE = "Available actions:";
   
   private final ExceptionLogger logger; 
   private User user;
    
   public MasterController() {
       logger = new ExceptionLogger(System.err);
       user = null;
   }
   
   public void run(String[] applicationArguments) {
       
       AuthenticationController authenticationController = new AuthenticationController(USERS_DB_PATH);
       GameController gameController = new GameController();
       UserOptionReader optionReader = new UserOptionReader(
               System.out, System.in, MenuOptionEnum.toStringArray(), MENU_MESSAGE);
       
       user = authenticationController.authenticate(applicationArguments);
       
       int selectedOptionIndex;
       boolean exit = false;
       MenuOptionEnum selectedOption;
       
       while(!exit) {
           selectedOptionIndex = optionReader.read();
           try {
               selectedOption = MenuOptionEnum.values()[selectedOptionIndex];
           } catch(IndexOutOfBoundsException ex) {
               continue;
           }
           
           switch(selectedOption) {
               case EXIT:
                   exit = true;
                   break;
           }
       }
   }
}
