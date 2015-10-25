/*
 * Created on 24 Oct 2015 ( Time 23:20:28 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.journal.domain.BookPublisher;
import com.kumasi.journal.domain.jpa.BookPublisherEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class BookPublisherServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public BookPublisherServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'BookPublisherEntity' to 'BookPublisher'
	 * @param bookPublisherEntity
	 */
	public BookPublisher mapBookPublisherEntityToBookPublisher(BookPublisherEntity bookPublisherEntity) {
		if(bookPublisherEntity == null) {
			return null;
		}

		//--- Generic mapping 
		BookPublisher bookPublisher = map(bookPublisherEntity, BookPublisher.class);

		return bookPublisher;
	}
	
	/**
	 * Mapping from 'BookPublisher' to 'BookPublisherEntity'
	 * @param bookPublisher
	 * @param bookPublisherEntity
	 */
	public void mapBookPublisherToBookPublisherEntity(BookPublisher bookPublisher, BookPublisherEntity bookPublisherEntity) {
		if(bookPublisher == null) {
			return;
		}

		//--- Generic mapping 
		map(bookPublisher, bookPublisherEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}