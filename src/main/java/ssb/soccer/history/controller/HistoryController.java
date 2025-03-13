package ssb.soccer.history.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.CommonApiResponse;
import ssb.soccer.history.dto.HistoryRequestDto;
import ssb.soccer.history.model.History;
import ssb.soccer.history.service.HistoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping()
    public ResponseEntity<CommonApiResponse<Boolean>> createHistory(@RequestBody HistoryRequestDto historyRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.successResponse(historyService.createHistory(historyRequestDto)));
    }

    @GetMapping("/{teamId}")  // URL에서 teamId 받기
    public ResponseEntity<CommonApiResponse<List<History>>> selectHistoryList(@PathVariable int teamId) {
        return ResponseEntity.ok(CommonApiResponse.successResponse(historyService.selectHistoryList(teamId)));
    }

}
