$(document).ready(function() {
  $('#submitTodayReply').on('click', submitTodayReply);
});

function submitTodayReply() {
  const boardToday = {id: $('#boardToday-id').val()}; // 추가
  const commentInput = $('#todayContent'); // 텍스트 입력공간
  console.log('commentInput:', commentInput.val()); // 추가
  const commentText = commentInput.val();
  const userId = $('#user-id').val();
  const boardTodayId = boardToday.id; // 어미글 아이디

  console.log("뭐가 문제니 ?" + userId);
  console.log("뭐가 문제니 ?" + boardTodayId);

  if (commentText === '') {
    alert('댓글을 입력해주세요.');
    commentInput.focus();
    return;
  }

// CSRF id 값 변수 추가
  var csrfToken = $("meta[name='_csrf']").attr("content");
  var csrfHeader = $("meta[name='_csrf_header']").attr("content");
$.ajax({
  type: 'POST',
  url: '/Board/Today/${boardToday.id}/Reply',
  contentType: 'application/json',

  beforeSend: function(xhr) { // CSRF 변수 값 가져오기
    xhr.setRequestHeader(csrfHeader, csrfToken); // 토큰 헤더에 같이 보내는 것
  },
  data: JSON.stringify({
    userId: userId,
    boardTodayId: boardTodayId,
    todayContent: commentText // 댓글 입력한 것
  }),
  success: function(response) {
    console.log('댓글이 등록되었습니다.');
    commentInput.val(''); // 페이지 새로고침
  },
  error: function(error) {
    console.log('오류:', error);
    alert('오류: ' + error.responseText);
  }
});
}