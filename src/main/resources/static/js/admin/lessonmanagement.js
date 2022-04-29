


$(document).ready(function() {
	var simplemde;
	var currentCourseId;
	//default. load all object baiGrammar
	window.onload = function() {
		loadAllLesson();

		// creat markdown
		simplemde = new SimpleMDE({
			element: document.getElementById("markdown-editor"),
			spellChecker: false,
		});

	};



	function loadAllLesson() {
		let searchParams = new URLSearchParams(window.location.search);
		currentCourseId = searchParams.get('courseId');
		$.ajax({
			dataType: 'json',
			type: 'GET',
			url: "http://localhost:8080/api/admin/lesson/loadCourseLesson/" + currentCourseId,

			success: function(data) {

				//convert array to json type
				var jsonArray = new Array();
				var fields, id, courseId, lessonName, videoPath;
				console.log(data[0]);
				for (var i = 0; i < data.length; i++) {
					var jsonObject = new Object();
					fields = data[i].split('|||');

					id = fields[0].split('==');
					jsonObject.lessonId = id[1];

					courseId = fields[1].split('==');
					jsonObject.courseId = courseId[1];

					lessonName = fields[2].split('==');
					jsonObject.lessonName = lessonName[1];

					videoPath = fields[3].split('==');
					jsonObject.videoPath = videoPath[1];

					jsonArray.push(jsonObject);
					console.log(jsonObject);
				}


				var jsonArr = JSON.parse(JSON.stringify(jsonArray));

				var trHTML = "";
				for (var i = 0; i < jsonArr.length; i++) {

					trHTML += '<tr><td class= "center">' + jsonArr[i].lessonId + '</td>'
						+ '<td class= "center">' + jsonArr[i].lessonName + '</td>'


						+ '<td class = "center"> <a id="edit.' + jsonArr[i].lessonId + ' "'

						+ 'class="yellow editLesson"><button class="btn btn-warning">Cập nhật</button></a> '

						+ '<a id="demo===' + jsonArr[i].videoPath + ' "'

						+ 'class="demoLesson"><button class="btn btn-warning">Demo</button></a> '

						+ ' <a id="delete.' + jsonArr[i].lessonId + ' "'

						+ 'class="red deleteLesson" ><button class="btn btn-danger">Xóa</button></a> </td>'

						+ '</tr>';
				}

				//$('#tableExam').append(trHTML);
				$('tbody').html(trHTML);

			}, error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}
		});
	}
	//add new Lesson
	$(document).on('click', '.btnOpenAddNewLesson', function(event) {
		$('#btnUpdate').hide();
		$('#btnAddNewLesson').show();
		var modal = $('#lessonModal');
		$('#lessonModal #idLessonModal').val("");
		modal.find('.modal-body #lessonName').val("");
		modal.find('.modal-body #lessonVideoPath').val("");
		modal.find('.modal-header #titleModal').text("Thêm mới bài giảng");
	});

	$('#btnAddNewLesson').click(function() {

		var formData = new FormData();
		var LessonName = $('#lessonName').val();
		var VideoPath = $('#lessonVideoPath').val();

		formData.append("lessonName", LessonName);
		formData.append("videoPath", VideoPath);
		formData.append("courseId", currentCourseId);

		$.ajax({
			data: formData,
			type: 'POST',
			url: "http://localhost:8080/api/admin/lesson/save",
			enctype: 'multipart/form-data',
			contentType: false,
			cache: false,
			processData: false,
			success: function(data) {
				$('#lessonModal').modal('hide');
				loadAllLesson();
				alert("Thêm mới bài học thành công")
			},
			error: function(e) {
				alert("error");
				$('#lessonModal').modal('hide');
				console.log("ERROR: ", e);
			}

		});
	});
	// demo Lesson	
	function convert_youtube(input) {
		var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
		var match = input.match(regExp);
		src = "//www.youtube.com/embed/" + match[2];
		return src;
	}
	$(document).on('click', '.demoLesson', function() {
		var demoId = $(this).attr('id');
		var fields = demoId.split('===');
		var lessonvideoPath = fields[1];

		var src = convert_youtube(lessonvideoPath);
		console.log(src, lessonvideoPath);
		var modal = $('#demoLessonModal');
		modal.find('.modal-body #ifrdemolessonSrc').attr('src', src);
		$('#demoLessonModal').modal('show');

	});
	// Delete Lesson	
	$(document).on('click', '.deleteLesson', function() {
		var deleteId = $(this).attr('id');
		var fields = deleteId.split('.');
		var lessonId = fields[1];

		if (confirm("Bạn muốn xóa bài học này?")) {
			$.ajax({
				type: 'POST',
				url: "http://localhost:8080/api/admin/lesson/delete/" + lessonId,
				success: function(data) {
					loadAllLesson();
					alert("Xóa bài học thành công");
				},
				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}

			});
		}

	});
	//edit Khoa Hoc
	$(document).on('click', '.editLesson', function(event) {
		var editId = $(this).attr('id');
		var fields = editId.split('.');
		var LessonId = fields[1];
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/admin/lesson/infoLesson/" + LessonId,
			success: function(data) {

				var jsonObject = new Object();
				fields = data.split('|||');

				id = fields[0].split('==');
				jsonObject.lessonId = id[1];

				courseId = fields[1].split('==');
				jsonObject.courseId = courseId[1];

				lessonName = fields[2].split('==');
				jsonObject.lessonName = lessonName[1];

				videoPath = fields[3].split('==');
				jsonObject.videoPath = videoPath[1];
				//set data for modal

				console.log(jsonObject);
				var modal = $('#lessonModal');
				$('#LessonModal #idLessonModal').val(LessonId);
				modal.find('.modal-body #lessonName').val(jsonObject.lessonName);
				modal.find('.modal-body #lessonVideoPath').val(jsonObject.videoPath);
				modal.find('.modal-header #titleModal').text("Cập nhật bài học");
				//simplemde = null;
				$('#btnUpdate').show();
				$('#btnLessonDetail').show();
				$('#btnAddNewLesson').hide();
				$('#lessonModal').modal('show');

			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});

		$('#btnLessonDetail').click(function() {
			window.location.href = "/admin/lesson/" + LessonId;
		});

		$('#btnUpdate').unbind().click(function() {

			var formData = new FormData();
			var LessonName = $('#lessonName').val();
			var LessonVideoPath = $('#lessonVideoPath').val();


			formData.append("lessonId", LessonId);
			formData.append("lessonName", LessonName);
			formData.append("videoPath", LessonVideoPath);
			$.ajax({
				data: formData,
				type: 'POST',
				url: "http://localhost:8080/api/admin/lesson/update",
				enctype: 'multipart/form-data',
				contentType: false,
				cache: false,
				processData: false,

				success: function(data) {
					$('#lessonModal').modal('hide');
					alert("Cập nhật bài học thành công");
					loadAllLesson();
				},

				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}
			});
		});
	});
});
