package com.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.elearning.entities.Post;
import com.elearning.helper.ApiRes;
import com.elearning.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	PostRepository blogRepo;

	public ApiRes<Object> save(Post posts) {

		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			apiRes.setObject(blogRepo.save(posts));
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> getList4api(int page, int limit, String keyword) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			Page<Post> lstPost = blogRepo.search4page(keyword, PageRequest.of(page, limit));
			apiRes.setObject(lstPost);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> getInfor(int id) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			List<Post> lstpostss = blogRepo.findById(id);
			apiRes.setObject(lstpostss.get(0));
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> getList(int page, int size) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			Page<Post> pageposts = blogRepo.findAll(PageRequest.of(page, size));
			apiRes.setObject(pageposts);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> getAll() {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			List<Post> lspostss = blogRepo.findAll();
			apiRes.setObject(lspostss);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;

	}

	public ApiRes<Object> delete(int id) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			blogRepo.deleteById(id);
			apiRes.setObject(true);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> search(String search) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			List<Post> lstposts = blogRepo.search(search);
			apiRes.setObject(lstposts);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public Post getPostId(int id){
		List<Post> lstPosts = blogRepo.findById(id);
		return lstPosts.get(0);
	}
}
