/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entities.Page;
import projekti.entities.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.PageRepository;
import projekti.repositories.PostRepository;

/**
 *
 * @author Mika Hoffren
 */
@Controller
public class PostController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PageRepository pageRepository;

    @PostMapping("/users/{url}/post")
    public String post(@RequestParam String content, @PathVariable String url) {
        Page page = pageRepository.findByCustomUrl(url);

        if (content != null && page != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();

            String poster = accountRepository.findByUsername(name).getName();

            Post post = new Post();
            post.setPoster(poster);
            post.setContent(content);
            post.setTime(LocalDateTime.now());

            page.getPosts().add(post);

            pageRepository.save(page);
        }

        return "redirect:/users/" + url;
    }

    @PostMapping("/post/like/{postId}")
    public String like(@RequestParam String url, @PathVariable Long postId) {

        Post post = postRepository.getOne(postId);

        if (post != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();

            List<String> likers = post.getLikers();

            if (!likers.contains(name)) {
                post.setLikes(post.getLikes() + 1);
                post.getLikers().add(name);

                postRepository.save(post);
            }
        }

        return "redirect:/users/" + url + "#" + postId;
    }
}
