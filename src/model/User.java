package model;

/**
 * This class serves as the model for Users
 */
public class User {
    private int userID;
    private String userName;
    private static int currentUserId;
    private static String currentUserName;

    /**
     * This is the User contructor
     * @param userID User ID
     * @param userName User Name
     */
    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    /**
     * Getter for user ID of user currently logged in
     * @return Returns User ID of current user
     */
    public static int getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Setter for user ID of user Currently logged in
     * @param sessionUserId User ID of current user
     */
    public static void setCurrentUserId(int sessionUserId) {
        currentUserId = sessionUserId;
    }

    /**
     * Getter for user name of user currently logged in
     * @return Returns user name of user currently logged in
     */
    public static String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * Setter for user name of user currently logged in
     * @param username User name of current user
     */
    public static void setCurrentUserName(String username) {
        currentUserName = username;
    }

    /**
     * This method overrides the Object toString method to display user name
     * @return Returns user name String
     */
    @Override
    public String toString() {
        return this.userName;
    }

    /**
     * Getter for User Name
     * @return Returns User Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for User Name
     * @param userName User Name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
