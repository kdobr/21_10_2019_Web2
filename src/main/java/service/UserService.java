package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {

    private static UserService instance;

    private UserService() {
    }

    public static UserService getUserService() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public List<User> getAllUsers() {
        return new ArrayList<>(dataBase.values());
    }
    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        if (!isExistsThisUser(user)) {
            long key = maxId.incrementAndGet();
            User userWithId = new User(key, user.getEmail(), user.getPassword());
            dataBase.put(key, userWithId);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return dataBase.containsValue(user);
    }

    public List<User> getAllAuth() {
        return new ArrayList<>(authMap.values());
    }


    public boolean authUser(User user) {
        if (user!=null && user.getId()!=null && isExistsThisUser(user)) {
            authMap.put(user.getId(), user);
            return true;
        } else {
            return false;
        }
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }
}
