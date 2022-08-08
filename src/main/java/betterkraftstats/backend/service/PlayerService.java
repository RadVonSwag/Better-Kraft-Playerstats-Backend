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

    @Value("${directory.stats}")
    String stats_dir;

    private final ObjectMapper mapper = new ObjectMapper();

    public void associateUserName() {
        log.info("usercache location: " + usercache_dir);
        log.info("stats location: " + usercache_dir);

        try {
            JsonNode players_array = mapper.readTree(new File(usercache_dir));
            System.out.println(players_array.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getTimePlayed() {
        Map<String, Integer> uuidAndTime = new HashMap<String, Integer>();

        File[] statsFiles = new File(stats_dir).listFiles();
        for (int i = 0; i < statsFiles.length; i++) {
            File currentFile = statsFiles[i];
            try {
                JsonNode currentPlayer = mapper.readTree(currentFile);
                String timePlayedAsString = currentPlayer.get("stats").get("minecraft:custom").get("minecraft:play_one_minute")
                        .toString();
                int timePlayed = Integer.parseInt(timePlayedAsString) / 72000;
                String uuid = currentFile.getName().substring(0, 35);
                uuidAndTime.put(uuid, timePlayed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uuidAndTime;
    }
}
