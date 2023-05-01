$(document).ready(function() {
  $('#submitTodayReply').on('click', submitTodayReply);
});

function submitTodayReply() {
  const commentInput = $('#todayContent');
  console.log('commentInput:', commentInput.val()); // 추가
  const commentText = commentInput.val().trim();
  const userId = $('#user-id').val();
  const boardTodayId = '${boardToday.id}';

  if (commentText === '') {
    alert('댓글을 입력해주세요.');
    commentInput.focus();
    return;
  }

  $.ajax({
    type: 'POST',
    url: '/Board/Today/${boardToday.id}/Reply',
    contentType: 'application/json',
    data: JSON.stringify({
      userId: userId,
      boardTodayId: boardTodayId,
      todayContent: commentText
    }),
    success: function(response) {
      console.log('댓글이 등록되었습니다.');
      commentInput.val(''); // 페이지 새로고침
    },
    error: function(error) {
      alert(`오류: ${error.responseText}`);
    }
  });
}
