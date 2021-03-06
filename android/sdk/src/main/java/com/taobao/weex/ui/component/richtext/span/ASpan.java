/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.taobao.weex.ui.component.richtext.span;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.component.richtext.node.RichTextNode;
import com.taobao.weex.utils.ATagUtil;
import com.taobao.weex.utils.WXDataStructureUtil;

import java.util.Map;

public class ASpan extends ClickableSpan {

  private String mInstanceId, mURL;
  private final String mComponentRef;
  private final String mPseudoRef;

  public ASpan(String instanceId, String url,String componentRef, String pseudoRef) {
    mInstanceId = instanceId;
    mURL = url;
    mComponentRef = componentRef;
    mPseudoRef = pseudoRef;
  }
  public ASpan(String instanceId, String url) {
    this.mInstanceId = instanceId;
    this.mURL = url;
    this.mComponentRef = "";
    this.mPseudoRef = "";
  }

  @Override
  public void onClick(View widget) {
    if (mURL.equals(RichTextNode.CLICK_HREF) && !(mPseudoRef.isEmpty()) &&!(mComponentRef.isEmpty())) {
      WXSDKInstance instance = WXSDKManager.getInstance().getSDKInstance(mInstanceId);
      if (instance != null && !instance.isDestroy()) {
        Map<String, Object> param = WXDataStructureUtil.newHashMapWithExpectedSize(1);
        param.put(RichTextNode.PSEUDO_REF, mPseudoRef);
        instance.fireEvent(mComponentRef, RichTextNode.ITEM_CLICK, param);
      }
    } else {
      ATagUtil.onClick(widget, mInstanceId, mURL);
    }
  }

  /**
   Override super method and do nothing. As no default color or text-decoration is needed.
   */
  @Override
  public void updateDrawState(TextPaint ds) {

  }
}
