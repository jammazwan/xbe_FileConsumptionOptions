/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jammazwan.xbe;

import java.io.File;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class PPreMoveFileTest extends CamelTestSupport {

	/*
	 * Almost all of this code was lifted shamelessly from
	 * FileConsumerBeginAndCommitExpressionRenameStrategyTest in camel core
	 * 
	 * All credit to them for it's authorship
	 */

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("file://target/begin?preMove=../inprogress/${file:name.noext}.bak&move=../completed/${file:name}&consumer.delay=5000")
						.process(new Processor() {
							@SuppressWarnings("unchecked")
							public void process(Exchange exchange) throws Exception {
								GenericFile<File> file = (GenericFile<File>) exchange
										.getProperty(FileComponent.FILE_EXCHANGE_FILE);
								assertNotNull(file);
								assertTrue(file.getRelativeFilePath().indexOf("inprogress") > -1);
							}
						}).to("mock:result");
			}
		};
	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodiesReceived("file content here!");
		resultEndpoint.expectedFileExists("target/completed/myfile.bak", "file content here!");

		template.sendBodyAndHeader("file content here!", Exchange.FILE_NAME, "myfile.txt");

		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "file:target/begin")
	protected ProducerTemplate template;

	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/inprogress");
		deleteDirectory("target/completed");
		deleteDirectory("target/begin");
		super.setUp();
	}
}
