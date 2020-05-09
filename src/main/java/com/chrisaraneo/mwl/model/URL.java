package com.chrisaraneo.mwl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "urls")
@EntityListeners(AuditingEntityListener.class)

public class URL {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "urlID")
    private Long urlID;
	
	@NotBlank
    private String url;

	
	public Long getUrlID() {
		return urlID;
	}

	public void setUrlID(Long urlID) {
		this.urlID = urlID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}