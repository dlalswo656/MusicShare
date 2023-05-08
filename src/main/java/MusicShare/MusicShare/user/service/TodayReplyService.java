package MusicShare.MusicShare.user.service;


import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.entity.TodayReplyEntity;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import MusicShare.MusicShare.user.repository.TodayReplyRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
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
        System.out.println("todayReplyDTO : " + todayReplyDTO); // todayReplyDTO에 userName값을 가져오는 지 디버깅
        return savedReply.getToday().getId();
    }

    // 댓글 리스트
    public List<TodayReplyDTO> getTodayByBoardTodayId(Long boardTodayId) {
        List<TodayReplyEntity> todayReplyEntities = todayReplyRepository.findByTodayId(boardTodayId);

        // 댓글 최신순
        List<TodayReplyDTO> todayReplyDTOS = todayReplyEntities.stream()
                .map(TodayReplyDTO::toTodayReplyDTO)
                .collect(Collectors.toList());
        
        // 댓글 최신순 정렬
        todayReplyDTOS.sort(Comparator.comparing(TodayReplyDTO::getReplyCreatedTime).reversed());


        return todayReplyDTOS;
    }

    // 댓글 수정
    public void toUpdateTodayReply(Long id, TodayReplyDTO todayReplyDTO) {
        TodayReplyEntity todayReplyEntity = todayReplyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        System.out.println("댓글 엔티티 : " + todayReplyEntity);

        todayReplyEntity.setReplyContent(todayReplyDTO.getReplyContent());
    }

    public void delete(Long id) {
        todayReplyRepository.deleteById(id);
    }
}
