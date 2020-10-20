package com.chrisaraneo.mwl.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="covers")
@NamedQuery(name="Cover.findAll", query="SELECT c FROM Cover c")
public class Cover implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cover_id", unique=true, nullable=false)
	private Integer coverID;

	@Lob
	private String data;


	public Cover() { }
	
	public Cover(Cover cover) {
		this.setCoverID(cover.getCoverID());
		this.setData(cover.getData());
	}

	public Integer getCoverID() {
		return this.coverID;
	}

	public void setCoverID(Integer coverID) {
		this.coverID = coverID;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

}