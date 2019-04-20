/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entities.Comment;
import projekti.entities.Image;
import projekti.entities.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.CommentRepository;
import projekti.repositories.ImageRepository;
import projekti.repositories.PostRepository;

/**
 *
 * @author Mika Hoffren
 */
@Controller
public class CommentController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("/users/{url}/comment/post/{postId}")
    public String commentPost(@PathVariable String url,
            @PathVariable Long postId, @RequestParam String content) {

        Post post = postRepository.findById(postId).get();

        if (content != null && !content.isEmpty() && post != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();

            String poster = accountRepository.findByUsername(name).getName();

            Comment comment = new Comment();
            comment.setPoster(poster);
            comment.setContent(content);
            comment.setTime(LocalDateTime.now());

            post.getComments().add(comment);

            postRepository.save(post);
        }

        return "redirect:/users/" + url + "#" + postId;
    }

    @PostMapping("/comment/{user}/image/{imageId}")
    public String commentImage(@PathVariable("user") String url,
            @PathVariable Long imageId, @RequestParam String content) {

        Image image = imageRepository.getOne(imageId);

        if (content != null && !content.isEmpty() && image != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();

            String poster = accountRepository.findByUsername(name).getName();

            Comment comment = new Comment();
            comment.setPoster(poster);
            comment.setContent(content);
            comment.setTime(LocalDateTime.now());

            image.getComments().add(comment);

            imageRepository.save(image);
        }

        return "redirect:/album/" + url + "#" + imageId;
    }

}
