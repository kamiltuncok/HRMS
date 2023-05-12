package kodlamaio.HRMS.api.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kodlamaio.HRMS.business.abstracts.userServices.PhotoService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.Photo;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/photos")
public class PhotosController {

	private PhotoService photoService;

	@Autowired
	public PhotosController(PhotoService photoService) {
		super();
		this.photoService = photoService;
	}
	
	@GetMapping(value = "/getall")
	public DataResult<List<Photo>> getAll(){
		return this.photoService.getAll();
	}
	
	@PostMapping(value="/add")
    public Result add(Photo photo, @RequestParam("file") MultipartFile multipartFile) {
        return photoService.add(photo, multipartFile);
    }
	
	
	
}
