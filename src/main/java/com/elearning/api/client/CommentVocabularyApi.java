package com.elearning.api.client;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.elearning.entities.CommentVocabulary;
import com.elearning.entities.NguoiDung;
import com.elearning.entities.Vocabulary;
import com.elearning.repository.NguoiDungRepository;
import com.elearning.request.VocabCommentReq;
import com.elearning.service.CommentVocabService;
import com.elearning.service.NguoiDungService;
import com.elearning.service.VocabularyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


@RestController
@RequestMapping("/api/comment/vocab")
@SessionAttributes("loggedInUser")
public class CommentVocabularyApi {
    @Autowired
    CommentVocabService commentVocabService;
    
    @Autowired
    VocabularyService vocabService;
    
    @Autowired
    NguoiDungRepository nguoidungRepo;
    
    @Autowired
    NguoiDungService nguoidungService;
    
    @GetMapping("/id={vocabId}")
	public ResponseEntity<Object> getListCommentVocabId(
			@PathVariable Integer vocabId) {

		Vocabulary objvocab = vocabService.getVocabId(vocabId);
		return ResponseEntity.ok(commentVocabService.findByVocabulary(objvocab));
	}

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		NguoiDung nguoiDung = new NguoiDung();
		if (auth.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
			String principal = auth.getPrincipal().toString();
			String[] part = principal.split(",");
			String name = part[2].split("=")[1];
			nguoiDung.setHoTen(name);
			nguoiDung.setLoginOauth2(true);
			return nguoiDung;
		} else {
			return nguoidungService.findByEmail(auth.getName());
		}
	}
	
	public NguoiDung getSessionUser(HttpServletRequest request) {
		NguoiDung nguoiDung = (NguoiDung) request.getSession().getAttribute("loggedInUser");
		return nguoiDung;
	}
	@PostMapping("/add-comment")
	public ResponseEntity<Object> addComment(@RequestBody VocabCommentReq newComment, HttpServletRequest request) {
// 		try {
// 			nguoiDung = getSessionUser(request);
// 		}catch(Exception ex) {
// 			nguoiDung.setVaiTro(Role.ROLE_MEMBER);
// //			ex.printStackTrace();
// 		}
		NguoiDung nguoiDung = getSessionUser(request);
		// NguoiDung nguoiDung= this.nguoidungRepo.getById((long) 2);

		Vocabulary vocab = new Vocabulary();
		vocab.setVocabularyId(newComment.getVocabularyId()); 
		CommentVocabulary comment = new CommentVocabulary();
		Date time = new Date();
//		String content = cmtdto.getContent();
//		try {
//			
//			commentvocab.setUserName(nguoiDung.getHoTen());
////			Commentvocab comment = this.commentvocabService.save(content);
//			return ResponseEntity.ok(commentvocabService.save(commentvocab));
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		comment.setUserName(nguoiDung.getHoTen());
		comment.setCommentDateVocab(time);
		comment.setContentVocabulary(newComment.getContentVocabulary());
		comment.setVocabulary(vocab);
		
        System.out.println("comment new: " + comment);
		return ResponseEntity.ok(commentVocabService.save(comment));	
	}
}
