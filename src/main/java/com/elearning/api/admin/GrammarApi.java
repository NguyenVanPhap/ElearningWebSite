package com.elearning.api.admin;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elearning.entities.Grammar;
import com.elearning.helper.ApiRes;
import com.elearning.request.BaseReq;
import com.elearning.service.GrammarService;

@RestController
@RequestMapping("/api/admin/grammar")
public class GrammarApi {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GrammarService baigrammarService;

	@GetMapping("/loadGrammar")
	public List<String> showAllGrammar() {

		List<Grammar> list = baigrammarService.getAllGrammar();

		List<String> response = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			String json = "baithithuid:" + list.get(i).getGrammarId() + "," + "tenbaithithu:"
					+ list.get(i).getGrammarName();

			response.add(json);
		}

		return response;

	}

	@RequestMapping(value = "/getlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> getlist(@RequestBody BaseReq req) {

		return ResponseEntity.ok(baigrammarService.getGrammar4api(req.getPage() - 1, 4, req.getKeyWord()));
	}

	@GetMapping("/search")
	public List<String> Search() {

		List<Grammar> list = baigrammarService.getAllGrammar();

		List<String> response = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			String json = "baithithuid:" + list.get(i).getGrammarId() + "," + "tenbaithithu:"
					+ list.get(i).getGrammarName();

			response.add(json);
		}

		return response;

	}

	@RequestMapping(value = "/delete/{idBaiGrammar}")
	public String deleteById(@PathVariable("idBaiGrammar") int id) {
		baigrammarService.delete(id);
		return "success";
	}

	// get info Grammar ->edit Grammar

	@RequestMapping(value = "/infoGrammar/{idBaiGrammar}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> infoGrammarById(@PathVariable("idBaiGrammar") int id) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		Grammar baiGrammar = baigrammarService.getInfor(id);
		apiRes.setObject(baiGrammar);
		return ResponseEntity.ok(apiRes);
	}

	@PostMapping(value = "/save")
	@ResponseBody
	public List<String> addBaiGrammar(@RequestParam("grammarName") String name,
			@RequestParam("contentMarkdown") String contentMarkdown, @RequestParam("contentHtml") String contentHtml,
			@RequestPart(value = "fileImage", required = false) MultipartFile file_image) {
		List<String> response = new ArrayList<String>();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Grammar baigrammar = new Grammar();
		try {
			if (file_image != null) {
				Path pathImage = Paths.get(rootDirectory + "/static/file/images/grammar/" + ""
						+ baigrammar.getGrammarId() + "." + file_image.getOriginalFilename());
				String localPath = "/static/file/images/grammar/" + "" + baigrammar.getGrammarId() + "."
						+ file_image.getOriginalFilename();
				file_image.transferTo(new File(pathImage.toString()));
				baigrammar.setFileName(file_image.getOriginalFilename());
				baigrammar.setFilePath(localPath);
			}
			baigrammar.setGrammarName(name);
			baigrammar.setContentMarkDown(contentMarkdown);
			baigrammar.setContentHTML(contentHtml);
			baigrammarService.save(baigrammar);

		} catch (Exception e) {
			response.add(e.toString());
			System.out.println("ErrorAddGrammar:" + e);
		}

		return response;
	}

	@PostMapping(value = "/update")
	@ResponseBody
	public List<String> updateBaiGrammar(@RequestParam("idGrammar") int id, @RequestParam("name") String name,
			@RequestParam("contentMarkdown") String contentMarkdown, @RequestParam("contentHtml") String contentHtml,
			@RequestPart(value = "fileImage", required = false) MultipartFile file_image) {
		List<String> response = new ArrayList<String>();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Grammar baigrammar = baigrammarService.getInfor(id);
		baigrammarService.save(baigrammar);
		try {

			if (file_image != null) {
				Path pathImage = Paths.get(rootDirectory + "/static/file/images/grammar/" + ""
						+ baigrammar.getGrammarId() + "." + file_image.getOriginalFilename());
				String localPath = "/static/file/images/grammar/" + "" + baigrammar.getGrammarId() + "."
						+ file_image.getOriginalFilename();
				file_image.transferTo(new File(pathImage.toString()));
				baigrammar.setFileName(file_image.getOriginalFilename());
				baigrammar.setFilePath(localPath);
			}
			baigrammar.setGrammarName(name);
			baigrammar.setContentMarkDown(contentMarkdown);
			baigrammar.setContentHTML(contentHtml);
			baigrammarService.save(baigrammar);

		} catch (Exception e) {
			response.add(e.toString());
			System.out.println("ErrorAddGrammar:" + e);

		}
		
		return response;
	}

}
