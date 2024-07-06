package kr.co.Dal.comm.web;

import kr.co.Dal.comm.model.*;
import kr.co.Dal.comm.service.CommAjaxService;
import kr.co.Dal.comm.service.FileService;
import kr.co.Dal.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommAjaxController {

    @Autowired
    private final CommAjaxService commAjaxService;
    private final FileService fileService;
    private final FileUtils fileUtils;

   /**
     * 게시판 등록, 수정
     */
    @RequestMapping("/comm/commAjaxWriteInsert")
    public String commWriteInsert(final PostRequest params, CommVO commVO) throws Exception{
        // 1. 게시글 정보 수정
        commAjaxService.commInsert(commVO);

        // 2. 파일 업로드 (to disk)
        List<FileRequest> uploadFiles = fileUtils.uploadFiles(params.getFiles());

        // 3. 파일 정보 저장 (to database)
        fileService.saveFiles(commVO.getBardId(), uploadFiles);

        // 4. 삭제할 파일 정보 조회 (from database)
        List<FileResponse> deleteFiles = fileService.findAllFileByIds(params.getRemoveFileIds());

        // 5. 파일 삭제 (from disk)
        fileUtils.deleteFiles(deleteFiles);

        // 6. 파일 삭제 (from database)
        fileService.deleteAllFileByIds(params.getRemoveFileIds());

        return "redirect:/comm/commList";	//게시글 리스트로 이동
    }

    /**
     * 게시판 상세 수정 조회
     */
    @RequestMapping("/comm/commAjaxView")
    public ResponseEntity<CommVO> commView(@RequestBody CommVO commVO) throws Exception{
        return ResponseEntity.ok(commAjaxService.commViewSelect(commVO));
    }

    /**
     * 게시판 삭제
     */
    @RequestMapping("/comm/commAjaxWriteDelete")
    public String commWriteDelete(CommVO commVO) throws Exception{
        commAjaxService.commDelete(commVO);
        return "redirect:/comm/commList";	//게시글 리스트로 이동
    }

    /**
     * 게시판 댓글 등록, 수정
     */
    @RequestMapping("/comm/commAjaxReplyInsert")
    public String commReplyInsert(ReplyVO replyVO) throws Exception {
        // 최대값 STEP
        int replyStepMax = commAjaxService.commReplyStepMax(replyVO);

        // 대댓글
        if (replyVO.getReplyGp() != 0 && replyVO.getReplyDepth() != 0) {
            replyVO.setReplyStep(Math.max(replyStepMax, replyVO.getReplyStep()) + 1);
            
        }

        // 대댓글로 인해 기존 글 STEP 업데이트
        commAjaxService.commReplyStepUpdate(replyVO);

        // 글 INSERT
        commAjaxService.commReplyInsert(replyVO);

        return "redirect:/comm/commView?bardId=" + replyVO.getBardId();
    }
    /**
     * 게시판 댓글 삭제
     */
    @RequestMapping("/comm/commAjaxWriteReplyDelete")
    public String commReplyDelete(ReplyVO replyVO) throws Exception {
        commAjaxService.commReplyDelete(replyVO);
        return "redirect:/comm/commView?bardId=" + replyVO.getBardId();
    }



}

