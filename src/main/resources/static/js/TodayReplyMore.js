// 댓글 수정
$(document).on('click', '.replyModify', function() {
    var replyId = $(this).closest('.reply-item').data('reply-id');
    var newContent = prompt("수정할 내용을 입력하세요.");
    const boardTodayId = $('#boardToday-id').val();
    const userId = $('#user-id').val(); // 로그인한 유저의 id

    console.log("유저 아이디" + userId); // 로그인 유저 id

    // CSRF id 값 변수 추가
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 댓글 작성자의 id 변수 추가
    var authorId = $(this).closest('.reply-item').attr('data-reply-author'); // Detail 85줄 data-reply-author 값 가져오기

    console.log("댓글 작성자 id" + authorId); // 댓글 작성자 id 디버깅

    // 댓글 작성자가 아닌 유저가 다른 경우 수정할 수 없도록 처리
    if (!userId || authorId !== userId.toString()) {
        alert('댓글 작성자가 아닙니다.');
        return false;
    }

    if (newContent !== null && newContent !== '') {
        $.ajax({
            type: 'PUT',
            url: '/Board/Today/' + boardTodayId + '/Reply/' + replyId,
            data: JSON.stringify({
                "replyContent": newContent, // 수정한 댓글을 replyContent에 저장
            }),
            contentType: "application/json",
            dataType: "json",
            // CSRF 변수 값 가져오기
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken); // 토큰 헤더에 같이 보내는 것
            },
            success: function(response) {
                alert('댓글이 수정되었습니다.');
                location.reload();
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    }
});

// 댓글 삭제
$(document).on('click', '.replyDelete', function() {
    var replyId = $(this).closest('.reply-item').data('reply-id'); // 삭제 버튼을 누른 댓글의 id
    const boardTodayId = $('#boardToday-id').val();
    const userId = $('#user-id').val(); // 로그인한 유저의 id

    // CSRF id 값 변수 추가
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 댓글 작성자의 id 변수 추가
    var authorId = $(this).closest('.reply-item').attr('data-reply-author');

    // 댓글 작성자가 아닌 유저가 다른 경우 수정할 수 없도록 처리
    if (!userId || authorId !== userId.toString()) {
        alert('댓글 작성자가 아닙니다.');
        return false;
    }

    // 댓글 삭제 요청 보내기
    if (confirm("댓글을 삭제 하시겠습니까?")) {
        $.ajax({
            url: "/Board/Today/" + boardTodayId + "/Reply/" + replyId,
            type: "DELETE",
            // CSRF 변수 값 가져오기
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken); // 토큰 헤더에 같이 보내는 것
            },
            success: function() {
                alert("댓글이 삭제되었습니다.");
                // 페이지 새로고침
                location.reload();
            },
            error: function() {
                alert("댓글 삭제에 실패했습니다.");
            }
        });
    } else {
        // 삭제 취소
        return false;
    }
});

// 댓글 더보기
function moreReplies() {
    var boardTodayId = $("#boardToday-id").val();  // 게시글 id
    var lastIndex = $(".reply-item:last").index();  // 마지막으로 출력된 댓글의 인덱스
    const userId = $('#user-id').val(); // 로그인한 유저의 id

    $.ajax({
        url: "/Board/Today/" + boardTodayId + "/Reply",
        type: "get",
        data: {
            index: lastIndex + 1 // 마지막으로 출력된 댓글의 인덱스를 서버에 전달하여 그 이후의 댓글을 가져옴
        },
        success: function (data) {
            var start = 0;
            if (lastIndex >= 0) { // 이전에 댓글을 가져왔을 경우
                start = lastIndex + 1; // 중복된 댓글은 제외하기 위해 5개 전부터 시작
            }

            for (var i = start; i < data.length && i < lastIndex + 6; i++) {
                var html = "<tr class='reply-item''>" +
                           "<td>" + data[i].userName + "</td>" +
                           "<td>" + data[i].replyContent + "</td>" +
                           "<td>" + data[i].replyCreatedTime.replace("T", " ") + "</td>" +
                           "<td>" +
                               "<button type='button' class='btn btn-info btn-sm replyModify'>수정</button>" +
                           "</td>" +
                           "<td>" +
                               "<button type='button' class='btn btn-danger btn-sm replyDelete'>삭제</button>" +
                           "</td>" +
                           "</tr>";
                $(".table tbody").append(html);  // 가져온 댓글을 출력
            }
            if (data.length === 0 || data.length < 5) {
                $("#more-btn").hide();  // 더 이상 가져올 댓글이 없는 경우 더보기 버튼 숨김
            } else if ($(".reply-item").length >= data.length) {
                $("#more-btn").hide();
            }
            else {
                $("#more-btn").attr("onclick", "moreReplies()");
            }
        },
        error: function (xhr) {
            console.error(xhr.responseText);
        }
    });
}
