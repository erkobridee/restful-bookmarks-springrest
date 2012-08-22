package com.erkobridee.restful.bookmarks.springrest.itest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.erkobridee.restful.bookmarks.springrest.entity.Bookmark;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/itest-context.xml")
public class BookmarkRestTest {

	@Autowired
	private RestTemplate restTemplate;
	
	private static Bookmark vo;
	
	private String getBaseUrl() {		
		String port = "8080";
		String app = "restful-bookmarks-springrest";
		String model = "bookmarks";
		
		String url = "http://localhost:";		
		url += port + "/";
		url += app + "/api/";
		url += model;
		
		return url;
	}
	
	@Test
	public void testInsert() {
		vo = new Bookmark();
		vo.setName("IT RESTFul");
		vo.setDescription("Insert : Integration Test RESTful");
		vo.setUrl("http://it.bookmarks.domain/");
		
		vo = restTemplate.postForObject(getBaseUrl(), vo, Bookmark.class, new Object[]{});
		
		Assert.assertNotNull(vo);	
	}
	
	@Test
	@SuppressWarnings( "unchecked" )
	public void testListAll() {
		List<Bookmark> list = restTemplate.getForObject(getBaseUrl(), List.class, new Object[]{});
		
		Assert.assertTrue(list.size() > 0);
	}
	
	private Bookmark getById(Long id) {		
		Map<String, String> vars = Collections.singletonMap("id", id + "");
		return restTemplate.getForObject(getBaseUrl()+"/{id}", Bookmark.class, vars);
	}
	
	@Test
	public void testGetByInvalidId() {
		Assert.assertNull( getById( Long.valueOf(-1) ) ); 
	}

	@Test
	public void testGetById() {
		vo = getById(vo.getId());
		
		Assert.assertNotNull(vo);
	}
	
	@SuppressWarnings( "unchecked" )
	public List<Bookmark> getByName(String name) {
		Map<String, String> vars = Collections.singletonMap("name", name + "");		
		return restTemplate.getForObject(getBaseUrl()+"/search/{name}", List.class, vars);
	}
	
	@Test
	public void testGetByInvalidName() {
		List<Bookmark> list = getByName( "IT RESTFul Invalid Name" );
		Assert.assertFalse(list.size() > 0);
	}
	
	@Test
	public void testGetByName() {
		List<Bookmark> list = getByName(vo.getName());	
		Assert.assertTrue(list.size() > 0);
	}
	
	@Test
	public void testUpdate() {
		String nameUpdated = vo.getName() + " ... updated";
		
		vo.setName( nameUpdated );
		vo.setDescription( vo.getDescription() + " ... updated" );
		vo.setUrl( vo.getUrl() + "/updated" );
		
		Map<String, String> vars = Collections.singletonMap("id", vo.getId() + "");
		restTemplate.put(getBaseUrl() + "/{id}", vo, vars);
		
		vo = getById(vo.getId());
		
		Assert.assertEquals(nameUpdated, vo.getName());
	}
	
	@Test
	public void testDelete() {
		Map<String, String> vars = Collections.singletonMap("id", vo.getId() + "");
		restTemplate.delete(getBaseUrl() + "/{id}", vars);
		
		vo = getById(vo.getId());
		
		Assert.assertNull(vo);
	}
	
}
