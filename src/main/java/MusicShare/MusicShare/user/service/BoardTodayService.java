package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.entity.BoardTodayEntity;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardTodayService {

    private final BoardTodayRepository boardTodayRepository;

    public void Save(BoardTodayDTO boardTodayDTO) {
        BoardTodayEntity boardTodayEntity = BoardTodayEntity.toBoardTodayEntity(boardTodayDTO);
        boardTodayRepository.save(boardTodayEntity);
    }
}
