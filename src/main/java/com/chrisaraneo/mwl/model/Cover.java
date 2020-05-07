package com.chrisaraneo.mwl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "covers")
@EntityListeners(AuditingEntityListener.class)

public class Cover {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coverID")
    private Long coverID;
	
    private String base64 = "";

	public Long getCoverID() {
		return coverID;
	}

	public void setCoverID(Long coverID) {
		this.coverID = coverID;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
}
