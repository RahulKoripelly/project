package com.example.chess.match.controller;

import com.example.chess.match.dto.CreateMatchResponse;
import com.example.chess.match.dto.JoinMatchRequest;
import com.example.chess.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/create")
    public CreateMatchResponse createMatch(Authentication authentication){
        return matchService.createMatch(authentication.getName());
    }



    @PostMapping("/join")
    public String joinMatch(@RequestBody JoinMatchRequest request, Authentication authentication){
        return matchService.joinMatch(request.getRoomId(), authentication.getName());
    }
}
