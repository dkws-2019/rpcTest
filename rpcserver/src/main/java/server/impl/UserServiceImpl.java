package server.impl;

import service.UserService;

public class UserServiceImpl implements UserService {
    public String sayHellow(String name) {
        return name+"向您问好";
    }
}
