package com.rest.util.http;

import java.nio.charset.Charset;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;

/**
 * 
 * 
 * 
 */

public class ContentTypeUtil {
	
	public static final ContentType APPLICATION_ATOM_XML = ContentType.create("application/atom+xml", Consts.UTF_8);
    public static final ContentType APPLICATION_FORM_URLENCODED = ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8);
    public static final ContentType APPLICATION_JSON = ContentType.create("application/json", Consts.UTF_8);
    public static final ContentType APPLICATION_OCTET_STREAM = ContentType.create("application/octet-stream", (Charset) null);
    public static final ContentType APPLICATION_SVG_XML = ContentType.create("application/svg+xml", Consts.UTF_8);
    public static final ContentType APPLICATION_XHTML_XML = ContentType.create("application/xhtml+xml", Consts.UTF_8);
    public static final ContentType APPLICATION_XML = ContentType.create("application/xml", Consts.UTF_8);
    public static final ContentType MULTIPART_FORM_DATA = ContentType.create("multipart/form-data", Consts.UTF_8);
    public static final ContentType TEXT_HTML = ContentType.create("text/html", Consts.UTF_8);
    public static final ContentType TEXT_PLAIN = ContentType.create("text/plain", Consts.UTF_8);
    public static final ContentType TEXT_XML = ContentType.create("text/xml", Consts.UTF_8);
    public static final ContentType WILDCARD = ContentType.create("*/*", (Charset) null);
    
}
