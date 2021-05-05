package unisa.dse.a1.students;

public class UntradedCompanyException extends Exception {
	public UntradedCompanyException(String companyCode) {
		super(companyCode + " is not a listed company on this exchange");
	}
}
