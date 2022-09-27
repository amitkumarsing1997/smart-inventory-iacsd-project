package com.iacsd.demo.repository;

import com.iacsd.demo.model.BaseEntity;

public interface BaseRepository<T extends BaseEntity, ID> {

	T findByUid(String uid);

}
