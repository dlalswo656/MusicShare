$(document).ready(function() {
  $('#submitTodayReply').on('click', submitTodayReply);
});

function submitTodayReply() {
    const boardTodayId = $('#boardToday-id').val();
    const commentInput = $('#replyContent'); // 텍스트 입력공간
    const commentText = commentInput.val();
    const userId = $('#user-id').val();
    const userName = $('#user-name').val(); // 댓글 작성자 이름
    const replyCreatedTime = new Date(new Date().getTime() + (9 * 60 * 60 * 1000)).toISOString(); // 한국 시간
//    const replyUpdatedTime = new Date(new Date().getTime() + (9 * 60 * 60 * 1000)).toISOString(); // 한국 시간

    // 값들을 변수에 잘 가져오는 지 디버깅
    console.log('commentInput:', commentInput.val());
    console.log("뭐가 문제니 ?" + userId);
    console.log("뭐가 문제니 ?" + boardTodayId);
    console.log("유저 이름" + userName);
//    console.log("수정 시간 " + replyUpdatedTime);

    // 비로그인 유저가 댓글 버튼을 누를 시 Login 페이지로
    if (!userId) {
        window.location.href = "/Login";
        return;
    }

    if (commentText === '') {
        alert('댓글을 입력해주세요.');
        commentInput.focus();
        return;
    }

    // CSRF id 값 변수 추가
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 댓글 등록 ajax
    $.ajax({
        type: 'POST',
        url: '/Board/Today/' + boardTodayId + '/Reply',
        contentType: 'application/json',
        // CSRF 변수 값 가져오기
        beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken); // 토큰 헤더에 같이 보내는 것
        },
        data: JSON.stringify({
        userId: userId,
        userName: userName,
        boardTodayId: boardTodayId,
        replyContent: commentText, // 댓글 입력한 것
        replyCreatedTime: replyCreatedTime // 댓글 작성 시간
//        replyUpdatedTime: replyUpdatedTime // 댓글 수정 시간
        }),
        success: function(response) {
            console.log('댓글이 등록되었습니다.');
            commentInput.val(''); // 페이지 새로고침
            window.location.href = "/Board/Today/" + boardTodayId // 댓글 작성 후 페이지 상단으로
        },
        error: function(error) {
            console.log('오류:', error);
            alert('오류: ' + error.responseText);
    }
  });
}

// 댓글 수정
$(document).on('click', '.replyModify', function() {
    var replyId = this.dataset.replyId; // 수정 버튼을 누른 댓글의 id
    var newContent = prompt("수정할 내용을 입력하세요.");
    const boardTodayId = $('#boardToday-id').val();

    // CSRF id 값 변수 추가
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    if (newContent !== null && newContent !== '') {
        $.ajax({
            type: 'PUT',
            url: '/Board/Today/' + boardTodayId + '/Reply/' + replyId,
            data: JSON.stringify({
                "content": newContent,
            }),
            // CSRF 변수 값 가져오기
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken); // 토큰 헤더에 같이 보내는 것
            },
            success: function(response) {
                alert('댓글이 수정되었습니다.');
                // 수정된 댓글 화면에 반영하는 코드 추가
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    }
});

// 댓글 삭제
$(document).on('click', '.replyDelete', function() {
    var replyId = $(this).data('reply-id'); // 삭제 버튼을 누른 댓글의 id
    const boardTodayId = $('#boardToday-id').val();

    // CSRF id 값 변수 추가
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    if (confirm("정말 삭제하시겠습니까?")) {
        $.ajax({
            type: 'DELETE',
            url: '/Board/Today/' + boardTodayId + '/Reply/' + replyId,
            // CSRF 변수 값 가져오기
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken); // 토큰 헤더에 같이 보내는 것
            },
            success: function(response) {
                alert('댓글이 삭제되었습니다.');
                // 삭제된 댓글 화면에서 제거하는 코드 추가
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    }
});









