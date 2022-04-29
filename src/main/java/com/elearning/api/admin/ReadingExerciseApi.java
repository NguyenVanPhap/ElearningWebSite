package com.elearning.api.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.elearning.entities.ReadingExercise;
import com.elearning.helper.ApiRes;
import com.elearning.service.ReadingExerciseService;

@RestController
@RequestMapping("/api/admin/readingexercise")
public class ReadingExerciseApi {

	@Autowired
	private ReadingExerciseService readingExerciseService;

	@GetMapping("/getall")
	public ResponseEntity<Object> findAllByPartAndLevel(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "2") int pagesize,
			@RequestParam(defaultValue = "") String part, @RequestParam(defaultValue = "") String level) {
		return ResponseEntity
				.ok(readingExerciseService.findAllListReadingExerciseByPartAndLevelAdmin(page, pagesize, part, level));
	}

	@GetMapping("/getinfor/{id}")
	public ResponseEntity<ReadingExercise> getInfor(@PathVariable long id) {
		Optional<ReadingExercise> bn = readingExerciseService.findReadingExerciseById(id);
		return bn.isPresent() ? new ResponseEntity<>(bn.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@ModelAttribute ReadingExercise readingExerciseInfor,
			HttpServletRequest request) {
		try {
			System.out.println(readingExerciseInfor.toString());
			ReadingExercise readingExercise = readingExerciseService.save(readingExerciseInfor, request);
			saveFileForreadingExercise(readingExercise, request);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalStateException | IOException e) {
			System.out.println("vao day");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable long id) {
		readingExerciseService.deleteReadingExercise(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// lưu ảnh của bài đọc vào thư mục
	private void saveFileForreadingExercise(ReadingExercise objReadingExercise, HttpServletRequest request)
			throws IllegalStateException, IOException {
		/*
		 * MultipartFile fileImage = objReadingExercise.getImage(); MultipartFile
		 * fileExcel = objReadingExercise.getFileExcel(); String rootDirectory =
		 * request.getSession().getServletContext().getRealPath("/"); Path pathImage =
		 * Paths.get(rootDirectory + "/resources/file/images/readingExerciseId=" +
		 * objReadingExercise.getId() + ".png"); Path pathExcel =
		 * Paths.get(rootDirectory + "/resources/file/excel/readingExerciseId=" +
		 * objReadingExercise.getId() + ".xlsx"); fileImage.transferTo(new
		 * File(pathImage.toString())); fileExcel.transferTo(new
		 * File(pathExcel.toString()));
		 */

	}

}
