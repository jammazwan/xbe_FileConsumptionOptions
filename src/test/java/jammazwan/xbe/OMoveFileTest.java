package jammazwan.xbe;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class OMoveFileTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("file://target/generated/tomove/?move=.done").to("mock:result");
			}
		};
	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		template.sendBodyAndHeader("file://target/generated/tomove", "meaningless value", Exchange.FILE_NAME,
				"deleteme.txt");
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedFileExists("target/generated/tomove/.done/deleteme.txt");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce
	protected ProducerTemplate template;

	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/generated");
		super.setUp();
	}

}
