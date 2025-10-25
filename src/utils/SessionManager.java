package utils;

import models.User;

/**
 * Session manager to track logged-in user
 * Singleton pattern implementation
 */
public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    
    private SessionManager() {
    }
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
    
    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : -1;
    }
}
