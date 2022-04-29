$(document).ready(function() {
	ajaxGetComment(1);
	function ajaxGetComment(page) {
		let searchParams = new URLSearchParams(window.location.search);
		var grammarId = searchParams.get('idGram');
		/*alert(grammarId);*/

		$.ajax({
			type: "GET",
			url: "http://localhost:8080/api/comment/grammar/id=" + grammarId + "?page=" + page,
			success: function(result) {
				soCau = result.totalElements;
				console.log(result);
				var divCMT = "";
				$.each(result.object, function(i, objres) {
					divCMT += '<tr>'
						+ '<td>'
						+ '<div class = "row">'				
						+ '<div class="col-md-2" style="padding: 10px;">'
						+ '<img class="circle" src="https://lovablemessages.com/wp-content/uploads/2021/12/hinh-nen-3D-cho-dien-thoai-lung-linh-16.jpg" alt="" width="100" height="100" >'
						+ '</div>'
						+ '<div class="col-md-10 my-dates">'
						+ '<span style="color: black" id="name_member" style="font-size: 10px">' + objres.userName + '</span>'
						+ '<p id="contentgrammar" name="cmtgrammarcontent">' + objres.content + '</p>'
						+ '<i name="commentdategrammar">' + objres.commentDate + '</i>'
						+ '</div>'
						+ '</div>'
						+ '<td>'
						+ '<tr>'
				});
				$('#lstcommentgrammar').html(divCMT);
			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});
	};
	
	var comment = {};
	$('#btnComment').click(function(){
		let searchParams = new URLSearchParams(window.location.search);
		comment.grammarId = searchParams.get('idGram');
		comment.content = $('#contentComment').val();
		comment.userName = $('#name_member').val();
		var commentObj = JSON.stringify(comment);
		$.ajax({
			url: "http://localhost:8080/api/comment/grammar/add-comment",
			method: 'POST',
			data: commentObj,
			contentType: 'application/json; charset = utf-8',
			success: function(){
				// alert('comment successfully');
				ajaxGetComment(1);
			},
			error:function(error){
				alert(error);
			}
		})

	});
	
	$(document).on('click', '.directpage', function(event) {
		var directId = $(this).attr('id');
		var fields = directId.split('.');
		var page = fields[1];
		ajaxGetForCauHoi(page);
	});
});