
package com.erkobridee.restful.bookmarks.springrest.persistence.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.erkobridee.restful.bookmarks.springrest.persistence.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.Bookmark;

@Repository("bookmarkDAO")
public class BookmarkDAO extends HibernateDaoSupport implements IBookmarkDAO {

	// --------------------------------------------------------------------------
	
	private Logger log = LoggerFactory.getLogger(BookmarkDAO.class);
	
	// --------------------------------------------------------------------------

	@Autowired
	public void init(SessionFactory sessionFactory) {
		log.debug("inject SessionFactory");
		
		super.setSessionFactory(sessionFactory);

		this.generateInitData();
	}

	// --------------------------------------------------------------------------

	public void generateInitData() {
		List<Bookmark> list = this.listAll();

		if (list.size() == 0) {
			log.debug("generateInitData");
			
			Bookmark vo;

			vo = new Bookmark();
			vo.setName("twitter");
			vo.setDescription("@ErkoBridee");
			vo.setUrl("https://twitter.com/ErkoBridee");
			this.save(vo);

			vo = new Bookmark();
			vo.setName("github");
			vo.setDescription("github/erkobridee");
			vo.setUrl("https://github.com/erkobridee");
			this.save(vo);

			vo = new Bookmark();
			vo.setName("Site");
			vo.setDescription("Site Erko Bridee");
			vo.setUrl("http://about.erkobridee.com/");
			this.save(vo);

			vo = new Bookmark();
			vo.setName("delicious");
			vo.setDescription("delicious/erko.bridee");
			vo.setUrl("http://www.delicious.com/erko.bridee");
			this.save(vo);

			vo = null;
		}
	}

	// --------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public List<Bookmark> listAll() {
		log.debug("listAll");
		Criteria c = super.getSession().createCriteria(Bookmark.class);
		return c.list();
	}

	public Bookmark findById(Long id) {
		log.debug("findById: " + id);
		return (Bookmark) super.getHibernateTemplate().get(Bookmark.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Bookmark> findByName(String name) {
		log.debug("findByName: " + name);
		Criteria c = super.getSession().createCriteria(Bookmark.class);
		c.add(Restrictions.like("name", "%"+ name + "%" ));
		return c.list();
	}

	public Bookmark save(Bookmark value) {
		log.debug("save");
		return super.getHibernateTemplate().merge(value);
	}

	public boolean remove(Long id) {
		log.debug("remove: " + id);
		boolean flag = true;
		try {

			super.getHibernateTemplate().delete(this.findById(id));

		} catch (DataAccessException dae) {
			flag = false;
			log.error("DataAccessException", dae);			
		}
		return flag;
	}

}
