package jammazwan.xbe;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class BSpecificFileSpecificDirectoryTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("file://src/test/resources/data/?noop=true&fileName=something.txt").to("mock:result");
			}
		};
	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived("something");
		resultEndpoint.assertIsSatisfied();
	}
	
	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
