/*
 * Copyright 2014 ptma@163.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xqsight.common.freemarker;

import com.xqsight.common.exception.TemplateEngineException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Map;

public class FreeMarkerImpl implements TemplateEngine {

    protected Logger logger = LogManager.getLogger(FreeMarkerImpl.class);

    private static final String DEFAULT_ENCODING = "UTF-8";

    private Configuration config;

    private String classPath;

    private  Version version = new Version(2, 3, 23);

    public FreeMarkerImpl(String classPath) {
        this.classPath = classPath;
        initConfiguration();
    }

    public void initConfiguration() {
        try {
            config = new Configuration(version);
            config.setDirectoryForTemplateLoading(new File(classPath));
            config.setObjectWrapper(new DefaultObjectWrapper(version));

            config.setSetting("classic_compatible", "true");
            config.setSetting("whitespace_stripping", "true");
            config.setSetting("template_update_delay", "1");
            config.setSetting("locale", "zh_CN");
            config.setSetting("default_encoding", DEFAULT_ENCODING);
            config.setSetting("url_escaping_charset", DEFAULT_ENCODING);
            config.setSetting("datetime_format", "yyyy-MM-dd hh:mm:ss");
            config.setSetting("date_format", "yyyy-MM-dd");
            config.setSetting("time_format", "HH:mm:ss");
            config.setSetting("number_format", "0.######;");
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    @Override
    public String processToString(Map<String, Object> model, String stringTemplate) throws TemplateEngineException {
        logger.debug("model:{}", model);
        try {
            Configuration cfg = new Configuration(version);
            cfg.setTemplateLoader(new StringTemplateLoader(stringTemplate));
            cfg.setDefaultEncoding(DEFAULT_ENCODING);

            Template template = cfg.getTemplate("");
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new TemplateEngineException(e);
        }
    }

    @Override
    public void processToFile(Map<String, Object> model, TemplateElement templateElement) throws TemplateEngineException {
        logger.debug("model:{}", model);
        try {
            Template template = config.getTemplate(templateElement.getTemplateFile(), templateElement.getEncoding());
            String targetPath = PathSupport.packagePathToFilePath(processToString(model, templateElement.getTargetPath()));
            String targetFileName = processToString(model, templateElement.getTargetFileName());
            File file = new File(targetPath + File.separator + targetFileName);
            File directory = new File(targetPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
                    templateElement.getEncoding()));
            template.process(model, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new TemplateEngineException(e);
        }
    }
}
