package kr.co.Dal.comm.service;

import kr.co.Dal.comm.mapper.FileMapper;
import kr.co.Dal.comm.model.FileRequest;
import kr.co.Dal.comm.model.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    /**
     * 파일 정보 저장
     * @param files - 파일 정보 리스트
     */
    @Transactional
    public void saveFiles(final int bardId, final List<FileRequest> files) {
        if(CollectionUtils.isEmpty(files)) {
            return;
        }

        for(FileRequest file : files) {
            file.setBardId(bardId);
        }

        fileMapper.saveAll(files);
    }

    /**
     * 파일 리스트 조회
     * @param bardId - 게시플 번호 (FK)
     * @return 파일 리스트
     */
    public List<FileResponse> findAllFileByPostId(final int bardId) {
        return fileMapper.findAllByPostId(bardId);
    }

    /**
     * 파일 리스트 조회(물리 파일 삭제를 위한)
     * @param biId - PK 리스트
     * @return 파일 리스트
     */
    public List<FileResponse> findAllFileByIds(final List<Integer> biId) {
        if(CollectionUtils.isEmpty(biId)) {
            return Collections.emptyList();
        }
        return fileMapper.findAllByIds(biId);
    }

    /**
     * 파일 삭제(DB에 있는 첨부파일 삭제)
     * @param biId - PK 리스트
     */
    @Transactional
    public void deleteAllByIds(final List<Integer> biId) {
        if (CollectionUtils.isEmpty(biId)) {
            return;
        }
        fileMapper.deleteAllByIds(biId);
    }

}
