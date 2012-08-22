package com.erkobridee.restful.bookmarks.springrest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger log = LoggerFactory.getLogger(BookmarkController.class);
	
	// --------------------------------------------------------------------------

	@Autowired
	private IBookmarkDAO dao;

	// --------------------------------------------------------------------------

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Bookmark> getAll() {
		log.debug("getAll");
		return dao.listAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Bookmark getById(@PathVariable String id) {
		log.debug("getById: " + id);
		return dao.findById(Long.valueOf(id));
	}

	@RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
	@ResponseBody
	public List<Bookmark> getByName(@PathVariable String name) {
		log.debug("getByName: " + name);
		return dao.findByName(name);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Bookmark insert(@RequestBody Bookmark value) {
		log.debug("insert");
		return dao.save(value);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Bookmark update(@RequestBody Bookmark value) {
		log.debug("update");
		return dao.save(value);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String remove(@PathVariable String id) {		
		boolean flag = dao.remove(Long.valueOf(id));
		log.debug("remove: " + id + " | status: " + flag);
		return "ok";
	}

}
