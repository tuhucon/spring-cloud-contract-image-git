package org.springframework.cloud.contract.verifier.tests;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;

public class ContractVerifierTest {

	@Test
	public void validate_match_api_with_normal_case() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.body("{\"pids\":[\"adki46\",\"adki47\",\"bhckh500\"],\"values\":[{\"type\":\"main\",\"path\":\"/x/y/image1.jpg\"},{\"type\":\"thumb_1\",\"path\":\"/x/y/image2.jpg\"}]}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/v1/objects/match");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array("['pids']").contains("['oid']").isEqualTo("adki47");
			assertThatJson(parsedJson).array("['pids']").contains("['oid']").isEqualTo("adki46");
			assertThatJson(parsedJson).array("['pids']").contains("['score']").isEqualTo(0.8);
			assertThatJson(parsedJson).array("['pids']").contains("['score']").isEqualTo(0.75);
	}

}
