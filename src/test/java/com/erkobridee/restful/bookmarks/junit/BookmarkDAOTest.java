package com.erkobridee.restful.bookmarks.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.erkobridee.restful.bookmarks.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.entity.Bookmark;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/resources/applicationContext.xml")
public class BookmarkDAOTest {

	@Autowired
	private IBookmarkDAO dao;
	
	private static Bookmark vo;
	
	public void insertTestData() {
		for (int i = 1; i <= 10; i++) {
			vo = new Bookmark();
			vo.setName("fake name " + i);
			vo.setDescription("fake description " + i);
			vo.setUrl("http://fake.bookmark"+ i +".domain/");
			dao.save(vo);
		}
		vo = null;
	}
	
	@Test
	public void testListAll() {
		List<Bookmark> list = dao.listAll();
		Assert.assertNotNull(list);
		
		boolean hasObjects = list.size() > 0;
		
		if(!hasObjects) {
			Assert.assertFalse(hasObjects);
			this.insertTestData();
		}
	}
	
	@Test
	public void testInsert() {
		long time = System.currentTimeMillis();
		
		vo = new Bookmark();
		vo.setName("Name Bookmark Test " + time);
		vo.setDescription("Description Bookmark Test " + time);
		vo.setUrl("http://test.bookmarksdomain.ext/" + time + "/");
		
		vo = dao.save(vo);
		Assert.assertNotNull(vo.getId());
	}
	
	@Test
	public void testFindByValidId() {
		Assert.assertNotNull(dao.findById(vo.getId()));
	}
	
	@Test
	public void testFindByInvalidId() {
		Assert.assertNull(dao.findById(Long.valueOf(-100)));
	}
	
	@Test
	public void testFindByValidName() {
		List<Bookmark> list = dao.findByName(vo.getName());
		Assert.assertTrue(list.size() > 0);
	}
	
	@Test
	public void testFindByInvalidName() {
		List<Bookmark> list = dao.findByName("***" + vo.getName() + "***");
		Assert.assertFalse(list.size() > 0);
	}
	
	@Test
	public void testUpdate() {
		String nameUpdated = vo.getName() + "++";
		vo.setName(nameUpdated);
		vo.setDescription(vo.getDescription() + "++");
		vo.setUrl(vo.getUrl() + "/updated");
		vo = dao.save(vo);
		
		Assert.assertEquals(vo.getName(), nameUpdated);		
	}
	
	@Test
	public void testRemove() {
		Assert.assertTrue(dao.remove(vo.getId()));
	}
	
	@Test
	public void testCheckRemoved() {
		vo = dao.findById(vo.getId());
		Assert.assertNull(vo);
	}
	
}
