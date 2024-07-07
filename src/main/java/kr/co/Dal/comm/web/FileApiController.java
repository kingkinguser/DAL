package kr.co.Dal.comm.web;

import kr.co.Dal.comm.model.FileResponse;
import kr.co.Dal.comm.service.FileService;
import kr.co.Dal.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileApiController {
    private final FileService fileService;
    private final FileUtils fileUtils;

    //파일 리스트 조회
    @GetMapping("/comm/{bardId}/files")
    public List<FileResponse> findAllFileByPostId(@PathVariable final int bardId) {
        return fileService.findAllFileByPostId(bardId);
    }

    // 첨부파일 다운로드
    @GetMapping("/comm/{bardId}/files/{biId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable final int bardId, @PathVariable final int biId) {
        FileResponse file = fileService.findFileById(biId);
        Resource resource = fileUtils.readFileAsResource(file);
        try {
            String fileName = URLEncoder.encode(file.getBiOriNm(), "UTF-8");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileName + "\";")
                    .header(HttpHeaders.CONTENT_LENGTH, file.getBiSize() + "")
                    .body(resource);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("filename encoding failed : " + file.getBiOriNm());
        }

    }
}
