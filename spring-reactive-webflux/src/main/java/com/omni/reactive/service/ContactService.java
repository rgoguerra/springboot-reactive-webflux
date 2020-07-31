package com.omni.reactive.service;

import java.util.HashMap;
import java.util.Map;

import com.omni.reactive.model.Contact;
import com.omni.reactive.repository.ContactRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ContactService implements ContactRepository {

	// initiate Users
	//private Map<Integer, User> users = null;
	private final Map<Integer, Contact> contacts = new HashMap<>();

	// fill dummy values for testing
	public ContactService() {
		
		
		// Java 9 Immutable map used
		/*
		users = Map.of(
				1, (new User(1, "David")),
				2, (new User(2, "John")),
				3, (new User(3, "Kevin"))
		);		
		*/
		
		this.contacts.put(100, new Contact(100, "David"));
	    this.contacts.put(101, new Contact(101, "John"));
	    this.contacts.put(102, new Contact(102, "Kevin"));
	    this.contacts.put(103, new Contact(103, "Tony"));
	    this.contacts.put(104, new Contact(104, "David"));
	    this.contacts.put(105, new Contact(105, "Katy"));
	    
	    
	    
	}


	@Override
	public Flux<Contact> getAllContacts() {
		return Flux.fromIterable(this.contacts.values());
	}
	
	@Override
	public Mono<Contact> getContact(Integer id){
	    return Mono.justOrEmpty(this.contacts.get(id)); 
	}


	@Override
	public Mono<Void> deleteContact(Integer id) {
		 this.contacts.remove(id);
		return Mono.empty();
	}


	@Override
	public Mono<Void> saveContact(Mono<Contact> contactMono) {
		
		return contactMono.doOnNext(contact -> { 
			this.contacts.put(contact.getContactid() , contact);
		      System.out.format("Saved %s with id %d%n", contact, contact.getContactid() );
		    }).thenEmpty(Mono.empty());
	}


	@Override
	public Mono<Void> updateContact(Mono<Contact> contactMono) {
		return contactMono.doOnNext(contact -> { 
		      this.contacts.put(contact.getContactid(), contact);
		      System.out.format("Saved %s with id %d%n", contact, contact.getContactid() );
		    }).thenEmpty(Mono.empty());
	}
	
	
}