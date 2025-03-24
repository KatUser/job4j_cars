package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Picture;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.body.BodyService;
import ru.job4j.cars.service.brand.BrandService;
import ru.job4j.cars.service.car.CarService;
import ru.job4j.cars.service.drive.DriveService;
import ru.job4j.cars.service.engine.EngineService;
import ru.job4j.cars.service.picture.PictureService;
import ru.job4j.cars.service.post.PostService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Base64;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final BrandService brandService;

    private final BodyService bodyService;

    private final DriveService driveService;

    private final CarService carService;

    private final EngineService engineService;

    private final PictureService pictureService;

    @GetMapping
    public String getAllPosts(Model model) {
        var posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "posts/all";
    }

    @GetMapping("/{id}")
    public String getPostById(Model model,
                              @PathVariable int id) {
        var postOptional = postService.findPostById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Такого объявления нет");
            return "errors/404";
        }
        if (postOptional.get().getPicture() == null) {
            model.addAttribute("message", "Фото не найдено");
            return "errors/404";
        }
        Post post = postOptional.get();
        model.addAttribute("post", post);
        model.addAttribute("brand", brandService.findBrandById(post.getCar().getBrand().getId()).get());
        model.addAttribute("body", bodyService.findBodyById(post.getCar().getBody().getId()).get());
        model.addAttribute("drive", driveService.findDriveById(post.getCar().getDrive().getId()).get());
        model.addAttribute("engine", engineService.findById(post.getCar().getEngine().getId()).get());
        model.addAttribute("picture", post.getPicture().getPath());
        return "posts/one";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("bodies", bodyService.findAllBodies());
        model.addAttribute("drives", driveService.findAllDrives());
        model.addAttribute("engines", engineService.findAllEngines());
        return "posts/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Post post,
                         @ModelAttribute Car car,
                         @SessionAttribute User user,
                         @RequestParam MultipartFile file,
                         Model model) throws IOException {

        var pic = new Picture();
        pic.setPath(Base64.getEncoder().encodeToString(file.getBytes()));
        pic.setPost(post);
        post.setUserId(user.getId());
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        post.setCar(car);
        var savedCar = carService.create(car);
        var savedPost = postService.create(post);
        var savedPicture = pictureService.create(pic);
        if (savedPost == null || savedCar == null || savedPicture == null) {
            carService.delete(savedCar);
            postService.delete(savedPost);
            pictureService.delete(savedPicture);
            model.addAttribute("message",
                    "Не удалось создать объявление, попробуйте заново");
            return "errors/404";
        }
        return "redirect:/posts";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        postService.delete(postService.findPostById(id).get());
        return "redirect:/posts";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(@PathVariable int id, Model model) {
        var postOptional = postService.findPostById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message",
                    "Нет такого объявления, попробуйте заново");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("bodies", bodyService.findAllBodies());
        model.addAttribute("drives", driveService.findAllDrives());
        model.addAttribute("engines", engineService.findAllEngines());
        return "posts/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Post post,
                         @ModelAttribute Car car,
                         @SessionAttribute User user,
                         @RequestParam MultipartFile file) throws IOException {
        var pic = new Picture();
        pic.setPath(Base64.getEncoder().encodeToString(file.getBytes()));
        pic.setPost(post);
        post.setUserId(user.getId());
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        post.setCar(car);
        pictureService.update(pic);
        carService.update(car);
        postService.update(post);
        return "redirect:/posts";
    }

    @GetMapping("/sold/{id}")
    public String setPostAsSold(@PathVariable int id) {
        postService.setAsSold(id);
        return "redirect:/posts";
    }
}
