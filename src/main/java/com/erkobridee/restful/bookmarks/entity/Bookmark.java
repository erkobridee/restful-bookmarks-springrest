package com.erkobridee.restful.bookmarks.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "bookmark")
@XmlRootElement
public class Bookmark implements Serializable {

	// --------------------------------------------------------------------------
	private static final long serialVersionUID = -1058743390684974211L;
	// --------------------------------------------------------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column(nullable = false, length = 250)
	private String name;

	@Column
	private String description;

	@Column(nullable = false, length = 250)
	private String url;

	// --------------------------------------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// --------------------------------------------------------------------------

}
