package com.erkobridee.restful.bookmarks.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.erkobridee.restful.bookmarks.entity.Bookmark;

@Transactional
public interface IBookmarkDAO {

	List<Bookmark> listAll();
	
	Bookmark findById(Long id);
	
	List<Bookmark> findByName(String name);
	
	Bookmark save(Bookmark value);
	
	boolean remove(Long id);
	
}
