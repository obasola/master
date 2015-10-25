/*
 * Created on 24 Oct 2015 ( Time 23:23:56 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//--- Common classes
import com.kumasi.journal.web.common.AbstractController;
import com.kumasi.journal.web.common.FormMode;
import com.kumasi.journal.web.common.Message;
import com.kumasi.journal.web.common.MessageType;

//--- Entities
import com.kumasi.journal.domain.Book;
import com.kumasi.journal.domain.Publisher;
import com.kumasi.journal.domain.Category;

//--- Services 
import com.kumasi.journal.business.service.BookService;
import com.kumasi.journal.business.service.PublisherService;
import com.kumasi.journal.business.service.CategoryService;

//--- List Items 
import com.kumasi.journal.web.listitem.PublisherListItem;
import com.kumasi.journal.web.listitem.CategoryListItem;

/**
 * Spring MVC controller for 'Book' management.
 */
@Controller
@RequestMapping("/book")
public class BookController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "book";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "book/form";
	private static final String JSP_LIST   = "book/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/book/create";
	private static final String SAVE_ACTION_UPDATE   = "/book/update";

	//--- Main entity service
	@Resource
    private BookService bookService; // Injected by Spring
	//--- Other service(s)
	@Resource
    private PublisherService publisherService; // Injected by Spring
	@Resource
    private CategoryService categoryService; // Injected by Spring

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public BookController() {
		super(BookController.class, MAIN_ENTITY_NAME );
		log("BookController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------
	/**
	 * Populates the combo-box "items" for the referenced entity "Publisher"
	 * @param model
	 */
	private void populateListOfPublisherItems(Model model) {
		List<Publisher> list = publisherService.findAll();
		List<PublisherListItem> items = new LinkedList<PublisherListItem>();
		for ( Publisher publisher : list ) {
			items.add(new PublisherListItem( publisher ) );
		}
		model.addAttribute("listOfPublisherItems", items ) ;
	}

	/**
	 * Populates the combo-box "items" for the referenced entity "Category"
	 * @param model
	 */
	private void populateListOfCategoryItems(Model model) {
		List<Category> list = categoryService.findAll();
		List<CategoryListItem> items = new LinkedList<CategoryListItem>();
		for ( Category category : list ) {
			items.add(new CategoryListItem( category ) );
		}
		model.addAttribute("listOfCategoryItems", items ) ;
	}


	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param book
	 */
	private void populateModel(Model model, Book book, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, book);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
			populateListOfPublisherItems(model);
			populateListOfCategoryItems(model);
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of Book found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<Book> list = bookService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new Book
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		Book book = new Book();	
		populateModel( model, book, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing Book
	 * @param model Spring MVC model
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{id}")
	public String formForUpdate(Model model, @PathVariable("id") Integer id ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		Book book = bookService.findById(id);
		populateModel( model, book, FormMode.UPDATE);		
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param book  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				Book bookCreated = bookService.create(book); 
				model.addAttribute(MAIN_ENTITY_NAME, bookCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, book.getId() );
			} else {
				populateModel( model, book, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "book.error.create", e);
			populateModel( model, book, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param book  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				Book bookSaved = bookService.update(book);
				model.addAttribute(MAIN_ENTITY_NAME, bookSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, book.getId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, book, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "book.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, book, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
		log("Action 'delete'" );
		try {
			bookService.delete( id );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "book.error.delete", e);
		}
		return redirectToList();
	}

}
