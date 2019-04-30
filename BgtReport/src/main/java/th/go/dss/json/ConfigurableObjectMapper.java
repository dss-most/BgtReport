package th.go.dss.json;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.ObjectMapper;

public class ConfigurableObjectMapper extends ObjectMapper
{
    public ConfigurableObjectMapper()
    {
    	this.enableDefaultTyping(DefaultTyping.JAVA_LANG_OBJECT);
        //this.enableDefaultTypingAsProperty(DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.Id.CLASS.getDefaultPropertyName());
    }
}
