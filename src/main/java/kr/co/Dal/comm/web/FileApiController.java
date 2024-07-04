package kr.co.Dal.comm.web;

import kr.co.Dal.comm.model.FileResponse;
import kr.co.Dal.comm.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileApiController {
    private final FileService fileService;

    //파일 리스트 조회
    @GetMapping("/comm/{bardId}/files")
    public List<FileResponse> findAllFileByPostId(@PathVariable final int bardId) {
        return fileService.findAllFileByPostId(bardId);
    }
}
