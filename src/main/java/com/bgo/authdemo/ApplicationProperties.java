/*
 * Copyright 2012-2017 the original author or authors.
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

package com.bgo.authdemo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * Application level properties handling. Best practice in Spring applications.
 */
@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class ApplicationProperties {

//app:
//	enable-ssl: true
//	stage: default
//	azdata.url: https://pcss-dev.ac-np.swissre.com/azinfo/api/v1
//	containerName: ${CONTAINER_NAME:local}
//	kibanaHost: http://applogs-np.swissre.com
//	kibanaPath: "/app/kibana#/discover?_a=(columns:!(severity,userId,message),\

    private boolean enableSsl = false;

    private String stage;

    private String azdataUrl;

    private String containerName;

    private String kibanaHosts;

    private String kibanaPath;

    @NotEmpty
    private String pingFederateKeyUrl;

    private String tokenTranslatorUrl;

}
