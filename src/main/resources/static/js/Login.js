function submitChk() {
    // 이메일과 비밀번호 값 가져오기
    var email = document.getElementById("email");
    var password = document.getElementById("password");

    // 로그인 유효성 검사
    if (!email.value) {
        alert ("이메일을 입력해주세요.");
        email.focus();
        return false;
    } else if (!password.value) {
        alert ("비밀번호를 입력해주세요.");
        password.focus();
        return false;
    } else {
        return submitChk;
    }
}