package com.chrisaraneo.mwl.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="Artists")
@EntityListeners(AuditingEntityListener.class)

public class Artist {
	private long id;
	private String name;
}
