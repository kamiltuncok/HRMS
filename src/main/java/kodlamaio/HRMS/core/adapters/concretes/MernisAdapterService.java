package kodlamaio.HRMS.core.adapters.concretes;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.adapters.abstracts.CandidateCheckService;
import kodlamaio.HRMS.entities.concretes.users.Candidate;
import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisAdapterService implements CandidateCheckService {
	@Override
	public boolean CheckIfRealCandidate(Candidate candidate) throws NumberFormatException, RemoteException {
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();

		return kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(candidate.getIdentityNumber()),
				candidate.getFirstName(), candidate.getLastName(), candidate.getBirthYear());
	}
}
