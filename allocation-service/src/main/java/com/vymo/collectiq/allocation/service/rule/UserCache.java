package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.model.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static com.vymo.collectiq.allocation.configuration.dao.LoaderUtil.loadUsers;

public class UserCache {
    public final List<User> users;
    public UserCache(List<User> users){
        this.users = users;
    }
    public UserCache(String csvFilePath) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            this.users = loadUsers(csvFilePath);
            this.users.addAll(loadUsers("src/main/resources/users.csv"));
    }
}
