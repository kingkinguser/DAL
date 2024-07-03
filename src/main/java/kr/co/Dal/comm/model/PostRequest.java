package kr.co.Dal.comm.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostRequest {

    private int biId;
    private String userId;
    private String bardTit;
    private String bardCn;
    private String bardStts;
    private String bardType;
    private List<MultipartFile> files = new ArrayList<>();    // 첨부파일 List
}
