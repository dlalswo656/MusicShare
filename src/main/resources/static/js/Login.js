function submitChk() {
    // 이메일과 비밀번호 값 가져오기
    var email = document.getElementById("email");
    var password = document.getElementById("password");

    // 로그인 유효성 검사
    if (!email.value) {
        alert ("이메일을 입력해주세요.");
        email.focus();
        return;
    }

    if (!password.value) {
        alert("비밀번호를 입력해주세요.");
        password.focus();
        return;
    }


    // 로그인 성공 확인
    if (email.value !== "사용자 이메일") {
        alert("이메일 틀림");
        return;
    }

    if (password.value !== "사용자 비밀번호") {
        alert("비밀번호 틀림");
        return;
    }

}