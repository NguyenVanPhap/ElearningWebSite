


$(document).ready(function() {



	//default. load all object baiGrammar
	window.onload = function() {
		loadAllGrammar(1);

	};




	function loadAllGrammar(pageinput) {

		var namekey = $('#searchkeyword').val();
		var datainput = { keyWord: namekey, page: pageinput };
		console.log(datainput);
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/admin/grammar/getlist",
			data: JSON.stringify(datainput),
			success: function(data) {

				console.log(data);
				//convert array to json type
				/*var jsonArray = new Array();
				var fields, id, tenbaigrammar;
				for (var i = 0; i < data.length; i++) {
					var jsonObject = new Object();
					fields = data[i].split(',');
					id = fields[0].split(':');
					jsonObject.baigrammarid = id[1];
					tenbaigrammar = fields[1].split(':');
					jsonObject.tenbaigrammar = tenbaigrammar[1];
					jsonArray.push(jsonObject);
				}*/

				/*var jsonArr = JSON.parse(JSON.stringify(jsonArray));*/

				var trHTML = "";
				$.each(data.object.content, function(i, objgrammar) {

					trHTML += '<tr><td class= "center">' + objgrammar.grammarId + '</td>'
						+ '<td class= "center">' + objgrammar.grammarName + '</td>'


						+ '<td class = "center"> <a id="edit.' + objgrammar.grammarId + ' "'

						+ 'class="yellow editBaiGrammar"><button class="btn btn-warning">Cập nhật</button></a> '

						+ ' <a id="delete.' + objgrammar.grammarId + ' "'

						+ 'class="red deleteBaiGrammar" ><button class="btn btn-danger">Xóa</button></a> </td>'

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
		$('#btnAddNewGrammar').show();
		var modal = $('#grammarModal');
		$('#grammarModal #idGrammarModal').val("");
		modal.find('.modal-body #nameGrammar').val("");
		modal.find('.modal-header #titleModal').text("Thêm mới bài ngữ pháp");
		/*simplemde.value("wiriting someshing here");*/
	});
	
	$(document).on('click', '.searchkeyword', function(event) {

		loadAllGrammar(1);
	});


	$(document).on('click', '.directpage', function(event) {
		var directId = $(this).attr('id');
		var fields = directId.split('.');
		var page = fields[1];
		loadAllGrammar(page);
	});
	//add new baigrammar

	$('#btnAddNewGrammar').click(function() {
		// formData: nameBaiThiThu,file_Excel, file_Image, file_imageQuestion, file_Listening

		var editorData = CKEDITOR.instances['myckeditor'].getData();

		var formData = new FormData();
		var name = $('#nameGrammar').val();
		var contentMarkdown = editorData; //get from textarea markdown
		var contentHTML = editorData;

		file_image = $('#file_imageGrammar')[0].files[0];
		formData.append("fileImage", file_image);



		formData.append("grammarName", name);
		formData.append("contentMarkdown", contentMarkdown);
		formData.append("contentHtml", contentHTML);

		$.ajax({
			data: formData,
			type: 'POST',
			url: "http://localhost:8080/api/admin/grammar/save",
			enctype: 'multipart/form-data',
			contentType: false,
			cache: false,
			processData: false,
			success: function(data) {

				$('#grammarModal').modal('hide');
				loadAllGrammar(1);
				alert("Thêm mới bài grammar thành công");

			},

			error: function(e) {
				alert("error");
				$('#grammarModal').modal('hide');
				console.log("ERROR: ", e);
			}

		});
	});

	// delete baiGrammar	
	$(document).on('click', '.deleteBaiGrammar', function() {
		var deleteId = $(this).attr('id');
		var fields = deleteId.split('.');
		var idBaiGrammar = fields[1];

		if (confirm("Bạn muốn xóa bài grammar này?")) {
			$.ajax({
				type: 'POST',
				url: "http://localhost:8080/api/admin/grammar/delete/" + idBaiGrammar,
				success: function(data) {
					loadAllGrammar(1);
					alert("Xóa bài grammar thành công");
				},
				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}

			});
		}

	});

	//edit baiGrammar	
	$(document).on('click', '.editBaiGrammar', function(event) {
		var editId = $(this).attr('id');
		var fields = editId.split('.');
		var idBaiGrammar = fields[1];

		$.ajax({
			type: 'GET',
			contentType: "application/json",
			url: "http://localhost:8080/api/admin/grammar/infoGrammar/" + idBaiGrammar,
			success: function(data) {

				/*var jsonObject = new Object();
				fields = data.split('|');

				id = fields[0].split('==');
				jsonObject.tenbaigrammar = id[1];

				contentgrammar = fields[1].split('==');
				jsonObject.contentgrammar = contentgrammar[1];*/

				//set data for modal

				var modal = $('#grammarModal');
				$('#grammarModal #idGrammarModal').val(idBaiGrammar);
				modal.find('.modal-body #nameGrammar').val(data.object.grammarName);
				modal.find('.modal-header #titleModal').text("Cập nhật bài ngữ pháp");
				modal.find('.modal-body #previewImage').attr('src', data.object.filePath);
				modal.find('.modal-body #myckeditor').val(data.object.contentHTML);
				CKEDITOR.instances['myckeditor'].setData(data.object.contentHTML);
				console.log(data.object.contentHTML);
				/*console.log(CKEDITOR.instances['myckeditor'].getData());*/
				//simplemde = null;
				$('#btnUpdate').show();
				$('#btnAddNewGrammar').hide();
				$('#grammarModal').modal('show');
			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});




		$('#btnUpdate').unbind().click(function() {
			var formData = new FormData();
			var name = $('#nameGrammar').val();
			var file_image;

			if ($('#file_imageGrammar').get(0).files.length != 0) {
				file_image = $('#file_imageGrammar')[0].files[0];
				formData.append("fileImage", file_image);
			}
			else {
				file_image = "test.jpg";
				formData.append("fileImage", "test.jpg");
			}

			/*var file_image = $('#file_imageGrammar')[0].files[0];
			formData.append("fileImage", file_image);*/
			var editorData = CKEDITOR.instances['myckeditor'].getData();
			formData.append("idGrammar", idBaiGrammar);
			formData.append("name", name);
			formData.append("contentMarkdown", editorData);
			formData.append("contentHtml", editorData);
			$.ajax({
				data: formData,
				type: 'POST',
				url: "http://localhost:8080/api/admin/grammar/update",
				enctype: 'multipart/form-data',
				contentType: false,
				cache: false,
				processData: false,

				success: function(data) {
					$('#grammarModal').modal('hide');
					alert("Cập nhật bài grammar thành công");
					loadAllGrammar(1);

				},

				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}
			});
		});
	});
});
