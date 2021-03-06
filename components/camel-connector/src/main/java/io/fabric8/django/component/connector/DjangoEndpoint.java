/**
 * Copyright 2005-2015 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package io.fabric8.django.component.connector;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.DelegateEndpoint;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.api.management.ManagedAttribute;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.util.ServiceHelper;

@ManagedResource(description = "Managed Connector Endpoint")
public class DjangoEndpoint extends DefaultEndpoint implements DelegateEndpoint {

    private final Endpoint endpoint;

    public DjangoEndpoint(String endpointUri, Component component, Endpoint endpoint) {
        super(endpointUri, component);
        this.endpoint = endpoint;
    }

    @Override
    public Producer createProducer() throws Exception {
        return endpoint.createProducer();
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return endpoint.createConsumer(processor);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @ManagedAttribute(description = "Delegate Endpoint URI", mask = true)
    public String getDelegateEndpointUri() {
        return endpoint.getEndpointUri();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        ServiceHelper.startService(endpoint);
    }

    @Override
    protected void doStop() throws Exception {
        ServiceHelper.stopService(endpoint);
        super.doStop();
    }
}
