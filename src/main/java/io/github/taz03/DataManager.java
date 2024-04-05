package io.github.taz03;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.github.Contents;
import com.jcabi.github.Coordinates;
import com.jcabi.github.RtGithub;

import io.github.taz03.models.Info;

@Service
public class DataManager {
    private static final Logger log = LoggerFactory.getLogger(DataManager.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Contents content;

    private Info info;

    public DataManager(@Autowired PortfolioProperties portfolioProperties) {
        this.content = new RtGithub()
            .repos()
            .get(new Coordinates.Simple(portfolioProperties.contentRepository()))
            .contents();
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, initialDelay = 0, fixedRate = 30)
    private void updateInfo() throws IOException {
        log.info("Info update routine running...");

        InputStreamReader infoReader = new InputStreamReader(content.get("info.json").raw());

        JsonParser parser = objectMapper.createParser(infoReader);
        this.info = parser.readValueAs(Info.class);
        log.info(this.info.toString());

        infoReader.close();
    }

    public Info getInfo() {
        return info;
    }
}
