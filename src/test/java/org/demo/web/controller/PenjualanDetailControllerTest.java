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
import org.demo.bean.PenjualanDetail;
import org.demo.bean.PenjualanHeader;
import org.demo.test.PenjualanDetailFactoryForTest;
import org.demo.test.PenjualanHeaderFactoryForTest;

//--- Services 
import org.demo.business.service.PenjualanDetailService;
import org.demo.business.service.PenjualanHeaderService;

//--- List Items 
import org.demo.web.listitem.PenjualanHeaderListItem;

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
public class PenjualanDetailControllerTest {
	
	@InjectMocks
	private PenjualanDetailController penjualanDetailController;
	@Mock
	private PenjualanDetailService penjualanDetailService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PenjualanHeaderService penjualanHeaderService; // Injected by Spring

	private PenjualanDetailFactoryForTest penjualanDetailFactoryForTest = new PenjualanDetailFactoryForTest();
	private PenjualanHeaderFactoryForTest penjualanHeaderFactoryForTest = new PenjualanHeaderFactoryForTest();

	List<PenjualanHeader> penjualanHeaders = new ArrayList<PenjualanHeader>();

	private void givenPopulateModel() {
		PenjualanHeader penjualanHeader1 = penjualanHeaderFactoryForTest.newPenjualanHeader();
		PenjualanHeader penjualanHeader2 = penjualanHeaderFactoryForTest.newPenjualanHeader();
		List<PenjualanHeader> penjualanHeaders = new ArrayList<PenjualanHeader>();
		penjualanHeaders.add(penjualanHeader1);
		penjualanHeaders.add(penjualanHeader2);
		when(penjualanHeaderService.findAll()).thenReturn(penjualanHeaders);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<PenjualanDetail> list = new ArrayList<PenjualanDetail>();
		when(penjualanDetailService.findAll()).thenReturn(list);
		
		// When
		String viewName = penjualanDetailController.list(model);
		
		// Then
		assertEquals("penjualanDetail/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = penjualanDetailController.formForCreate(model);
		
		// Then
		assertEquals("penjualanDetail/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((PenjualanDetail)modelMap.get("penjualanDetail")).getIdPenjualanDetail());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/penjualanDetail/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		Integer idPenjualanDetail = penjualanDetail.getIdPenjualanDetail();
		when(penjualanDetailService.findById(idPenjualanDetail)).thenReturn(penjualanDetail);
		
		// When
		String viewName = penjualanDetailController.formForUpdate(model, idPenjualanDetail);
		
		// Then
		assertEquals("penjualanDetail/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanDetail, (PenjualanDetail) modelMap.get("penjualanDetail"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/penjualanDetail/update", modelMap.get("saveAction"));
		
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PenjualanDetail penjualanDetailCreated = new PenjualanDetail();
		when(penjualanDetailService.create(penjualanDetail)).thenReturn(penjualanDetailCreated); 
		
		// When
		String viewName = penjualanDetailController.create(penjualanDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/penjualanDetail/form/"+penjualanDetail.getIdPenjualanDetail(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanDetailCreated, (PenjualanDetail) modelMap.get("penjualanDetail"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = penjualanDetailController.create(penjualanDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanDetail, (PenjualanDetail) modelMap.get("penjualanDetail"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/penjualanDetail/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
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

		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		
		Exception exception = new RuntimeException("test exception");
		when(penjualanDetailService.create(penjualanDetail)).thenThrow(exception);
		
		// When
		String viewName = penjualanDetailController.create(penjualanDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanDetail, (PenjualanDetail) modelMap.get("penjualanDetail"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/penjualanDetail/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "penjualanDetail.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		Integer idPenjualanDetail = penjualanDetail.getIdPenjualanDetail();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PenjualanDetail penjualanDetailSaved = new PenjualanDetail();
		penjualanDetailSaved.setIdPenjualanDetail(idPenjualanDetail);
		when(penjualanDetailService.update(penjualanDetail)).thenReturn(penjualanDetailSaved); 
		
		// When
		String viewName = penjualanDetailController.update(penjualanDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/penjualanDetail/form/"+penjualanDetail.getIdPenjualanDetail(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanDetailSaved, (PenjualanDetail) modelMap.get("penjualanDetail"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = penjualanDetailController.update(penjualanDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanDetail, (PenjualanDetail) modelMap.get("penjualanDetail"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/penjualanDetail/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
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

		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		
		Exception exception = new RuntimeException("test exception");
		when(penjualanDetailService.update(penjualanDetail)).thenThrow(exception);
		
		// When
		String viewName = penjualanDetailController.update(penjualanDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("penjualanDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(penjualanDetail, (PenjualanDetail) modelMap.get("penjualanDetail"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/penjualanDetail/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "penjualanDetail.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		Integer idPenjualanDetail = penjualanDetail.getIdPenjualanDetail();
		
		// When
		String viewName = penjualanDetailController.delete(redirectAttributes, idPenjualanDetail);
		
		// Then
		verify(penjualanDetailService).delete(idPenjualanDetail);
		assertEquals("redirect:/penjualanDetail", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PenjualanDetail penjualanDetail = penjualanDetailFactoryForTest.newPenjualanDetail();
		Integer idPenjualanDetail = penjualanDetail.getIdPenjualanDetail();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(penjualanDetailService).delete(idPenjualanDetail);
		
		// When
		String viewName = penjualanDetailController.delete(redirectAttributes, idPenjualanDetail);
		
		// Then
		verify(penjualanDetailService).delete(idPenjualanDetail);
		assertEquals("redirect:/penjualanDetail", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "penjualanDetail.error.delete", exception);
	}
	
	
}
