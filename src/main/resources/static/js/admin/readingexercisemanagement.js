$(document).ready(function() {
	var changeImage = false;
	var changeExcel = false;


	ajaxGet(1);
	function ajaxGet(page) {
		var level = $("#lvlSearch").val();
		var part = $("#partSearch").val();
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/api/admin/readingexercise/getall" + "?page=" + page + "&level=" + level + "&part=" + part,
			success: function(result) {

				var baiDocRow ="";
				$.each(result.object.content, function(i, baiDoc) {
					//					console.log(baiDoc);
					baiDocRow += '<tr style="text-align: center;">' +
						'<td width="5%">' + baiDoc.id + '</td>' +
						'<td>' + baiDoc.name + '</td>';
					if (baiDoc.part === 5) {
						baiDocRow += '<td>PART5_COMPLETE_SENTENCE</td>';
					} else {
						baiDoc.part === 6 ? (baiDocRow += '<td>PART6_COMPLETE_THE_PARAGRAPH</td>') : (baiDocRow += '<td>PART7_READING_COMPREHENSION</td>');
					}
					if (baiDoc.level === 1) {
						baiDocRow += '<td>300-500</td>';
					} else {
						baiDoc.level === 2 ? (baiDocRow += '<td>500-700</td>') : (baiDocRow += '<td>700-900</td>');
					}

					baiDocRow += '<td>' + '<input type="hidden" value=' + baiDoc.id + '>'
						+ '  <button class="btn btn-primary btnUpdateReadingExercise" >Cập nhật</button>'
						+ '   <button class="btn btn-danger btnDeleteReadingExercise">Xóa</button></td>'
					'</tr>';
					$('.baiDocTable tbody').append(baiDocRow);
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
				$('tbody').html(baiDocRow);
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
		$('.baiDocTable tbody tr').remove();
		$('.pagination li').remove();
		ajaxGet(1);
	});

	// click event button Thêm mới bài đọc
	$('.addReadingExercise').on("click", function(event) {
		event.preventDefault();
		$('#baiDocModal').modal();
		$('.readingExerciseForm #id').prop("disabled", true);
		$('#formReadingExercise').removeClass().addClass("addForm");
		$('#formReadingExercise #btnSubmit').removeClass().addClass("btn btn-primary btnSaveForm");
	});

	// click event button cập nhật bài đọc
	$(document).on('click', '.btnUpdateReadingExercise', function(event) {
		event.preventDefault();
		var baiDocId = $(this).parent().find('input').val();
		$('#formReadingExercise').removeClass().addClass("updateForm");
		$('#formReadingExercise #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateForm");
		var href = "http://localhost:8080/webtoeic/api/admin/readingexercise/" + baiDocId;
		$.get(href, function(baiDoc) {
			console.log(baiDoc);
			$('#id').val(baiDoc.id);
			$('#readingExerciseName').val(baiDoc.name);
			$('#level').val(baiDoc.level);
			$('#partToeic').val(baiDoc.part);
			$('#script').val(baiDoc.script);
			$("img").attr("src", "http://localhost:8080/webtoeic/file/images/baiDocId=" + baiDoc.id + ".png");
			$("#previewImage").removeClass("hidden");
			$("#linkExcel").attr("href", "http://localhost:8080/webtoeic/file/excel/baiDocId=" + baiDoc.id + ".xlsx");
			$("#linkExcel").removeClass("hidden");
		});
		$('#baiDocModal').modal();
	});

	// event khi hide modal
	$('#baiDocModal').on('hidden.bs.modal', function() {
		$('#formReadingExercise').removeClass().addClass("readingExerciseForm");
		$('#formReadingExercise #btnSubmit').removeClass().addClass("btn btn-primary");
		resetForm();
	});


	// delete request
	$(document).on("click", ".btnDeleteReadingExercise", function() {
		var baiDocId = $(this).parent().find('input').val();
		var workingObject = $(this);
		var confirmation = confirm("Bạn chắc chắn xóa bài đọc này ?");
		if (confirmation) {
			$.ajax({
				type: "DELETE",
				url: "http://localhost:8080/api/admin/readingexercise/delete/" + baiDocId,
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
		$('.baiDocTable tbody tr').remove();
		var page = $('li.active').children().text();
		$('.pagination li').remove();
		ajaxGet(page);
	};

	// reset table after delete
	function resetDataForDelete() {
		var count = $('.baiDocTable tbody').children().length;
		//    	console.log("số cột " + count);
		$('.baiDocTable tbody tr').remove();
		ajaxGet(1);

	};

	// event khi click vào phân trang bài đọc
	$(document).on('click', '.pageNumber', function(event) {
		//		event.preventDefault();
		var page = $(this).text();
		$('.baiDocTable tbody tr').remove();
		$('.pagination li').remove();
		ajaxGet(page);
	});

	//validate form trước khi submit
	$("#formReadingExercise").validate({
		errorElement: "p",
		errorClass: "error-message",
		rules: {
			readingExerciseName: {
				required: true,
				maxlength: 100
			},
			readingExerciseImage: {
				required: true
			},
			fileExcel: {
				required: true
			},
			script: {
				required: {
					depends: function() {
						return $("#partToeic").val() === '5' || $("#partToeic").val() === '6' || $("#partToeic").val() === '7';
					}
				}
			}
		},
		messages: {
			readingExerciseName: {
				required: "Bạn không được để trống phần này",
				maxlength: "Tiêu đề dài nhất là 100 chữ cái"
			},
			readingExerciseImage: {
				required: "Bạn không được để trống phần này"
			},
			fileExcelCauHoi: {
				required: "Bạn không được để trống phần này"
			},
			script: {
				required: "Bạn không được để trống phần này"
			}
		},
		submitHandler: function(form) {
			var form = $('#formReadingExercise')[0];
			var formData = new FormData(form);
			saveFunction(formData);
		}
	});

	$(document).on('click', '.btnSaveForm', function(event) {
		event.preventDefault();
		if ($("#formReadingExercise").valid()) {
			$("#formReadingExercise").submit();
		}
	});


	// validate các trường file input và preview file ảnh
	$("#readingExerciseImage").change(function() {
		var fileExtension = ['jpg', 'png'];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép ảnh định dang JPEG, PNG");
			$("#readingExerciseImage").wrap('<form>').closest('form').get(0).reset();
			$("#readingExerciseImage").unwrap();
			$("#previewImage").removeClass().addClass("hidden");
		} else {
			var files = event.target.files;
			$("#previewImage").attr("src", URL.createObjectURL(files[0]));
			$("#previewImage").removeClass("hidden");
			// $("#previewImage").load();
		}
		changeImage = true;
	});


	$("#fileExcel").change(function() {
		var fileExtension = ['xlsx'];
		if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
			alert("Chỉ cho phép file Excel định dang xlsx");
			$("#fileExcel").wrap('<form>').closest('form').get(0).reset();
			$("#fileExcel").unwrap();

		}
	});

	// reset form
	function resetForm() {
		$("#id").val("");
		$("#readingExerciseName").val("");
		$("#script").val("");
		$("#fileExcel").wrap('<form>').closest('form').get(0).reset();
		$("#fileExcel").unwrap();
		$("#readingExerciseImage").wrap('<form>').closest('form').get(0).reset();
		$("#readingExerciseImage").unwrap();
		$("#previewImage").addClass("hidden");
		$("#linkExcel").addClass("hidden");
		$("#linkExcel").attr("href", "");
		changeImage = false;
		changeExcel = false;
	};


	function saveFunction(formData) {
		// do post
		$.ajax({
			async: false,
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/admin/readingexercise/add",
			enctype: 'multipart/form-data',
			data: formData,
			// prevent jQuery from automatically transforming the data into a
			// query string
			processData: false,
			contentType: false,
			cache: false,
			timeout: 1000000,

			success: function(response) {
				$("#baiDocModal").modal('hide');
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
			url: "http://localhost:8080/webtoeic/api/admin/readingexercise/update/" + id,
			enctype: 'multipart/form-data',
			data: formData,
			// prevent jQuery from automatically transforming the data into a
			// query string
			processData: false,
			contentType: false,
			cache: false,
			timeout: 1000000,

			success: function(response) {
				$("#baiDocModal").modal('hide');
				alert("Cập nhật thành công");


			},
			error: function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}

});