package org.demo.test;

import org.demo.bean.PenjualanDetail;

public class PenjualanDetailFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PenjualanDetail newPenjualanDetail() {

		Integer idPenjualanDetail = mockValues.nextInteger();

		PenjualanDetail penjualanDetail = new PenjualanDetail();
		penjualanDetail.setIdPenjualanDetail(idPenjualanDetail);
		return penjualanDetail;
	}
	
}
