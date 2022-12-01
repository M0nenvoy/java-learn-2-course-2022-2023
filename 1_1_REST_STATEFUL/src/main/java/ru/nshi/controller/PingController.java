package ru.nshi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.nshi.model.PingModel;

@RestController
@RequestMapping(PingController.MAPPING)
public class PingController {
    public static final String MAPPING = "/ping";
    public static final String UP_STATUS = "UP";


    @GetMapping
    public PingModel getStatus() {
        return new PingModel(UP_STATUS);
    }
}
