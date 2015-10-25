package com.kumasi.journal.web.controller;

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
import com.kumasi.journal.domain.Entrytype;
import com.kumasi.journal.test.EntrytypeFactoryForTest;

//--- Services 
import com.kumasi.journal.business.service.EntrytypeService;


import com.kumasi.journal.web.common.Message;
import com.kumasi.journal.web.common.MessageHelper;
import com.kumasi.journal.web.common.MessageType;
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
public class EntrytypeControllerTest {
	
	@InjectMocks
	private EntrytypeController entrytypeController;
	@Mock
	private EntrytypeService entrytypeService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private EntrytypeFactoryForTest entrytypeFactoryForTest = new EntrytypeFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Entrytype> list = new ArrayList<Entrytype>();
		when(entrytypeService.findAll()).thenReturn(list);
		
		// When
		String viewName = entrytypeController.list(model);
		
		// Then
		assertEquals("entrytype/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = entrytypeController.formForCreate(model);
		
		// Then
		assertEquals("entrytype/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Entrytype)modelMap.get("entrytype")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/entrytype/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		Integer id = entrytype.getId();
		when(entrytypeService.findById(id)).thenReturn(entrytype);
		
		// When
		String viewName = entrytypeController.formForUpdate(model, id);
		
		// Then
		assertEquals("entrytype/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrytype, (Entrytype) modelMap.get("entrytype"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/entrytype/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Entrytype entrytypeCreated = new Entrytype();
		when(entrytypeService.create(entrytype)).thenReturn(entrytypeCreated); 
		
		// When
		String viewName = entrytypeController.create(entrytype, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/entrytype/form/"+entrytype.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrytypeCreated, (Entrytype) modelMap.get("entrytype"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = entrytypeController.create(entrytype, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entrytype/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrytype, (Entrytype) modelMap.get("entrytype"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/entrytype/create", modelMap.get("saveAction"));
		
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

		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		
		Exception exception = new RuntimeException("test exception");
		when(entrytypeService.create(entrytype)).thenThrow(exception);
		
		// When
		String viewName = entrytypeController.create(entrytype, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entrytype/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrytype, (Entrytype) modelMap.get("entrytype"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/entrytype/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "entrytype.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		Integer id = entrytype.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Entrytype entrytypeSaved = new Entrytype();
		entrytypeSaved.setId(id);
		when(entrytypeService.update(entrytype)).thenReturn(entrytypeSaved); 
		
		// When
		String viewName = entrytypeController.update(entrytype, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/entrytype/form/"+entrytype.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrytypeSaved, (Entrytype) modelMap.get("entrytype"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = entrytypeController.update(entrytype, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entrytype/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrytype, (Entrytype) modelMap.get("entrytype"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/entrytype/update", modelMap.get("saveAction"));
		
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

		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		
		Exception exception = new RuntimeException("test exception");
		when(entrytypeService.update(entrytype)).thenThrow(exception);
		
		// When
		String viewName = entrytypeController.update(entrytype, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entrytype/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrytype, (Entrytype) modelMap.get("entrytype"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/entrytype/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "entrytype.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		Integer id = entrytype.getId();
		
		// When
		String viewName = entrytypeController.delete(redirectAttributes, id);
		
		// Then
		verify(entrytypeService).delete(id);
		assertEquals("redirect:/entrytype", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Entrytype entrytype = entrytypeFactoryForTest.newEntrytype();
		Integer id = entrytype.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(entrytypeService).delete(id);
		
		// When
		String viewName = entrytypeController.delete(redirectAttributes, id);
		
		// Then
		verify(entrytypeService).delete(id);
		assertEquals("redirect:/entrytype", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "entrytype.error.delete", exception);
	}
	
	
}
