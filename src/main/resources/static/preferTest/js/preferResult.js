"use strict";
/*
File Name      : preferTest.js
Program Name   : 술 MBTI 화면
Draft Author   :
Draft Date     : 2024.01.01
*/

var sendKakao = function() {
    // 메시지 공유 함수
    Kakao.Link.sendScrap({
        requestUrl: 'http://localhost:8282/preferTest/preferTest', // 페이지 url
        templateId: 110014, // 메시지템플릿 번호 (실제 템플릿 ID로 변경 필요)
        templateArgs: {
            PROFILE: 'http://localhost:8282/preferTest/img/dalLogo.png', // 프로필 이미지 주소 ${PROFILE}
            THUMB: thumbImg, // 썸네일 주소 ${THUMB}
            TITLE: 'DAL', // 제목 텍스트 ${TITLE}
            DESC: '당신은 어떤 유형인가요?', // 설명 텍스트 ${DESC}
        },
    });
};

// head에서 정보 가져오기(JQuery)
var thumbImg = $('meta[property="og:image"]').attr('content'); //og이미지 주소
var thumbTitle = $('meta[property="og:title"]').attr('content'); //og타이틀
var thumbDesc = $('meta[property="og:description"]').attr('content'); //og설명
var linkUrl = $('meta[property="og:url"]').attr('content'); //url
