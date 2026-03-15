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
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
