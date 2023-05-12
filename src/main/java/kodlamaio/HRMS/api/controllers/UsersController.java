package kodlamaio.HRMS.api.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.business.abstracts.userServices.UserService;
import kodlamaio.HRMS.entities.concretes.users.User;

@RequestMapping("/api/users")
@RestController
@CrossOrigin
public class UsersController extends BaseController{
	
	private UserService userService;
	
	@Autowired
	public UsersController(UserService userService) {
		super();
		this.userService = userService;
	}
	@GetMapping("/getall")
	public ResponseEntity<?>  getAll(){
		 return Ok(()->this.userService.getAll());
	}
	
	@PostMapping("/add")
	public ResponseEntity<?>  add(@RequestBody User user) {
		return Ok(()->this.userService.add(user));
	}
	
	@GetMapping("/getuser")
	public ResponseEntity<?> getById(int userId){
		return Ok(()->this.userService.getUserById(userId));
	}
	
}
