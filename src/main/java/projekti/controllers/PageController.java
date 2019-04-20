/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.entities.Account;
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
public class PageController {

    //TODO muuta /users/ -> /page/ tjsp.
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/home")
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        String url = accountRepository.findByUsername(name).getPage().getCustomUrl();

        return "redirect:/users/" + url;
    }

    @GetMapping("/users/{url}")
    public String view(@PathVariable String url, Model model) {
        Account account = accountRepository.findByCustomUrl(url);
        Page page = account.getPage();

        if (page == null) {
            return "index";
        }

        List<Post> posts = postRepository.find25NewestPostsForPage(url);

        model.addAttribute("account", account);
        model.addAttribute("page", page);
        model.addAttribute("posts", posts);

        return "userpage";
    }
}
