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

    // 값들을 변수에 잘 가져오는 지 디버깅
    console.log('commentInput:', commentInput.val());
    console.log("뭐가 문제니 ?" + userId);
    console.log("뭐가 문제니 ?" + boardTodayId);
    console.log("유저 이름" + userName);

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

// 댓글 수정 버튼 클릭 시
    $('.edit-btn').click(function () {
        // 수정할 댓글의 id 가져오기
        var replyId = $(this).data('replyList').id;

        console.log("댓글 수정 가능 ? " + replyId);
        // 수정할 댓글의 내용을 입력하는 폼
        var editForm = $('<form>').attr('id', 'edit-form-' + replyId);
        var editInput = $('<input>').attr({
            type: 'text',
            name: 'content',
            value: $(this).data('replyList').content
        });
        var editButton = $('<button>').attr({
            type: 'submit'
        }).text('수정');
        editForm.append(editInput, editButton);

        $(this).closest('tr').after($('<tr>').append($('<td>').attr('colspan', '3').append(editForm)));

        // 수정 버튼 클릭 시 Ajax 요청을 보내어 처리함
        $('#edit-form-'+ replyId).submit(function (event) {
            event.preventDefault();
            $.ajax({
                type: 'PUT',
                url: '/api/reply/' + replyId,
                data: $(this).serialize(),
                success: function (result) {
                    // ...
                },
                error: function (error) {
                    // ...
                }
            });
        });
    });

    // 댓글 삭제 버튼 클릭 시
    $('.delete-btn').click(function () {
        // 삭제할 댓글의 id를 가져옴
        var replyId = $(this).data('replyList').id;

        console.log("댓글 삭제 가능 ? " + replyId);
        // 삭제 확인 다이얼로그를 출력함
        if (confirm('댓글을 삭제하시겠습니까?')) {
            // 삭제 버튼 클릭 시 Ajax 요청을 보내어 처리함
            $.ajax({
                type: 'DELETE',
                url: '/api/reply/' + replyId,
                success: function (result) {
                    // ...
                },
                error: function (error) {
                    // ...
                }
            });
        }
    });



