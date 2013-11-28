package com.erkobridee.restful.bookmarks.springrest.itest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.erkobridee.restful.bookmarks.springrest.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.ResultData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/itest-context.xml")
public class BookmarkIntegrationTest {

	//--------------------------------------------------------------------------
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static Bookmark vo;
	
	//--------------------------------------------------------------------------
	
	private String getBaseUrl() {		
		String port = "8080";
		String app = "restful-bookmarks-springrest";
		String model = "bookmarks";
		
		String url = "http://localhost:";		
		url += port + "/";
		url += app + "/rest/";
		url += model;
		
		return url;
	}
	
	//--------------------------------------------------------------------------
	// RESTful POST
	
	@Test
	public void test_01_Post() {
		vo = new Bookmark();
		vo.setName( "IT RESTFul" );
		vo.setDescription( "Insert : Integration Test RESTful" );
		vo.setUrl( "http://it.bookmarks.domain/" );
		
		vo = restTemplate.postForObject(
			getBaseUrl(), vo, Bookmark.class, new Object[]{}
		);
		
		Assert.assertNotNull( vo );	
	}
	
	//--------------------------------------------------------------------------
	// RESTful GET
	
	@Test
	@SuppressWarnings( "unchecked" )
	public void test_02_Get() {
		ResultData<List<Bookmark>> rd = restTemplate.getForObject(
			getBaseUrl(), ResultData.class, new Object[]{}
		);
		
		Assert.assertTrue( rd.getData().size() > 0 );
	}
	
	//--------------------------------------------------------------------------
	// RESTful GET .../{id}
	
	private Bookmark getById(Long id) {		
		Map<String, String> vars = Collections.singletonMap( "id", id + "" );
		
		try{
			return restTemplate.getForObject(
				getBaseUrl() + "/{id}", Bookmark.class, vars
			);
		} catch(Exception e) {}
			
		return null;
	}
	
	@Test
	public void test_03_GetByInvalidId() {
		Assert.assertNull( getById( Long.valueOf( -1 ) ) ); 
	}

	@Test
	public void test_03_GetById() {
		vo = getById( vo.getId() );
		
		Assert.assertNotNull( vo );
	}
	
	//--------------------------------------------------------------------------
	// RESTful GET .../search/{name}
	
	@SuppressWarnings( "unchecked" )
	public ResultData<List<Bookmark>> getByName( String name ) {
		Map<String, String> vars = Collections.singletonMap( "name", name + "" );
		
		return restTemplate.getForObject(
			getBaseUrl() + "/search/{name}", ResultData.class, vars
		);
	}
	
	@Test
	public void test_04_GetByInvalidName() {
		ResultData<List<Bookmark>> rd = getByName( "IT RESTFul Invalid Name" );
		
		Assert.assertFalse( rd.getData().size() > 0 );
	}
	
	@Test
	public void test_04_GetByName() {
		ResultData<List<Bookmark>> rd = getByName( vo.getName() );	
		
		Assert.assertTrue( rd.getData().size() > 0 );
	}
	
	//--------------------------------------------------------------------------
	// RESTful PUT .../{id}
	
	@Test
	public void test_05_Put() {
		String nameUpdated = vo.getName() + " ... updated";
		
		vo.setName( nameUpdated );
		vo.setDescription( vo.getDescription() + " ... updated" );
		vo.setUrl( vo.getUrl() + "/updated" );
		
		Map<String, String> vars = Collections.singletonMap( "id", vo.getId() + "" );
		
		restTemplate.put( getBaseUrl() + "/{id}", vo, vars );
		
		vo = getById( vo.getId() );
		
		Assert.assertEquals( nameUpdated, vo.getName() );
	}
	
	//--------------------------------------------------------------------------
	// RESTful DELETE .../{id}
	
	@Test
	public void test_06_Delete() {
		Map<String, String> vars = Collections.singletonMap( "id", vo.getId() + "" );
		
		restTemplate.delete(getBaseUrl() + "/{id}", vars);
		
		vo = getById( vo.getId() );
		
		Assert.assertNull( vo );
	}
	
	//--------------------------------------------------------------------------
	
}
