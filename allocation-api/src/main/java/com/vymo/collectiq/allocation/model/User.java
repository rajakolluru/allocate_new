package com.vymo.collectiq.allocation.model;

import java.util.HashMap;
import java.util.Objects;

public class User extends HashMap<String,String>{

    public String getId(){
        return get("id");
    }
    @Override
    public boolean equals(Object o) {
        String id = getId();
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        String otherId = user.getId();
        if (!super.equals(o)) return false;
        return Objects.equals(id, otherId);
    }

    @Override
    public int hashCode() {
        String id = getId();
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "User{id = " + getId() + "}";
    }
}