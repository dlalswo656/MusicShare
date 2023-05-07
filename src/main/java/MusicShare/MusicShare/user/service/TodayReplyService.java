package MusicShare.MusicShare.user.service;


import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.entity.TodayReplyEntity;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import MusicShare.MusicShare.user.repository.TodayReplyRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public void updateTodayReply(TodayReplyDTO todayReplyDTO) {
        List<TodayReplyEntity> todayReplyEntitys = todayReplyRepository.findByTodayId(todayReplyDTO.getId());
        if (todayReplyEntitys.isEmpty()) {
            throw new IllegalArgumentException("해당 댓글이 없습니다. id = " + todayReplyDTO.getId());
        }
        TodayReplyEntity todayReplyEntity = todayReplyEntitys.get(0);

        System.out.println("댓글 Entitys 넘어오나 ? " + todayReplyEntitys);
        System.out.println("댓글 Entity 넘어오나 ? " + todayReplyEntity);
        System.out.println("서비스 DTO 넘어오나 ? " + todayReplyDTO);
    }

    // 댓글 삭제
    public void deleteTodayReply(Long replyId) {
        todayReplyRepository.deleteById(replyId);
    }
    
}
