package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.entity.BoardTodayEntity;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardTodayService {

    private final BoardTodayRepository boardTodayRepository;

    public void TodaySave(BoardTodayDTO boardTodayDTO) {
        BoardTodayEntity boardTodayEntity = BoardTodayEntity.toBoardTodayEntity(boardTodayDTO);
        boardTodayRepository.save(boardTodayEntity);
    }

    // 오늘의 음악 리스트
    public List<BoardTodayDTO> findAll() {
        List<BoardTodayEntity> boardTodayEntityList = boardTodayRepository.findAll();
        List<BoardTodayDTO> boardTodayDTOList = new ArrayList<>();
        for (BoardTodayEntity boardTodayEntity: boardTodayEntityList) {
            boardTodayDTOList.add(BoardTodayDTO.toBoardTodayDTO(boardTodayEntity));
        }
        return boardTodayDTOList;
    }

    // 게시글 조회수
    @Transactional
    public void TodayHits(Long id) {
        boardTodayRepository.UpdateHits(id);
    }

    public BoardTodayDTO TodayId(Long id) {
        Optional<BoardTodayEntity> optionalBoardTodayEntity = boardTodayRepository.findById(id);
        if (optionalBoardTodayEntity.isPresent()) {
            BoardTodayEntity boardTodayEntity = optionalBoardTodayEntity.get();
            BoardTodayDTO boardTodayDTO = BoardTodayDTO.toBoardTodayDTO(boardTodayEntity);
            return boardTodayDTO;
        } else {
            return null;
        }
    }
}
