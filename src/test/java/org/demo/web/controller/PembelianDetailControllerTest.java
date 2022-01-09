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
import org.demo.bean.PembelianDetail;
import org.demo.bean.PembelianHeader;
import org.demo.test.PembelianDetailFactoryForTest;
import org.demo.test.PembelianHeaderFactoryForTest;

//--- Services 
import org.demo.business.service.PembelianDetailService;
import org.demo.business.service.PembelianHeaderService;

//--- List Items 
import org.demo.web.listitem.PembelianHeaderListItem;

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
public class PembelianDetailControllerTest {
	
	@InjectMocks
	private PembelianDetailController pembelianDetailController;
	@Mock
	private PembelianDetailService pembelianDetailService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PembelianHeaderService pembelianHeaderService; // Injected by Spring

	private PembelianDetailFactoryForTest pembelianDetailFactoryForTest = new PembelianDetailFactoryForTest();
	private PembelianHeaderFactoryForTest pembelianHeaderFactoryForTest = new PembelianHeaderFactoryForTest();

	List<PembelianHeader> pembelianHeaders = new ArrayList<PembelianHeader>();

	private void givenPopulateModel() {
		PembelianHeader pembelianHeader1 = pembelianHeaderFactoryForTest.newPembelianHeader();
		PembelianHeader pembelianHeader2 = pembelianHeaderFactoryForTest.newPembelianHeader();
		List<PembelianHeader> pembelianHeaders = new ArrayList<PembelianHeader>();
		pembelianHeaders.add(pembelianHeader1);
		pembelianHeaders.add(pembelianHeader2);
		when(pembelianHeaderService.findAll()).thenReturn(pembelianHeaders);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<PembelianDetail> list = new ArrayList<PembelianDetail>();
		when(pembelianDetailService.findAll()).thenReturn(list);
		
		// When
		String viewName = pembelianDetailController.list(model);
		
		// Then
		assertEquals("pembelianDetail/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = pembelianDetailController.formForCreate(model);
		
		// Then
		assertEquals("pembelianDetail/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((PembelianDetail)modelMap.get("pembelianDetail")).getIdPembelianDetail());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/pembelianDetail/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		Integer idPembelianDetail = pembelianDetail.getIdPembelianDetail();
		when(pembelianDetailService.findById(idPembelianDetail)).thenReturn(pembelianDetail);
		
		// When
		String viewName = pembelianDetailController.formForUpdate(model, idPembelianDetail);
		
		// Then
		assertEquals("pembelianDetail/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianDetail, (PembelianDetail) modelMap.get("pembelianDetail"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/pembelianDetail/update", modelMap.get("saveAction"));
		
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PembelianDetail pembelianDetailCreated = new PembelianDetail();
		when(pembelianDetailService.create(pembelianDetail)).thenReturn(pembelianDetailCreated); 
		
		// When
		String viewName = pembelianDetailController.create(pembelianDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/pembelianDetail/form/"+pembelianDetail.getIdPembelianDetail(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianDetailCreated, (PembelianDetail) modelMap.get("pembelianDetail"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = pembelianDetailController.create(pembelianDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianDetail, (PembelianDetail) modelMap.get("pembelianDetail"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/pembelianDetail/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
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

		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		
		Exception exception = new RuntimeException("test exception");
		when(pembelianDetailService.create(pembelianDetail)).thenThrow(exception);
		
		// When
		String viewName = pembelianDetailController.create(pembelianDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianDetail, (PembelianDetail) modelMap.get("pembelianDetail"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/pembelianDetail/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "pembelianDetail.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		Integer idPembelianDetail = pembelianDetail.getIdPembelianDetail();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PembelianDetail pembelianDetailSaved = new PembelianDetail();
		pembelianDetailSaved.setIdPembelianDetail(idPembelianDetail);
		when(pembelianDetailService.update(pembelianDetail)).thenReturn(pembelianDetailSaved); 
		
		// When
		String viewName = pembelianDetailController.update(pembelianDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/pembelianDetail/form/"+pembelianDetail.getIdPembelianDetail(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianDetailSaved, (PembelianDetail) modelMap.get("pembelianDetail"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = pembelianDetailController.update(pembelianDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianDetail, (PembelianDetail) modelMap.get("pembelianDetail"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/pembelianDetail/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
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

		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		
		Exception exception = new RuntimeException("test exception");
		when(pembelianDetailService.update(pembelianDetail)).thenThrow(exception);
		
		// When
		String viewName = pembelianDetailController.update(pembelianDetail, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("pembelianDetail/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(pembelianDetail, (PembelianDetail) modelMap.get("pembelianDetail"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/pembelianDetail/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "pembelianDetail.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		Integer idPembelianDetail = pembelianDetail.getIdPembelianDetail();
		
		// When
		String viewName = pembelianDetailController.delete(redirectAttributes, idPembelianDetail);
		
		// Then
		verify(pembelianDetailService).delete(idPembelianDetail);
		assertEquals("redirect:/pembelianDetail", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PembelianDetail pembelianDetail = pembelianDetailFactoryForTest.newPembelianDetail();
		Integer idPembelianDetail = pembelianDetail.getIdPembelianDetail();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(pembelianDetailService).delete(idPembelianDetail);
		
		// When
		String viewName = pembelianDetailController.delete(redirectAttributes, idPembelianDetail);
		
		// Then
		verify(pembelianDetailService).delete(idPembelianDetail);
		assertEquals("redirect:/pembelianDetail", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "pembelianDetail.error.delete", exception);
	}
	
	
}
