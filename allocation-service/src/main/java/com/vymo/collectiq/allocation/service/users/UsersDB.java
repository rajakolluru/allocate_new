package com.vymo.collectiq.allocation.service.users;

import com.vymo.collectiq.allocation.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsersDB {
    public List<User> users ;
    public Map<String, User> userMap = new HashMap<>();
    public void load(List<User> users){
        this.users = users;
        this.userMap = this.users.stream().collect(Collectors.groupingBy(User::getId,
                Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.get(0) // Get the first element from the list
                )));
    }
}
