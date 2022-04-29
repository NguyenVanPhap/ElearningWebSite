


$(document).ready(function() {
	var simplemde;

	//default. load all object baiGrammar
	window.onload = function() {
		loadAllCourse();

		// creat markdown
		simplemde = new SimpleMDE({
			element: document.getElementById("markdown-editor"),
			spellChecker: false,
		});

	};




	function loadAllCourse() {
		$.ajax({
			dataType: 'json',
			type: 'GET',
			url: "http://localhost:8080/api/admin/course/loadCources",

			success: function(data) {

				//convert array to json type
				var jsonArray = new Array();
				var fields, id, courseName, targetuser, content;
				for (var i = 0; i < data.length; i++) {
					var jsonObject = new Object();
					fields = data[i].split('|@|');

					id = fields[0].split('===');
					jsonObject.courseId = id[1];

					courseName = fields[1].split('===');
					jsonObject.courseName = courseName[1];

					targetuser = fields[2].split('===');
					jsonObject.targetuser = targetuser[1];

					content = fields[3].split('===');
					jsonObject.content = content[1];

					jsonArray.push(jsonObject);
				}

				console.log(jsonArray);
				var jsonArr = JSON.parse(JSON.stringify(jsonArray));

				var trHTML = "";
				for (var i = 0; i < jsonArr.length; i++) {

					trHTML += '<tr><td class= "center">' + jsonArr[i].courseId + '</td>'
						+ '<td class= "center">' + jsonArr[i].courseName + '</td>'

						+ '<td class= "elementcontent">' + jsonArr[i].content + '</td>'
						+ '<td >'+'</td>'
						+ '<td class= "elementcontent">' + jsonArr[i].targetuser + '</td>'

						+ '<td class = "center"> <a id="edit.' + jsonArr[i].courseId + ' "'

						+ 'class="yellow editCourse "><button class="btn btn-warning">Cập nhật</button></a> '

						+ ' <a id="delete.' + jsonArr[i].courseId + ' "'

						+ 'class="red deleteCourse" ><button class="btn btn-danger">Xóa</button></a> </td>'

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
	//add new course
	$(document).on('click', '.btnOpenAddNewCourse', function(event) {
		$('#btnUpdate').hide();
		$('#btnAddNewCourse').show();
		var modal = $('#courseModal');
		$('#grammarModal #idCourseModal').val("");
		modal.find('.modal-body #nameCourse').val("");
		modal.find('.modal-body #CourseContent').val("");
		modal.find('.modal-body #CourseTargetUser').val("");
		modal.find('.modal-header #titleModal').text("Thêm mới khóa học");
	});

	$('#btnAddNewCourse').click(function() {

		var formData = new FormData();
		var courseName = $('#nameCourse').val();
		var courseTargetUser = $('#CourseTargetUser').val();
		var courseContent = $('#CourseContent').val();

		formData.append("courseName", courseName);
		formData.append("targetUser", courseTargetUser);
		formData.append("content", courseContent);

		$.ajax({
			data: formData,
			type: 'POST',
			url: "http://localhost:8080/api/admin/course/save",
			enctype: 'multipart/form-data',
			contentType: false,
			cache: false,
			processData: false,
			success: function(data) {

				$('#courseModal').modal('hide');
				loadAllCourse();

				alert("Thêm mới khóa học thành công")

			},

			error: function(e) {
				alert("error");
				$('#courseModal').modal('hide');
				console.log("ERROR: ", e);
			}

		});
	});
	// delete Course	
	$(document).on('click', '.deleteCourse', function() {
		var deleteId = $(this).attr('id');
		var fields = deleteId.split('.');
		var courseId = fields[1];

		if (confirm("Bạn muốn xóa khóa học này?")) {
			$.ajax({
				type: 'POST',
				url: "http://localhost:8080/api/admin/course/delete/" + courseId,
				success: function(data) {
					loadAllCourse();
					alert("Xóa khóa học thành công");
				},
				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}

			});
		}

	});
	//edit Khoa Hoc
	$(document).on('click', '.editCourse', function(event) {
		var editId = $(this).attr('id');
		var fields = editId.split('.');
		var courseId = fields[1];
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/admin/course/infoCourse/" + courseId,
			success: function(data) {

				var jsonObject = new Object();
				fields = data.split('|');

				id = fields[0].split('==');
				jsonObject.courseId = id[1];

				courseName = fields[1].split('==');
				jsonObject.courseName = courseName[1];

				targetuser = fields[2].split('==');
				jsonObject.targetuser = targetuser[1];

				content = fields[3].split('==');
				jsonObject.content = content[1];
				//set data for modal

				console.log(jsonObject);
				var modal = $('#courseModal');
				$('#courseModal #idcourseModal').val(courseId);
				modal.find('.modal-body #nameCourse').val(jsonObject.courseName);
				modal.find('.modal-body #CourseContent').val(jsonObject.content);
				modal.find('.modal-body #CourseTargetUser').val(jsonObject.targetuser);
				modal.find('.modal-header #titleModal').text("Cập nhật khóa học");
				//simplemde = null;
				$('#btnUpdate').show();
				$('#btnCourseDetail').show();
				$('#btnAddNewCourse').hide();
				$('#courseModal').modal('show');

			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});
		
		$('#btnCourseDetail').click(function() {
			window.location.href = "/admin/coursedetail?courseId="+ courseId;
		});

		$('#btnUpdate').unbind().click(function() {

			var formData = new FormData();
			var courseName = $('#nameCourse').val();
			var courseTargetUser = $('#CourseTargetUser').val();
			var courseContent = $('#CourseContent').val();


			formData.append("courseId", courseId);
			formData.append("courseName", courseName);
			formData.append("targetUser", courseTargetUser);
			formData.append("content", courseContent);


			$.ajax({
				data: formData,
				type: 'POST',
				url: "http://localhost:8080/api/admin/course/update",
				enctype: 'multipart/form-data',
				contentType: false,
				cache: false,
				processData: false,

				success: function(data) {
					$('#courseModal').modal('hide');
					alert("Cập nhật khóa học thành công");
					loadAllCourse();
				},

				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}
			});
		});
	});
});
