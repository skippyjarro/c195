package model;

public class User {
    private static int currentUserId;
    private int userID;
    private static String currentUserName;

    public User(int userID, String userName) {
        this.userID = userID;
        this.currentUserName = userName;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int sessionUserId) {
        currentUserId = sessionUserId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String username) {
        currentUserName = username;
    }

}
