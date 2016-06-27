### FileConsumptionOptions NOTES:

This project takes a departure from the normal approach for other x__projects. Spring was entirely removed as a dependency, and each test stands alone, not requiring any other artifacts - each contains it's own RouteBuilder.

See (this Camel doc)[http://camel.apache.org/testing] for a sample of where this testing style came from.

_The motivation for this approach?_ Each test needed it's own route, many tests were required, and doing this in the same way as other x__project tests with Spring and separate RouterBuilder files could be quite confusing.

In another break with convention, I placed the route at top, and test below it, as the route is the main interesting point of the test. It looks just "wrong". But it is easy to read.

### Test Naming Conventions

ASpecificDirectoryAnyFileTest - example.

 * First charachter A-Z to order tests easiest to hardest
 * Then CamelCase test function description
 * Then Test

### 


