package kodlamaio.HRMS.api.controllers;









import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.business.abstracts.userServices.CandidateService;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.users.Candidate;


@RestController
@RequestMapping("/api/candidates")
@CrossOrigin
public class CandidatesController{
	private CandidateService candidateService;

	@Autowired
	public CandidatesController(CandidateService candidateService) {
		super();
		this.candidateService = candidateService;

	}


	@PostMapping("/save")
	public Result save(@RequestBody Candidate candidate) throws Exception {

		return candidateService.save(candidate);
	}

	
}
