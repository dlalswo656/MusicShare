package MusicShare.MusicShare.user.service;

import MusicShare.MusicShare.user.dto.ShareReplyDTO;
import MusicShare.MusicShare.user.entity.ShareReplyEntity;
import MusicShare.MusicShare.user.repository.MusicShareRepository;
import MusicShare.MusicShare.user.repository.ShareReplyRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareReplyService {

    private final UserRepository userRepository;
    private final MusicShareRepository musicShareRepository;
    private final ShareReplyRepository shareReplyRepository;

    public Long saveShareReply (ShareReplyDTO shareReplyDTO) {
        ShareReplyEntity shareReplyEntity = ShareReplyEntity.toShareReplyEntity(shareReplyDTO, userRepository, musicShareRepository);
        ShareReplyEntity saveReply = shareReplyRepository.save(shareReplyEntity);
        System.out.println("shareReplyDTO : " + shareReplyDTO);
        return  saveReply.getShare().getId();
    }
    
    // 댓글 리스트
    public List<ShareReplyDTO> getShareByBoardShareId(Long boardShareId) {
        List<ShareReplyEntity> shareReplyEntities = shareReplyRepository.findByShareId(boardShareId);
        
        // 댓글 최신순
        List<ShareReplyDTO> shareReplyDTOS = shareReplyEntities.stream()
                .map(ShareReplyDTO::toShareReplyDTO)
                .collect(Collectors.toList());

        // 댓글 최신순 정렬
        shareReplyDTOS.sort(Comparator.comparing(ShareReplyDTO::getReplyCreatedTime).reversed());

        return shareReplyDTOS;
    }

    // 댓글 수정
    public ShareReplyDTO toUpdateShareReply(Long id, ShareReplyDTO shareReplyDTO) {
        ShareReplyEntity shareReplyEntity = shareReplyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        shareReplyEntity.setReplyContent(shareReplyDTO.getReplyContent());
        ShareReplyEntity updatedReply = shareReplyRepository.save(shareReplyEntity);
        return new ShareReplyDTO(updatedReply);
    }

    // 댓글 삭제
    public void delete(Long id) {
        musicShareRepository.deleteById(id);
    }
}
