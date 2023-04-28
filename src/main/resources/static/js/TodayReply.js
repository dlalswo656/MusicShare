const TodayReply = () => {
    const TodayContent = document.getElementById("TodayContent").value;
    const TodayId = document.getElementById("boardToday").getAttribute("data-today-id"); // 해당 게시글의 아이디를 얻어옴
    console.log("댓글 내용 : ", todayContent);

    $.ajax({
        // 요청 방식 : post, 요청주소 : /Today/Reply, 요청데이터 : 작성자, 작성 내용, 게시글 번호
        type: "post",
        url: "/Today/Reply",
        data: {
            "todayContent": todayContent,
            "TodayId": TodayId
        }
    });
}