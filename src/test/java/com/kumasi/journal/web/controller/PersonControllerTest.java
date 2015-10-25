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
import com.kumasi.journal.domain.Person;
import com.kumasi.journal.test.PersonFactoryForTest;

//--- Services 
import com.kumasi.journal.business.service.PersonService;


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
public class PersonControllerTest {
	
	@InjectMocks
	private PersonController personController;
	@Mock
	private PersonService personService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private PersonFactoryForTest personFactoryForTest = new PersonFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Person> list = new ArrayList<Person>();
		when(personService.findAll()).thenReturn(list);
		
		// When
		String viewName = personController.list(model);
		
		// Then
		assertEquals("person/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = personController.formForCreate(model);
		
		// Then
		assertEquals("person/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Person)modelMap.get("person")).getIdperson());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/person/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Person person = personFactoryForTest.newPerson();
		Integer idperson = person.getIdperson();
		when(personService.findById(idperson)).thenReturn(person);
		
		// When
		String viewName = personController.formForUpdate(model, idperson);
		
		// Then
		assertEquals("person/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(person, (Person) modelMap.get("person"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/person/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Person person = personFactoryForTest.newPerson();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Person personCreated = new Person();
		when(personService.create(person)).thenReturn(personCreated); 
		
		// When
		String viewName = personController.create(person, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/person/form/"+person.getIdperson(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(personCreated, (Person) modelMap.get("person"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Person person = personFactoryForTest.newPerson();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = personController.create(person, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("person/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(person, (Person) modelMap.get("person"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/person/create", modelMap.get("saveAction"));
		
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

		Person person = personFactoryForTest.newPerson();
		
		Exception exception = new RuntimeException("test exception");
		when(personService.create(person)).thenThrow(exception);
		
		// When
		String viewName = personController.create(person, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("person/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(person, (Person) modelMap.get("person"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/person/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "person.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Person person = personFactoryForTest.newPerson();
		Integer idperson = person.getIdperson();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Person personSaved = new Person();
		personSaved.setIdperson(idperson);
		when(personService.update(person)).thenReturn(personSaved); 
		
		// When
		String viewName = personController.update(person, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/person/form/"+person.getIdperson(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(personSaved, (Person) modelMap.get("person"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Person person = personFactoryForTest.newPerson();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = personController.update(person, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("person/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(person, (Person) modelMap.get("person"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/person/update", modelMap.get("saveAction"));
		
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

		Person person = personFactoryForTest.newPerson();
		
		Exception exception = new RuntimeException("test exception");
		when(personService.update(person)).thenThrow(exception);
		
		// When
		String viewName = personController.update(person, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("person/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(person, (Person) modelMap.get("person"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/person/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "person.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Person person = personFactoryForTest.newPerson();
		Integer idperson = person.getIdperson();
		
		// When
		String viewName = personController.delete(redirectAttributes, idperson);
		
		// Then
		verify(personService).delete(idperson);
		assertEquals("redirect:/person", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Person person = personFactoryForTest.newPerson();
		Integer idperson = person.getIdperson();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(personService).delete(idperson);
		
		// When
		String viewName = personController.delete(redirectAttributes, idperson);
		
		// Then
		verify(personService).delete(idperson);
		assertEquals("redirect:/person", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "person.error.delete", exception);
	}
	
	
}
