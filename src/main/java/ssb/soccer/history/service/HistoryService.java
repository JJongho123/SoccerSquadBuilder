package ssb.soccer.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.history.dto.HistoryRequestDto;
import ssb.soccer.history.mapper.HistoryMapper;
import ssb.soccer.history.model.History;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryMapper historyMapper;

    public Boolean createHistory(HistoryRequestDto historyRequestDto) {
        boolean result = true;
        try {
            historyRequestDto.setCreatedAt(new Date());
            historyMapper.createHistory(historyRequestDto);
        }catch (Exception e) {
            result = false;
        }
        return result;
    }

    public List<History> selectHistoryList(int teamId) {
        return historyMapper.selectHistoryList(teamId);
    }

    public History selectHistoryDetail(int historyId) {
        return historyMapper.selectHistoryDetail(historyId);
    }
}
