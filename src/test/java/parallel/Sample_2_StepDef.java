package parallel;

import com.api.reporting.ReportFactory;
import com.bdd.base.Parallel_BaseClass;
import com.data.Arom_Constants;

import io.cucumber.java.en.Given;

public class Sample_2_StepDef {

	@Given("sample step {int}")
	public void sample_step(Integer int1) {

	}

	@Given("Step is executed in parallel")
	public void step_is_executed_in_parallel() {
		System.out.println("INFO :: Time ="+System.currentTimeMillis()+", Thread Count ="+Thread.activeCount());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Given("Test Patch Request")
	public void test_Patch_Request() {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("MPM_ModifyOrder", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request("C111376","20110113311116");
	}

	@Given("Test Delete Request")
	public void test_Delete_Request() {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("MPM_CancelOrder", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request("C111376");
	}

}
