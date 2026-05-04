package kodlamaio.HRMS.controller;

import java.util.concurrent.Callable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class BaseController {
	
	public <T> ResponseEntity<?> Ok(Callable<T> call) {
		try {
			T t = call.call();
			return ResponseEntity.ok(t);
		} catch (RuntimeException e) {
			// Let RuntimeExceptions propagate to GlobalExceptionHandler
			throw e;
		} catch (Exception e) {
			// Wrap checked exceptions as RuntimeException so they also reach the handler
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}

