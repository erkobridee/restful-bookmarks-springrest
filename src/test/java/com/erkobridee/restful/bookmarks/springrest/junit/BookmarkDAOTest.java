package com.erkobridee.restful.bookmarks.springrest.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.erkobridee.restful.bookmarks.springrest.persistence.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.ResultData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class BookmarkDAOTest {

	//--------------------------------------------------------------------------
	
	@Autowired
	private IBookmarkDAO dao;

	private static Bookmark vo;

	//--------------------------------------------------------------------------
	
	public void insertTestData() {
		for (int i = 1; i <= 10; i++) {
			vo = new Bookmark();
			vo.setName("fake name " + i);
			vo.setDescription("fake description " + i);
			vo.setUrl("http://fake.bookmark" + i + ".domain/");
			dao.save(vo);
		}
		vo = null;
	}

	//--------------------------------------------------------------------------
	
	@Test
	public void test_01_List() {
		ResultData<List<Bookmark>> r = dao.list();
		
		Assert.assertNotNull(r);

		boolean hasObjects = ( r.getCount() > 0 );

		if( !hasObjects ) {
			
			Assert.assertFalse( hasObjects );
			this.insertTestData();
			
		}
	}
	
	@Test
	public void test_02_Count() {
		Assert.assertTrue( dao.count() > 0 );
	}
	
	@Test
	public void test_03_ListOffsetLimit() {
		ResultData<List<Bookmark>> r = dao.list( 0, 3 );
		
		Assert.assertNotNull(r);
		
		Assert.assertTrue( r.getData().size() == 3 );
	}
	
	//--------------------------------------------------------------------------

	@Test
	public void test_04_Insert() {
		long time = System.currentTimeMillis();

		vo = new Bookmark();
		vo.setName( "Name Bookmark Test " + time );
		vo.setDescription( "Description Bookmark Test " + time );
		vo.setUrl( "http://test.bookmarksdomain.ext/" + time + "/" );

		vo = dao.save( vo );
		
		Assert.assertNotNull( vo.getId() );
	}

	//--------------------------------------------------------------------------
	
	@Test
	public void test_05_FindByValidId() {
		Assert.assertNotNull( dao.findById( vo.getId() ) );
	}

	@Test
	public void test_05_FindByInvalidId() {
		Assert.assertNull( dao.findById( Long.valueOf( -100 ) ) );
	}

	//--------------------------------------------------------------------------
	
	@Test
	public void test_06_FindByValidName() {
		ResultData<List<Bookmark>> r = dao.findByName( vo.getName() );
		
		Assert.assertTrue( r.getData().size() > 0 );
	}

	@Test
	public void test_06_FindByInvalidName() {
		ResultData<List<Bookmark>> r = dao.findByName( "***" + vo.getName() + "***" );
		
		Assert.assertFalse( r.getData().size() > 0 );
	}

	//--------------------------------------------------------------------------
	
	@Test
	public void test_07_Update() {
		String nameUpdated = vo.getName() + "++";
		
		vo.setName( nameUpdated );
		vo.setDescription( vo.getDescription() + "++" );
		vo.setUrl( vo.getUrl() + "/updated" );
		vo = dao.save( vo );

		Assert.assertEquals( nameUpdated, vo.getName() );
	}

	//--------------------------------------------------------------------------
	
	@Test
	public void test_08_RemoveByInvalidId() {
		Assert.assertFalse( dao.remove( Long.valueOf( -1 ) ) );
	}
	
	@Test
	public void test_08_RemoveById() {
		Assert.assertTrue( dao.remove( vo.getId() ) );
	}

	//--------------------------------------------------------------------------

}
