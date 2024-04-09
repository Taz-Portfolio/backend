package io.github.taz03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.taz03.responses.Info;
import io.github.taz03.responses.Connections;

@RestController
@CrossOrigin
class PortfolioController {
    @Autowired
    private DataManager dataManager;

    @GetMapping("/info")
    public Info info() {
        return dataManager.getInfo();
    }

    @GetMapping("/connections")
    public Connections connections() {
        return dataManager.getConnections();
    }
}
