//package org.demo.web.controller;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
////--- Entities
//import org.demo.bean.Login;
//import org.demo.test.LoginFactoryForTest;
//
////--- Services
//import org.demo.business.service.LoginService;
//
//
//import org.demo.web.common.Message;
//import org.demo.web.common.MessageHelper;
//import org.demo.web.common.MessageType;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.context.MessageSource;
//import org.springframework.ui.ExtendedModelMap;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LoginControllerTest {
//
//	@InjectMocks
//	private LoginController loginController;
//	@Mock
//	private LoginService loginService;
//	@Mock
//	private MessageHelper messageHelper;
//	@Mock
//	private MessageSource messageSource;
//
//	private LoginFactoryForTest loginFactoryForTest = new LoginFactoryForTest();
//
//
//	private void givenPopulateModel() {
//	}
//
//	@Test
//	public void list() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		List<Login> list = new ArrayList<Login>();
//		when(loginService.findAll()).thenReturn(list);
//
//		// When
//		String viewName = loginController.list(model);
//
//		// Then
//		assertEquals("login/list", viewName);
//		Map<String,?> modelMap = model.asMap();
//		assertEquals(list, modelMap.get("list"));
//	}
//
//	@Test
//	public void formForCreate() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		givenPopulateModel();
//
//		// When
//		String viewName = loginController.formForCreate(model);
//
//		// Then
//		assertEquals("login/form", viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertNull(((Login)modelMap.get("login")).getUserName());
//		assertEquals("create", modelMap.get("mode"));
//		assertEquals("/login/create", modelMap.get("saveAction"));
//
//	}
//
//	@Test
//	public void formForUpdate() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		givenPopulateModel();
//
//		Login login = loginFactoryForTest.newLogin();
//		String userName = login.getUserName();
//		when(loginService.findById(userName)).thenReturn(login);
//
//		// When
//		String viewName = loginController.formForUpdate(model, userName);
//
//		// Then
//		assertEquals("login/form", viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertEquals(login, (Login) modelMap.get("login"));
//		assertEquals("update", modelMap.get("mode"));
//		assertEquals("/login/update", modelMap.get("saveAction"));
//
//	}
//
//	@Test
//	public void createOk() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		Login login = loginFactoryForTest.newLogin();
//		BindingResult bindingResult = mock(BindingResult.class);
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//
//		Login loginCreated = new Login();
//		when(loginService.create(login)).thenReturn(loginCreated);
//
//		// When
//		String viewName = loginController.create(login, bindingResult, model, redirectAttributes, httpServletRequest);
//
//		// Then
//		assertEquals("redirect:/login/form/"+login.getUserName(), viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertEquals(loginCreated, (Login) modelMap.get("login"));
//		assertEquals(null, modelMap.get("mode"));
//		assertEquals(null, modelMap.get("saveAction"));
//
//		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
//	}
//
//	@Test
//	public void createBindingResultErrors() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		givenPopulateModel();
//
//		Login login = loginFactoryForTest.newLogin();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(bindingResult.hasErrors()).thenReturn(true);
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//
//		// When
//		String viewName = loginController.create(login, bindingResult, model, redirectAttributes, httpServletRequest);
//
//		// Then
//		assertEquals("login/form", viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertEquals(login, (Login) modelMap.get("login"));
//		assertEquals("create", modelMap.get("mode"));
//		assertEquals("/login/create", modelMap.get("saveAction"));
//
//	}
//
//	@Test
//	public void createException() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		givenPopulateModel();
//
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(bindingResult.hasErrors()).thenReturn(false);
//
//		Login login = loginFactoryForTest.newLogin();
//
//		Exception exception = new RuntimeException("test exception");
//		when(loginService.create(login)).thenThrow(exception);
//
//		// When
//		String viewName = loginController.create(login, bindingResult, model, redirectAttributes, httpServletRequest);
//
//		// Then
//		assertEquals("login/form", viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertEquals(login, (Login) modelMap.get("login"));
//		assertEquals("create", modelMap.get("mode"));
//		assertEquals("/login/create", modelMap.get("saveAction"));
//
//		Mockito.verify(messageHelper).addException(model, "login.error.create", exception);
//
//	}
//
//	@Test
//	public void updateOk() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		Login login = loginFactoryForTest.newLogin();
//		String userName = login.getUserName();
//
//		BindingResult bindingResult = mock(BindingResult.class);
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//
//		Login loginSaved = new Login();
//		loginSaved.setUserName(userName);
//		when(loginService.update(login)).thenReturn(loginSaved);
//
//		// When
//		String viewName = loginController.update(login, bindingResult, model, redirectAttributes, httpServletRequest);
//
//		// Then
//		assertEquals("redirect:/login/form/"+login.getUserName(), viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertEquals(loginSaved, (Login) modelMap.get("login"));
//		assertEquals(null, modelMap.get("mode"));
//		assertEquals(null, modelMap.get("saveAction"));
//
//		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
//	}
//
//	@Test
//	public void updateBindingResultErrors() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		givenPopulateModel();
//
//		Login login = loginFactoryForTest.newLogin();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(bindingResult.hasErrors()).thenReturn(true);
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//
//		// When
//		String viewName = loginController.update(login, bindingResult, model, redirectAttributes, httpServletRequest);
//
//		// Then
//		assertEquals("login/form", viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertEquals(login, (Login) modelMap.get("login"));
//		assertEquals("update", modelMap.get("mode"));
//		assertEquals("/login/update", modelMap.get("saveAction"));
//
//	}
//
//	@Test
//	public void updateException() {
//		// Given
//		Model model = new ExtendedModelMap();
//
//		givenPopulateModel();
//
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(bindingResult.hasErrors()).thenReturn(false);
//
//		Login login = loginFactoryForTest.newLogin();
//
//		Exception exception = new RuntimeException("test exception");
//		when(loginService.update(login)).thenThrow(exception);
//
//		// When
//		String viewName = loginController.update(login, bindingResult, model, redirectAttributes, httpServletRequest);
//
//		// Then
//		assertEquals("login/form", viewName);
//
//		Map<String,?> modelMap = model.asMap();
//
//		assertEquals(login, (Login) modelMap.get("login"));
//		assertEquals("update", modelMap.get("mode"));
//		assertEquals("/login/update", modelMap.get("saveAction"));
//
//		Mockito.verify(messageHelper).addException(model, "login.error.update", exception);
//
//	}
//
//
//	@Test
//	public void deleteOK() {
//		// Given
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//
//		Login login = loginFactoryForTest.newLogin();
//		String userName = login.getUserName();
//
//		// When
//		String viewName = loginController.delete(redirectAttributes, userName);
//
//		// Then
//		verify(loginService).delete(userName);
//		assertEquals("redirect:/login", viewName);
//		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
//	}
//
//	@Test
//	public void deleteException() {
//		// Given
//		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//
//		Login login = loginFactoryForTest.newLogin();
//		String userName = login.getUserName();
//
//		Exception exception = new RuntimeException("test exception");
//		doThrow(exception).when(loginService).delete(userName);
//
//		// When
//		String viewName = loginController.delete(redirectAttributes, userName);
//
//		// Then
//		verify(loginService).delete(userName);
//		assertEquals("redirect:/login", viewName);
//		Mockito.verify(messageHelper).addException(redirectAttributes, "login.error.delete", exception);
//	}
//
//
//}
