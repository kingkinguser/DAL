"use strict";
/*
File Name      : commWrite.js
Program Name   : 게시판 등록, 수정화면
Draft Author   :
Draft Date     : 2023.12.04
*/

document.addEventListener('DOMContentLoaded', function() {
    fnAjaxWrite();
});

function fnAjaxWrite() {
    let bardId = document.getElementById("bardId").value;
    if (bardId !== null && bardId !== "") {
       let jsonData = {
           bardId: bardId
       }

       ajaxAPI("/comm/commAjaxView", jsonData, "POST").then(response => {
           document.getElementById("bardTit").value = "";
           document.getElementById("bardCn").value = "";

           if(response) {
               document.getElementById("bardTit").value = response.bardTit;
               document.getElementById("bardCn").value = response.bardCn;
           }

           //oEditors.getById["bardCn"].exec("SET_IR", [""]);
           //oEditors.getById["bardCn"].exec("PASTE_HTML", [response.bardCn]);
       });
    }
}

// 파일 선택
function selectFile(element) {
    const file = element.files[0];
    const filename = element.closest('.file_input').firstElementChild;

    // 1. 파일 선택 창에서 취소 버튼이 클릭된 경우
    if ( !file ) {
        filename.value = '';
        return false;
    }

    // 2. 파일 크기가 10MB를 초과하는 경우
    const fileSize = Math.floor(file.size / 1024 / 1024);
    if (fileSize > 10) {
        alert('10MB 이하의 파일로 업로드해 주세요.');
        filename.value = '';
        element.value = '';
        return false;
    }

    // 3. 파일명 지정
    filename.value = file.name;
}


// 파일 추가
function addFile() {
    const fileDiv = document.createElement('div');
    fileDiv.innerHTML =`
        <div class="file_input">
            <input type="text" readonly />
            <label> 첨부파일
                <input type="file" name="files" onchange="selectFile(this);" />
            </label>
        </div>
        <button type="button" onclick="removeFile(this);" class="btns del_btn"><span>삭제</span></button>
    `;
    document.querySelector('.file_list').appendChild(fileDiv);
}


// 파일 삭제
function removeFile(element) {
    const fileAddBtn = element.nextElementSibling;
    if (fileAddBtn) {
        const inputs = element.previousElementSibling.querySelectorAll('input');
        inputs.forEach(input => input.value = '')
        return false;
    }
    element.parentElement.remove();
}


document.getElementById("save").addEventListener("click", function() {
    oEditors.getById["bardCn"].exec("UPDATE_CONTENTS_FIELD", []);
    document.getElementById("frm").submit();
});