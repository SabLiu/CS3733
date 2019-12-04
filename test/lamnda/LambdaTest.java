package lamnda;

import static org.junit.Assert.fail;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.amazonaws.services.lambda.runtime.Context;

public class LambdaTest {
	
	/**
	 * Helper method that creates a context that supports logging so you can test lambda functions
	 * in JUnit without worrying about the logger anymore.
	 * 
	 * @param apiCall      An arbitrary string to identify which API is being called.
	 * @return
	 */
	protected Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
	
	protected static String getEncodedValue(String filePath){
		String encoded = "";
		try {
	        byte[] content = Files.readAllBytes(Paths.get(filePath));
	        encoded =  java.util.Base64.getEncoder().encodeToString(content);
		}catch(Exception e){
			System.out.println("EXCPETION: " + e.getMessage());
			e.printStackTrace();
			fail("EXCPETION: " + e.getMessage());
        }
        return encoded;
	}
	

}
