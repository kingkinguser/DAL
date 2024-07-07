"use strict";
/*
File Name      : commWrite.js
Program Name   : 게시판 등록, 수정화면
Draft Author   :
Draft Date     : 2023.12.04
*/

document.addEventListener('DOMContentLoaded', function() {
    fnBtn();
    findAllFile();
});

function fnBtn() {
	document.getElementById("btnUpdate").addEventListener('click',function() {
	    let bardId = document.getElementById("bardId").value
        window.location = '/comm/commWrite?bardId=' + bardId;
    });

   	document.getElementById("btnDelete").addEventListener('click',function() {
   	    let bardId = document.getElementById("bardId").value
           window.location = '/comm/commAjaxWriteDelete?bardId=' + bardId;
    });

    document.getElementById("save").addEventListener("click", function() {
        document.getElementById("frm").submit();
    });
}

function editButtonClick(replyId) {
    // 댓글 수정
    document.getElementById('replyCnView-' + replyId).classList.add('hide');
    document.getElementById('replyCnInsert-' + replyId).classList.remove('hide');

    // 버튼
    document.getElementById('btnReplyCancel-' + replyId).classList.remove('hide');
    document.getElementById('btnReplyDelete-' + replyId).classList.add('hide');
    document.getElementById('btnReplyUpdate-' + replyId).classList.add('hide');
    document.getElementById('btnReplyInsert-' + replyId).classList.remove('hide');
    document.getElementById('btnReplyReply-' + replyId).classList.add('hide');
}

function cancelButtonClick(replyId) {
    // 댓글 수정
    document.getElementById('replyCnView-' + replyId).classList.remove('hide');
    document.getElementById('replyCnInsert-' + replyId).classList.add('hide');

    // 버튼
    document.getElementById('btnReplyCancel-' + replyId).classList.add('hide');
    document.getElementById('btnReplyDelete-' + replyId).classList.remove('hide');
    document.getElementById('btnReplyUpdate-' + replyId).classList.remove('hide');
    document.getElementById('btnReplyInsert-' + replyId).classList.add('hide');
    document.getElementById('btnReplyReply-' + replyId).classList.remove('hide');
    document.getElementById('commReplyReply-' + replyId).classList.add('hide');
}

function insertButtonClick(replyId) {
    document.getElementById('btnReplyInsert-' + replyId).addEventListener("click", function() {
       document.getElementById("commDetailFrm").submit();
    });
}

function deleteButtonClick(replyId) {
       let bardId = document.getElementById("bardId").value

       ajaxAPI("/comm/commAjaxWriteReplyDelete?bardId=" + bardId + "&replyId=" + replyId, null, "GET").then(response => {
            $('#commDetailFrm-' + replyId).remove();
       });
}

function replyButtonClick(replyId) {
    document.getElementById('commReplyReply-' + replyId).classList.remove('hide');
        let bardId = document.getElementById("bardId").value
}

// 전체 파일 조회
function findAllFile() {
    // 1. API 호출
    let bardId = document.getElementById("bardId").value

    // 2. 로직 종료
    ajaxAPI("/comm/" + bardId + "/files", null, "GET").then(response => {
        if (!response.length) {
            return false;
        }

    // 3. 파일 영역 추가
    let fileHtml = '<div class="file_down"><div class="cont">';
    response.forEach(row => {
        fileHtml += `<a href="/comm/${bardId}/files/${row.biId}/download"><span class="icons"><i class="fas fa-folder-open"></i></span>${row.biOriNm}</a>`;
    })
    fileHtml += '</div></div>';

    // 4. 파일 HTML 렌더링
    document.getElementById('files').innerHTML = fileHtml;


    });

}