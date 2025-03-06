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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/squads")
public class SquadBuilderController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonApiResponse<?>> generateSquad(@RequestBody SquadRequestDto requestDto) throws IOException {
        GptApiClient gptApiClient = new GptApiClient();
        List<User> userList = userService.getUsersByIds(requestDto.getMemberIds());

        System.out.println(gptApiClient.sendMessage(requestDto.getSquadType(), userList));
        return null;
    }
}
