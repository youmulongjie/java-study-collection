package com.aikan.mockdemo.controller;

import com.aikan.mockdemo.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aikan
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static Map<Long, User> users = new ConcurrentHashMap<>();


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {
        List<User> userList = new ArrayList<User>(users.values());
        return userList;
    }

    @PostMapping("/")
    public String postUser(@ModelAttribute User user) {
        users.put(user.getId(), user);
        return "success";
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }

}