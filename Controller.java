package com.hackerrank.github.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.github.entity.EventResult;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.GithubApiService;
import com.hackerrank.github.utils.EventResponse;

@RestController
public class GithubApiRestController {
	@Autowired
	GithubApiService githubApiService;

	@DeleteMapping(value = "/erase")
	public ResponseEntity<?> deleteEvent() {
		this.githubApiService.deleteEvent();
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping(value = "events")
	public ResponseEntity<HttpStatus> addEvent(@RequestBody Event event) {
		EventResponse eventReponse = githubApiService.addEvent(event);

		if (eventReponse == EventResponse.EVENT_CREATED)
			return ResponseEntity.status(HttpStatus.CREATED).build();
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping(value = "/events/actors/{actorID}", produces = "application/json")
	public ResponseEntity<?> getEventsByActor(@PathVariable(name = "actorID", required = true) Long actorId) {
		if (actorId == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		EventResult<List<Event>> result = this.githubApiService.getEventsByActor(actorId);

		if (result.getResponse() == EventResponse.ACTOR_DOES_NOT_EXIST) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result.getData());
	}
	
	@PutMapping(value="/actors")
	public ResponseEntity<?> updateAvatarUrl(@RequestBody Actor actor) {
		EventResult<EventResponse> eventResult = this.githubApiService.up
		return ResponseEntity.status(HttpStatus.OK).body(result.getData());
	}
}
