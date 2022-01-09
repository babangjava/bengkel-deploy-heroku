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
import org.demo.bean.Mekanik;
import org.demo.test.MekanikFactoryForTest;

//--- Services 
import org.demo.business.service.MekanikService;


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
public class MekanikControllerTest {
	
	@InjectMocks
	private MekanikController mekanikController;
	@Mock
	private MekanikService mekanikService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private MekanikFactoryForTest mekanikFactoryForTest = new MekanikFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Mekanik> list = new ArrayList<Mekanik>();
		when(mekanikService.findAll()).thenReturn(list);
		
		// When
		String viewName = mekanikController.list(model);
		
		// Then
		assertEquals("mekanik/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = mekanikController.formForCreate(model);
		
		// Then
		assertEquals("mekanik/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Mekanik)modelMap.get("mekanik")).getIdMekanik());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/mekanik/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		String idMekanik = mekanik.getIdMekanik();
		when(mekanikService.findById(idMekanik)).thenReturn(mekanik);
		
		// When
		String viewName = mekanikController.formForUpdate(model, idMekanik);
		
		// Then
		assertEquals("mekanik/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(mekanik, (Mekanik) modelMap.get("mekanik"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/mekanik/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Mekanik mekanikCreated = new Mekanik();
		when(mekanikService.create(mekanik)).thenReturn(mekanikCreated); 
		
		// When
		String viewName = mekanikController.create(mekanik, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/mekanik/form/"+mekanik.getIdMekanik(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(mekanikCreated, (Mekanik) modelMap.get("mekanik"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = mekanikController.create(mekanik, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("mekanik/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(mekanik, (Mekanik) modelMap.get("mekanik"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/mekanik/create", modelMap.get("saveAction"));
		
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

		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		
		Exception exception = new RuntimeException("test exception");
		when(mekanikService.create(mekanik)).thenThrow(exception);
		
		// When
		String viewName = mekanikController.create(mekanik, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("mekanik/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(mekanik, (Mekanik) modelMap.get("mekanik"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/mekanik/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "mekanik.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		String idMekanik = mekanik.getIdMekanik();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Mekanik mekanikSaved = new Mekanik();
		mekanikSaved.setIdMekanik(idMekanik);
		when(mekanikService.update(mekanik)).thenReturn(mekanikSaved); 
		
		// When
		String viewName = mekanikController.update(mekanik, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/mekanik/form/"+mekanik.getIdMekanik(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(mekanikSaved, (Mekanik) modelMap.get("mekanik"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = mekanikController.update(mekanik, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("mekanik/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(mekanik, (Mekanik) modelMap.get("mekanik"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/mekanik/update", modelMap.get("saveAction"));
		
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

		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		
		Exception exception = new RuntimeException("test exception");
		when(mekanikService.update(mekanik)).thenThrow(exception);
		
		// When
		String viewName = mekanikController.update(mekanik, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("mekanik/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(mekanik, (Mekanik) modelMap.get("mekanik"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/mekanik/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "mekanik.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		String idMekanik = mekanik.getIdMekanik();
		
		// When
		String viewName = mekanikController.delete(redirectAttributes, idMekanik);
		
		// Then
		verify(mekanikService).delete(idMekanik);
		assertEquals("redirect:/mekanik", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Mekanik mekanik = mekanikFactoryForTest.newMekanik();
		String idMekanik = mekanik.getIdMekanik();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(mekanikService).delete(idMekanik);
		
		// When
		String viewName = mekanikController.delete(redirectAttributes, idMekanik);
		
		// Then
		verify(mekanikService).delete(idMekanik);
		assertEquals("redirect:/mekanik", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "mekanik.error.delete", exception);
	}
	
	
}
