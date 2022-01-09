/*
 * Created on 25 Jul 2021 ( Time 02:07:43 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.demo.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.demo.bean.ReturPembelian;
import org.demo.bean.jpa.ReturPembelianEntity;
import java.util.Date;
import org.demo.business.service.ReturPembelianService;
import org.demo.business.service.mapping.ReturPembelianServiceMapper;
import org.demo.data.repository.jpa.ReturPembelianJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ReturPembelianService
 */
@Component
@Transactional
public class ReturPembelianServiceImpl implements ReturPembelianService {

	@Resource
	private ReturPembelianJpaRepository returPembelianJpaRepository;

	@Resource
	private ReturPembelianServiceMapper returPembelianServiceMapper;
	
	@Override
	public ReturPembelian findById(Integer idreturPembelian) {
		ReturPembelianEntity returPembelianEntity = returPembelianJpaRepository.findOne(idreturPembelian);
		return returPembelianServiceMapper.mapReturPembelianEntityToReturPembelian(returPembelianEntity);
	}

	@Override
	public List<ReturPembelian> findAll() {
		Iterable<ReturPembelianEntity> entities = returPembelianJpaRepository.findAll();
		List<ReturPembelian> beans = new ArrayList<ReturPembelian>();
		for(ReturPembelianEntity returPembelianEntity : entities) {
			beans.add(returPembelianServiceMapper.mapReturPembelianEntityToReturPembelian(returPembelianEntity));
		}
		return beans;
	}

	@Override
	public ReturPembelian save(ReturPembelian returPembelian) {
		return update(returPembelian) ;
	}

	@Override
	public ReturPembelian create(ReturPembelian returPembelian) {
		ReturPembelianEntity returPembelianEntity = returPembelianJpaRepository.findOne(returPembelian.getIdreturPembelian());
		if( returPembelianEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		returPembelianEntity = new ReturPembelianEntity();
		returPembelianServiceMapper.mapReturPembelianToReturPembelianEntity(returPembelian, returPembelianEntity);
		ReturPembelianEntity returPembelianEntitySaved = returPembelianJpaRepository.save(returPembelianEntity);
		return returPembelianServiceMapper.mapReturPembelianEntityToReturPembelian(returPembelianEntitySaved);
	}

	@Override
	public ReturPembelian update(ReturPembelian returPembelian) {
		ReturPembelianEntity returPembelianEntity = returPembelianJpaRepository.findOne(returPembelian.getIdreturPembelian());
		returPembelianServiceMapper.mapReturPembelianToReturPembelianEntity(returPembelian, returPembelianEntity);
		ReturPembelianEntity returPembelianEntitySaved = returPembelianJpaRepository.save(returPembelianEntity);
		return returPembelianServiceMapper.mapReturPembelianEntityToReturPembelian(returPembelianEntitySaved);
	}

	@Override
	public void delete(Integer idreturPembelian) {
		returPembelianJpaRepository.delete(idreturPembelian);
	}

	public ReturPembelianJpaRepository getReturPembelianJpaRepository() {
		return returPembelianJpaRepository;
	}

	public void setReturPembelianJpaRepository(ReturPembelianJpaRepository returPembelianJpaRepository) {
		this.returPembelianJpaRepository = returPembelianJpaRepository;
	}

	public ReturPembelianServiceMapper getReturPembelianServiceMapper() {
		return returPembelianServiceMapper;
	}

	public void setReturPembelianServiceMapper(ReturPembelianServiceMapper returPembelianServiceMapper) {
		this.returPembelianServiceMapper = returPembelianServiceMapper;
	}

}