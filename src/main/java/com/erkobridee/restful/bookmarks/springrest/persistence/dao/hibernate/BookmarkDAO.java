
package com.erkobridee.restful.bookmarks.springrest.persistence.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.erkobridee.restful.bookmarks.springrest.persistence.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.springrest.persistence.entity.ResultData;

@Repository("bookmarkDAO")
public class BookmarkDAO extends HibernateDaoSupport implements IBookmarkDAO {

	//--------------------------------------------------------------------------
	
	private Logger log = LoggerFactory.getLogger( BookmarkDAO.class );
	
	//--------------------------------------------------------------------------

	@Autowired
	public void init( SessionFactory sessionFactory ) {
		
		log.debug( "inject SessionFactory" );
		
		super.setSessionFactory( sessionFactory );

		this.generateInitData();
	}

	//--------------------------------------------------------------------------

	public void generateInitData() {
		
		Integer bookmarksCount = this.count();

		if( bookmarksCount == 0 ) {
			
			log.debug( "generateInitData" );
			
			Bookmark vo;

			vo = new Bookmark();
			vo.setName( "twitter" );
			vo.setDescription( "@ErkoBridee" );
			vo.setUrl( "https://twitter.com/ErkoBridee" );
			this.save( vo );

			vo = new Bookmark();
			vo.setName( "github" );
			vo.setDescription( "github/erkobridee" );
			vo.setUrl( "https://github.com/erkobridee" );
			this.save( vo );

			vo = new Bookmark();
			vo.setName( "Site" );
			vo.setDescription( "Site Erko Bridee" );
			vo.setUrl( "http://about.erkobridee.com/" );
			this.save( vo );

			vo = new Bookmark();
			vo.setName( "delicious" );
			vo.setDescription( "delicious/erko.bridee" );
			vo.setUrl( "http://www.delicious.com/erko.bridee" );
			this.save ( vo );

			vo = null;
		}
	}

	//--------------------------------------------------------------------------

	public Integer count() {
		
		return this.count( null );
		
	}
	
	public Integer count( Criterion criterion ) {		
		
		Criteria c = super.getSession().createCriteria( Bookmark.class );
		
		c.setProjection( Projections.rowCount() );
		
		if( criterion != null ) {
			
			c.add( criterion );
			
		}
		
		return (Integer) c.list().get( 0 );
	}
	
	public ResultData<List<Bookmark>> list() {
		
		return this.list( 0, 0 );
		
	}
	
	@SuppressWarnings("unchecked")
	public ResultData<List<Bookmark>> list( int page, int size ) {
		
		log.debug( "list [ page: " + page + " | size: " + size + " ]" );
		
		Criteria c = super.getSession().createCriteria( Bookmark.class );
		
		if( size == 0 ) {
			
			size = 10;
			
		}
		
		c.setMaxResults( size );
		
		if( page > 1 ) {
			
			c.setFirstResult( ( page-1 ) * size );
			
		} else {
			
			c.setFirstResult( 0 );
			
		}
		
		return new ResultData<List<Bookmark>>( c.list(), this.count(), page, size );
		
	}

	public Bookmark findById( Long id ) {
		
		log.debug( "findById: " + id );
		
		return (Bookmark) super.getHibernateTemplate().get( Bookmark.class, id );
		
	}

	public ResultData<List<Bookmark>> findByName(String name ) {
		
		return this.findByName( name, 0, 0 );
		
	}
	
	@SuppressWarnings("unchecked")
	public ResultData<List<Bookmark>> findByName( String name, int page, int size ) {
		
		log.debug( "findByName: " + name + " [ page: " + page + " | size: " + size + " ]" );
		
		Criterion criterion = Restrictions.like( "name", "%"+ name + "%"  ); 
		
		Criteria c = super.getSession().createCriteria( Bookmark.class );
		
		c.add( criterion );
		
		if( size == 0 ) {
			
			size = 10;
			
		}
		
		c.setMaxResults( size );
		
		if( page > 1 ) {
			
			c.setFirstResult( ( page-1 ) * size );
			
		} else {
			
			c.setFirstResult( 0 );
			
		}
		
		return new ResultData<List<Bookmark>>( c.list(), this.count( criterion ), page, size );
		
	}

	public Bookmark save( Bookmark value ) {
		
		log.debug( "save" );
		
		return super.getHibernateTemplate().merge( value );
		
	}

	public boolean remove( Long id ) {
		
		log.debug( "remove: " + id );
		
		boolean flag = false;
		
		Bookmark bookmark = this.findById( id );
		
		if( bookmark != null ) {
		
			try {
				
				super.getHibernateTemplate().delete( bookmark );
				flag = true;
				
			} catch ( DataAccessException dae ) {
				
				log.error( "DataAccessException", dae );
				
			}
			
		}
		
		return flag;
		
	}

}
