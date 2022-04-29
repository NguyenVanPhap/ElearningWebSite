$(document).ready(function() {
	ajaxGet(1);
	function ajaxGet(page) {
		var level = $("#levelSearch").val();
		var part = $("#partSearch").val();
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/api/client/listening-exercise/getall" + "?page=" + page + "&level=" + level + "&part=" + part,
			success: function(result) {
				if (result.error == false) {
					$("#pTag").removeClass().addClass("hidden");
					console.log(result);
					console.log("http://localhost:8080/api/client/listening-exercise/getall" + "?page=" + page + "&level=" + level + "&part=" + part);
					var html="";
					$.each(result.object.content, function(i, listeningExercise) {
						var strlvl = ""
						if (listeningExercise.level == 1)
							strlvl = "300-500"
						else if (listeningExercise.level == 2)
							strlvl = "500-700"
						else
							strlvl = "700-900"
						
						html += '<div class="job-box d-md-flex align-items-center justify-content-between mb-30">'
							+ '<div class="job-left my-4 d-md-flex align-items-center flex-wrap">'
							+ '<div class="img-holder mr-md-4 mb-md-0 mb-4 mx-auto mx-md-0 d-md-none d-lg-flex">'
							+ 'FD</div>'
							+ '<div class="job-content">'
							+ '<h5 class="text-center text-md-left">' + listeningExercise.name + '</h5>'
							+ '<ul class="d-md-flex flex-wrap text-capitalize ff-open-sans">'
							+ '	<li class="mr-md-4">'
							+ '		<i class="zmdi zmdi-pin mr-2"></i>Part ' + listeningExercise.part
							+ '	</li>'
							+ '<li class="mr-md-4">'
							+ '	<i class="zmdi zmdi-money mr-2"></i>' + strlvl
							+ '</li>'

							+ '</ul>'
							+ '</div>'
							+ '</div>'
							+ '<div class="job-right my-4 flex-shrink-0">'
							+ '<a class="btn d-block w-100 d-sm-inline-block btn-light" href="/test/skill-test/listening/part-'+listeningExercise.part+'/'+listeningExercise.id+'">Làm ngay</a>'
							+ '</div>'
							+ '</div>'
						/*var html = '<div class="span1"></div>'
							+ '<div class="span5"> '
							+ '<h4 class="content-heading" id="namebaithithu">' + baiDoc.name + '</h4>'
							+ '<a class="btn btn-primary" href="/reading/part-' + baiDoc.part + '/' + baiDoc.id + '"> Chi tiết</button>'
							+ '</div>'*/

						
					});
					$('.listListeningExercise').html(html);

					if (result.object.totalPages > 0) {

						$('.pagination').empty();
						for (var numberPage = 1; numberPage <= result.object.totalPages; numberPage++) {
							var li;
							if (numberPage == page)
								li = '<a class="directpage active" id="direct.' + numberPage + '"> ' + numberPage + '</a>';
							else
								li = '<a class="directpage" id="direct.' + numberPage + '"> ' + numberPage + '</a>';
							$('.pagination').append(li);
						};


					};
				} else {

					alert("lỗi");
					/*$("#pTag").removeClass("hidden");*/
				}
			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});
	};

	$(document).on('click', '.directpage', function(event) {
		var directId = $(this).attr('id');
		var fields = directId.split('.');
		var page = fields[1];
		ajaxGet(page);
	});


	// event khi click duyệt bài đọc 
	$(document).on('click', '#btnDuyetBaiDoc', function(event) {
		event.preventDefault();
		$('.danhSach div').remove();
		$('.pagination li').remove();
		ajaxGet(1);
	});
});