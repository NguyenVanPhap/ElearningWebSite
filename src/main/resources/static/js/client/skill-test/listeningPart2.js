$(document).ready(function() {

	var soCauDung = [];
	var soCau = 0;
	ajaxGetForCauHoi(1);
	startListeningClock();
	var MapCheckQuestion = new Map();
	function ajaxGetForCauHoi(page) {
		var listeningExerciseId = $("#listeningExerciseId").val();
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/api/client/listening-exercise/id=" + listeningExerciseId + "?page=" + page + "&pagesize=" +3,
			success: function(result) {
				soCau = result.object.totalElements;
				console.log(result);
				var divCauHoi = "";
				$.each(result.object.content, function(i, cauHoi) {
					divCauHoi += '<div class="postmetadata" style="margin-left: 0px">'
						+ '<ul><li style="font-weight: bold"><i class="icon-user"></i>Câu ' + cauHoi.number + ': ' + cauHoi.question + '</li></ul></div>'
						+ '<div class="form-group">'
						+ '  <div class="span8" style="float:none; display: inline-block;">'
						+ '   <div class="span4" style="margin-left: 0px">'
						+ '     <label style="float: left;" class="radio-inline radioLabel">'
						+ '       <input type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="dapAn_1" value="A">A. ' +  '</label></div>'
						+ '     <div class="span4" style="margin-left: 0px">'
						+ '         <label  style="float: left;" class="radio-inline radioLabel">'
						+ '        <input type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_2" value="B">B. ' + '</label></div>'
						+ '    <div class="span4" style="margin-left:0px">'
						+ '      <label  style="float: left;" class="radio-inline radioLabel">'
						+ '       <input type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_3" value="C">C.' +  '</label></div>'
						+ '     <div class="span4" style="margin-left: 0px;display:none"><label  style="float: left;" class="radio-inline radioLabel">'
						+ '        <input type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_4" value="D">D. ' + '</label></div>'
						+ '        <input type="radio" name="' + cauHoi.number + '" id="correct_answer" value="' + cauHoi.correct_answer + '" class="hidden">'
						+ '   </div>'
						+'</div>'
						+'<hr class="center">'
					
				});
				$('#cauHoi').html(divCauHoi);
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

			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});
	};


	// event khi click vào phân trang giải thích bài đọc
	$(document).on('click', '.pageNumber-giaiThich', function(event) {
		event.preventDefault();
		var page = $(this).text();
		$('#cauHoi').empty();
		$('.ul-pagination li').remove();
		ajaxGetForGiaiThich(page);
	});


	$(document).on('click', '.radioLabel', function(event) {
		var nameRadio = $(this).find('input:radio').attr("name");
		var dapAnChon = $(this).find('input:radio').val();
		console.log(nameRadio);
		checkExam(nameRadio, dapAnChon);
	});

	var checkExam = function(nameRadio, dapAnChon) {
		var dapAnDung = $('input:radio[name=' + nameRadio + '][id="correct_answer"]').val();
		MapCheckQuestion.set(nameRadio, dapAnChon);
		console.log(MapCheckQuestion);
		if (dapAnDung === dapAnChon) {
			if (soCauDung.indexOf(nameRadio) == -1) {
				soCauDung.push(nameRadio);
			}
		} else {
			soCauDung = $.grep(soCauDung, function(value) {
				return value != nameRadio;
			});
		}
		console.log(soCauDung);

	}

	// click event button Thêm mới bài đọc
	$('#btnSubmitExercise').on("click", function(event) {
		event.preventDefault();
		var confirmation = confirm("Bạn chắc chắn nộp bài ?");
		if (confirmation) {
			clearInterval(timecheckListening);
			$("#ketQuaText").html("Số câu đúng của bạn là: " + soCauDung.length + '/' + soCau);

			jQuery.noConflict();
			$('#nopBaiModal').modal('show');
			/*$('#nopBaiModal').modal('show');*/
		}
	});

	$('#btnLamLai').on("click", function(event) {
		location.reload();
	});

	$('#btnViewExplaint').on("click", function(event) {
		event.preventDefault();
		$('#btnNopBai').addClass("hidden");
		$('#btnBaiThiKhac').removeClass("hidden");
		$('#script-detail').removeClass("hidden");
		$('#cauHoi').empty();
		$('.ul-pagination li').remove();
		ajaxGetForExplaint(1);
		$('#nopBaiModal').modal('hide');
	});

	function ajaxGetForExplaint(page) {
		var baiDocId = $("#listeningExerciseId").val();
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/api/client/listening-exercise/id=" + baiDocId + "?page=" + page + "&pagesize=" + 3,
			success: function(result) {
				//				soCau = result.totalElements;
				var divCauHoi = "";
				$.each(result.object.content, function(i, cauHoi) {
					divCauHoi +=
						'<div class="postmetadata" style="margin-left: 0px">'
						+ '<ul><li style="font-weight: bold"><i class="icon-user"></i>Câu ' + cauHoi.number + ': ' + cauHoi.question + '</li></ul></div>'
						+ '<div class="form-group">'
						+ '  <div class="span8" style="float:none; display: inline-block;">'
						+ '   <div class="span4" style="margin-left: 0px">'
						+ '     <label style="float: left;" class="radio-inline radioLabel">'
					if (MapCheckQuestion.get(cauHoi.number) == 'A')
						divCauHoi += '<input type="radio" checked onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="dapAn_1" value="A">A. ' + cauHoi.answer_1 + '</label></div>'
					else
						divCauHoi += '<input type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="dapAn_1" value="A">A. ' + cauHoi.answer_1 + '</label></div>'


					divCauHoi += '     <div class="span4" style="margin-left: 0px">'
						+ '         <label  style="float: left;" class="radio-inline radioLabel">'
					if (MapCheckQuestion.get(cauHoi.number) == 'B')
						divCauHoi += '        <input type="radio" checked onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_2" value="B">B. ' + cauHoi.answer_2 + '</label></div>'
					else
						divCauHoi += '        <input type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_2" value="B">B. ' + cauHoi.answer_2 + '</label></div>'
					divCauHoi += '     <div class="span4" style="margin-left: 0px">'
						+ '         <label  style="float: left;" class="radio-inline radioLabel">'
					if (MapCheckQuestion.get(cauHoi.number) == 'C')
						divCauHoi += '        <input type="radio" checked onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_3" value="C">C. ' + cauHoi.answer_3 + '</label></div>'
					else
						divCauHoi += '        <input type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_3" value="C">C. ' + cauHoi.answer_3 + '</label></div>'


					divCauHoi += '     <div class="span4" style="margin-left: 0px">'
						+ '         <label  style="float: left;" class="radio-inline radioLabel">'
					if (MapCheckQuestion.get(cauHoi.number) == 'D')
						divCauHoi += '        <input style="display:none" type="radio" checked onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_4" value="D">D. ' + cauHoi.answer_4 + '</label></div>'
					else
						divCauHoi += '        <input style="display:none" type="radio" onclick="markColorListening(' + cauHoi.number + ')" name="' + cauHoi.number + '" id="answer_4" value="D">D. ' + cauHoi.answer_4 + '</label></div>'

					divCauHoi += '        <input style="display:none" type="radio" name="' + cauHoi.number + '" id="correct_answer" value="' + cauHoi.correct_answer + '" class="hidden">'
						+ '   </div>'

					var stt = cauHoi.number;

					if (soCauDung.indexOf(stt) > -1) {
						console.log(soCauDung.indexOf(stt) > -1)
						divCauHoi += '<div class="span4"  style="margin-left:0px;color:green">'
							+ '<span>Đáp án đúng:' + cauHoi.correct_answer + '</span><br> </div>';
					} else {
						divCauHoi += '<div class="span4" style="margin-left:0px;color:red">'
							+ '<span>Đáp án đúng:' + cauHoi.correct_answer + '</span><br> </div>';
					}

					
				});
				$('#cauHoi').html(divCauHoi);
				if (result.object.totalPages > 0) {

					$('.pagination').empty();
					for (var numberPage = 1; numberPage <= result.object.totalPages; numberPage++) {
						var li;
						if (numberPage == page)
							li = '<a class="directpageExplain active" id="direct.' + numberPage + '"> ' + numberPage + '</a>';
						else
							li = '<a class="directpageExplain" id="direct.' + numberPage + '"> ' + numberPage + '</a>';
						$('.pagination').append(li);
					};
				};
			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});
	};
	///////////////////////
	var timecheckListening;
	function startTimerListening(duration, display) {
		var timer = duration, minutes, seconds;
		timecheckListening = setInterval(function() {
			minutes = parseInt(timer / 60, 10)
			seconds = parseInt(timer % 60, 10);
			minutes = minutes < 10 ? "0" + minutes : minutes;
			seconds = seconds < 10 ? "0" + seconds : seconds;
			document.getElementById("timeListening").textContent = minutes + ":" + seconds;
			if (--timer < 0) {
				clearInterval(timecheckListening);
				alert("Đã hết thời gian làm bài test");
				$('#btnSubmitExercise').click();

			}
		}, 1000);
	}

	function startListeningClock() {
		//change time here
		//var fortyFiveMinutes = 0.2 * 30;
		var fortyFiveMinutes = 60 * 5;
		// display = document.querySelectorAll('#timeListening');
		// var check = document.getElementById("timeListening").value();
		//console.log("check:"+check);
		startTimerListening(fortyFiveMinutes, '5:00');
	};


	/////////////////////
	$(document).on('click', '.directpage', function(event) {
		var directId = $(this).attr('id');
		var fields = directId.split('.');
		var page = fields[1];
		ajaxGetForCauHoi(page);
	});

	$(document).on('click', '.directpageExplain', function(event) {
		var directId = $(this).attr('id');
		var fields = directId.split('.');
		var page = fields[1];
		ajaxGetForExplaint(page);
	});
});