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
import org.demo.bean.PembelianHeader;
import org.demo.test.PembelianHeaderFactoryForTest;

//--- Services 
import org.demo.business.service.PembelianHeaderService;


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
public class PembelianHeaderControllerTest {
	
	@InjectMocks
	private PembelianHeaderController pembelianHeaderController;
	@Mock
	private PembelianHeaderService pembelianHeaderService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private PembelianHeaderFactoryForTest pembelianHeaderFactoryForTest = new PembelianHeaderFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<PembelianHeader> list = new ArrayList<PembelianHeader>();
		when(pembelianHeaderService.findAll()).thenReturn(list);
		
		// When
		String viewName = pembelianHeaderController.list(model);
		
		// Then
		assertEquals("pembelianHeader/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = pembelianHeaderController.formForCreate(model);
		
		// Then
		assertEquals("pembelianHeader/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((PembelianHeader)modelMap.get("pembelianHeader")).getIdPembelian());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/pembelianHeader/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		Integer idPembelian = pembelianHeader.getIdPembelian();
		when(pembelianHeaderService.findById(idPembelian)).thenReturn(pembelianHeader);
		
		// When
		String viewName = pembelianHeaderController.formForUpdate(model, idPembelian);
		
		// Then
		assertEquals("pembelianHeader/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianHeader, (PembelianHeader) modelMap.get("pembelianHeader"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/pembelianHeader/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PembelianHeader pembelianHeaderCreated = new PembelianHeader();
		when(pembelianHeaderService.create(pembelianHeader)).thenReturn(pembelianHeaderCreated); 
		
		// When
		String viewName = pembelianHeaderController.create(pembelianHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/pembelianHeader/form/"+pembelianHeader.getIdPembelian(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianHeaderCreated, (PembelianHeader) modelMap.get("pembelianHeader"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = pembelianHeaderController.create(pembelianHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianHeader, (PembelianHeader) modelMap.get("pembelianHeader"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/pembelianHeader/create", modelMap.get("saveAction"));
		
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

		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		
		Exception exception = new RuntimeException("test exception");
		when(pembelianHeaderService.create(pembelianHeader)).thenThrow(exception);
		
		// When
		String viewName = pembelianHeaderController.create(pembelianHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianHeader, (PembelianHeader) modelMap.get("pembelianHeader"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/pembelianHeader/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "pembelianHeader.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		Integer idPembelian = pembelianHeader.getIdPembelian();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PembelianHeader pembelianHeaderSaved = new PembelianHeader();
		pembelianHeaderSaved.setIdPembelian(idPembelian);
		when(pembelianHeaderService.update(pembelianHeader)).thenReturn(pembelianHeaderSaved); 
		
		// When
		String viewName = pembelianHeaderController.update(pembelianHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/pembelianHeader/form/"+pembelianHeader.getIdPembelian(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianHeaderSaved, (PembelianHeader) modelMap.get("pembelianHeader"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = pembelianHeaderController.update(pembelianHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianHeader, (PembelianHeader) modelMap.get("pembelianHeader"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/pembelianHeader/update", modelMap.get("saveAction"));
		
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

		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		
		Exception exception = new RuntimeException("test exception");
		when(pembelianHeaderService.update(pembelianHeader)).thenThrow(exception);
		
		// When
		String viewName = pembelianHeaderController.update(pembelianHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianHeader, (PembelianHeader) modelMap.get("pembelianHeader"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/pembelianHeader/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "pembelianHeader.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		Integer idPembelian = pembelianHeader.getIdPembelian();
		
		// When
		String viewName = pembelianHeaderController.delete(redirectAttributes, idPembelian);
		
		// Then
		verify(pembelianHeaderService).delete(idPembelian);
		assertEquals("redirect:/pembelianHeader", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PembelianHeader pembelianHeader = pembelianHeaderFactoryForTest.newPembelianHeader();
		Integer idPembelian = pembelianHeader.getIdPembelian();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(pembelianHeaderService).delete(idPembelian);
		
		// When
		String viewName = pembelianHeaderController.delete(redirectAttributes, idPembelian);
		
		// Then
		verify(pembelianHeaderService).delete(idPembelian);
		assertEquals("redirect:/pembelianHeader", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "pembelianHeader.error.delete", exception);
	}
	
	
}
