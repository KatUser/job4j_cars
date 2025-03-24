package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.service.picture.PictureService;

import java.io.IOException;

@Controller
@RequestMapping(value = "picture", produces = MediaType.IMAGE_PNG_VALUE)
@AllArgsConstructor
public class PictureController {
    private final PictureService pictureService;

    @ResponseBody
    @GetMapping("/{id}")
    public byte[] picture(@PathVariable int id) throws IOException {
        var pictureOptional = pictureService.findByPost(id);
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(pictureOptional.get().getPath());
    }
}
