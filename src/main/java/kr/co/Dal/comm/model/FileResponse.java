package kr.co.Dal.comm.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FileResponse {
    private int biId;                     // 파일 번호
    private int bardId;                   // 게시글 번호
    private String biOriNm;               // 원본 파일명
    private String biNm;                  // 저장 파일명
    private long biSize;                  // 파일 크기
    private int bi_att;                   // 삭제 여부
    private LocalDateTime createdDate;    // 생성일시
    private LocalDateTime deletedDate;    // 삭제일시
}
