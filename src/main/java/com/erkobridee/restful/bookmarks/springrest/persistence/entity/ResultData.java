package com.erkobridee.restful.bookmarks.springrest.persistence.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultData<T> implements Serializable {

	// --------------------------------------------------------------------------
	private static final long serialVersionUID = -5452958925419083369L;
	// --------------------------------------------------------------------------
	
	private T data;
	
	private int count;
	private int page; // page index
	private int pages; // available pages
	
	// --------------------------------------------------------------------------
	
	public ResultData() {
		this.count = 0;
		this.page = 1;
		this.pages = 0;
	}
	
	public ResultData(int count, int page, int size) {
		this.updateInfo(count, page, size);
	}
	
	public ResultData(T data, int count, int page, int size) {		
		this.data = data;
		this.updateInfo(count, page, size);
	}
	
	// --------------------------------------------------------------------------
	
	private void updateInfo(int count, int page, int size) {
		if(page <= 0) { page = 1; }
		
		this.count = count;
		this.page = page;		
		this.pages = (int)Math.ceil(((double)count)/size);
	}
	
	// --------------------------------------------------------------------------

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	// --------------------------------------------------------------------------
	
}
