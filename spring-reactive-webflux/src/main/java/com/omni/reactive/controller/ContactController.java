package com.omni.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.omni.reactive.model.Contact;
import com.omni.reactive.service.ContactService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contacts")
	public Flux<Contact> getAllContacts() {
		return contactService.getAllContacts();
	}

	@GetMapping("/contact/{id}")
	public Mono<Contact> getContact(@PathVariable Integer id) {
		return contactService.getContact(id);
	}
	
	@DeleteMapping("/contact/{id}")
	public Mono<Void> delete(@PathVariable int id) {
		return contactService.deleteContact(id);
	}
	
	@PostMapping("/contact")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Void> createContact(@RequestBody Mono<Contact> contactMono) {
		return contactService.saveContact(contactMono);	
	}
	
	@PutMapping("contact")
	public Mono<Void> updateProduct(@RequestBody Mono<Contact> contactMono){
		return contactService.updateContact(contactMono);
	}
	
}
