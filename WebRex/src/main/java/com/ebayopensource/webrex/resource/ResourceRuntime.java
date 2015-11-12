/*
    Copyright [2015-2016] eBay Software Foundation

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.ebayopensource.webrex.resource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.ebayopensource.webrex.resource.api.IResourceRuntimeConfig;

public enum ResourceRuntime {
   INSTANCE;

   IResourceRuntimeConfig m_config;

   private ResourceRuntime() {
   }

   @Deprecated
   public Collection<IResourceRuntimeConfig> getAllConfig() {
      if (m_config == null) {
         return Collections.emptyList();
      } else {
         return Arrays.asList(m_config);
      }
   }

   @Deprecated
   public IResourceRuntimeConfig getConfig(String contextPath) {
      return getConfig();
   }

   @Deprecated
   public boolean hasConfig(String contextPath) {
      return m_config != null;
   }

   @Deprecated
   public void removeConfig(String contextPath) {
      reset();
   }

   public IResourceRuntimeConfig getConfig() {
      if (m_config == null) {
         throw new ResourceException(ResourceErrConstants.CONFIG_NOT_SET,
               "Resource runtime config has not been set!");
      }

      return m_config;
   }

   public void reset() {
      m_config = null;
   }

   IResourceRuntimeConfig setConfig(IResourceRuntimeConfig config) {
      if (m_config != null) {
         String message = "Resource runtime config has already registered!";
         throw new RuntimeException(message);
      }
      m_config = config;

      return config;
   }
}
