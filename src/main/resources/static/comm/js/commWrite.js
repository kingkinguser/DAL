"use strict";

document.addEventListener('DOMContentLoaded', function() {
    fnAjaxWrite();
    findAllFile();
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

// 파일 삭제 처리용 익명 함수
const removeFileId = (function() {
    const biIds = [];
    return {
        add(biId) {
            if (biIds.includes(biId)) {
                return false;
            }
            biIds.push(biId);
        },
        getAll() {
            return biIds;
        }
    }
}());

// 파일 선택
function selectFile(element, biId) {
    const file = element.files[0];
    const filename = element.closest('.file_input').firstElementChild;

    // 1. 파일 선택 창에서 취소 버튼이 클릭된 경우
    if (!file) {
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

    // 4. 삭제할 파일 id 추가
    if (biId) {
        removeFileId.add(biId);
    }

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
function removeFile(element, biId) {
    // 1. 삭제할 파일 id 추가
    if (biId) {
        removeFileId.add(biId);
    }

    // 2. 파일 영역 초기화 & 삭제
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
    document.getElementById("frm").removeFileIds.value = removeFileId.getAll().join();
    document.getElementById("frm").submit();
});

// 전체 파일 조회
function findAllFile() {
    // 1. 신규 등록/수정 체크
    let bardId = document.getElementById("bardId").value
    if(!bardId) {
        return false;
    }

    // 2. API 호출, 로직 종료
    ajaxAPI("/comm/" + bardId + "/files", null, "GET").then(response => {
        if (!response.length) {
            return false;
        }

        // 3. 업로드 영역 추가
        for(let i = 0, len = (response.length -1); i < len; i++) {
            addFile();
        }

        // 4. 파일 선택 & 삭제 이벤트 재선언 & 파일명 세팅
        const filenameInputs = document.querySelectorAll('.file_list input[type="text"]');
        filenameInputs.forEach((input, i) => {
            const fileInput = input.nextElementSibling.firstElementChild;
            const fileRemoveBtn = input.parentElement.nextElementSibling;
            fileInput.setAttribute('onchange', `selectFile(this, ${response[i].biId})`);
            fileRemoveBtn.setAttribute('onclick', `removeFile(this, ${response[i].biId})`);
            input.value = response[i].biOriNm;
        })
    });

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

}