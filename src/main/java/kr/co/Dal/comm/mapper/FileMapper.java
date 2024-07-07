package kr.co.Dal.comm.mapper;

import kr.co.Dal.comm.model.FileRequest;
import kr.co.Dal.comm.model.FileResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    /**
     * 파일 정보 저장
     * @param files - 파일 정보 리스트
     */
    void saveAll(List<FileRequest> files);

    /**
     * 파일 리스트 조회
     * @param bardId - 게시플 번호 (FK)
     * @return 파일 리스트
     */
    List<FileResponse> findAllByPostId(int bardId);

    /**
     * 파일 리스트 조회(물리 파일 삭제를 위한)
     * @param biId - PK 리스트
     * @return 파일 리스트
     */
    List<FileResponse> findAllByIds(List<Integer> biId);

    /**
     * 파일 삭제(DB에 있는 첨부파일 삭제)
     * @param biId - PK 리스트
     */
    void deleteAllByIds(List<Integer> biId);

    /**
     * 파일 상세정보 조회
     * @param biId - PK
     * @return 파일 상세정보
     */
    FileResponse findById(int biId);



}
