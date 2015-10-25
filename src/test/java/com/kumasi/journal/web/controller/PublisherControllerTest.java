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
import com.kumasi.journal.domain.Publisher;
import com.kumasi.journal.test.PublisherFactoryForTest;

//--- Services 
import com.kumasi.journal.business.service.PublisherService;


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
public class PublisherControllerTest {
	
	@InjectMocks
	private PublisherController publisherController;
	@Mock
	private PublisherService publisherService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private PublisherFactoryForTest publisherFactoryForTest = new PublisherFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Publisher> list = new ArrayList<Publisher>();
		when(publisherService.findAll()).thenReturn(list);
		
		// When
		String viewName = publisherController.list(model);
		
		// Then
		assertEquals("publisher/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = publisherController.formForCreate(model);
		
		// Then
		assertEquals("publisher/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Publisher)modelMap.get("publisher")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/publisher/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Publisher publisher = publisherFactoryForTest.newPublisher();
		Integer id = publisher.getId();
		when(publisherService.findById(id)).thenReturn(publisher);
		
		// When
		String viewName = publisherController.formForUpdate(model, id);
		
		// Then
		assertEquals("publisher/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(publisher, (Publisher) modelMap.get("publisher"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/publisher/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Publisher publisher = publisherFactoryForTest.newPublisher();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Publisher publisherCreated = new Publisher();
		when(publisherService.create(publisher)).thenReturn(publisherCreated); 
		
		// When
		String viewName = publisherController.create(publisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/publisher/form/"+publisher.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(publisherCreated, (Publisher) modelMap.get("publisher"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Publisher publisher = publisherFactoryForTest.newPublisher();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = publisherController.create(publisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("publisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(publisher, (Publisher) modelMap.get("publisher"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/publisher/create", modelMap.get("saveAction"));
		
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

		Publisher publisher = publisherFactoryForTest.newPublisher();
		
		Exception exception = new RuntimeException("test exception");
		when(publisherService.create(publisher)).thenThrow(exception);
		
		// When
		String viewName = publisherController.create(publisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("publisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(publisher, (Publisher) modelMap.get("publisher"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/publisher/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "publisher.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Publisher publisher = publisherFactoryForTest.newPublisher();
		Integer id = publisher.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Publisher publisherSaved = new Publisher();
		publisherSaved.setId(id);
		when(publisherService.update(publisher)).thenReturn(publisherSaved); 
		
		// When
		String viewName = publisherController.update(publisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/publisher/form/"+publisher.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(publisherSaved, (Publisher) modelMap.get("publisher"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Publisher publisher = publisherFactoryForTest.newPublisher();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = publisherController.update(publisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("publisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(publisher, (Publisher) modelMap.get("publisher"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/publisher/update", modelMap.get("saveAction"));
		
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

		Publisher publisher = publisherFactoryForTest.newPublisher();
		
		Exception exception = new RuntimeException("test exception");
		when(publisherService.update(publisher)).thenThrow(exception);
		
		// When
		String viewName = publisherController.update(publisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("publisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(publisher, (Publisher) modelMap.get("publisher"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/publisher/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "publisher.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Publisher publisher = publisherFactoryForTest.newPublisher();
		Integer id = publisher.getId();
		
		// When
		String viewName = publisherController.delete(redirectAttributes, id);
		
		// Then
		verify(publisherService).delete(id);
		assertEquals("redirect:/publisher", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Publisher publisher = publisherFactoryForTest.newPublisher();
		Integer id = publisher.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(publisherService).delete(id);
		
		// When
		String viewName = publisherController.delete(redirectAttributes, id);
		
		// Then
		verify(publisherService).delete(id);
		assertEquals("redirect:/publisher", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "publisher.error.delete", exception);
	}
	
	
}
