package kodlamaio.HRMS.core.business;

import kodlamaio.HRMS.core.utilities.results.Result;

public class BusinessRules {
	
	public static Result Run(Result...results) {
		
		for(Result logic:results) {
			if(!logic.isSuccess()) {
				return logic;
			}
		}
		return null;
	}
	
}
