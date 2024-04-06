package io.github.taz03.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.taz03.DataManager;
import io.github.taz03.models.Info;

@RestController
class InfoController {
    @Autowired
    private DataManager dataManager;

    @GetMapping("/info")
    public Info info() {
        return dataManager.getInfo();
    }
}
