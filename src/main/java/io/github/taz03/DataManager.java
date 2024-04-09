package io.github.taz03;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.github.Contents;
import com.jcabi.github.Coordinates;
import com.jcabi.github.RtGithub;

import io.github.taz03.responses.Connections;
import io.github.taz03.responses.Info;

@Service
public class DataManager {
    private static final Logger log = LoggerFactory.getLogger(DataManager.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Contents content;

    private Info info;
    private Connections connections;

    public DataManager(@Autowired PortfolioProperties portfolioProperties) {
        this.content = new RtGithub()
            .repos()
            .get(new Coordinates.Simple(portfolioProperties.contentRepository()))
            .contents();
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, initialDelay = 0, fixedRate = 30)
    private void updateCache() {
        log.info("Update routine running...");

        updateInfo();
        updateConnections();
    }

    private void updateInfo() {
        log.info("Info update routine running...");
        this.info = parseGithubFile("info.json", Info.class);
    }

    private void updateConnections() {
        log.info("Connections update routine running...");
        this.connections = parseGithubFile("connections.json", Connections.class);
    }

    private <T> T parseGithubFile(String path, Class<T> type) {
        try (InputStreamReader fileReader = new InputStreamReader(content.get(path).raw())) {
            T result = objectMapper.readValue(fileReader, type);
            log.info(result.toString());
            return result;
        } catch (IOException e) {
            log.error("Error reading file " + path, e);
        }
        
        return null;
    }

    public Info getInfo() {
        return info;
    }

    public Connections getConnections() {
        return connections;
    }
}
