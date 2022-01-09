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
import org.demo.bean.ReturPenjualan;
import org.demo.bean.PenjualanHeader;
import org.demo.test.ReturPenjualanFactoryForTest;
import org.demo.test.PenjualanHeaderFactoryForTest;

//--- Services 
import org.demo.business.service.ReturPenjualanService;
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
public class ReturPenjualanControllerTest {
	
	@InjectMocks
	private ReturPenjualanController returPenjualanController;
	@Mock
	private ReturPenjualanService returPenjualanService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PenjualanHeaderService penjualanHeaderService; // Injected by Spring

	private ReturPenjualanFactoryForTest returPenjualanFactoryForTest = new ReturPenjualanFactoryForTest();
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
		
		List<ReturPenjualan> list = new ArrayList<ReturPenjualan>();
		when(returPenjualanService.findAll()).thenReturn(list);
		
		// When
		String viewName = returPenjualanController.list(model);
		
		// Then
		assertEquals("returPenjualan/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = returPenjualanController.formForCreate(model);
		
		// Then
		assertEquals("returPenjualan/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ReturPenjualan)modelMap.get("returPenjualan")).getIdreturPenjualan());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/returPenjualan/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		Integer idreturPenjualan = returPenjualan.getIdreturPenjualan();
		when(returPenjualanService.findById(idreturPenjualan)).thenReturn(returPenjualan);
		
		// When
		String viewName = returPenjualanController.formForUpdate(model, idreturPenjualan);
		
		// Then
		assertEquals("returPenjualan/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPenjualan, (ReturPenjualan) modelMap.get("returPenjualan"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/returPenjualan/update", modelMap.get("saveAction"));
		
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReturPenjualan returPenjualanCreated = new ReturPenjualan();
		when(returPenjualanService.create(returPenjualan)).thenReturn(returPenjualanCreated); 
		
		// When
		String viewName = returPenjualanController.create(returPenjualan, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/returPenjualan/form/"+returPenjualan.getIdreturPenjualan(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPenjualanCreated, (ReturPenjualan) modelMap.get("returPenjualan"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = returPenjualanController.create(returPenjualan, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPenjualan/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPenjualan, (ReturPenjualan) modelMap.get("returPenjualan"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/returPenjualan/create", modelMap.get("saveAction"));
		
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

		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		
		Exception exception = new RuntimeException("test exception");
		when(returPenjualanService.create(returPenjualan)).thenThrow(exception);
		
		// When
		String viewName = returPenjualanController.create(returPenjualan, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPenjualan/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPenjualan, (ReturPenjualan) modelMap.get("returPenjualan"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/returPenjualan/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "returPenjualan.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		Integer idreturPenjualan = returPenjualan.getIdreturPenjualan();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReturPenjualan returPenjualanSaved = new ReturPenjualan();
		returPenjualanSaved.setIdreturPenjualan(idreturPenjualan);
		when(returPenjualanService.update(returPenjualan)).thenReturn(returPenjualanSaved); 
		
		// When
		String viewName = returPenjualanController.update(returPenjualan, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/returPenjualan/form/"+returPenjualan.getIdreturPenjualan(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPenjualanSaved, (ReturPenjualan) modelMap.get("returPenjualan"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = returPenjualanController.update(returPenjualan, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPenjualan/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPenjualan, (ReturPenjualan) modelMap.get("returPenjualan"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/returPenjualan/update", modelMap.get("saveAction"));
		
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

		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		
		Exception exception = new RuntimeException("test exception");
		when(returPenjualanService.update(returPenjualan)).thenThrow(exception);
		
		// When
		String viewName = returPenjualanController.update(returPenjualan, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("returPenjualan/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(returPenjualan, (ReturPenjualan) modelMap.get("returPenjualan"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/returPenjualan/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "returPenjualan.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PenjualanHeaderListItem> penjualanHeaderListItems = (List<PenjualanHeaderListItem>) modelMap.get("listOfPenjualanHeaderItems");
		assertEquals(2, penjualanHeaderListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		Integer idreturPenjualan = returPenjualan.getIdreturPenjualan();
		
		// When
		String viewName = returPenjualanController.delete(redirectAttributes, idreturPenjualan);
		
		// Then
		verify(returPenjualanService).delete(idreturPenjualan);
		assertEquals("redirect:/returPenjualan", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReturPenjualan returPenjualan = returPenjualanFactoryForTest.newReturPenjualan();
		Integer idreturPenjualan = returPenjualan.getIdreturPenjualan();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(returPenjualanService).delete(idreturPenjualan);
		
		// When
		String viewName = returPenjualanController.delete(redirectAttributes, idreturPenjualan);
		
		// Then
		verify(returPenjualanService).delete(idreturPenjualan);
		assertEquals("redirect:/returPenjualan", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "returPenjualan.error.delete", exception);
	}
	
	
}
