// 이메일 중복 체크 함수
async function emailCheck() {
    const email = $("#email").val();
    const checkResult = $("#check-result");
    const passwordCheck = $("#passwordCheck");
    const pwCheck = $("#pwCheck");
    console.log("입력 값 : ", email);

// 이메일이 공백일 경우
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // 정규식 패턴 사용

    if (!emailRegex.test(email)) {
      checkResult.css("color", "red").text("이메일 형식이 유효하지 않습니다.");
      return;
    }

    try {
      const res = await
      $.ajax({
        // 요청방식: post, url: "email-check", 데이터: 이메일
        type: "post",
        url: "/User/email-check",
        data: {"email": email },
      });
      console.log("요청 성공", res);
      if (res === "ok") {
        console.log("사용 가능한 이메일");
        checkResult.css("color", "green").text("사용 가능한 이메일");
      } else {
        console.log("이미 사용 중인 이메일");
        checkResult.css("color", "red").text("이미 사용 중인 이메일");
      }
    } catch (err) {
      console.log("에러발생", err);
    }
  }

// 회원가입 버튼 클릭 시 함수
function submitChk() {
    const email = $("#email").val();
    const password = $("#password").val();
    const pwchk = $("#pwchk").val();
    const name = $("#name").val();
    const nameChk = $("#nameChk");
    const phone = $("#phone").val();
    const phoneChk = $("#phoneChk");
    const checkResult = $("#check-result");
    const passwordCheck = $("#passwordCheck");
    const pwCheck = $("#pwCheck");

    // 이메일 중복 체크
    if (checkResult.text() !== "사용 가능한 이메일") {
      $("#email-check-msg").text("이메일 중복을 확인해주세요.").show();
      $("#email").select();
      return false;
    } else {
        $("#email-check-msg").hide(); // 경고 메시지 숨기기
    }

    // 비밀번호 확인
    if (password === "") {
        passwordCheck.css("color", "red").text("비밀번호를 입력해주세요");
        // alert("비밀번호를 입력해주세요.");
        $("#password").select();
        return false;
      } else if (password !== pwchk) {
        pwCheck.css("color", "red").text("비밀번호가 틀렸습니다");
        // alert("비밀번호가 일치하지 않습니다.");
        $("#pwchk").select();
        return false;
      }

    // 이름 유효성
    if (name === "") {
    nameChk.css("color", "red").text("이름을 입력해주세요");
    $("#name").select();
    return false;
    }
    // 전화번호 유효성
    if (phone === "") {
    phoneChk.css("color", "red").text("번호를 입력해주세요");
    $("#phone").select();
    return false;
    }
    // 모든 조건을 만족하면 form 제출
    return true;
    }

    $(document).ready(function() {
// 이메일 blur 시 이메일 중복 체크 실행
    $("#email").blur(function() {
      $("#password").focus();
    });

// 회원가입 버튼 클릭 시 submitChk 함수 실행
    $("form").submit(function() {
        console.log(header); // 디버깅
        console.log(token);
      return submitChk();
    });
  });
