package jammazwan.xbe;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class NDoneWithSpecificFileSpecificDirectoryTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("file://src/test/resources/data/more/deeper/deepest/?noop=true&fileName=moreDeepest.txt&doneFileName=done").to("mock:result");
				from("file://src/test/resources/data/more/deeper/?noop=true&fileName=moreDeeper.txt&doneFileName=done").to("mock:noresult");
			}
		};
	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived("something");
		resultEndpoint.assertIsSatisfied();
		noResultEndpoint.expectedMessageCount(1);;
		noResultEndpoint.assertIsNotSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@EndpointInject(uri = "mock:noresult")
	protected MockEndpoint noResultEndpoint;

}
