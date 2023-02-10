package parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.api.payloads.Position;
import com.api.payloads.SpanCalculateMargin;
import com.api.reporting.ReportFactory;
import com.api.utilities.ExcelUtil;
import com.bdd.base.Parallel_BaseClass;
import com.data.Arom_Constants;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONArray;

public class SPAN_Calculator_StepDef {

	@When("Application calls the Span Calculator Get Exchange API")
	public void application_calls_the_Span_Calculator_Get_Exchange_API() {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_GetExchange", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request();
	}

	@When("Application calls the Span Calculator Get Exchange Product API for exchange {string}")
	public void application_calls_the_Span_Calculator_Get_Exchange_Product_API_for_exchange(String exchange) {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_GetExchangeProduct", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request(exchange);
	}

	@When("Application calls the Span Calculator Get Product Contrat API for exchange {string} and product {string}")
	public void application_calls_the_Span_Calculator_Get_Product_Contrat_API_for_exchange_and_product(String exchange, String product) {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_GetProductContract", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request(exchange,product);
	}

	@Then("Contract symbol contains symbol and expiry date seperated by -")
	public void contract_symbol_contains_symbol_and_expiry_date_seperated_by() {
		JSONArray array = (JSONArray) Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.contract.*.symbol");
		int mismatch = 0;
		//String regex = "^[A-Z0-9]+-\\d{1,2}[A-Z]{3}\\d{2}";
		//String regex =  "^([A-Z0-9]+-)+\\d{1,2}[A-Z]{3}\\d{2}";
		String regex = ".+-\\d{1,2}[A-Z]{3}\\d{2}$";
		Pattern p1  = Pattern.compile(regex);
		for(Object o:array) {
			String symbol = (String) o;
			if(!p1.matcher(symbol).matches()) {
				mismatch++;
			}
		}
		if(mismatch==0) {
			ReportFactory.PassTest("SUCCESS :: Symbol name contains the symbol and expiry date in the expected format for all instances ");
		}else {
			ReportFactory.FailTest("FAILURE :: Symbol name does not contain the symbol and expiry date in the expected format for all instances["+mismatch+" not matched]");
		}
	}

	@When("Application calls the Span Calculator Get Strike Price API for exchange {string} and product {string} and symbol {string} and option type {string}")
	public void application_calls_the_Span_Calculator_Get_Strike_Price_API_for_exchange_and_product_and_symbol_and_option_type(String exchange, String product, String symbol, String optionType) {
		if(Arom_Constants.property_Map.keySet().contains(symbol)) {
			symbol=Arom_Constants.property_Map.get(symbol);
		}
		Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_GetStrikePrice", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request(exchange,product,symbol,optionType);
	}

	@Given("Positions {string} are fetched from row {int} in file {string}")
	public void positions_are_fetched_from_row_in_file(String positions, Integer dataRow, String filePath) {
		ExcelUtil data = new ExcelUtil(filePath);
		data.setAvtiveSheet("Positions");
		SpanCalculateMargin payload = new SpanCalculateMargin();
		List<Position> positionList = new ArrayList<Position>();
		try {
			for(String p:positions.split(",")) {
				Position pos = new Position();
				pos.setContract(data.getParam(p+"_contract", dataRow));
				pos.setExchange(data.getParam(p+"_exchange", dataRow));
				pos.setOptionType(data.getParam(p+"_optionType", dataRow));
				pos.setProduct(data.getParam(p+"_product", dataRow));
				pos.setQty(Integer.valueOf(data.getParam(p+"_qty", dataRow)));
				pos.setStrikePrice(Double.valueOf(String.valueOf(data.getParam(p+"_strikePrice", dataRow))));
				pos.setTradeType(data.getParam(p+"_tradeType", dataRow));
				positionList.add(pos);
			}
			payload.setPosition(positionList);
			Constants.getAPIConstants().CalculateMarginPayload = payload;
		} catch (NumberFormatException e) {
			ReportFactory.FailTest("FAILURE :: exception while reading data for payload");
			e.printStackTrace();
		}
	}

	@When("Application calls the Margin Calculator API")
	public void application_calls_the_Margin_Calculator_API() {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_MarginCalculator", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().setAPIRequestBody(Constants.getAPIConstants().CalculateMarginPayload);
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request();
	}

	@When("Application calls the Span Calculator Get App State API")
	public void application_calls_the_Span_Calculator_Get_App_State_API() {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_AppState", new Arom_Constants());
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request();
	}

	@Then("totalPositionMargin is the sum of all totalMargin in postionMargin")
	public void totalpositionmargin_is_the_sum_of_all_totalMargin_in_psotionMargin() {
		Object jo = Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.totalPositionMargin");
		double totalPositionMargin=0.0;
		if(jo instanceof Double) {
			totalPositionMargin = (Double) Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.totalPositionMargin");
		}else {
			totalPositionMargin = ((Integer)Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.totalPositionMargin"))*1.0;
		}
		JSONArray array = (JSONArray) Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.positionMargin.*.totalMargin");
		Double sumTotalMargin=0.0;
		for(Object o: array) {
			double totalMargin=0.0;
			if(o instanceof Double){
				totalMargin = (Double) o;
			}else {
				totalMargin = ((Integer)o)*1.0;
			}
			sumTotalMargin+=totalMargin;;
		}
		if(totalPositionMargin==sumTotalMargin) {
			ReportFactory.PassTest("SUCCESS :: totalPositionMargin is "+totalPositionMargin+" [Expected = "+sumTotalMargin+"]");
		}else {
			ReportFactory.FailTest("FAILURE :: totalPositionMargin is "+totalPositionMargin+" [Expected = "+sumTotalMargin+"]");
		}
	}

	@Given("Valid symbol {string} is set as property for exchange {string}, product {string}")
	public void valid_symbol_is_set_as_property_for_exchange_product(String propertName, String exchange, String product) {
		Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_GetProductContract", new Arom_Constants(),true);
		Parallel_BaseClass.getAPIBaseClass().Send_API_Request(exchange,product);
		String propertyValue = String.valueOf(Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.contract[0].symbol"));
		if(Constants.getAPIConstants().property_Map.keySet().contains("{{"+propertName+"}}")) {
			Constants.getAPIConstants().property_Map.remove("{{"+propertName+"}}");
		}
		Constants.getAPIConstants().property_Map.put("{{"+propertName+"}}", propertyValue);

		ExcelUtil data = new ExcelUtil("src/test/resources/Margin_Calculator_Data.xlsx");
		data.setAvtiveSheet("Symbols");
		data.setParam(exchange+"_"+product, 1, propertyValue);
		ReportFactory.PassTest("INFO :: property {{"+propertName+"}} : "+propertyValue);

		if(product.equalsIgnoreCase("OPTION")) {
			//get current strike price -CALL
			Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_GetStrikePrice", new Arom_Constants(),true);
			Parallel_BaseClass.getAPIBaseClass().Send_API_Request(exchange,product,propertyValue,"CALL");
			Double strikepricecall = Double.valueOf(String.valueOf(Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.strikePrice[0]")));
			data.setParam(exchange+"_"+product, 2, strikepricecall.doubleValue());
			ReportFactory.PassTest("INFO :: CALL Strike Price  : "+strikepricecall.doubleValue());

			//get current strike price -PUT
			Parallel_BaseClass.getAPIBaseClass().API_Setup("SPAN_GetStrikePrice", new Arom_Constants(),true);
			Parallel_BaseClass.getAPIBaseClass().Send_API_Request(exchange,product,propertyValue,"PUT");
			Double strikepriceput = Double.valueOf(String.valueOf(Parallel_BaseClass.getAPIBaseClass().getJsonParameter("$.strikePrice[0]")));
			data.setParam(exchange+"_"+product, 3, strikepriceput.doubleValue());
			ReportFactory.PassTest("INFO :: CALL Strike Price  : "+strikepriceput.doubleValue());
		}
		data.closeWorkbook();
		//ReportFactory.PassTest("INFO :: property {{"+propertName+"}} : "+propertyValue);
	}
}
