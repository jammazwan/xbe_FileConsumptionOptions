package jammazwan.xbe;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class HAntIncludeFileMatchRecursiveDepthTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			/*
			 * note that going recursive with antInclude requires both
			 * recursive=true & antInclude starting with double asterisk
			 */
			public void configure() {
				from("file://src/test/resources/data/more/?noop=true&recursive=true&antInclude=**/more*.txt")
						.to("mock:result");
			}
		};
	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedMessageCount(3);
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
