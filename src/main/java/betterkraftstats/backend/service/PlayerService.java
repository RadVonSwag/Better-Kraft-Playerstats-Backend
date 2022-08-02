package betterkraftstats.backend.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayerService {
    @Value("${directory.file.usercache}")
    String usercache_dir;

    @Value("${directory.stats")
    String stats_dir;

    public void associateUserName() {
        log.info("usercache location: " + usercache_dir);
        log.info("stats location: " + usercache_dir);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode players_array = mapper.readTree(new File(usercache_dir));
            System.out.println(players_array.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getTimePlayed() {
        Map<String, Integer> uuid_and_time = new HashMap<String, Integer>();
        return null;
    }
}
