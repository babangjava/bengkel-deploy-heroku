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
import org.demo.bean.ReturPembelian;
import org.demo.bean.PembelianHeader;
import org.demo.test.ReturPembelianFactoryForTest;
import org.demo.test.PembelianHeaderFactoryForTest;

//--- Services 
import org.demo.business.service.ReturPembelianService;
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
public class ReturPembelianControllerTest {
	
	@InjectMocks
	private ReturPembelianController returPembelianController;
	@Mock
	private ReturPembelianService returPembelianService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PembelianHeaderService pembelianHeaderService; // Injected by Spring

	private ReturPembelianFactoryForTest returPembelianFactoryForTest = new ReturPembelianFactoryForTest();
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
		
		List<ReturPembelian> list = new ArrayList<ReturPembelian>();
		when(returPembelianService.findAll()).thenReturn(list);
		
		// When
		String viewName = returPembelianController.list(model);
		
		// Then
		assertEquals("returPembelian/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = returPembelianController.formForCreate(model);
		
		// Then
		assertEquals("returPembelian/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ReturPembelian)modelMap.get("returPembelian")).getIdreturPembelian());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/returPembelian/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		Integer idreturPembelian = returPembelian.getIdreturPembelian();
		when(returPembelianService.findById(idreturPembelian)).thenReturn(returPembelian);
		
		// When
		String viewName = returPembelianController.formForUpdate(model, idreturPembelian);
		
		// Then
		assertEquals("returPembelian/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPembelian, (ReturPembelian) modelMap.get("returPembelian"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/returPembelian/update", modelMap.get("saveAction"));
		
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReturPembelian returPembelianCreated = new ReturPembelian();
		when(returPembelianService.create(returPembelian)).thenReturn(returPembelianCreated); 
		
		// When
		String viewName = returPembelianController.create(returPembelian, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/returPembelian/form/"+returPembelian.getIdreturPembelian(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPembelianCreated, (ReturPembelian) modelMap.get("returPembelian"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = returPembelianController.create(returPembelian, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPembelian/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPembelian, (ReturPembelian) modelMap.get("returPembelian"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/returPembelian/create", modelMap.get("saveAction"));
		
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

		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		
		Exception exception = new RuntimeException("test exception");
		when(returPembelianService.create(returPembelian)).thenThrow(exception);
		
		// When
		String viewName = returPembelianController.create(returPembelian, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPembelian/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPembelian, (ReturPembelian) modelMap.get("returPembelian"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/returPembelian/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "returPembelian.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		Integer idreturPembelian = returPembelian.getIdreturPembelian();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReturPembelian returPembelianSaved = new ReturPembelian();
		returPembelianSaved.setIdreturPembelian(idreturPembelian);
		when(returPembelianService.update(returPembelian)).thenReturn(returPembelianSaved); 
		
		// When
		String viewName = returPembelianController.update(returPembelian, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/returPembelian/form/"+returPembelian.getIdreturPembelian(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPembelianSaved, (ReturPembelian) modelMap.get("returPembelian"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = returPembelianController.update(returPembelian, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPembelian/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPembelian, (ReturPembelian) modelMap.get("returPembelian"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/returPembelian/update", modelMap.get("saveAction"));
		
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

		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		
		Exception exception = new RuntimeException("test exception");
		when(returPembelianService.update(returPembelian)).thenThrow(exception);
		
		// When
		String viewName = returPembelianController.update(returPembelian, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPembelian/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPembelian, (ReturPembelian) modelMap.get("returPembelian"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/returPembelian/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "returPembelian.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PembelianHeaderListItem> pembelianHeaderListItems = (List<PembelianHeaderListItem>) modelMap.get("listOfPembelianHeaderItems");
		assertEquals(2, pembelianHeaderListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		Integer idreturPembelian = returPembelian.getIdreturPembelian();
		
		// When
		String viewName = returPembelianController.delete(redirectAttributes, idreturPembelian);
		
		// Then
		verify(returPembelianService).delete(idreturPembelian);
		assertEquals("redirect:/returPembelian", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReturPembelian returPembelian = returPembelianFactoryForTest.newReturPembelian();
		Integer idreturPembelian = returPembelian.getIdreturPembelian();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(returPembelianService).delete(idreturPembelian);
		
		// When
		String viewName = returPembelianController.delete(redirectAttributes, idreturPembelian);
		
		// Then
		verify(returPembelianService).delete(idreturPembelian);
		assertEquals("redirect:/returPembelian", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "returPembelian.error.delete", exception);
	}
	
	
}
