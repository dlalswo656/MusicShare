package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.dto.BoardNoticeDTO;
import MusicShare.MusicShare.user.entity.BoardNoticeEntity;
import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.BoardNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardNoticeService {

    private final BoardNoticeRepository boardNoticeRepository;

    // 게시글 작성
    public BoardNoticeEntity toBoardNoticeEntity(BoardNoticeDTO boardNoticeDTO, UserEntity user) {
        return BoardNoticeEntity.toBoardNoticeEntity(boardNoticeDTO, user);
    }

    public void NoticeSave(BoardNoticeDTO boardNoticeDTO, UserEntity user) {
        BoardNoticeEntity boardNoticeEntity = BoardNoticeEntity.toBoardNoticeEntity(boardNoticeDTO, user);
        boardNoticeRepository.save(boardNoticeEntity);
    }

    // 공지사항 리스트
    public Page<BoardNoticeDTO> findAll(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageLimit = 10;
        int pageSize = 10;
        Page<BoardNoticeEntity> boardTodayEntityList = boardNoticeRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardNoticeDTO> boardNoticeDTOList = boardTodayEntityList.map(BoardNoticeDTO::toBoardNoticeDTO);
        return boardNoticeDTOList;
    }

    // 게시글 조회수
    @Transactional
    public void NoticeHits(Long id) {
        boardNoticeRepository.UpdateHits(id);
    }

    public BoardNoticeDTO NoticeId(Long id) {
        Optional<BoardNoticeEntity> optionalBoardNoticeEntity = boardNoticeRepository.findById(id);

        if (optionalBoardNoticeEntity.isPresent()) {
            BoardNoticeEntity boardNoticeEntity = optionalBoardNoticeEntity.get();
            BoardNoticeDTO boardNoticeDTO = BoardNoticeDTO.toBoardNoticeDTO(boardNoticeEntity);
            return boardNoticeDTO;
        } else {
            return null;
        }
    }

    // 게시글 수정
    public void UpdateNotice(Long id, BoardNoticeDTO boardNoticeDTO) {
        Optional<BoardNoticeEntity> optionalBoardNoticeEntity = boardNoticeRepository.findById(id);

        if (optionalBoardNoticeEntity.isPresent()) {
            BoardNoticeEntity boardNoticeEntity = optionalBoardNoticeEntity.get();

            // 수정 정보 설정
            boardNoticeEntity.setNoticeContent(boardNoticeDTO.getNoticeContent());

            // 저장
            boardNoticeRepository.save(boardNoticeEntity);
        } else {
            System.out.println("게시글 수정 실패");
        }
    }
    
    // 게시글 삭제
    public void Delete(Long id) {
        boardNoticeRepository.deleteById(id);
        System.out.println("삭제 완료");
    }
}
