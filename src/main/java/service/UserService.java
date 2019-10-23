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
        ArrayList<User> returnList = new ArrayList<>();
       dataBase.values().stream().forEach(u-> returnList.add(u));
       return returnList;
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        long key = maxId.incrementAndGet();
        User userWithId =  new User(key, user.getEmail(), user.getPassword());
        dataBase.put(key, userWithId);
        return true;
    }

    public void deleteAllUser() {
dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        long count = dataBase.values().stream().filter(u-> u.getEmail().equals(user.getEmail())).count();
        return count>0;
    }

    public List<User> getAllAuth() {
        ArrayList<User> returnAuthList = new ArrayList<>();
        dataBase.values().stream().forEach(u-> returnAuthList.add(u));
    return returnAuthList;
    }


    public boolean authUser(User user) {
        authMap.put(user.getId(), user);
        return true;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

}
