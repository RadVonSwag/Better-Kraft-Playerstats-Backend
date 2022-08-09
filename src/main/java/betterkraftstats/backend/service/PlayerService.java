package betterkraftstats.backend.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayerService {
    @Value("${directory.file.usercache}")
    String usercache_dir;

    @Value("${directory.stats}")
    String stats_dir;

    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, Integer> getTimePlayed() {
        log.info("usercache directory: " + usercache_dir);
        log.info("user-stats directory: " + stats_dir);
        Map<String, Integer> uuidAndTime = new HashMap<String, Integer>();
        Map<String, Integer> usernameAndTime = new HashMap<String, Integer>();

        File[] statsFiles = new File(stats_dir).listFiles();
        for (int i = 0; i < statsFiles.length; i++) {
            File currentFile = statsFiles[i];
            try {
                JsonNode currentPlayer = mapper.readTree(currentFile);
                String timePlayedAsString = currentPlayer.get("stats").get("minecraft:custom")
                        .get("minecraft:play_one_minute")
                        .toString();
                int timePlayed = Integer.parseInt(timePlayedAsString) / 72000;
                String uuid = currentFile.getName().substring(0, 36);
                uuidAndTime.put(uuid, timePlayed);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JsonNode playerList;

        try {
            playerList = mapper.readTree(new File(usercache_dir));
            for (int i = 0; i < playerList.size(); i++) {
                String uuidKey = playerList.get(i).get("uuid").asText();
                int time = 0;
                if (uuidAndTime.get(uuidKey) != null) {
                    time = uuidAndTime.get(uuidKey);
                }
                usernameAndTime.put(playerList.get(i).get("name").asText(), time);
                log.info("Found user: " + playerList.get(i).get("name").asText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return usernameAndTime;
    }

}
