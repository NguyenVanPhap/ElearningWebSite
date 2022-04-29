package com.elearning.api.admin;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elearning.entities.ListeningExercise;
import com.elearning.service.ListeningExerciseService;

@RestController
@RequestMapping("/api/admin/listeningexercise")
public class ListeningExerciseApi {

	@Autowired
	private ListeningExerciseService listeningExerciseService;

	@GetMapping("/getlist")
	public ResponseEntity<Object> findAllByPartAndLevel(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "4") int pagesize, @RequestParam(defaultValue = "") String part,
			@RequestParam(defaultValue = "") String level) {
		return ResponseEntity.ok(
				listeningExerciseService.findAllListListeningExerciseByPartAndlevelAdmin(page, pagesize, part, level));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ListeningExercise> findById(@PathVariable long id) {
		Optional<ListeningExercise> bn = listeningExerciseService.findListeningExerciseById(id);
		return bn.isPresent() ? new ResponseEntity<>(bn.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<?> saveBaiNghe(@ModelAttribute ListeningExercise baiNgheInfor, HttpServletRequest request) {
		try {
			System.out.println(baiNgheInfor.toString());
			ListeningExercise baiNghe = listeningExerciseService.saveListeningExercise(baiNgheInfor, request);
			saveFileForBaiNghe(baiNghe, request);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable long id) {
		listeningExerciseService.deleteListeningExercise(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// lưu ảnh và audio của bài nghe vào thư mục
	private void saveFileForBaiNghe(ListeningExercise bn, HttpServletRequest request)
			throws IllegalStateException, IOException {

		MultipartFile fileAudio = bn.getAudio();
		MultipartFile fileExcel = bn.getFileExcelQuestion();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path pathAudio = Paths.get(rootDirectory + "/static/file/audio/baiNgheId=" + bn.getId() + ".mp3");
		Path pathExcel = Paths.get(rootDirectory + "/static/file/excel/baiNgheId=" + bn.getId() + ".xlsx");
		fileAudio.transferTo(new File(pathAudio.toString()));
		fileExcel.transferTo(new File(pathExcel.toString()));

	}

}
