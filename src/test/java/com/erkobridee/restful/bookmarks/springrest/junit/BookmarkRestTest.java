package com.erkobridee.restful.bookmarks.springrest.junit;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.erkobridee.restful.bookmarks.springrest.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.ResultData;
import com.erkobridee.restful.bookmarks.springrest.rest.controller.BookmarkRESTController;
import com.erkobridee.restful.bookmarks.springrest.rest.resource.ResultMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class BookmarkRestTest {

	@Autowired
	private BookmarkRESTController rest;

	private static Bookmark vo;

	@Test
	// RESTful POST
	public void testInsert() {
		vo = new Bookmark();
		vo.setName("BookmarkServiceTest Name");
		vo.setDescription("BookmarkServiceTest Description");
		vo.setUrl("http://service.bookmarkdomain.test/"
				+ System.currentTimeMillis() + "/");
		
		vo = rest.create(vo).getBody();

		Assert.assertNotNull(vo.getId());
	}

	@Test
	// RESTful GET .../{id}
	public void testGetById() {
		Assert.assertNotNull(rest.get(vo.getId().toString()));
	}

	@Test
	// RESTful GET .../search/{name}
	public void testGetByName() {
		ResultData<List<Bookmark>> r = rest.search(vo.getName(), 1, 10).getBody();

		Assert.assertTrue(r.getData().size() > 0);
	}

	@Test
	// RESTful PUT .../{id}
	public void testUpdate() {
		String nameUpdated = vo.getName() + "++";

		vo.setName(nameUpdated);
		vo.setDescription(vo.getDescription() + "++");
		vo.setUrl(vo.getUrl() + System.currentTimeMillis());

		vo = rest.update(vo).getBody();

		Assert.assertEquals(vo.getName(), nameUpdated);
	}

	@Test
	// RESTful GET
	public void testGetAll() {
		ResultData<List<Bookmark>> r = rest.getList(1, 10).getBody();

		Assert.assertTrue(r.getData().size() > 0);
	}

	@Test
	// RESTful DELETE
	public void testDelete() {
		String id = vo.getId().toString();

		rest.delete(id);

		ResponseEntity<?> response = rest.get(id);
		
		boolean flag = response.getBody() instanceof ResultMessage;
		
		Assert.assertTrue(flag);

		if(flag) {
			ResultMessage rm = (ResultMessage)response.getBody();
			Assert.assertEquals(404, rm.getCode());
		}		
	}

}
