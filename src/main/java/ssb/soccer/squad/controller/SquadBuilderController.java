package ssb.soccer.squad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.com.api.dto.CommonApiResponse;
import ssb.soccer.gpt.GptApiClient;
import ssb.soccer.squad.dto.SquadRequestDto;
import ssb.soccer.user.model.User;
import ssb.soccer.user.service.UserService;

import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/squad")
public class SquadBuilderController {

    private final UserService userService;
    private final GptApiClient gptApiClient;

    @PostMapping
    public ResponseEntity<CommonApiResponse<String>> generateSquad(@RequestBody SquadRequestDto requestDto) throws IOException {

        List<User> userList = userService.getUsersByIds(requestDto.getMemberIds());
        String gptResponseText = gptApiClient.sendMessage(requestDto.getSquadType(), userList);
        return ResponseEntity.ok(CommonApiResponse.successResponse(gptResponseText));

    }
}
