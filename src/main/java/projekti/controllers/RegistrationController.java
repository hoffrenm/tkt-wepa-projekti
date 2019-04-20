/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.entities.Account;
import projekti.entities.Page;
import projekti.entities.Registration;
import projekti.repositories.AccountRepository;
import projekti.repositories.PageRepository;

/**
 *
 * @author Mika Hoffren
 */
@Controller
public class RegistrationController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    private Registration getRegistration() {
        return new Registration();
    }

    @GetMapping("/registrations")
    public String registration() {
        return "register";
    }

    @Transactional
    @PostMapping("/registrations")
    public String registration(@ModelAttribute("registration") @Valid Registration registration,
            BindingResult bindingResult) {

        
        Account ac = accountRepository.findByUsername(registration.getUsername());
        Page page = pageRepository.findByCustomUrl(registration.getCustomUrl());

        if (ac != null) {
            bindingResult.rejectValue("username", "user.error",  "Username is already in use");
            
        }
        
        if (page != null) {
            bindingResult.rejectValue("customUrl", "user.error", "Someone else has already taken that url");
        }
        
        if (bindingResult.hasErrors()) {
            return "register";
        }

        ac = new Account();
        Page pg = new Page();

        ac.setName(registration.getName());
        ac.setUsername(registration.getUsername());
        ac.setPassword(passwordEncoder.encode(registration.getPassword()));
        ac.setCustomUrl(registration.getCustomUrl());
        ac.setPage(pg);

        pg.setAccount(ac);
        pg.setCustomUrl(registration.getCustomUrl());

        accountRepository.save(ac);
        pageRepository.save(pg);

        return "redirect:/login";
    }
}
