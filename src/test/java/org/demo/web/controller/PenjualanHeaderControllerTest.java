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
import org.demo.bean.PenjualanHeader;
import org.demo.test.PenjualanHeaderFactoryForTest;

//--- Services 
import org.demo.business.service.PenjualanHeaderService;


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
public class PenjualanHeaderControllerTest {
	
	@InjectMocks
	private PenjualanHeaderController penjualanHeaderController;
	@Mock
	private PenjualanHeaderService penjualanHeaderService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private PenjualanHeaderFactoryForTest penjualanHeaderFactoryForTest = new PenjualanHeaderFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<PenjualanHeader> list = new ArrayList<PenjualanHeader>();
		when(penjualanHeaderService.findAll()).thenReturn(list);
		
		// When
		String viewName = penjualanHeaderController.list(model);
		
		// Then
		assertEquals("penjualanHeader/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = penjualanHeaderController.formForCreate(model);
		
		// Then
		assertEquals("penjualanHeader/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((PenjualanHeader)modelMap.get("penjualanHeader")).getIdPenjualanHeader());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/penjualanHeader/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		Integer idPenjualanHeader = penjualanHeader.getIdPenjualanHeader();
		when(penjualanHeaderService.findById(idPenjualanHeader)).thenReturn(penjualanHeader);
		
		// When
		String viewName = penjualanHeaderController.formForUpdate(model, idPenjualanHeader);
		
		// Then
		assertEquals("penjualanHeader/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanHeader, (PenjualanHeader) modelMap.get("penjualanHeader"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/penjualanHeader/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PenjualanHeader penjualanHeaderCreated = new PenjualanHeader();
		when(penjualanHeaderService.create(penjualanHeader)).thenReturn(penjualanHeaderCreated); 
		
		// When
		String viewName = penjualanHeaderController.create(penjualanHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/penjualanHeader/form/"+penjualanHeader.getIdPenjualanHeader(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanHeaderCreated, (PenjualanHeader) modelMap.get("penjualanHeader"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = penjualanHeaderController.create(penjualanHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanHeader, (PenjualanHeader) modelMap.get("penjualanHeader"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/penjualanHeader/create", modelMap.get("saveAction"));
		
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

		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		
		Exception exception = new RuntimeException("test exception");
		when(penjualanHeaderService.create(penjualanHeader)).thenThrow(exception);
		
		// When
		String viewName = penjualanHeaderController.create(penjualanHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanHeader, (PenjualanHeader) modelMap.get("penjualanHeader"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/penjualanHeader/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "penjualanHeader.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		Integer idPenjualanHeader = penjualanHeader.getIdPenjualanHeader();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PenjualanHeader penjualanHeaderSaved = new PenjualanHeader();
		penjualanHeaderSaved.setIdPenjualanHeader(idPenjualanHeader);
		when(penjualanHeaderService.update(penjualanHeader)).thenReturn(penjualanHeaderSaved); 
		
		// When
		String viewName = penjualanHeaderController.update(penjualanHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/penjualanHeader/form/"+penjualanHeader.getIdPenjualanHeader(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanHeaderSaved, (PenjualanHeader) modelMap.get("penjualanHeader"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = penjualanHeaderController.update(penjualanHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanHeader, (PenjualanHeader) modelMap.get("penjualanHeader"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/penjualanHeader/update", modelMap.get("saveAction"));
		
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

		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		
		Exception exception = new RuntimeException("test exception");
		when(penjualanHeaderService.update(penjualanHeader)).thenThrow(exception);
		
		// When
		String viewName = penjualanHeaderController.update(penjualanHeader, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanHeader/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanHeader, (PenjualanHeader) modelMap.get("penjualanHeader"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/penjualanHeader/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "penjualanHeader.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		Integer idPenjualanHeader = penjualanHeader.getIdPenjualanHeader();
		
		// When
		String viewName = penjualanHeaderController.delete(redirectAttributes, idPenjualanHeader);
		
		// Then
		verify(penjualanHeaderService).delete(idPenjualanHeader);
		assertEquals("redirect:/penjualanHeader", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PenjualanHeader penjualanHeader = penjualanHeaderFactoryForTest.newPenjualanHeader();
		Integer idPenjualanHeader = penjualanHeader.getIdPenjualanHeader();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(penjualanHeaderService).delete(idPenjualanHeader);
		
		// When
		String viewName = penjualanHeaderController.delete(redirectAttributes, idPenjualanHeader);
		
		// Then
		verify(penjualanHeaderService).delete(idPenjualanHeader);
		assertEquals("redirect:/penjualanHeader", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "penjualanHeader.error.delete", exception);
	}
	
	
}
