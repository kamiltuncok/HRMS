package kodlamaio.HRMS.core.adapters.concretes;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.adapters.abstracts.CandidateCheckService;
import kodlamaio.HRMS.entities.concretes.users.Candidate;

@Service
public class CandidateCheckManager implements CandidateCheckService {
	@Override
	public boolean CheckIfRealCandidate(Candidate candidate) throws NumberFormatException, RemoteException {

		return true;
	}
}
