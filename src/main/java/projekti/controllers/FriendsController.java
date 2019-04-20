/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entities.Account;
import projekti.repositories.AccountRepository;

/**
 *
 * @author Mika Hoffren
 */
@Controller
public class FriendsController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/friends")
    public String view(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account account = accountRepository.findByUsername(name);

        model.addAttribute("friends", account.getFriends());
        model.addAttribute("requests", account.getRequests());

        return "friends";
    }

    @GetMapping("/friends/search")
    public String search(@RequestParam(value = "text", required = false) String text, Model model) {
        if (text == null || text.isEmpty()) {
            model.addAttribute("found", new ArrayList<>());
            return "search";
        }

        List<Account> found = accountRepository.findAllBySearchCriteria(text);

        model.addAttribute("found", found);

        return "search";
    }

    @PostMapping("/friends/requests/{id}/accept")
    public String accept(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account ac1 = accountRepository.findByUsername(name);
        Account ac2 = accountRepository.getOne(id);

        if (ac1 != null && ac2 != null) {
            List<Account> friends1 = ac1.getFriends();
            List<Account> friends2 = ac2.getFriends();

            if (!friends1.contains(ac2) && !friends2.contains(ac1)) {
                ac1.getRequests().remove(ac2);
                friends1.add(ac2);
                friends2.add(ac1);
                accountRepository.save(ac1);
            }
        }

        return "redirect:/friends";
    }

    @PostMapping("/friends/requests/{id}/decline")
    public String decline(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account ac1 = accountRepository.findByUsername(name);
        Account ac2 = accountRepository.getOne(id);

        if (ac1 != null && ac2 != null) {
            ac1.getRequests().remove(ac2);

            accountRepository.save(ac1);
        }

        return "redirect:/friends";
    }

//    @PostMapping("/friends/search")
//    public String searchFriend(@PathVariable String url, Model model) {
//
//        return "friends";
//    }

    @PostMapping("/friends/{url}/friendrequest")
    public String addFriend(@PathVariable String url) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account sender = accountRepository.findByUsername(name);
        Account target = accountRepository.findByCustomUrl(url);

        List<Account> requests = target.getRequests();

        if (!requests.contains(sender)) {
            requests.add(sender);
        }

        accountRepository.save(target);

        return "redirect:/friends/search";
    }

}
