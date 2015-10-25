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
import com.kumasi.journal.domain.BookCategory;
import com.kumasi.journal.test.BookCategoryFactoryForTest;

//--- Services 
import com.kumasi.journal.business.service.BookCategoryService;


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
public class BookCategoryControllerTest {
	
	@InjectMocks
	private BookCategoryController bookCategoryController;
	@Mock
	private BookCategoryService bookCategoryService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private BookCategoryFactoryForTest bookCategoryFactoryForTest = new BookCategoryFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<BookCategory> list = new ArrayList<BookCategory>();
		when(bookCategoryService.findAll()).thenReturn(list);
		
		// When
		String viewName = bookCategoryController.list(model);
		
		// Then
		assertEquals("bookCategory/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = bookCategoryController.formForCreate(model);
		
		// Then
		assertEquals("bookCategory/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((BookCategory)modelMap.get("bookCategory")).getBookId());
		assertNull(((BookCategory)modelMap.get("bookCategory")).getCategoryId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bookCategory/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		Integer bookId = bookCategory.getBookId();
		Integer categoryId = bookCategory.getCategoryId();
		when(bookCategoryService.findById(bookId, categoryId)).thenReturn(bookCategory);
		
		// When
		String viewName = bookCategoryController.formForUpdate(model, bookId, categoryId);
		
		// Then
		assertEquals("bookCategory/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookCategory, (BookCategory) modelMap.get("bookCategory"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bookCategory/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		BookCategory bookCategoryCreated = new BookCategory();
		when(bookCategoryService.create(bookCategory)).thenReturn(bookCategoryCreated); 
		
		// When
		String viewName = bookCategoryController.create(bookCategory, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/bookCategory/form/"+bookCategory.getBookId()+"/"+bookCategory.getCategoryId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookCategoryCreated, (BookCategory) modelMap.get("bookCategory"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = bookCategoryController.create(bookCategory, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookCategory/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookCategory, (BookCategory) modelMap.get("bookCategory"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bookCategory/create", modelMap.get("saveAction"));
		
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

		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		
		Exception exception = new RuntimeException("test exception");
		when(bookCategoryService.create(bookCategory)).thenThrow(exception);
		
		// When
		String viewName = bookCategoryController.create(bookCategory, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookCategory/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookCategory, (BookCategory) modelMap.get("bookCategory"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bookCategory/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "bookCategory.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		Integer bookId = bookCategory.getBookId();
		Integer categoryId = bookCategory.getCategoryId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		BookCategory bookCategorySaved = new BookCategory();
		bookCategorySaved.setBookId(bookId);
		bookCategorySaved.setCategoryId(categoryId);
		when(bookCategoryService.update(bookCategory)).thenReturn(bookCategorySaved); 
		
		// When
		String viewName = bookCategoryController.update(bookCategory, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/bookCategory/form/"+bookCategory.getBookId()+"/"+bookCategory.getCategoryId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookCategorySaved, (BookCategory) modelMap.get("bookCategory"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = bookCategoryController.update(bookCategory, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookCategory/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookCategory, (BookCategory) modelMap.get("bookCategory"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bookCategory/update", modelMap.get("saveAction"));
		
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

		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		
		Exception exception = new RuntimeException("test exception");
		when(bookCategoryService.update(bookCategory)).thenThrow(exception);
		
		// When
		String viewName = bookCategoryController.update(bookCategory, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bookCategory/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bookCategory, (BookCategory) modelMap.get("bookCategory"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bookCategory/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "bookCategory.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		Integer bookId = bookCategory.getBookId();
		Integer categoryId = bookCategory.getCategoryId();
		
		// When
		String viewName = bookCategoryController.delete(redirectAttributes, bookId, categoryId);
		
		// Then
		verify(bookCategoryService).delete(bookId, categoryId);
		assertEquals("redirect:/bookCategory", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		BookCategory bookCategory = bookCategoryFactoryForTest.newBookCategory();
		Integer bookId = bookCategory.getBookId();
		Integer categoryId = bookCategory.getCategoryId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(bookCategoryService).delete(bookId, categoryId);
		
		// When
		String viewName = bookCategoryController.delete(redirectAttributes, bookId, categoryId);
		
		// Then
		verify(bookCategoryService).delete(bookId, categoryId);
		assertEquals("redirect:/bookCategory", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "bookCategory.error.delete", exception);
	}
	
	
}
