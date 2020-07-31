package com.omni.reactive;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.omni.reactive.handlers.ContactHandler;
import com.omni.reactive.repository.ContactRepository;
import com.omni.reactive.service.ContactService;

import reactor.ipc.netty.http.server.HttpServer;

public class RestReactiveServer {

	public static final String HOST = "localhost";
	public static final int PORT = 8081;

	public static void main(String[] args) throws InterruptedException, IOException {
		
		RestReactiveServer server = new RestReactiveServer();
		server.startReactorServer();

		System.out.println("Press ENTER to exit.");
		System.in.read();
	}

	public void startReactorServer() throws InterruptedException {
		RouterFunction<ServerResponse> route = routingFunction();
		HttpHandler httpHandler = toHttpHandler(route);

		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.create(HOST, PORT);
		server.newHandler(adapter).block();
	}

	
	public RouterFunction<ServerResponse> routingFunction() {
		ContactRepository repository = new ContactService();
		ContactHandler handler = new ContactHandler(repository);
		
		
		return RouterFunctions.route(GET("/contacts").and(accept(MediaType.APPLICATION_JSON))
				                        ,handler::getAllUsers)
				.andRoute(POST("/contact").and(accept(MediaType.APPLICATION_JSON))
                        ,handler::createContact)
				
				.andRoute(DELETE("/contact/{id}").and(accept(MediaType.APPLICATION_JSON))
						                        ,handler::deleteContact)
				.andRoute(PUT("/contact").and(accept(MediaType.APPLICATION_JSON))
                        ,handler::updateContact)
				
				                .andRoute(GET("/contact/{id}").and(accept(MediaType.APPLICATION_JSON))
				                        ,handler::getUser);

	}
}