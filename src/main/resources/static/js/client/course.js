
$(document).ready(function() {
	var simplemde;
	var currentCourseId;
	//default. load all object baiGrammar
	window.onload = function() {
		let searchParams = new URLSearchParams(window.location.search);
		StartLessonId = searchParams.get('start');
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/admin/lesson/infoLesson/" + StartLessonId,
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

				var src = convert_youtube(jsonObject.videoPath);
				$('#ifrdemolessonSrc').attr('src', src);

			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});

	};
	// demo Lesson	
	function convert_youtube(input) {
		var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
		var match = input.match(regExp);
		src = "//www.youtube.com/embed/" + match[2];
		return src;
	}
	$(document).on('click', '.leaningLesson', function() {
		var loadingId = $(this).attr('id');
		var fields, id, courseId, lessonName, videoPath;
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/admin/lesson/infoLesson/" + loadingId,
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

				var src = convert_youtube(jsonObject.videoPath);
				$('#ifrdemolessonSrc').attr('src', src);

			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});

		/*var demoId = $(this).attr('id');
		var fields = demoId.split('===');
		var lessonvideoPath = fields[1];

		var src = convert_youtube(lessonvideoPath);
		console.log(src, lessonvideoPath);
		var modal = $('#demoLessonModal');
		modal.find('.modal-body #ifrdemolessonSrc').attr('src', src);
		$('#demoLessonModal').modal('show');*/

	});

});
