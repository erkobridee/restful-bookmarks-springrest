package com.erkobridee.restful.bookmarks.springrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erkobridee.restful.bookmarks.springrest.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.springrest.entity.Bookmark;

@Controller
@RequestMapping("/bookmarks")
public class BookmarkController {

	// --------------------------------------------------------------------------

	@Autowired
	private IBookmarkDAO dao;

	// --------------------------------------------------------------------------

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Bookmark> getAll() {
		return dao.listAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Bookmark getById(@PathVariable String id) {
		return dao.findById(Long.valueOf(id));
	}

	@RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
	@ResponseBody
	public List<Bookmark> getByName(@PathVariable String name) {
		return dao.findByName(name);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Bookmark insert(@RequestBody Bookmark value) {
		return dao.save(value);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Bookmark update(@RequestBody Bookmark value) {
		return dao.save(value);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String remove(@PathVariable String id) {
		dao.remove(Long.valueOf(id));
		return "ok";
	}

}
