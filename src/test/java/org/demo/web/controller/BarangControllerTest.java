package org.demo.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.demo.bean.Barang;
import org.demo.test.BarangFactoryForTest;

//--- Services 
import org.demo.business.service.BarangService;


import org.demo.web.common.Message;
import org.demo.web.common.MessageHelper;
import org.demo.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class BarangControllerTest {
	
	@InjectMocks
	private BarangController barangController;
	@Mock
	private BarangService barangService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private BarangFactoryForTest barangFactoryForTest = new BarangFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Barang> list = new ArrayList<Barang>();
		when(barangService.findAll()).thenReturn(list);
		
		// When
		String viewName = barangController.list(model);
		
		// Then
		assertEquals("barang/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = barangController.formForCreate(model);
		
		// Then
		assertEquals("barang/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Barang)modelMap.get("barang")).getKodeBarang());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/barang/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Barang barang = barangFactoryForTest.newBarang();
		String kodeBarang = barang.getKodeBarang();
		when(barangService.findById(kodeBarang)).thenReturn(barang);
		
		// When
		String viewName = barangController.formForUpdate(model, kodeBarang);
		
		// Then
		assertEquals("barang/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(barang, (Barang) modelMap.get("barang"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/barang/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Barang barang = barangFactoryForTest.newBarang();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Barang barangCreated = new Barang();
		when(barangService.create(barang)).thenReturn(barangCreated); 
		
		// When
		String viewName = barangController.create(barang, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/barang/form/"+barang.getKodeBarang(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(barangCreated, (Barang) modelMap.get("barang"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Barang barang = barangFactoryForTest.newBarang();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = barangController.create(barang, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("barang/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(barang, (Barang) modelMap.get("barang"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/barang/create", modelMap.get("saveAction"));
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Barang barang = barangFactoryForTest.newBarang();
		
		Exception exception = new RuntimeException("test exception");
		when(barangService.create(barang)).thenThrow(exception);
		
		// When
		String viewName = barangController.create(barang, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("barang/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(barang, (Barang) modelMap.get("barang"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/barang/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "barang.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Barang barang = barangFactoryForTest.newBarang();
		String kodeBarang = barang.getKodeBarang();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Barang barangSaved = new Barang();
		barangSaved.setKodeBarang(kodeBarang);
		when(barangService.update(barang)).thenReturn(barangSaved); 
		
		// When
		String viewName = barangController.update(barang, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/barang/form/"+barang.getKodeBarang(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(barangSaved, (Barang) modelMap.get("barang"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Barang barang = barangFactoryForTest.newBarang();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = barangController.update(barang, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("barang/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(barang, (Barang) modelMap.get("barang"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/barang/update", modelMap.get("saveAction"));
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Barang barang = barangFactoryForTest.newBarang();
		
		Exception exception = new RuntimeException("test exception");
		when(barangService.update(barang)).thenThrow(exception);
		
		// When
		String viewName = barangController.update(barang, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("barang/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(barang, (Barang) modelMap.get("barang"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/barang/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "barang.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Barang barang = barangFactoryForTest.newBarang();
		String kodeBarang = barang.getKodeBarang();
		
		// When
		String viewName = barangController.delete(redirectAttributes, kodeBarang);
		
		// Then
		verify(barangService).delete(kodeBarang);
		assertEquals("redirect:/barang", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Barang barang = barangFactoryForTest.newBarang();
		String kodeBarang = barang.getKodeBarang();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(barangService).delete(kodeBarang);
		
		// When
		String viewName = barangController.delete(redirectAttributes, kodeBarang);
		
		// Then
		verify(barangService).delete(kodeBarang);
		assertEquals("redirect:/barang", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "barang.error.delete", exception);
	}
	
	
}
