package ut.com.oneandone.sourcecenter.confluence.webvideo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockitoAnnotations.Mock;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import com.oneandone.sourcecenter.confluence.webvideo.macro.WebVideo;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class WebVideoUnitTest
{
	private String[] inputSources = {"width","height","autoplay","muted","loop","preview","videolink"};
	@ComponentImport
	PageBuilderService pageBuilderService;
	int width,height;
	String testURL ="https://united-internet.org/intube/media/marbecker/ekHl_o5HAXA-b2qHAF5m3G.mp4";

	@Mock
	ConversionContext conversionContext;
    @Test
    public void testMyName()
    {
    	WebVideo component = new WebVideo(null);
        assertEquals("names do not match!", "WebVideo Macro",component.getName());
    }
    
    @Test
    public void checkExecute()
    {
    	//WebVideo component = new WebVideo(pageBuilderService);
    	int runs = 50;
    	for(int i = 0; i < runs; i++) {
    		Map<String,String> map = this.createRandomMap();
    		System.out.println("Test Case: " + map.toString());
    		Assert.assertTrue(this.run(map)); //Activate for testing and pageBuilderService must be comment out in execute method.
    		System.out.println("Passed");
    	}
    	
        //assertEquals("names do not match!", "1&1 InTube Macro",component.execute(new Map(), s, conversionContext)(""));
    }
    
    @Test
    public void testURLs() {
    	WebVideo component = new WebVideo(pageBuilderService);
    	Map<String,String> map = this.createRandomMap();
        String test="";
        try {
			test = component.execute(map, "", conversionContext);
		} catch (MacroExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			
		}
      System.out.println(testURL.substring(testURL.lastIndexOf('.')+1,testURL.length()));
        Assert.assertTrue(test.contains(testURL));
        Assert.assertTrue(test.contains("type=\"video/" + testURL.substring(testURL.lastIndexOf('.')+1,testURL.length())+"\""));
    }
    
    /**
     * Create Random valid values to a Map
     * @return the Map
     */
    private Map<String,String> createRandomMap() {
    	width = (int)(Math.random()*1000);
    	height = (int)(Math.random()*1000);
    	String autoplay = "" + this.randomBoolean();
    	String muted = "" + this.randomBoolean();
    	String loop = "" + this.randomBoolean();
    	String preview = ".jpg";
    	String videolink = testURL;
/**--------------------------------------------------------**/
    	String[] param = {"" + width,"" + height, autoplay, muted, loop, preview, videolink};
    	Map<String,String> map = new HashMap<String,String>();
    	for(int i = 0; i < param.length; i++) {
    		map.put(inputSources[i], param[i]);
    	}
    	return map;
    }
    
    private boolean run(Map<String,String> map) {
    	WebVideo component = new WebVideo(pageBuilderService);
    	String test = "";
    	try {
			test = component.execute(map, "", conversionContext);
		} catch (MacroExecutionException e) {
			// TODO Auto-generated catch block
			System.err.println("MacroExecutionException");
			System.out.println(test);
			e.printStackTrace();
			return false;
		}
    	catch (NullPointerException e) {
			// TODO Auto-generated catch block
    		System.err.println("NullpointerException");
    		System.out.println(map.toString());
			System.out.println(test);
			System.out.println("Attention: The pageBuilderService must be comment out in execute method");
			e.printStackTrace();
			return false;
		}
    	for(int i = 0; i < 5; i++) {
    		if(!component.isEmpty(map.get(inputSources[i]))&&!map.get(inputSources[i]).equals("false")) {
    			if(test.contains(inputSources[i])) {
    				
    			} else if(i==0&&map.get("height")==null){
    				
    			} else if (width < 50 || height < 50){
    			} else {
    				System.out.println(inputSources[i] + " was not found in tag.");
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
   private boolean randomBoolean() {
	   return Math.random() < 0.5;
   }
}
