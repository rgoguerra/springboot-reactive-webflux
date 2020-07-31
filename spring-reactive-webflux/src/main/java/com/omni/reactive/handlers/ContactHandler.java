package com.omni.reactive.handlers;


import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.omni.reactive.model.Contact;
import com.omni.reactive.repository.ContactRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import reactor.core.publisher.Flux;


@Component
public class ContactHandler {

	private final ContactRepository contactRepository;

	public ContactHandler(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
	
	public Mono<ServerResponse> getAllUsers(ServerRequest request) {
		Flux<Contact> contacts = this.contactRepository.getAllContacts();		
		
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(contacts, Contact.class);
	}
	
	
	public Mono<ServerResponse> getUser(ServerRequest request){
	    int contactId = Integer.valueOf(request.pathVariable("id"));
	    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	    Mono<Contact> contactMono = this.contactRepository.getContact(contactId);
	    return contactMono
	        .flatMap(contact -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(contact)))
	        .switchIfEmpty(notFound); 
	}

	public Mono<ServerResponse> deleteContact(ServerRequest serverRequest) {
		int userId = Integer.valueOf(serverRequest.pathVariable("id"));
		return ServerResponse.ok().build(this.contactRepository.deleteContact(userId));

	}
	
	
	public Mono<ServerResponse> createContact(ServerRequest request) {
	    Mono<Contact> contact = request.bodyToMono(Contact.class);
	    return ServerResponse.ok().build(this.contactRepository.saveContact(contact));
	}
	
	
	public Mono<ServerResponse> updateContact(ServerRequest request) {
	    Mono<Contact> contact = request.bodyToMono(Contact.class);
	    return ServerResponse.ok().build(this.contactRepository.updateContact(contact) );
	}
}