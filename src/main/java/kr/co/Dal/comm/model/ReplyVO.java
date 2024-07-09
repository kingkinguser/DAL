package kr.co.Dal.comm.model;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.sql.Date;

@Data
public class ReplyVO{
    private int replyId;
    private int bardId;
    private int replyUserId;
    private String userNick;
    private String replyCn;
    private Date replyRdate;
    private int replyStts;
    private int replyGp;
    private int replyDepth;
    private int replyStep;
    private int replyParentId;
    private int totalDataCnt;
    private int replyGpMax;
    private int replyIdMax;
    private int replyStepMax;
}
