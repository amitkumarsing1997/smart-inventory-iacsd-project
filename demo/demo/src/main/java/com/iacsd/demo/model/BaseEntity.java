package com.iacsd.demo.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.UUID;
@Getter @Setter
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "uid", length = 50)
	private String uid;

	@PrePersist
	public void generateUid() {
		this.uid=UUID.randomUUID().toString();
	}
}
