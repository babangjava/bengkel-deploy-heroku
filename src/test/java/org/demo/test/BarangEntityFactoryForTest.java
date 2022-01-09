package org.demo.test;

import org.demo.bean.jpa.BarangEntity;

public class BarangEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BarangEntity newBarangEntity() {

		String kodeBarang = mockValues.nextString(10);

		BarangEntity barangEntity = new BarangEntity();
		barangEntity.setKodeBarang(kodeBarang);
		return barangEntity;
	}
	
}
