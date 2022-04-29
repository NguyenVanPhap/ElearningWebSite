$(document).ready(function() {

	var changeImage = false;
	var changeExcel = false;
	var changeAudio = false;

	ajaxGet(1);
	function ajaxGet(page) {
		var level = $("#levelSearch").val();
		var part = $("#partSearch").val();
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/api/admin/listeningexercise/getlist" + "?page=" + page + "&level=" + level + "&part=" + part,
			success: function(result) {
				console.log(result)
				var listeningExerciseRow = "";
				$.each(result.object.content, function(i, listeningExercise) {
					// console.log(listeningExercise);
					listeningExerciseRow += '<tr style="text-align: center;">' + '<td width="5%">' + listeningExercise.id + '</td>' + '<td>' + listeningExercise.name + '</td>';
					if (listeningExercise.part === 1) {
						listeningExerciseRow += '<td>PART1 PHOTOGRAPHS</td>';
					} else if (listeningExercise.part === 2) {
						listeningExerciseRow += '<td>PART2 QUESTION RESPONSE</td>';
					} else {
						listeningExercise.part === 3 ? (listeningExerciseRow += '<td>PART3 SHORT CONVERSATIONS</td>') : (listeningExerciseRow += '<td>PART4 SHORT TALKS</td>');
					}
					if (listeningExercise.level === 1) {
						listeningExerciseRow += '<td>300-500</td>';
					} else {
						listeningExercise.level === 2 ? (listeningExerciseRow += '<td>500-700</td>') : (listeningExerciseRow += '<td>700-900</td>');
					}

					listeningExerciseRow += '<td>' + '<input type="hidden" value=' + listeningExercise.id + '>'
						+ '   <button class="btn btn-danger btnDeleteListeningExercise">Xóa</button></td>'
					'</tr>';
					$('.listeningExerciseTable tbody').append(listeningExerciseRow);
				});

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
				$('tbody').html(listeningExerciseRow);
			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});
	}
	;

	// event khi click duyệt bài nghe
	$(document).on('click', '#btnSubmitListeningExercise', function(event) {
		event.preventDefault();
		$('.listeningExerciseTable tbody tr').remove();
		$('.pagination li').remove();
		ajaxGet(1);
	});

	$(document).on('click', '.directpage', function(event) {
		var directId = $(this).attr('id');
		var fields = directId.split('.');
		var page = fields[1];
		ajaxGet(page);
	});
	// click event button Thêm mới bài nghe
	$('.btnAddListeningExercise').on("click", function(event) {
		event.preventDefault();
		$('#ListeningExerciseModal').modal();
		$('.listeningExerciseForm #id').prop("disabled", true);
		$('#formListeningExercise').removeClass().addClass("addForm");
		$('#formListeningExercise #btnSubmit').removeClass().addClass("btn btn-primary btnSaveForm");
	});

	// click event button cập nhật bài nghe
	$(document).on('click', '.btnCapNhatListeningExercise', function(event) {
		event.preventDefault();
		var listeningExerciseId = $(this).parent().find('input').val();
		$('#formListeningExercise').removeClass().addClass("updateForm");
		$('#formListeningExercise #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateForm");
		var href = "http://localhost:8080/api/admin/listeningexercise/" + listeningExerciseId;
		$.get(href, function(ListeningExercise) {
			console.log(listeningExercise);
			$('#id').val(listeningExercise.id);
			$('#listeningExerciseName').val(listeningExercise.name);
			$('#level').val(listeningExercise.level);
			$('#phanThi').val(listeningExercise.part);
			$('#script').val(listeningExercise.script);

			//            $('#photolisteningExercise').val("http://localhost:8080/file/images/baiNgheId="+ baiNghe.id+".png");
			$("img").attr("src", "http://localhost:8080/file/images/listeningExerciseId=" + listeningExercise.id + ".png");
			$("#previewImage").removeClass("hidden");
			$("#previewAudio").attr("src", "http://localhost:8080/file/audio/listeningExerciseId=" + listeningExercise.id + ".mp3");
			$("#previewAudio").removeClass("hidden");
			$("#linkExcel").attr("href", "http://localhost:8080/file/excel/listeningExerciseId=" + listeningExercise.id + ".xlsx");
			$("#linkExcel").removeClass("hidden");
		});
		$('#listeningExerciseModal').modal();
	});

	// event khi hide modal
	$('#listeningExerciseModal').on('hidden.bs.modal', function() {
		$('#formListeningExercise').removeClass().addClass("listeningExerciseForm");
		$('#formListeningExercise #btnSubmit').removeClass().addClass("btn btn-primary");
		resetForm();
	});

	// delete request
	$(document).on("click", ".btnDeleteListeningExercise", function() {
		var listeningExerciseId = $(this).parent().find('input').val();
		var workingObject = $(this);
		var confirmation = confirm("Bạn chắc chắn xóa bài nghe này ?");
		if (confirmation) {
			$.ajax({
				type: "DELETE",
				url: "http://localhost:8080/api/admin/listeningexercise/delete/" + listeningExerciseId,
				success: function(resultMsg) {
					resetDataForDelete();
					alert("Xóa thành công");
				},
				error: function(e) {
					alert("Không thể xóa danh mục này ! Hãy kiểm tra lại");
					console.log("ERROR: ", e);
				}
			});
		}
	});

	// reset table after post, put
	function resetData() {
		$('.listeningExerciseTable tbody tr').remove();
		var page = $('li.active').children().text();
		$('.pagination li').remove();
		ajaxGet(page);
	}
	;

	function resetDataForDelete() {
		var count = $('.listeningExerciseTable tbody').children().length;
		//    	console.log("số cột " + count);
		$('.listeningExerciseTable tbody tr').remove();
		ajaxGet(1);

	};

	// event khi click vào phân trang bài nghe
	$(document).on('click', '.pageNumber', function(event) {
		// event.preventDefault();
		var page = $(this).text();
		$('.listeningExerciseTable tbody tr').remove();
		$('.pagination li').remove();
		ajaxGet(page);
	});

	// validate form trước khi submit
	$("#formListeningExercise").validate({
		errorElement: "p",
		errorClass: "error-message",
		rules: {
			listeningExerciseName: {
				required: true,
				maxlength: 100
			},
			photoListeningExercise: {
				required: true
			},
			audioListeningExercise: {
				required: true
			},
			fileCauHoi: {
				required: true
			},
			//			script : { // nếu là part 3 hoặc 4 thì ko đc để trống
			//				required : {
			//					depends : function() {
			//						return $("#phanThi").val() === '3' || $("#phanThi").val() === '4';
			//					}
			//				}
			//			}
		},
		messages: {
			name: {
				required: "Bạn không được để trống phần này",
				maxlength: "Tiêu đề dài nhất là 100 chữ cái"
			},
			photo: {
				required: "Bạn không được để trống phần này"
			},
			audio: {
				required: "Bạn không được để trống phần này"
			},
			fileExcelQuestion: {
				required: "Bạn không được để trống phần này"
			},
			script: {
				required: "Bạn không được để trống phần này"
			}
		},
		submitHandler: function(form) {
			var form = $('#formListeningExercise')[0];
			var formData = new FormData(form);
			saveFunction(formData);
		}
	});


	$(document).on('click', '.btnSaveForm', function(event) {
		event.preventDefault();
		if ($("#formListeningExercise").valid()) {
			$("#formListeningExercise").submit();
		}
	});

	//	// click event button xác nhận update form
	//	$(document).on('click', '.btnUpdateForm', function(event) {
	//		event.preventDefault();	
	//		var formData = new FormData();
	//		if(changeAudio = true){
	//			formData.append("audio", $("#audioBaiNghe").files[0]);
	//		}
	//		if(changeImage = true){
	//			formData.append("photoBaiNghe", $("#photoBaiNghe").files[0]);
	//		}
	//		if(changeExcel = true){
	//			formData.append("fileCauHoi", $("#fileCauHoi").files[0]);
	//		}
	//		formData.append("id", $("#id").val());
	//		formData.append("phanThi", $("#phanThi").val());
	//		formData.append("level", $("#level").val());
	//	});

	// validate các trường file input và preview file ảnh/ audio
	$("#audioListeningExercise").change(function() {
		var fileExtension = ['mp3'];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép định dạng audio mp3 ");
			$("#audioListeningExercise").wrap('<form>').closest('form').get(0).reset();
			$("#audioListeningExercise").unwrap();
			$("#previewAudio").removeClass().addClass("hidden");
		} else {
			var files = event.target.files;
			$("#previewAudio").attr("src", URL.createObjectURL(files[0]));
			$("#previewAudio").removeClass("hidden");
		}
		changeAudio = true;
	});

	$("#photoListeningExercise").change(function() {
		var fileExtension = ['jpg', 'png'];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép ảnh định dang JPEG, PNG");
			$("#photoListeningExercise").wrap('<form>').closest('form').get(0).reset();
			$("#photoListeningExercise").unwrap();
			$("#previewImage").removeClass().addClass("hidden");
		} else {
			var files = event.target.files;
			$("#previewImage").attr("src", URL.createObjectURL(files[0]));
			$("#previewImage").removeClass("hidden");
			// $("#previewImage").load();
		}
		changeImage = true;
	});

	$("#fileCauHoi").change(function() {
		var fileExtension = ['xlsx'];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép file Excel định dang xlsx");
			$("#fileCauHoi").wrap('<form>').closest('form').get(0).reset();
			$("#fileCauHoi").unwrap();

		}
		changeExcel = true;
	});

	// reset form
	function resetForm() {
		$("#id").val("");
		$("#listeningExerciseName").val("");
		$("#script").val("");
		$("#fileCauHoi").wrap('<form>').closest('form').get(0).reset();
		$("#fileCauHoi").unwrap();
		$("#photoListeningExercise").wrap('<form>').closest('form').get(0).reset();
		$("#photoListeningExercise").unwrap();
		$("#previewImage").addClass("hidden");
		$("#audioListeningExercise").wrap('<form>').closest('form').get(0).reset();
		$("#audioListeningExercise").unwrap();
		$("#previewAudio").addClass("hidden");
		$("#linkExcel").addClass("hidden");
		$("#linkExcel").attr("href", "");
		changeImage = false;
		changeExcel = false;
		changeAudio = false;
	}

	function saveFunction(formData) {
		// do post
		$.ajax({
			async: false,
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/admin/listeningexercise/save",
			enctype: 'multipart/form-data',
			data: formData,
			// prevent jQuery from automatically transforming the data into a
			// query string
			processData: false,
			contentType: false,
			cache: false,
			timeout: 1000000,

			success: function(response) {
				$("#ListeningExerciseModal").modal('hide');
				alert("Thêm thành công");
				window.location.reload();
			},
			error: function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}

	function updateFunction(formData, id) {
		// do post
		$.ajax({
			async: false,
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/admin/listeningexercise/update/" + id,
			enctype: 'multipart/form-data',
			data: formData,
			// prevent jQuery from automatically transforming the data into a
			// query string
			processData: false,
			contentType: false,
			cache: false,
			timeout: 1000000,

			success: function(response) {
				$("#ListeningExerciseModal").modal('hide');
				alert("Cập nhật thành công");


			},
			error: function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}
});

