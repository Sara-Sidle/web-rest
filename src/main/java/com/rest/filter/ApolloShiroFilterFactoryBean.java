package com.rest.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.apache.shiro.mgt.SecurityManager;

public class ApolloShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    private static transient final Logger log = LoggerFactory.getLogger(ShiroFilterFactoryBean.class);

    private List<String> filterChainExcludeList;

    public ApolloShiroFilterFactoryBean() {
        super();
        this.filterChainExcludeList = new ArrayList<String>();
    }

    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        log.debug("Creating Shiro Filter instance.");
        SecurityManager securityManager =getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }

        FilterChainManager manager = createFilterChainManager();

        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        return new ApolloSpringShiroFilter((WebSecurityManager) securityManager, chainResolver, this.filterChainExcludeList);
    }

    public void setFilterChainExcludeList(List<String> filterChainExcludeList) {
        this.filterChainExcludeList = filterChainExcludeList;
    }

    private static final class ApolloSpringShiroFilter extends AbstractShiroFilter {

        private PatternMatcher pathMatcher;
        private List<String> filterChainExcludeList;

        protected ApolloSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver, List<String> filterChainExcludeList) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
            this.pathMatcher = new AntPathMatcher();
            this.filterChainExcludeList = filterChainExcludeList;
        }

        @Override
        protected void doFilterInternal(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws ServletException, IOException {
            String requestURI = WebUtils.getPathWithinApplication(WebUtils.toHttp(servletRequest));

            for (String exclude : this.filterChainExcludeList) {
                if (pathMatcher.matches(exclude, requestURI)) {
                    executeChain(servletRequest, servletResponse, chain);
                    return;
                }
            }

            super.doFilterInternal(servletRequest, servletResponse, chain);
        }
    }
}
