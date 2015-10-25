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
import com.kumasi.journal.domain.Entry;
import com.kumasi.journal.domain.Entrytype;
import com.kumasi.journal.test.EntryFactoryForTest;
import com.kumasi.journal.test.EntrytypeFactoryForTest;

//--- Services 
import com.kumasi.journal.business.service.EntryService;
import com.kumasi.journal.business.service.EntrytypeService;

//--- List Items 
import com.kumasi.journal.web.listitem.EntrytypeListItem;

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
public class EntryControllerTest {
	
	@InjectMocks
	private EntryController entryController;
	@Mock
	private EntryService entryService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private EntrytypeService entrytypeService; // Injected by Spring

	private EntryFactoryForTest entryFactoryForTest = new EntryFactoryForTest();
	private EntrytypeFactoryForTest entrytypeFactoryForTest = new EntrytypeFactoryForTest();

	List<Entrytype> entrytypes = new ArrayList<Entrytype>();

	private void givenPopulateModel() {
		Entrytype entrytype1 = entrytypeFactoryForTest.newEntrytype();
		Entrytype entrytype2 = entrytypeFactoryForTest.newEntrytype();
		List<Entrytype> entrytypes = new ArrayList<Entrytype>();
		entrytypes.add(entrytype1);
		entrytypes.add(entrytype2);
		when(entrytypeService.findAll()).thenReturn(entrytypes);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Entry> list = new ArrayList<Entry>();
		when(entryService.findAll()).thenReturn(list);
		
		// When
		String viewName = entryController.list(model);
		
		// Then
		assertEquals("entry/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = entryController.formForCreate(model);
		
		// Then
		assertEquals("entry/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Entry)modelMap.get("entry")).getIdentry());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/entry/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<EntrytypeListItem> entrytypeListItems = (List<EntrytypeListItem>) modelMap.get("listOfEntrytypeItems");
		assertEquals(2, entrytypeListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Entry entry = entryFactoryForTest.newEntry();
		Integer identry = entry.getIdentry();
		when(entryService.findById(identry)).thenReturn(entry);
		
		// When
		String viewName = entryController.formForUpdate(model, identry);
		
		// Then
		assertEquals("entry/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entry, (Entry) modelMap.get("entry"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/entry/update", modelMap.get("saveAction"));
		
		List<EntrytypeListItem> entrytypeListItems = (List<EntrytypeListItem>) modelMap.get("listOfEntrytypeItems");
		assertEquals(2, entrytypeListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Entry entry = entryFactoryForTest.newEntry();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Entry entryCreated = new Entry();
		when(entryService.create(entry)).thenReturn(entryCreated); 
		
		// When
		String viewName = entryController.create(entry, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/entry/form/"+entry.getIdentry(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entryCreated, (Entry) modelMap.get("entry"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Entry entry = entryFactoryForTest.newEntry();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = entryController.create(entry, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entry/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entry, (Entry) modelMap.get("entry"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/entry/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<EntrytypeListItem> entrytypeListItems = (List<EntrytypeListItem>) modelMap.get("listOfEntrytypeItems");
		assertEquals(2, entrytypeListItems.size());
		
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

		Entry entry = entryFactoryForTest.newEntry();
		
		Exception exception = new RuntimeException("test exception");
		when(entryService.create(entry)).thenThrow(exception);
		
		// When
		String viewName = entryController.create(entry, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entry/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entry, (Entry) modelMap.get("entry"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/entry/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "entry.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<EntrytypeListItem> entrytypeListItems = (List<EntrytypeListItem>) modelMap.get("listOfEntrytypeItems");
		assertEquals(2, entrytypeListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Entry entry = entryFactoryForTest.newEntry();
		Integer identry = entry.getIdentry();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Entry entrySaved = new Entry();
		entrySaved.setIdentry(identry);
		when(entryService.update(entry)).thenReturn(entrySaved); 
		
		// When
		String viewName = entryController.update(entry, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/entry/form/"+entry.getIdentry(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entrySaved, (Entry) modelMap.get("entry"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Entry entry = entryFactoryForTest.newEntry();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = entryController.update(entry, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entry/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entry, (Entry) modelMap.get("entry"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/entry/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<EntrytypeListItem> entrytypeListItems = (List<EntrytypeListItem>) modelMap.get("listOfEntrytypeItems");
		assertEquals(2, entrytypeListItems.size());
		
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

		Entry entry = entryFactoryForTest.newEntry();
		
		Exception exception = new RuntimeException("test exception");
		when(entryService.update(entry)).thenThrow(exception);
		
		// When
		String viewName = entryController.update(entry, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("entry/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(entry, (Entry) modelMap.get("entry"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/entry/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "entry.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<EntrytypeListItem> entrytypeListItems = (List<EntrytypeListItem>) modelMap.get("listOfEntrytypeItems");
		assertEquals(2, entrytypeListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Entry entry = entryFactoryForTest.newEntry();
		Integer identry = entry.getIdentry();
		
		// When
		String viewName = entryController.delete(redirectAttributes, identry);
		
		// Then
		verify(entryService).delete(identry);
		assertEquals("redirect:/entry", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Entry entry = entryFactoryForTest.newEntry();
		Integer identry = entry.getIdentry();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(entryService).delete(identry);
		
		// When
		String viewName = entryController.delete(redirectAttributes, identry);
		
		// Then
		verify(entryService).delete(identry);
		assertEquals("redirect:/entry", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "entry.error.delete", exception);
	}
	
	
}
