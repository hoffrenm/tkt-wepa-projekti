/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import projekti.entities.Account;
import projekti.entities.Image;
import projekti.repositories.AccountRepository;
import projekti.repositories.ImageRepository;

/**
 *
 * @author Mika Hoffren
 */
@Controller
public class AlbumController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/album")
    public String homeAlbum(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account account = accountRepository.findByUsername(name);
        List<Image> images = account.getAlbum();

        model.addAttribute("images", images);

        return "homealbum";
    }

    @GetMapping("/album/{url}")
    public String userAlbum(@PathVariable String url, Model model) {
        Account account = accountRepository.findByCustomUrl(url);
        List<Image> images = account.getAlbum();
        
        model.addAttribute("test", account.getUsername());
        model.addAttribute("url", url);
        model.addAttribute("images", images);

        return "useralbum";
    }  

}
