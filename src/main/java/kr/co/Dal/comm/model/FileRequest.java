package kr.co.Dal.comm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequest {
    private int biId;          // 파일 번호
    private int bardId;        // 게시글 번호
    private String biOriNm;   // 원본 파일명
    private String biNm;       // 저장 파일명
    private long biSize;        // 파일 크기

    @Builder
    public FileRequest(String biOriNm, String biNm, long biSize) {
        this.biOriNm = biOriNm;
        this.biNm = biNm;
        this.biSize = biSize;
    }
}
