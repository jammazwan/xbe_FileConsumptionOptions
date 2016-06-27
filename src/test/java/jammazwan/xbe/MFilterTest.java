package jammazwan.xbe;

import java.io.File;

import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MFilterTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("file://src/test/resources/data/?noop=true&recursive=true&filter=#deepfilter").to("mock:result");
			}
		};
	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedMessageCount(4); 
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Override
	protected JndiRegistry createRegistry() throws Exception {
		JndiRegistry jndi = super.createRegistry();
		DeepFolderFilter<File> deepfilter = new DeepFolderFilter<File>();
		jndi.bind("deepfilter", deepfilter);
		return jndi;
	}

	// from http://camel.apache.org/file2.html, and modified
	public class DeepFolderFilter<T> implements GenericFileFilter<T> {

		public boolean accept(GenericFile<T> file) {
			if (file.isDirectory() && file.getFileName().contains("/deep")) {
				return false;
			}

			return true;
		}

	}
}
