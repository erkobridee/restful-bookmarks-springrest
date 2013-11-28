package com.erkobridee.restful.bookmarks.springrest.junit;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.erkobridee.restful.bookmarks.springrest.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.ResultData;
import com.erkobridee.restful.bookmarks.springrest.rest.controller.BookmarkRESTController;
import com.erkobridee.restful.bookmarks.springrest.rest.resource.ResultMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class BookmarkRestTest {

	//--------------------------------------------------------------------------
	
	@Autowired
	private BookmarkRESTController rest;

	private static Bookmark vo;

	//--------------------------------------------------------------------------
	// RESTful POST
	
	@Test	
	public void test_01_Create() {
		vo = new Bookmark();
		vo.setName( "BookmarkServiceTest Name" );
		vo.setDescription( "BookmarkServiceTest Description" );
		vo.setUrl( "http://service.bookmarkdomain.test/"
				+ System.currentTimeMillis() + "/" );
		
		vo = rest.create( vo ).getBody();

		Assert.assertNotNull( vo.getId() );
	}

	//--------------------------------------------------------------------------
	// RESTful GET
	
	@Test
	public void test_02_GetList() {
		ResultData<List<Bookmark>> r = rest.list( 1, 10 ).getBody();

		Assert.assertTrue( r.getData().size() > 0 );
	}	
	
	//--------------------------------------------------------------------------
	// RESTful GET .../{id}
	
	private Bookmark getById( Long id ) {
		Object entity = rest.get( id ).getBody();
		
		if( entity instanceof Bookmark ) return (Bookmark) entity;
		
		return null;
	}
	
	@Test
	public void test_03_GetByInvalidId() {
		Assert.assertNull( this.getById( Long.valueOf( -1 ) ) );
	}
	
	@Test	
	public void test_03_GetById() {
		Assert.assertNotNull( this.getById( vo.getId() ) );
	}	

	//--------------------------------------------------------------------------
	// RESTful GET .../search/{name}
	
	private ResultData<List<Bookmark>> getByName( String name ) {				
		return rest.search( name, 1, 10 ).getBody();
	}

	@Test	
	public void test_04_GetByInvalidName() {
		ResultData<List<Bookmark>> r = this.getByName( "***" + vo.getName() + "***" );

		Assert.assertFalse( r.getData().size() > 0 );
	}
	
	@Test	
	public void test_04_GetByName() {
		ResultData<List<Bookmark>> r = this.getByName( vo.getName() );

		Assert.assertTrue( r.getData().size() > 0 );
	}
	
	//--------------------------------------------------------------------------
	// RESTful PUT .../{id}
	
	@Test	
	public void test_05_Update() {
		String nameUpdated = vo.getName() + "++";

		vo.setName( nameUpdated );
		vo.setDescription( vo.getDescription() + "++" );
		vo.setUrl( vo.getUrl() + System.currentTimeMillis() );

		vo = rest.update( vo ).getBody();

		Assert.assertEquals( nameUpdated, vo.getName() );
	}

	//--------------------------------------------------------------------------
	// RESTful DELETE .../{id}
	
	private ResultMessage deleteById( Long id ) {
		return rest.delete( id ).getBody();
	}
	
	@Test
	public void test_06_DeleteByInvalidId() {
		ResultMessage message = this.deleteById( Long.valueOf( -1 ) );
		
		Assert.assertEquals( 404, message.getCode() );
	}
	
	@Test
	public void test_06_DeleteById() {
		ResultMessage message = this.deleteById( vo.getId() );
		
		Assert.assertEquals( 202, message.getCode() );
	}
	
	//--------------------------------------------------------------------------

}
