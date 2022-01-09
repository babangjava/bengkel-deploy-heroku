/*
 * Created on 25 Jul 2021 ( Time 02:07:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.demo.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.demo.bean.Barang;
import org.demo.bean.jpa.BarangEntity;
import org.demo.business.service.BarangService;
import org.demo.business.service.mapping.BarangServiceMapper;
import org.demo.data.repository.jpa.BarangJpaRepository;
import org.demo.web.common.AdvanceSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of BarangService
 */
@Component
@Transactional
public class BarangServiceImpl implements BarangService {

	@Resource
	private BarangJpaRepository barangJpaRepository;

	@Resource
	private BarangServiceMapper barangServiceMapper;
	
	@Override
	public Barang findById(String kodeBarang) {
		BarangEntity barangEntity = barangJpaRepository.findOne(kodeBarang);
		return barangServiceMapper.mapBarangEntityToBarang(barangEntity);
	}

	@Override
	public List<Barang> findAll() {
		Iterable<BarangEntity> entities = barangJpaRepository.findAll();
		List<Barang> beans = new ArrayList<Barang>();
		for(BarangEntity barangEntity : entities) {
			beans.add(barangServiceMapper.mapBarangEntityToBarang(barangEntity));
		}
		return beans;
	}

	@Override
	public Map<String,Object> findAll(AdvanceSearch params) {
		if(params.getSort()==null){
			params.setOrder("DESC");
			params.setSort("kodeBarang");
		}

		if(params.getSearch()==null){
			params.setSearch("");
		}
		int page = params.getOffset() / params.getLimit();
		PageRequest sortedByPriceDesc = new PageRequest(page,params.getLimit(), Sort.Direction.fromString(params.getOrder()), params.getSort());
		Page<BarangEntity> all = barangJpaRepository.getPagging(params.getSearch(),sortedByPriceDesc);

		Map<String,Object> map =new HashMap();
		map.put("total",all.getTotalElements());
		map.put("rows", all.getContent());

		return map;
	}

	@Override
	public Barang save(Barang barang) {
		return update(barang) ;
	}

	@Override
	public Barang create(Barang barang) {
		BarangEntity barangEntity = barangJpaRepository.findOne(barang.getKodeBarang());
		if( barangEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		barangEntity = new BarangEntity();
		barangServiceMapper.mapBarangToBarangEntity(barang, barangEntity);
		BarangEntity barangEntitySaved = barangJpaRepository.save(barangEntity);
		return barangServiceMapper.mapBarangEntityToBarang(barangEntitySaved);
	}

	@Override
	public void saveJquery(List<Barang> entity) {
		for (Barang item : entity) {
			BarangEntity barangFind = barangJpaRepository.findOne(item.getKodeBarang());
			if( barangFind != null ) {
				throw new IllegalStateException("Kode barang "+item.getKodeBarang()+" already exist");
			}
			BarangEntity barangEntity = new BarangEntity();
			barangServiceMapper.mapBarangToBarangEntity(item, barangEntity);
			barangJpaRepository.save(barangEntity);
		}
	}

	@Override
	public Barang update(Barang barang) {
		BarangEntity barangEntity = barangJpaRepository.findOne(barang.getKodeBarang());
		barangServiceMapper.mapBarangToBarangEntity(barang, barangEntity);
		BarangEntity barangEntitySaved = barangJpaRepository.save(barangEntity);
		return barangServiceMapper.mapBarangEntityToBarang(barangEntitySaved);
	}

	@Override
	public void delete(String kodeBarang) {
		barangJpaRepository.delete(kodeBarang);
	}

	public BarangJpaRepository getBarangJpaRepository() {
		return barangJpaRepository;
	}

	public void setBarangJpaRepository(BarangJpaRepository barangJpaRepository) {
		this.barangJpaRepository = barangJpaRepository;
	}

	public BarangServiceMapper getBarangServiceMapper() {
		return barangServiceMapper;
	}

	public void setBarangServiceMapper(BarangServiceMapper barangServiceMapper) {
		this.barangServiceMapper = barangServiceMapper;
	}

}
