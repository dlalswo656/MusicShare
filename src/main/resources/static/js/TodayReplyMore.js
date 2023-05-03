function moreReplies() {
  // 모든 댓글 요소를 선택해서 배열로 저장
  var replies = Array.from(document.getElementsByClassName("reply-item"));

  // 현재 보여지고 있는 댓글의 개수를 계산
  var visibleCount = replies.filter(function(reply) {
    return reply.style.display !== "none";
  }).length;

  // 다음 5개의 댓글을 보여줌
  replies.slice(visibleCount, visibleCount + 5).forEach(function(reply) {
    reply.style.display = "table-row";
  });

  // 더 이상 보일 댓글이 없으면 더보기 버튼을 숨김
  if (visibleCount + 5 >= replies.length) {
    document.getElementById("more-btn").style.display = "none";
  }
}
