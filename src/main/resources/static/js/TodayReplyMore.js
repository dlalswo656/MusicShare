function moreReplies() {
    var boardTodayId = $("#boardToday-id").val();  // 게시글 id
    var lastIndex = $(".reply-item:last").index();  // 마지막으로 출력된 댓글의 인덱스

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
                var html = "<tr class='reply-item'>" +
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
