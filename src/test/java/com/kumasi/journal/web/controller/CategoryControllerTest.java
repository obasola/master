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
import com.kumasi.journal.domain.Category;
import com.kumasi.journal.test.CategoryFactoryForTest;

//--- Services 
import com.kumasi.journal.business.service.CategoryService;


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
public class CategoryControllerTest {
	
	@InjectMocks
	private CategoryController categoryController;
	@Mock
	private CategoryService categoryService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private CategoryFactoryForTest categoryFactoryForTest = new CategoryFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Category> list = new ArrayList<Category>();
		when(categoryService.findAll()).thenReturn(list);
		
		// When
		String viewName = categoryController.list(model);
		
		// Then
		assertEquals("category/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = categoryController.formForCreate(model);
		
		// Then
		assertEquals("category/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Category)modelMap.get("category")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/category/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Category category = categoryFactoryForTest.newCategory();
		Integer id = category.getId();
		when(categoryService.findById(id)).thenReturn(category);
		
		// When
		String viewName = categoryController.formForUpdate(model, id);
		
		// Then
		assertEquals("category/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(category, (Category) modelMap.get("category"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/category/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Category category = categoryFactoryForTest.newCategory();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Category categoryCreated = new Category();
		when(categoryService.create(category)).thenReturn(categoryCreated); 
		
		// When
		String viewName = categoryController.create(category, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/category/form/"+category.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(categoryCreated, (Category) modelMap.get("category"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Category category = categoryFactoryForTest.newCategory();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = categoryController.create(category, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("category/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(category, (Category) modelMap.get("category"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/category/create", modelMap.get("saveAction"));
		
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

		Category category = categoryFactoryForTest.newCategory();
		
		Exception exception = new RuntimeException("test exception");
		when(categoryService.create(category)).thenThrow(exception);
		
		// When
		String viewName = categoryController.create(category, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("category/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(category, (Category) modelMap.get("category"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/category/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "category.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Category category = categoryFactoryForTest.newCategory();
		Integer id = category.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Category categorySaved = new Category();
		categorySaved.setId(id);
		when(categoryService.update(category)).thenReturn(categorySaved); 
		
		// When
		String viewName = categoryController.update(category, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/category/form/"+category.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(categorySaved, (Category) modelMap.get("category"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Category category = categoryFactoryForTest.newCategory();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = categoryController.update(category, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("category/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(category, (Category) modelMap.get("category"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/category/update", modelMap.get("saveAction"));
		
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

		Category category = categoryFactoryForTest.newCategory();
		
		Exception exception = new RuntimeException("test exception");
		when(categoryService.update(category)).thenThrow(exception);
		
		// When
		String viewName = categoryController.update(category, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("category/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(category, (Category) modelMap.get("category"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/category/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "category.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Category category = categoryFactoryForTest.newCategory();
		Integer id = category.getId();
		
		// When
		String viewName = categoryController.delete(redirectAttributes, id);
		
		// Then
		verify(categoryService).delete(id);
		assertEquals("redirect:/category", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Category category = categoryFactoryForTest.newCategory();
		Integer id = category.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(categoryService).delete(id);
		
		// When
		String viewName = categoryController.delete(redirectAttributes, id);
		
		// Then
		verify(categoryService).delete(id);
		assertEquals("redirect:/category", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "category.error.delete", exception);
	}
	
	
}
