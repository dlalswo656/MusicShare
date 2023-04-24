package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.entity.BoardTodayEntity;
import MusicShare.MusicShare.user.entity.UserEntity;
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

    public BoardTodayEntity toBoardTodayEntity(BoardTodayDTO boardTodayDTO, UserEntity user) {
        return BoardTodayEntity.toBoardTodayEntity(boardTodayDTO, user);
    }

    public void TodaySave(BoardTodayDTO boardTodayDTO, UserEntity user) {
        BoardTodayEntity boardTodayEntity = BoardTodayEntity.toBoardTodayEntity(boardTodayDTO, user);
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

    // 게시글 수정
    public void UpdateToday(Long id, BoardTodayDTO boardTodayDTO) {
        Optional<BoardTodayEntity> optionalBoardTodayEntity = boardTodayRepository.findById(id);

        if (optionalBoardTodayEntity.isPresent()) {
            BoardTodayEntity boardTodayEntity = optionalBoardTodayEntity.get();

            // 수정 정보 설정
            boardTodayEntity.setTodayContent(boardTodayDTO.getTodayContent());

            // 저장
            boardTodayRepository.save(boardTodayEntity);
        } else {
            System.out.println("게시글 수정 실패");
        }

    //    boardTodayRepository.save(BoardTodayEntity.toUpdateTodayEntity(boardTodayDTO));
    }
    
    // 게시글 삭제
    public void Delete(Long id) {
        boardTodayRepository.deleteById(id);
    }
}
