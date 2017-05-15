package com.Forum.Controller;

import com.Forum.Service.PubMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Tomal on 2017-05-14.
 */
@RequestMapping("/forum")
@Controller
public class PubMsgController {

    @Autowired
    private PubMsgService pubMsgService;

    @RequestMapping("/")
    public String showPosts(@RequestParam(value = "sort_by", defaultValue = "date") String sort_by,
                            @RequestParam(value = "category", defaultValue = "wszystkie") String category,
                            Model model) {


        model.addAttribute("posts", pubMsgService.showPosts(sort_by,category));

        return "forum";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showAnswers(@PathVariable("id") int id, Model model) {

        model.addAttribute("post", pubMsgService.showSinglePost(id));
        model.addAttribute("answers", pubMsgService.showAnswers(id));


        return "answers";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String addAnswer(@PathVariable("id") int id, @RequestParam(value = "user_name", defaultValue = " ") String user_name,
                            @RequestParam(value = "content", defaultValue = " ") String answer, Model model) {

        if (!(answer.equals(" "))) pubMsgService.addAnswer(id, user_name, answer);

        model.addAttribute("post", pubMsgService.showSinglePost(id));
        model.addAttribute("answers", pubMsgService.showAnswers(id));

        return "answers";
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public String addLike(@RequestParam(value = "id", defaultValue = "0") int id,
                          @RequestParam(value = "user_name", defaultValue = "") String user_name) {

        if (!(id == 0)) pubMsgService.addLike(id, user_name);


        String redirectUrl = "/forum/";
        return "redirect:" + redirectUrl + id;
    }


    @RequestMapping(value = "/like_from_forum", method = RequestMethod.POST)
    public String addLikeFromForum(@RequestParam(value = "id", defaultValue = "0") int id,
                                   @RequestParam(value = "user_name", defaultValue = "") String user_name) {

        if (!(id == 0)) pubMsgService.addLike(id, user_name);


        String redirectUrl = "/forum/";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = "/add_pub_msg/", method = RequestMethod.GET)
    public String add_pub_msg() {


        return "add_pub_msg";
    }

    @RequestMapping(value = "/add_pub_msg/", method = RequestMethod.POST)
    public String add_pub_msg(@RequestParam(value = "user_name", defaultValue = " ") String user_name,
                              @RequestParam(value = "content", defaultValue = " ") String content,
                              @RequestParam(value = "category", defaultValue = "Ogolny") String category,
                              @RequestParam(value = "topic", defaultValue = " ") String topic) {

        if (!(content.equals(" ")) && !(topic.equals(" ")))
            pubMsgService.addPubMsg(category, user_name, content, topic);

        String redirectUrl = "/forum/";
        return "redirect:" + redirectUrl;
    }

}
