package ssb.soccer.history.mapper;

import ssb.soccer.history.dto.HistoryRequestDto;
import ssb.soccer.history.model.History;

import java.util.List;

public interface HistoryMapper {
    void createHistory(HistoryRequestDto historyRequestDto);

    List<History> selectHistoryList(int teamId);

    History selectHistoryDetail(int historyId);
}
