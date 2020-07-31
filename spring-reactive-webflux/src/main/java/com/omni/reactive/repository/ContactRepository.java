package com.omni.reactive.repository;

import com.omni.reactive.model.Contact;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContactRepository {

	Flux<Contact> getAllContacts();
	Mono<Contact> getContact(Integer id);
	Mono<Void> deleteContact(Integer id);
	Mono<Void> saveContact(Mono<Contact> contact);
	Mono<Void> updateContact(Mono<Contact> contactMono);
	
 }