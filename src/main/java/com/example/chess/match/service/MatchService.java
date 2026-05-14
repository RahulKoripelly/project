package com.example.chess.match.service;

import com.example.chess.match.dto.CreateMatchResponse;
import com.example.chess.match.entity.Match;
import com.example.chess.match.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final StringRedisTemplate redisTemplate;

    public CreateMatchResponse createMatch(String email){
        String roomId = UUID.randomUUID().toString();
        Match match = Match.builder()
                .roomId(roomId)
                .whitePlayer(email)
                .status("WAITING")
                .build();
        matchRepository.save(match);

        redisTemplate.opsForValue().set("match:" + roomId, "WAITING");

        return new CreateMatchResponse(roomId);
    }

    public String joinMatch(String roomId, String email){
        Match match = matchRepository.findAll()
                .stream()
                .filter(m->m.getRoomId().equals(roomId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Match not found"));

        if(match.getBlackPlayer()!=null){
            throw new RuntimeException("Match already full");
        }
        match.setBlackPlayer(email);
        match.setStatus("ACTIVE");

        matchRepository.save(match);

        redisTemplate.opsForValue().set("match:" + roomId, "ACTIVE");

        return "Joined successfully";
    }

}
