package org.demo.test;

import org.demo.bean.PenjualanHeader;

public class PenjualanHeaderFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PenjualanHeader newPenjualanHeader() {

		Integer idPenjualanHeader = mockValues.nextInteger();

		PenjualanHeader penjualanHeader = new PenjualanHeader();
		penjualanHeader.setIdPenjualanHeader(idPenjualanHeader);
		return penjualanHeader;
	}
	
}
