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

### ASpecificDirectoryAnyFileTest

* Consumes a file
* Verifies consumption

### BSpecificFileSpecificDirectoryTest

* Consumes a file in a specific directory, with a specific name
* Verifies consumption

### CAnyFileAnyDirectoryTest

 * Recursively walks directories and consumes every file it finds
 * Verifies count

### DSpecificFileAnyDirectoryTest

 * Recursively walks the directories and consumes any file of a specific name
 * Verifies count
 
### EAnyFile1DirectoryDeepTest

 * Recursively walks the directories 1 deep, and consume any file it finds
 * Verifies count
 
### FSpecificFile3DirectoriesDeepTest

 * Recursively walks the directories 3 deep, and consume any file it finds
 * Verifies count
 
### GAntIncludeFileMatchSingleDepthTest
 
 * Uses antInclude to do an ant-like file match on a name, in a single directory
 * Verifies count
 
### HAntIncludeFileMatchRecursiveDepthTest

This test demonstrated some unexpected syntax for me.

* Recursively walk the directories, using an ant-like match on a name.
* Verifies count

### IAntIncludeExcludeFileMatchRecursiveDepthTest

 * Recursively walk directories using both antInclude and antExclude in combination
 * Verify count
 
### JIncludeFileMatchSingleDepthTest

 * Uses a regex include on file name to consume a file in a single directory
 * Verifies count
 
### KIncludeFileMatchRecursiveDepthTest

 * Uses a regex include on file name to consume a directories recursively
 * Verifies count
 
### LIncludeExcludeFileMatchRecursiveDepthTest

 * Uses regex include and exclude on a file name, to consume directories recursively
 * Verifies count
 
### MFilterTest

This example uses a filter class, by implementing the GenericFileFilter interface.

 * Uses filter to consume a specific file name pattern on recursive directories
 * Filters on a directory, not the file name
 * Verifies count
 
### NDoneWithSpecificFileSpecificDirectoryTest

 * Consumes file using "done" requirement, successfully.
 * Attempts to consume file using "done" requirement, when it is known that requirement is not met.
 * Verifies proper counts, in both respects.
 
### OMoveFileTest

 * A file is produced and dropped into a folder which consumes that file.
 * Because the from route in that folder is move=.done, the file is not deleted, but instead moved into a .done folder
 * Verifies that the file was moved into the .done folder as directed
 
### PPreMoveFileTest

_This code was shamelessly stolen from camel-core FileConsumerBeginAndCommitExpressionRenameStrategyTest_

 * A file is moved into the begin directory
 * This route is set up for a preMove, which uses simple EL to name and direct the file into a temporary folder along the way
 * Counts, inbetween existence in the preMove folder, and final placement are verified.
 



