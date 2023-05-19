package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.dto.MusicShareDTO;
import MusicShare.MusicShare.user.entity.BoardTodayEntity;
import MusicShare.MusicShare.user.entity.MusicShareEntity;
import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.MusicShareRepository;
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
public class MusicShareService {

    private final MusicShareRepository musicShareRepository;

    // 게시글 작성
    public MusicShareEntity toShareEntity(MusicShareDTO musicShareDTO, UserEntity user) {
        return MusicShareEntity.toMusicShareEntity(musicShareDTO, user);
    }
    public void ShareSave(MusicShareDTO musicShareDTO, UserEntity user) {
        MusicShareEntity musicShareEntity = MusicShareEntity.toMusicShareEntity(musicShareDTO, user);
        musicShareRepository.save(musicShareEntity);
    }

    // 음악 공유 리스트
    public Page<MusicShareDTO> findAll(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageLimit = 10;
        int pageSize = 10;
        Page<MusicShareEntity> musicShareEntityList = musicShareRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<MusicShareDTO> musicShareDTOList = musicShareEntityList.map(MusicShareDTO::toMusicShareDTO);
        return musicShareDTOList;
    }

    // 게시글 조회수
    @Transactional
    public void ShareHits(Long id) {
        musicShareRepository.UpdateHits(id);
    }

    public MusicShareDTO ShareId(Long id) {
        Optional<MusicShareEntity> optionalMusicShareEntity = musicShareRepository.findById(id);

        if (optionalMusicShareEntity.isPresent()) {
            MusicShareEntity musicShareEntity = optionalMusicShareEntity.get();
            MusicShareDTO musicShareDTO = MusicShareDTO.toMusicShareDTO(musicShareEntity);
            return musicShareDTO;
        } else {
            return null;
        }
    }
}
