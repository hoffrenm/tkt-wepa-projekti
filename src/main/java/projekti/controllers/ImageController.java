/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;
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
public class ImageController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> viewImage(@PathVariable Long id, Model model) {
        Image image = imageRepository.getOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getContentType()));
        headers.add("Content-Disposition", "attachment; filename=" + image.getName());
        headers.setContentLength(image.getContentLength());

        return new ResponseEntity<>(image.getContent(), headers, HttpStatus.CREATED);
    }
    
    @Transactional
    @PostMapping("/image/add")
    public String addFile(@RequestParam("file") MultipartFile file, @RequestParam String description) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setDescription(description);
        image.setContentType(file.getContentType());
        image.setContent(file.getBytes());
        image.setContentLength(file.getSize());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account account = accountRepository.findByUsername(name);

        if (account.getAlbum().size() < 10) {
            account.getAlbum().add(image);

            imageRepository.save(image);
            accountRepository.save(account);
        }

        return "redirect:/album";
    }

    @PostMapping("/image/delete/{id}")
    public String deleteImage(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account account = accountRepository.findByUsername(name);

        List<Image> album = account.getAlbum();
        Image image = imageRepository.getOne(id);

        if (album.contains(image)) {
            if (image == account.getProfilePicture()) {
                account.setProfilePicture(null);
            }

            album.remove(image);
            imageRepository.delete(image);
        }

        return "redirect:/album";
    }

    @PostMapping("/image/set/{id}")
    public String setProfilePicture(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Account account = accountRepository.findByUsername(name);

        List<Image> album = account.getAlbum();
        Image image = imageRepository.getOne(id);

        if (album.contains(image)) {
            account.setProfilePicture(image);
            accountRepository.save(account);
        }

        return "redirect:/album";
    }

    @PostMapping("/image/like/{id}")
    public String likeImage(@RequestParam String url, @PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Image image = imageRepository.getOne(id);

        if (!image.getLikers().contains(name)) {
            image.setLikes(image.getLikes() + 1);
            image.getLikers().add(name);
            imageRepository.save(image);
        }

        return "redirect:/album/" + url + "#" + id;
    }

}
