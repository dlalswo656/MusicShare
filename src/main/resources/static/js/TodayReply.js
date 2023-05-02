$(document).ready(function() {
  $('#submitTodayReply').on('click', submitTodayReply);
});

function submitTodayReply() {
  const boardToday = {id: $('#boardToday-id').val()}; // 추가
  const commentInput = $('#replyContent'); // 텍스트 입력공간
  const commentText = commentInput.val();
  const userId = $('#user-id').val();
  const boardTodayId = boardToday.id; // 어미글 아이디
  const userName = $('#user-name').val(); // 댓글 작성자 이름
  const replyCreatedTime = new Date(new Date().getTime() + (9 * 60 * 60 * 1000)).toISOString(); // 수정된 부분

  console.log('commentInput:', commentInput.val()); // 추가
  console.log("뭐가 문제니 ?" + userId);
  console.log("뭐가 문제니 ?" + boardTodayId);
  console.log("유저 이름" + userName);

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
  url: '/Board/Today/' + boardToday.id + '/Reply',
  contentType: 'application/json',

  beforeSend: function(xhr) { // CSRF 변수 값 가져오기
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
  },
  error: function(error) {
    console.log('오류:', error);
    alert('오류: ' + error.responseText);
  }
});
}