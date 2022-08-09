package betterkraftstats.backend;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import betterkraftstats.backend.service.PlayerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/playerstats")
public class PlayerStatsController {

    @Autowired
    PlayerService playerService;
    
    @GetMapping("/playtime")
    public Map<String, Integer> getPlaytime() {
        //playerService.associateUserName();
        return playerService.getTimePlayed();
    }
}
