const header = $("meta[name='_csrf_header']").attr("content");
const token = $("meta[name='_csrf']").attr("content");

$.ajaxSetup({
  beforeSend: function(xhr) {
    xhr.setRequestHeader(header, token);
  }
});

console.log(header); // 디버깅
console.log(token);

