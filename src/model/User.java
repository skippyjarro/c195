package model;

public class User {
    private int userID;
    private String userName;
    private static int currentUserId;
    private static String currentUserName;

    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int sessionUserId) {
        currentUserId = sessionUserId;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String username) {
        currentUserName = username;
    }

    @Override
    public String toString() {
        return this.userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
