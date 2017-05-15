package com.Forum.Controller;

import com.Forum.Entity.User;
import com.Forum.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * Created by Tomal on 2017-05-13.
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String addUserPanel() {
        return "register";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "username", defaultValue = " ") String username,
                          @RequestParam(value = "password", defaultValue = " ") String pass) {





        if (!username.contains(" ")&&!pass.contains(" ")){ userService.addUser(username, pass); return "home";}
        else return "register";


    }

    @RequestMapping(value={"/login"})
    public String login(){
        return "login";
    }

    @RequestMapping(value="/admin")
    public String admin(){
        return "admin";
    }


    @RequestMapping(value="/listall",method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        model.addAttribute("allusers",userService.getAllUsers());
        return "listall";
    }

    @RequestMapping(value = "/admin/disable",method = RequestMethod.GET)
    public String disableUser(){
        return "disable_user";
    }
    @RequestMapping(value = "/admin/disable",method = RequestMethod.POST)
    public String disableUserByName(@RequestParam(value = "user_name",defaultValue = "") String user_name){
        if (!(user_name.equals("admin")))userService.disableUserByName(user_name);
        return "admin";
    }
}
