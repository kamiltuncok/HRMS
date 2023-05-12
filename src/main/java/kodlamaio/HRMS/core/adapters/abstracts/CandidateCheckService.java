package kodlamaio.HRMS.core.adapters.abstracts;

import java.rmi.RemoteException;

import kodlamaio.HRMS.entities.concretes.users.Candidate;

public interface CandidateCheckService {
	public boolean CheckIfRealCandidate(Candidate candidate) throws NumberFormatException, RemoteException;
}
