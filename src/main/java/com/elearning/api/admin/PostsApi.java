package com.elearning.api.admin;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.elearning.entities.Post;
import com.elearning.helper.ApiRes;
import com.elearning.request.BaseReq;
import com.elearning.service.PostService;

@RestController
@RequestMapping("/api/admin/post")
public class PostsApi {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/getall", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> getall() {
		return ResponseEntity.ok(postService.getAll());
	}

	@RequestMapping(value = "/delete/{listeningLectureId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> deleteById(@PathVariable("listeningLectureId") int id) {
		return ResponseEntity.ok(postService.delete(id));

	}

	@RequestMapping(value = "/getlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> getlist(@RequestBody BaseReq req) {

		return ResponseEntity.ok(postService.getList4api(req.getPage() - 1, 4, req.getKeyWord()));
	}

	@RequestMapping(value = "/getinfor/{postId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> getinfor(@PathVariable("postId") int id) {
		// return ResponseEntity.ok(postService.getInfor(id));
		ApiRes<Object> apiRes = new ApiRes<Object>();
		Post post = postService.getPostId(id);
		apiRes.setObject(post);
		return ResponseEntity.ok(apiRes);
	}
	@PostMapping(value = "add")
	@ResponseBody
	public ResponseEntity<Object> add(@RequestParam("postName") String name,
			@RequestParam("contentHtml") String contentHtml,
			@RequestPart(value = "fileImage", required = false) MultipartFile file_image) {
		// List<String> response = new ArrayList<String>();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Post objPost = new Post();
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			if (file_image != null) {
				Path pathImage = Paths.get(rootDirectory + "/static/file/images/post/" + ""
						+ objPost.getId() + "." + file_image.getOriginalFilename());
				String localPath = "/static/file/images/post/" + "" + objPost.getId() + "."
						+ file_image.getOriginalFilename();
				file_image.transferTo(new File(pathImage.toString()));
				objPost.setFileName(file_image.getOriginalFilename());
				objPost.setFilePath(localPath);
			}
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
			return ResponseEntity.ok(apiRes);
		}
		objPost.setName(name);
		objPost.setContentHTML(contentHtml);
		return ResponseEntity.ok(postService.save(objPost));
	}

	/*
	 * @RequestMapping(value = "/update/{idBailisteninglecture}", method =
	 * RequestMethod.POST, consumes = "application/json", produces =
	 * "application/json; charset=utf-8") public List<String>
	 * update(@RequestParam("idlisteninglecture") int id,
	 * 
	 * @RequestParam("name") String name, @RequestParam("contentMarkdown") String
	 * contentMarkdown,
	 * 
	 * @RequestParam("contentHtml") String contentHtml,
	 * 
	 * @RequestPart(value = "fileImage", required = false) MultipartFile file_image)
	 * { List<String> response = new ArrayList<String>(); String rootDirectory =
	 * request.getSession().getServletContext().getRealPath("/"); listeninglecture
	 * bailisteninglecture = postService.getInfor(id);
	 * postService.save(bailisteninglecture); try {
	 * 
	 * if (file_image != null) { Path pathImage = Paths.get(rootDirectory +
	 * "/static/file/images/listeninglecture/" + "" +
	 * bailisteninglecture.getlisteninglectureId() + "." +
	 * file_image.getOriginalFilename()); String localPath =
	 * "/static/file/images/listeninglecture/" + "" +
	 * bailisteninglecture.getlisteninglectureId() + "." +
	 * file_image.getOriginalFilename(); file_image.transferTo(new
	 * File(pathImage.toString()));
	 * bailisteninglecture.setFileName(file_image.getOriginalFilename());
	 * bailisteninglecture.setFilePath(localPath); }
	 * bailisteninglecture.setlisteninglectureName(name);
	 * bailisteninglecture.setContentMarkDown(contentMarkdown);
	 * bailisteninglecture.setContentHTML(contentHtml);
	 * 
	 * postService.save(bailisteninglecture);
	 * 
	 * } catch (Exception e) { response.add(e.toString());
	 * System.out.println("ErrorAddlisteninglecture:" + e);
	 * 
	 * }
	 * 
	 * return response; }
	 */
	@PostMapping(value = "update")
	@ResponseBody
	public ResponseEntity<Object> update(@RequestParam("idPost") int id,
		@RequestParam("postName") String name,
			@RequestParam("contentHtml") String contentHtml,
			@RequestPart(value = "fileImage", required = false) MultipartFile file_image) {
		// List<String> response = new ArrayList<String>();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Post objPost = postService.getPostId(id);
		System.out.println("id" + objPost.getId());
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			if (file_image != null) {
				Path pathImage = Paths.get(rootDirectory + "/static/file/images/post/" + ""
						+ objPost.getId() + "." + file_image.getOriginalFilename());
				String localPath = "/static/file/images/post/" + "" + objPost.getId() + "."
						+ file_image.getOriginalFilename();
				file_image.transferTo(new File(pathImage.toString()));
				objPost.setFileName(file_image.getOriginalFilename());
				objPost.setFilePath(localPath);
			}
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
			return ResponseEntity.ok(apiRes);
		}
		objPost.setName(name);
		System.out.println("name : " + objPost.getName());
		objPost.setContentHTML(contentHtml);
		return ResponseEntity.ok(postService.save(objPost));
	}

	@RequestMapping(value ="/delete/{postId}")
	public String deletePostById(@PathVariable("postId") int id){
		postService.delete(id);
		return "success";
	}
}
