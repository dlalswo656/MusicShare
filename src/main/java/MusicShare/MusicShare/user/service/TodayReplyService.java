package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.entity.TodayReplyEntity;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import MusicShare.MusicShare.user.repository.TodayReplyRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodayReplyService {
    private final UserRepository userRepository;
    private final BoardTodayRepository boardTodayRepository;
    private final TodayReplyRepository todayReplyRepository;

    public Long saveTodayReply (TodayReplyDTO todayReplyDTO) {
        TodayReplyEntity todayReplyEntity = TodayReplyEntity.toTodayReplyEntity(todayReplyDTO, userRepository, boardTodayRepository);
        TodayReplyEntity savedReply = todayReplyRepository.save(todayReplyEntity);
        return savedReply.getToday().getId();
    }

    public List<TodayReplyDTO> getTodayByBoardTodayId(Long boardTodayId) {
        List<TodayReplyEntity> todayReplyEntities = todayReplyRepository.findByTodayId(boardTodayId);
        return todayReplyEntities.stream()
                .map(TodayReplyDTO::toTodayReplyDTO)
                .collect(Collectors.toList());
    }
}
