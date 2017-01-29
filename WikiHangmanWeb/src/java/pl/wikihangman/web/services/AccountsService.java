package pl.wikihangman.web.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pl.wikihangman.web.exceptions.EntityAlreadyExistsException;
import pl.wikihangman.web.exceptions.EntityDoesNotExistException;
import pl.wikihangman.web.models.User;
import pl.wikihangman.web.services.sql.InsertStatement;
import pl.wikihangman.web.services.sql.SelectStatement;

/**
 * {@code AccountsService} allows to read and manipulate existing users in
 * database.
 * 
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
public class AccountsService {
    
    private static AccountsService instance = null;
    
    private final String TABLE_NAME = "USERS";
    private final String COLUMN_USER_NAME = "USERNAME";
    private final String COLUMN_PASS_NAME = "PASSWORD";
    private final String COLUMN_ID_NAME = "ID";
    private final String COLUMN_POINTS_NAME = "POINTS";
    private final String USER = "hangman";
    private final String PASS = "pass"; 
    private final String dbPath;
    private final Connection connection;
    
    /**
     * Gets singleton value. If it has not been created yet, it is initialized
     * with value given in getter's parameter.
     * 
     * @param dbPath path to jdbc database
     * @return singleton instance
     * @throws ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public static AccountsService getInstance(String dbPath) 
            throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new AccountsService(dbPath);
        }
        return instance;
    }
    
    /**
     * 
     * @param dbPath path to database file
     * @throws java.lang.ClassNotFoundException
     */
    private AccountsService(String dbPath) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        this.connection = DriverManager.getConnection(dbPath, USER, PASS);
        this.dbPath = dbPath;
    }
    
    /**
     * Checks if user of given credentials exists in database.
     * 
     * @param userName user's name
     * @param password user's password
     * @return user object if successful, otherwise null
     * @throws java.sql.SQLException
     */
    public User authenticate(String userName, String password) throws SQLException {
        
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        User user = null;
        
        try (ResultSet result = statement.executeQuery(new SelectStatement()
            .setTable(TABLE_NAME)
            .addData(COLUMN_USER_NAME, String.format("'%1$s'", userName))
            .addData(COLUMN_PASS_NAME, String.format("'%1$s'", password))
            .toString())) {
            
            boolean matched = result.first();
            user = matched ? createUserFromResult(result) : null;
        }
        
        return user;
    }
    
    /**
     * Adds user of given credentials to database. Unique id is determined.
     * 
     * @param userName user's name
     * @param password user's password
     * @return created user
     * @throws java.sql.SQLException
     * @throws EntityAlreadyExistsException 
     */
    public User register(String userName, String password) throws
            SQLException, EntityAlreadyExistsException {
        
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try {
            statement.executeUpdate(new InsertStatement()
                    .setTable(TABLE_NAME)
                    .addData(COLUMN_USER_NAME, String.format("'%1$s'", userName))
                    .addData(COLUMN_PASS_NAME, String.format("'%1$s'", password))
                    .toString());
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 30000) {
                throw new EntityAlreadyExistsException(COLUMN_USER_NAME, userName);
            } else {
                throw ex;
            }
        }
        
        User user = null;
        
        try (ResultSet result = statement.executeQuery(new SelectStatement()
                .setTable(TABLE_NAME)
                .addData(COLUMN_USER_NAME, String.format("'%1$s'", userName))
                .toString())) {
            
            result.first();
            user = createUserFromResult(result);
        }
        
        return user;
    }
    
    /**
     * Updates database record of given user. If user is not found, exception
     * is thrown.
     * 
     * @param user user data to be updated
     * @throws SQLException
     * @throws EntityDoesNotExistException 
     */
    public void update(User user) 
            throws SQLException, EntityDoesNotExistException {
        
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        try (ResultSet result = statement.executeQuery(new SelectStatement()
                .setTable(TABLE_NAME)
                .addData(COLUMN_ID_NAME, Integer.toString(user.getId()))
                .toString())) {
            
            if (!result.first()) {
                throw new EntityDoesNotExistException("ID", Integer.toString(user.getId()));
            }
            
            result.updateString(COLUMN_USER_NAME, user.getName());
            result.updateString(COLUMN_PASS_NAME, user.getPassword());
            result.updateLong(COLUMN_POINTS_NAME, user.getPoints());
            result.updateRow();
        }
    }
    
    /**
     * Returns all users from the database.
     * 
     * @return All users list
     * @throws java.sql.SQLException
     */
    public List<User> getPlayersList() throws SQLException {
        
        List<User> users = new ArrayList<>();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        try (ResultSet result = statement.executeQuery(
                new SelectStatement().setTable(TABLE_NAME).toString())) {
            
            while(result.next()) {
                users.add(createUserFromResult(result));
            }
        }
        
        return users;
    }
    
    /**
     * Creates user object based on result set from database.
     * 
     * @param resultSet result set from SQL database query
     * @return user object
     */
    private User createUserFromResult(ResultSet resultSet) throws SQLException {
         return new User()
            .setId(resultSet.getInt(COLUMN_ID_NAME))
            .setName(resultSet.getString(COLUMN_USER_NAME))
            .setPassword(resultSet.getString(COLUMN_PASS_NAME))
            .setPoints(resultSet.getLong(COLUMN_POINTS_NAME));
    }
}
