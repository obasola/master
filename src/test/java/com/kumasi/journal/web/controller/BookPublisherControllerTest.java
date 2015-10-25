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
import com.kumasi.journal.domain.BookPublisher;
import com.kumasi.journal.test.BookPublisherFactoryForTest;

//--- Services 
import com.kumasi.journal.business.service.BookPublisherService;


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
public class BookPublisherControllerTest {
	
	@InjectMocks
	private BookPublisherController bookPublisherController;
	@Mock
	private BookPublisherService bookPublisherService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private BookPublisherFactoryForTest bookPublisherFactoryForTest = new BookPublisherFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<BookPublisher> list = new ArrayList<BookPublisher>();
		when(bookPublisherService.findAll()).thenReturn(list);
		
		// When
		String viewName = bookPublisherController.list(model);
		
		// Then
		assertEquals("bookPublisher/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = bookPublisherController.formForCreate(model);
		
		// Then
		assertEquals("bookPublisher/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((BookPublisher)modelMap.get("bookPublisher")).getPublisherId());
		assertNull(((BookPublisher)modelMap.get("bookPublisher")).getBookId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bookPublisher/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		Integer publisherId = bookPublisher.getPublisherId();
		Integer bookId = bookPublisher.getBookId();
		when(bookPublisherService.findById(publisherId, bookId)).thenReturn(bookPublisher);
		
		// When
		String viewName = bookPublisherController.formForUpdate(model, publisherId, bookId);
		
		// Then
		assertEquals("bookPublisher/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookPublisher, (BookPublisher) modelMap.get("bookPublisher"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bookPublisher/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		BookPublisher bookPublisherCreated = new BookPublisher();
		when(bookPublisherService.create(bookPublisher)).thenReturn(bookPublisherCreated); 
		
		// When
		String viewName = bookPublisherController.create(bookPublisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/bookPublisher/form/"+bookPublisher.getPublisherId()+"/"+bookPublisher.getBookId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookPublisherCreated, (BookPublisher) modelMap.get("bookPublisher"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = bookPublisherController.create(bookPublisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookPublisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookPublisher, (BookPublisher) modelMap.get("bookPublisher"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bookPublisher/create", modelMap.get("saveAction"));
		
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

		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		
		Exception exception = new RuntimeException("test exception");
		when(bookPublisherService.create(bookPublisher)).thenThrow(exception);
		
		// When
		String viewName = bookPublisherController.create(bookPublisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookPublisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookPublisher, (BookPublisher) modelMap.get("bookPublisher"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bookPublisher/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "bookPublisher.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		Integer publisherId = bookPublisher.getPublisherId();
		Integer bookId = bookPublisher.getBookId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		BookPublisher bookPublisherSaved = new BookPublisher();
		bookPublisherSaved.setPublisherId(publisherId);
		bookPublisherSaved.setBookId(bookId);
		when(bookPublisherService.update(bookPublisher)).thenReturn(bookPublisherSaved); 
		
		// When
		String viewName = bookPublisherController.update(bookPublisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/bookPublisher/form/"+bookPublisher.getPublisherId()+"/"+bookPublisher.getBookId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookPublisherSaved, (BookPublisher) modelMap.get("bookPublisher"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = bookPublisherController.update(bookPublisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookPublisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookPublisher, (BookPublisher) modelMap.get("bookPublisher"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bookPublisher/update", modelMap.get("saveAction"));
		
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

		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		
		Exception exception = new RuntimeException("test exception");
		when(bookPublisherService.update(bookPublisher)).thenThrow(exception);
		
		// When
		String viewName = bookPublisherController.update(bookPublisher, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookPublisher/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookPublisher, (BookPublisher) modelMap.get("bookPublisher"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bookPublisher/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "bookPublisher.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		Integer publisherId = bookPublisher.getPublisherId();
		Integer bookId = bookPublisher.getBookId();
		
		// When
		String viewName = bookPublisherController.delete(redirectAttributes, publisherId, bookId);
		
		// Then
		verify(bookPublisherService).delete(publisherId, bookId);
		assertEquals("redirect:/bookPublisher", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		Integer publisherId = bookPublisher.getPublisherId();
		Integer bookId = bookPublisher.getBookId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(bookPublisherService).delete(publisherId, bookId);
		
		// When
		String viewName = bookPublisherController.delete(redirectAttributes, publisherId, bookId);
		
		// Then
		verify(bookPublisherService).delete(publisherId, bookId);
		assertEquals("redirect:/bookPublisher", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "bookPublisher.error.delete", exception);
	}
	
	
}
