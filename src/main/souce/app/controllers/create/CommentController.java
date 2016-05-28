package app.controllers.create;

import app.actions.post.CommentPostAction;
import app.entities.entityActivity.Comment;
import app.entities.userdata.UserData;
import app.services.ArticleService;
import app.services.UserService;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by click on 5/19/2016.
 */
@Controller
public class CommentController {
    @Autowired
    CommentPostAction commentPostAction;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;


    @RequestMapping( value = "/article/{id}/comments", method = RequestMethod.GET)
    public String loadPage(@PathVariable("id") int id, Model model){
        model.addAttribute("post", articleService.getArticleById(id));
        model.addAttribute("comment", new Comment());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return "article";
    }

    @RequestMapping( value = "/article/{id}/comments",method = RequestMethod.POST)
    public String addComment(@PathVariable("id") int id, @ModelAttribute("comment")  @Valid Comment comment, Model model){
        UserData userData = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setAuthorPhotoUrl(userData.getPhoto());
        model.addAttribute("post", commentPostAction.comment(comment, id,
                SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("comment", new Comment());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return "article";
    }

    @RequestMapping( value = "/article/{id}/deleteComment/{commentId}", method = RequestMethod.POST)
    public String loadPage(@PathVariable("id") int id, @PathVariable("commentId") int commentId, Model model){
        articleService.deleteComment(id, commentId);
        model.addAttribute("post", articleService.getArticleById(id));
        model.addAttribute("comment", new Comment());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return "article";
    }

}
