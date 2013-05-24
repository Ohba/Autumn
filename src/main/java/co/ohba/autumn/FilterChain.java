package co.ohba.autumn;

import com.google.inject.Key;
import lombok.Data;
import org.apache.shiro.web.servlet.AbstractFilter;

@Data
public class FilterChain {

    public FilterChain(String pattern, Key<? extends AbstractFilter> ... keys){
        this.pattern = pattern;
        this.keys = keys;
    }

    private String pattern;

    private Key<? extends AbstractFilter>[] keys;

    public void setKeys(Key<? extends AbstractFilter> ... keys){
       this.keys = keys;
    }

}
