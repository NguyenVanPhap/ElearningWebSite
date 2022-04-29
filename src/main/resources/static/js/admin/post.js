


$(document).ready(function() {



	//default. load all object baiPost
	window.onload = function() {
		loadAllPost(1);

	};




	function loadAllPost(pageinput) {

		var namekey = $('#searchkeyword').val();
		var datainput = { keyWord: namekey, page: pageinput };
		console.log(datainput);
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/admin/post/getlist",
			data: JSON.stringify(datainput),
			success: function(data) {

				var trHTML = "";
				$.each(data.object.content, function(i, objPost) {

					trHTML += '<tr><td class= "center">' + objPost.id + '</td>'
						+ '<td class= "center">' + objPost.name + '</td>'


						+ '<td class = "center"> <a id="edit.' + objPost.id + ' "'

						+ 'class="yellow editBaiPost"><button class="btn btn-warning">Cập nhật</button></a> '

						+ ' <a id="delete.' + objPost.id + ' "'

						+ 'class="red deleteBaiPost" ><button class="btn btn-danger">Xóa</button></a> </td>'

						+ '</tr>';
				});

				if (data.object.totalPages > 0) {
					$('.pagination').empty();
					for (var numberPage = 1; numberPage <= data.object.totalPages; numberPage++) {
						var li;
						if (numberPage == pageinput)
							li = '<a class="directpage active" id="direct.' + numberPage + '"> ' + numberPage + '</a>';
						else
							li = '<a class="directpage" id="direct.' + numberPage + '"> ' + numberPage + '</a>';
						$('.pagination').append(li);
					};


				};
				//$('#tableExam').append(trHTML);
				$('tbody').html(trHTML);

			}, error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}
		});


	}
	$(document).on('click', '.btnAddnewGram', function(event) {

		$('#btnUpdate').hide();
		$('#btnAddNewPost').show();
		var modal = $('#PostModal');
		$('#PostModal #idPostModal').val("");
		modal.find('.modal-body #namePost').val("");
		modal.find('.modal-header #titleModal').text("Thêm mới bài ngữ pháp");
		/*simplemde.value("wiriting someshing here");*/
	});
	
	$(document).on('click', '.searchkeyword', function(event) {

		loadAllPost(1);
	});


	$(document).on('click', '.directpage', function(event) {
		var directId = $(this).attr('id');
		var fields = directId.split('.');
		var page = fields[1];
		loadAllPost(page);
	});
	//add new baiPost

	$('#btnAddNewPost').click(function() {
		// formData: nameBaiThiThu,file_Excel, file_Image, file_imageQuestion, file_Listening

		var editorData = CKEDITOR.instances['myckeditor'].getData();

		var formData = new FormData();
		var name = $('#PostName').val();
		var contentHTML = editorData;
		file_image = $('#file_imagePost')[0].files[0];
		formData.append("fileImage", file_image);
		formData.append("postName", name);
		
		formData.append("contentHtml", contentHTML);

		$.ajax({
			data: formData,
			type: 'POST',
			url: "http://localhost:8080/api/admin/post/add",
			enctype: 'multipart/form-data',
			contentType: false,
			cache: false,
			processData: false,
			success: function(data) {

				$('#PostModal').modal('hide');
				loadAllPost(1);
				alert("Thêm mới bài viết thành công");

			},

			error: function(e) {
				alert("error");
				$('#PostModal').modal('hide');
				console.log("ERROR: ", e);
			}

		});
	});

	// delete baiPost	
	$(document).on('click', '.deleteBaiPost', function() {
		var deleteId = $(this).attr('id');
		var fields = deleteId.split('.');
		var idBaiPost = fields[1];

		if (confirm("Bạn muốn xóa bài Post này?")) {
			$.ajax({
				type: 'POST',
				url: "http://localhost:8080/api/admin/post/delete/" + idBaiPost,
				success: function(data) {
					loadAllPost(1);
					alert("Xóa bài Post thành công");
				},
				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}

			});
		}

	});

	//edit baiPost	
	$(document).on('click', '.editBaiPost', function(event) {
		var editId = $(this).attr('id');
		var fields = editId.split('.');
		var idPost = fields[1];


		$.ajax({
			type: 'GET',
			contentType: "application/json",
			url: "http://localhost:8080/api/admin/post/getinfor/" + idPost,
			success: function(data) {

				var modal = $('#PostModal');
				$('#PostModal #PostModalId').val(idPost);
				modal.find('.modal-body #PostName').val(data.object.postName);
				modal.find('.modal-header #titleModal').text("Cập nhật bài ngữ pháp");
				modal.find('.modal-body #previewImage').attr('src', data.object.filePath);
				modal.find('.modal-body #myckeditor').val(data.object.contentHTML);
				CKEDITOR.instances['myckeditor'].setData(data.object.contentHTML);
				console.log(data.object.contentHTML);
				/*console.log(CKEDITOR.instances['myckeditor'].getData());*/
				//simplemde = null;
				$('#btnUpdate').show();
				$('#btnAddNewPost').hide();
				$('#PostModal').modal('show');
			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});

		$('#btnUpdate').unbind().click(function() {
			var formData = new FormData();
			var name = $('#PostName').val();
			var file_image;

			if ($('#file_imagePost').get(0).files.length != 0) {
				file_image = $('#file_imagePost')[0].files[0];
				formData.append("fileImage", file_image);
			}
			else {
				file_image = "test.jpg";
				formData.append("fileImage", "test.jpg");
			}

			/*var file_image = $('#file_imagePost')[0].files[0];
			formData.append("fileImage", file_image);*/
			var editorData = CKEDITOR.instances['myckeditor'].getData();
			formData.append("idPost", idPost);
			formData.append("postName", name);
			formData.append("contentHtml", editorData);
			$.ajax({
				data: formData,
				type: 'POST',
				url: "http://localhost:8080/api/admin/post/update",
				enctype: 'multipart/form-data',
				contentType: false,
				cache: false,
				processData: false,

				success: function(data) {
					$('#PostModal').modal('hide');
					alert("Cập nhật bài Post thành công");
					loadAllPost(1);

				},

				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}
			});
		});
	});
});
